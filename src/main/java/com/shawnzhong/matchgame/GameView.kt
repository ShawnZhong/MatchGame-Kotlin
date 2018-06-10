package com.shawnzhong.matchgame

import android.content.Context
import android.util.AttributeSet
import android.widget.GridLayout

/**
 * Created by Shawn on 2017/6/5.
 * It's the main view of the game.
 */

class GameView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : GridLayout(context, attrs, defStyleAttr) {
    var prevCard: Card? = null
    private var cardCount = 0
    private lateinit var cardsMap: Array<Card>

    fun initGameView(row: Int = 5, column: Int = 4) {
        gameView = this
        cardCount = column * row
        removeAllViews()
        columnCount = column
        post {
            cardsMap = Array(cardCount) { Card(context) }

            val cardSize = width / column
            cardsMap.forEach { addView(it, cardSize, cardSize) }

            shuffle()
        }
    }

    fun startGame() {
        prevCard = null
        shuffle()
        setAllStatus(CardStatus.OPEN)
        postDelayed({ setAllStatus(CardStatus.CLOSED) }, cardCount * 100L)
    }

    private fun setAllStatus(status: CardStatus) = cardsMap.forEach { it.status = status }

    private fun shuffle() {
        val list = MutableList(cardCount) { it }
        for (i in 1..cardCount / 2)
            repeat(2) { cardsMap[list.removeAt((Math.random() * list.size).toInt())].num = i }
    }

    fun checkEnd() = cardsMap.all { it.status == CardStatus.PAIRED }

    companion object {
        var gameView: GameView? = null
            private set
    }
}


