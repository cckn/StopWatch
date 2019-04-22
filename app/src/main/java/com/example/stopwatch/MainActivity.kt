package com.example.stopwatch

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.timer


class MainActivity : AppCompatActivity() {

    private var time = 0
    private var timerTask: Timer? = null

    private var isRunning = false

    private var lap = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startButton.setOnClickListener() {
            isRunning = !isRunning
            if (isRunning)
                start()
            else
                pause()
        }

        lapTimeButton.setOnClickListener() {
            recordLapTime()
        }

        resetButton.setOnClickListener {
            reset()
        }
    }


    private fun start(): Unit {
        startButton.setImageResource(R.drawable.ic_pause_black_24dp)
        timerTask = timer(period = 10) {
            time++

            val sec = time / 100
            val milli = time % 100
            runOnUiThread {
                secTextView.text = "$sec"
                milliTextView.text = "$milli"
            }
        }
    }

    private fun pause(): Unit {
        startButton.setImageResource(R.drawable.ic_play_arrow_black_24dp)
        timerTask?.cancel()
    }

    private fun recordLapTime(): Unit {
        val lapTime = this.time
        val textView = TextView(this)
        textView.text = "$lap LAP : ${lapTime / 100}.${lapTime % 100}"

        lapLayout.addView(textView, 0)
        lap++
    }

    private fun reset(): Unit {

        timerTask?.cancel()
        time = 0
        isRunning = false
        startButton.setImageResource(R.drawable.ic_play_arrow_black_24dp)
        secTextView.text = "0"
        milliTextView.text = "00"

        lapLayout.removeAllViewsInLayout()
        lap = 1
        /* TODO: Reset Button을 눌렀을 때 표시되는 시간이 0으로 초기화 되지 않는 버그가 존재(약 30%확률) */
    }

}

