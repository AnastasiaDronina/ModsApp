package com.dronina.modsapp.ui.launch

import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.dronina.modsapp.R
import com.dronina.modsapp.utils.navigateMainPage
import com.dronina.modsapp.utils.setFullScreen
import kotlinx.android.synthetic.main.activity_launch.*


class LaunchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen()
        setContentView(R.layout.activity_launch)
        showProgress()
    }

    private fun showProgress() {
        progress_bar.progress = 0
        val threeSec = 2300
        object : CountDownTimer(
            threeSec.toLong(), 10
        ) {
            override fun onTick(millisUntilFinished: Long) {
                val finishedSeconds = threeSec - millisUntilFinished
                val total = (finishedSeconds.toFloat() / threeSec.toFloat() * 100.0).toInt()
                progress_bar.progress = total
            }

            override fun onFinish() {
                navigateMainPage()
            }
        }.start()
    }
}