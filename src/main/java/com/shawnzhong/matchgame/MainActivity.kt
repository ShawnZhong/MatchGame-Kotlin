package com.shawnzhong.matchgame

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gameView.initGameView()
    }

    fun restart(v: View) = gameView.startGame()

    fun setting(v: View) {
        AlertDialog.Builder(this@MainActivity)
                .setTitle(R.string.settingTitle)
                .setItems(arrayOf("4 × 3", "4 × 4", "5 × 4")) { _, which ->
                    when (which) {
                        0 -> gameView.initGameView(4, 3)
                        1 -> gameView.initGameView(4, 4)
                        2 -> gameView.initGameView(5, 4)
                    }
                }.show()
    }
}
