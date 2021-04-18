package com.example.moonlovers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.moonlovers.databinding.ActivityMainBinding
import com.example.moonlovers.model.OverviewViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel: OverviewViewModel by lazy {
        ViewModelProvider(this).get(OverviewViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setContentView(binding.root)
        setSupportActionBar(binding.mainToolbar)

        viewModel.getMoonAgeProperties()
        setTodayText()

        createNotificationChannel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getMoonAgeProperties()
        setTodayText()
    }

    private fun setTodayText() {
        binding?.let {
            val current = LocalDateTime.now()
            val formatter =  DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm")
            val formattedText = current.format(formatter)

            binding.today.text = formattedText
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                getString(R.string.fcm_channel_id),
                getString(R.string.fcm_channel_name),
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                setShowBadge(false)
                description = getString(R.string.fcm_channel_description)
            }

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
}