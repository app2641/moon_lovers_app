package com.example.moonlovers

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moonlovers.databinding.ActivityMainBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setTodayText()
    }

    private fun setTodayText() {
        val current = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日")
        val formattedText = current.format(formatter)

        binding.today.text = formattedText
    }
}