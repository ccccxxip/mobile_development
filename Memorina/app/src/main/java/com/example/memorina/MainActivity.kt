package com.example.memorina

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val cardResources = intArrayOf(
        R.drawable.card_1,
        R.drawable.card_2,
        R.drawable.card_3,
        R.drawable.card_4,
        R.drawable.card_5,
        R.drawable.card_6,
        R.drawable.card_7,
        R.drawable.card_8
    )

    private val gridCards = mutableListOf<ImageView>()
    private var firstOpenView: ImageView? = null
    private var secondOpenView: ImageView? = null
    private var isBusy = false
    private var matchedPairs = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnRestart = findViewById<Button>(R.id.btnRestart)
        btnRestart.setOnClickListener {
            setupGame()
        }

        setupGame()
    }

    private fun setupGame() {
        val gridLayout = findViewById<GridLayout>(R.id.gridCards)
        gridLayout.removeAllViews()
        gridCards.clear()
        firstOpenView = null
        secondOpenView = null
        isBusy = false
        matchedPairs = 0

        val pairs = mutableListOf<Int>()
        for (resId in cardResources) {
            pairs.add(resId)
            pairs.add(resId)
        }
        pairs.shuffle()

        val size = dpToPx(70)
        val margin = dpToPx(6)

        for (i in 0 until 16) {
            val cardView = ImageView(this)
            val params = GridLayout.LayoutParams()
            params.width = size
            params.height = (size * 1.35).toInt()
            params.setMargins(margin, margin, margin, margin)
            cardView.layoutParams = params

            cardView.setImageResource(R.drawable.card_back)
            cardView.tag = pairs[i]
            cardView.visibility = View.VISIBLE
            cardView.setOnClickListener(colorListener)

            gridLayout.addView(cardView)
            gridCards.add(cardView)
        }
    }

    private val colorListener = View.OnClickListener { view ->
        if (isBusy) return@OnClickListener
        val card = view as? ImageView ?: return@OnClickListener

        if (card == firstOpenView || card.visibility == View.INVISIBLE) return@OnClickListener

        val faceResId = card.tag as? Int ?: return@OnClickListener
        card.setImageResource(faceResId)

        if (firstOpenView == null) {
            firstOpenView = card
        } else if (secondOpenView == null) {
            secondOpenView = card
            isBusy = true

            val card1 = firstOpenView
            val card2 = secondOpenView
            val firstTag = card1?.tag as? Int
            val secondTag = card2?.tag as? Int

            lifecycleScope.launch(Dispatchers.Main) {
                if (firstTag == secondTag) {
                    delay(400)
                    card1?.visibility = View.INVISIBLE
                    card2?.visibility = View.INVISIBLE
                    matchedPairs++
                    resetSelection()
                    checkWinCondition()
                } else {
                    delay(1000)
                    card1?.setImageResource(R.drawable.card_back)
                    card2?.setImageResource(R.drawable.card_back)
                    resetSelection()
                }
            }
        }
    }

    private fun resetSelection() {
        firstOpenView = null
        secondOpenView = null
        isBusy = false
    }

    private fun checkWinCondition() {
        if (matchedPairs == cardResources.size) {
            Toast.makeText(this, "Поздравляем! Вы нашли все пары и выиграли!", Toast.LENGTH_LONG).show()
        }
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }
}
