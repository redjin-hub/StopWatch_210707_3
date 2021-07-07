package com.redjin.stopwatch

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.timer


class MainActivity : AppCompatActivity() {

    private lateinit var mAdView : AdView

    private var time = 0
    private var isRunning = false
    private var timerTask: Timer? = null

    private var lap = 1



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MobileAds.initialize(this) {}

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        infoText.setOnClickListener{
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://blog.naver.com/kpupil/222422920253"))
            startActivity(intent)
        }

        resetFab.setOnClickListener {
            reset()
        }

        lapBtn.setOnClickListener {
            recordLapTime()
        }

        fab.setOnClickListener{
            isRunning = !isRunning

            if (isRunning){
                start()
            }else{
                pause()
            }
        }
    }


    private fun pause(){
        fab.setImageResource(R.drawable.ic_baseline_play_arrow_24)
        timerTask?.cancel()
    }

    private fun start(){
        fab.setImageResource(R.drawable.ic_baseline_pause_24)

        timerTask = timer(period = 10){
            time++
            val sec = time / 100
            val milli = time % 100
            runOnUiThread {
                secView.text = "$sec"
                milliview.text = "$milli"
            }
        }
    }

    private fun recordLapTime(){
        val lapTime = this.time
        val textView = TextView(this)
        textView.text = "$lap Lap : ${lapTime / 100}.${lapTime % 100}"

        lapLayout.addView(textView, 0)
        lap++

    }

    private fun reset(){
        timerTask?.cancel()

        time = 0
        isRunning = false
        fab.setImageResource(R.drawable.ic_baseline_play_arrow_24)
        secView.text = "0"
        milliview.text ="00"

        lapLayout.removeAllViews()
        lap = 1
    }


}