package com.app2641.moonlovers

import android.content.Intent
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
import com.app2641.moonlovers.preferences.MoonLoversPreference
import com.app2641.moonlovers.services.NotificationService
import com.app2641.moonlovers.utils.DateUtils
import com.google.android.play.core.review.ReviewManagerFactory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel: OverviewViewModel by lazy {
        ViewModelProvider(this).get(OverviewViewModel::class.java)
    }

    private val preference: MoonLoversPreference by lazy {
        MoonLoversPreference(this)
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

        initNotification()
        startReviewFlow()
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

    private fun initNotification() {
        val notificationService = NotificationService(this)

        notificationService.createNotificationChannel()
        notificationService.subscribeTopic()
    }

    private fun startReviewFlow() {
        if (! isStartReviewFlow()) {
            return
        }

        val manager = ReviewManagerFactory.create(this)
        val request = manager.requestReviewFlow()
        request.addOnCompleteListener {
            if (it.isSuccessful) {
                val reviewInfo = it.result
                val flow = manager.launchReviewFlow(this, reviewInfo)
                flow.addOnCompleteListener {
                    Log.d("ML review flow", "completed")
                    updateReviewedAt()
                }
            }
        }
    }

    // インストール or レビュー時から一ヶ月後かどうか
    private fun isStartReviewFlow(): Boolean {
        val reviewedAt = DateUtils.toZoneDateTime(preference.getReviewedAt())
        val minutes = DateUtils.dateDiff(reviewedAt, DateUtils.now())

        return (60 * 24 * 30) < minutes
    }

    private fun updateReviewedAt() {
        val reviewedAt = DateUtils.toString(DateUtils.now())
        preference.putReviewedAt(reviewedAt).apply()
    }
}