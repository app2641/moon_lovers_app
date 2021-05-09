package com.app2641.moonlovers

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class AboutApp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_app)

        // Toolbarにアップボタンを配置
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.mainToolbar)
        toolbar.title = getString(R.string.about_app_title)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}