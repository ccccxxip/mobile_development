import csv
import json
import re
import sys
from datetime import datetime

CSV_FILE = sys.argv[1] if len(sys.argv) > 1 else 'csv_to_json/googleplaystore.csv'
JSON_FILE = sys.argv[2] if len(sys.argv) > 2 else 'googleplaystore.json'

CATEGORIES_RU = {
    'ART_AND_DESIGN': 'Искусство и дизайн',
    'AUTO_AND_VEHICLES': 'Авто и транспорт',
    'BEAUTY': 'Красота',
    'BOOKS_AND_REFERENCE': 'Книги и справочники',
    'BUSINESS': 'Бизнес',
    'COMICS': 'Комиксы',
    'COMMUNICATION': 'Связь и общение',
    'DATING': 'Знакомства',
    'EDUCATION': 'Образование',
    'ENTERTAINMENT': 'Развлечения',
    'EVENTS': 'События',
    'FAMILY': 'Семья',
    'FINANCE': 'Финансы',
    'FOOD_AND_DRINK': 'Еда и напитки',
    'GAME': 'Игры',
    'HEALTH_AND_FITNESS': 'Здоровье и фитнес',
    'HOUSE_AND_HOME': 'Дом и интерьер',
    'LIBRARIES_AND_DEMO': 'Библиотеки и демо',
    'LIFESTYLE': 'Образ жизни',
    'MAPS_AND_NAVIGATION': 'Карты и навигация',
    'MEDICAL': 'Медицина',
    'NEWS_AND_MAGAZINES': 'Новости и журналы',
    'PARENTING': 'Родителям',
    'PERSONALIZATION': 'Персонализация',
    'PHOTOGRAPHY': 'Фотография',
    'PRODUCTIVITY': 'Продуктивность',
    'SHOPPING': 'Покупки',
    'SOCIAL': 'Социальные сети',
    'SPORTS': 'Спорт',
    'TOOLS': 'Инструменты',
    'TRAVEL_AND_LOCAL': 'Путешествия и местное',
    'VIDEO_PLAYERS': 'Видеоплееры и редакторы',
    'WEATHER': 'Погода',
}

ANDROID_API = {
    '1.0': 1, '1.1': 2, '1.5': 3, '1.6': 4, '2.0': 5, '2.0.1': 6, '2.1': 7,
    '2.2': 8, '2.3': 9, '2.3.3': 10, '3.0': 11, '3.1': 12, '3.2': 13,
    '4.0': 14, '4.0.3': 15, '4.1': 16, '4.2': 17, '4.3': 18, '4.4': 19,
    '4.4W': 20, '5.0': 21, '5.1': 22, '6.0': 23, '7.0': 24, '7.1': 25,
    '8.0': 26, '8.1': 27, '9': 28,
}

def parse_min_api(text):
    match = re.match(r'(\d+(?:\.\d+)*W?)', text.strip())
    if not match:
        return None
    return ANDROID_API.get(match.group(1))

def parse_installs(text):
    digits = re.sub(r'\D', '', text)
    return int(digits) if digits else None

def parse_price(text):
    value = text.strip().replace('$', '').replace(',', '')
    try:
        return float(value) > 0
    except ValueError:
        return False

def parse_date(text):
    try:
        return datetime.strptime(text.strip(), '%B %d, %Y').date().isoformat()
    except ValueError:
        return None

def parse_size_mb(text):
    match = re.fullmatch(r'([\d.]+)([Mk])', text.strip())
    if not match:
        return None
    number = float(match.group(1))
    return round(number if match.group(2) == 'M' else number / 1024, 2)

def parse_rating(text):
    if text.strip() in ('', 'NaN'):
        return None
    return float(text)

def parse_reviews(text):
    return int(text) if text.isdigit() else 0

def clean_text(text):
    text = text.strip()
    return None if text in ('', 'NaN') else text

rows = []
skipped_broken = 0
with open(CSV_FILE, encoding='utf-8', errors='replace', newline='') as f:
    for row in csv.DictReader(f):
        if row.get('Category') not in CATEGORIES_RU:
            skipped_broken += 1
            continue

        rows.append({
            'app': row.get('App', '').strip(),
            'category': row['Category'],
            'rating': parse_rating(row.get('Rating', '')),
            'reviews': parse_reviews(row.get('Reviews', '0')),
            'size_mb': parse_size_mb(row.get('Size', '')),
            'installs': parse_installs(row.get('Installs', '')),
            'is_paid': parse_price(row.get('Price', '')),
            'content_rating': clean_text(row.get('Content Rating', '')),
            'genres': [g.strip() for g in row.get('Genres', '').split(';') if g.strip()],
            'last_updated': parse_date(row.get('Last Updated', '')),
            'current_ver': clean_text(row.get('Current Ver', '')),
            'min_api': parse_min_api(row.get('Android Ver', '')),
        })

unique = {}
for item in rows:
    key = (item['app'], item['category'])
    if key not in unique or item['reviews'] > unique[key]['reviews']:
        unique[key] = item
apps = list(unique.values())

groups = {}
for item in apps:
    groups.setdefault(item['category'], []).append(item)

result = []
for category, items in groups.items():
    items.sort(key=lambda a: (a['installs'] or 0, a['reviews']), reverse=True)
    for item in items:
        del item['category']
    result.append({
        'category': category,
        'category_ru': CATEGORIES_RU[category],
        'count': len(items),
        'apps': items,
    })
result.sort(key=lambda g: g['count'], reverse=True)

with open(JSON_FILE, 'w', encoding='utf-8') as f:
    json.dump(result, f, ensure_ascii=False, indent=2, allow_nan=False)

print(f'Прочитано строк: {len(rows) + skipped_broken}, '
      f'пропущено битых: {skipped_broken}, '
      f'после удаления дубликатов: {len(apps)}')
print(f'Тематик: {len(result)}. Результат записан в {JSON_FILE}')