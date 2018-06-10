package com.shawnzhong.matchgame
import android.app.AlertDialog
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.os.Handler
import android.view.Gravity
import android.view.MotionEvent
import android.widget.FrameLayout
import android.widget.TextView

/**
 * Created by Shawn on 2017/6/5.
 * Card
 */

enum class CardStatus {
    CLOSED, OPEN, PAIRED
}

class Card(context: Context) : FrameLayout(context) {

    private val label = TextView(context)
    private val shape = GradientDrawable()

    var num = 0
    var status = CardStatus.CLOSED
        set(status) {
            field = status
            when (status) {
                CardStatus.OPEN -> {
                    label.text = "$num"
                    when (num) {
                        1 -> shape.setColor(0xfff2995a.toInt())
                        2 -> shape.setColor(0xfff2e55a.toInt())
                        3 -> shape.setColor(0xffa6f25a.toInt())
                        4 -> shape.setColor(0xff5af2bf.toInt())
                        5 -> shape.setColor(0xff5a8df2.toInt())
                        6 -> shape.setColor(0xff995af2.toInt())
                        7 -> shape.setColor(0xfff25ad9.toInt())
                        8 -> shape.setColor(0xfff25a5a.toInt())
                        9 -> shape.setColor(0xffc20078.toInt())
                        10 -> shape.setColor(0xffff81c0.toInt())
                    }
                }
                CardStatus.PAIRED -> {
                    label.text = "$num"
                    shape.setColor(0xffcccccc.toInt())
                }
                CardStatus.CLOSED -> {
                    label.text = ""
                    shape.setColor(0xff204056.toInt())
                }
            }
            label.background = shape
        }

    init {
        label.gravity = Gravity.CENTER
        label.setTextColor(0xffffffff.toInt())
        shape.cornerRadius = 12f
        status = CardStatus.CLOSED

        val lp = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        lp.setMargins(10, 10, 10, 10)
        addView(label, lp)

        setOnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_UP)
                view.performClick()
            true
        }

        post { label.textSize = height / 6f }
    }

    override fun performClick(): Boolean {
        super.performClick()

        val gameView = GameView.gameView!!
        val prevCard = gameView.prevCard

        if (status != CardStatus.PAIRED && prevCard !== this) {
            status = CardStatus.OPEN
            when {
                prevCard == null -> gameView.prevCard = this
                prevCard.equals(this) -> {
                    status = CardStatus.PAIRED
                    prevCard.status = CardStatus.PAIRED
                    gameView.prevCard = null
                }
                else -> {
                    Handler().postDelayed({
                        status = CardStatus.CLOSED
                        prevCard.status = CardStatus.CLOSED
                    }, 300)
                    gameView.prevCard = null
                }
            }
        }

        if (gameView.checkEnd())
            AlertDialog.Builder(context).setTitle("WOW!").setMessage("You Win!").setPositiveButton("Restart") { _, _ -> gameView.startGame() }.show()

        return true
    }

    private fun equals(other: Card) = (num == other.num)

}
