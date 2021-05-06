package com.app2641.moonlovers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.OnRebindCallback
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.transition.TransitionManager
import com.app2641.moonlovers.databinding.ActivityMainBinding
import com.app2641.moonlovers.model.OverviewViewModel
import com.google.firebase.messaging.FirebaseMessaging
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

        // ビューの変更時にアニメーションする
        binding.addOnRebindCallback(object : OnRebindCallback<ViewDataBinding>() {
            override fun onPreBind(binding: ViewDataBinding?): Boolean {
                TransitionManager.beginDelayedTransition(binding?.root as ViewGroup)
                return super.onPreBind(binding)
            }
        })

        setContentView(binding.root)
        setSupportActionBar(binding.mainToolbar)

        viewModel.getMoonAgeProperties()
        setTodayText()

        createNotificationChannel()
        subscribeTopic()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getMoonAgeProperties()
        setTodayText()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_about_notification -> {
                val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                        .putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                startActivity(intent)
                true
            }
            R.id.menu_about_app -> {
                val intent = Intent(this, AboutApp::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setTodayText() {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm")
        val formattedText = current.format(formatter)

        binding.today.text = formattedText
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

    private fun subscribeTopic() {
        FirebaseMessaging.getInstance().subscribeToTopic(getString(R.string.fcm_tonight_topic))
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("ML dev", "success!")
                    } else {
                        Log.d("ML dev", "failure!")
                    }
                }
    }
}