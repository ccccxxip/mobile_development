package figures
fun main() {
    println("demonstration\n")

    val rect = Rect(x = 1, y = 2, width = 4, height = 2, id = 1)
    val circle = Circle(x = 5, y = 5, radius = 3, id = 2)
    val square = Square(x = 0, y = 0, side = 4, id = 3)

    val figures = listOf(rect, circle, square)

    println("Исходные фигуры")
    figures.forEach { println(it) }

    println("\nПеремещение (move dx = 2, dy = 3)")
    figures.forEach {
        it.move(2, 3)
        println(it)
    }

    println("\nМасштабирование (resize zoom = 2)")
    figures.forEach {
        it.resize(2)
        println(it)
    }

    println("\nПоворот по часовой стрелке вокруг точки (0, 0)")
    figures.forEach {
        it.rotate(RotateDirection.Clockwise, centerX = 0, centerY = 0)
        println(it)
    }

    println("\nПоворот против часовой стрелки вокруг точки (0, 0)")
    figures.forEach {
        it.rotate(RotateDirection.CounterClockwise, centerX = 0, centerY = 0)
        println(it)
    }
}