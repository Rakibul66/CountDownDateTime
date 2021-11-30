package com.user.countdowntimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.user.countdowntimer.databinding.ActivityMainBinding
import java.util.*

private val handler = Handler()
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)
        // Update TextView every second
        handler.post(object : Runnable {
            override fun run() {
                // Keep the postDelayed before the updateTime(), so when the event ends, the handler will stop too.
                handler.postDelayed(this, 1000)
                updateTime()
            }
        })
    }

    fun updateTime() {
        // Set Current Date
        val currentDate = Calendar.getInstance()

        // Set Event Date
        val eventDate = Calendar.getInstance()
        eventDate[Calendar.YEAR] = 2022
        eventDate[Calendar.MONTH] = 10 // 0-11 so 1 less
        eventDate[Calendar.DAY_OF_MONTH] = 21
        eventDate[Calendar.HOUR] = 10
        eventDate[Calendar.MINUTE] = 0
        eventDate[Calendar.SECOND] = 0
        eventDate.timeZone = TimeZone.getTimeZone("GMT")


        // Find how many milliseconds until the event
        val diff = eventDate.timeInMillis - currentDate.timeInMillis


        // Change the milliseconds to days, hours, minutes and seconds
        val days = diff / (24 * 60 * 60 * 1000)
        val hours = diff / (1000 * 60 * 60) % 24
        val minutes = diff / (1000 * 60) % 60
        val seconds = (diff / 1000) % 60
// Display Countdown
        binding.countdownText.text = "${days}d ${hours}h ${minutes}m ${seconds}s"

        // Show different text when the event has passed
        endEvent(currentDate, eventDate)
    }

    private fun endEvent(currentdate: Calendar, eventdate: Calendar) {
        if (currentdate.time >= eventdate.time) {
            binding.countdownText.text = "Happy New Year!"
            //Stop Handler
            handler.removeMessages(0)
        }

    }
}