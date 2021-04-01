package com.example.moonlovers

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
}