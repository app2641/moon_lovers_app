package com.app2641.moonlovers

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
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

        val twitter: ImageView = findViewById(R.id.twitter_icon)
        twitter.setOnClickListener(View.OnClickListener {
            val intent = Intent()
            intent.setAction(Intent.ACTION_VIEW)
            intent.addCategory(Intent.CATEGORY_BROWSABLE)
            intent.setData(Uri.parse("https://twitter.com/app2641"))
            startActivity(intent)
        })
    }
}