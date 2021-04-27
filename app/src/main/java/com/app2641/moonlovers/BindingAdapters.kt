
package com.app2641.moonlovers

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import com.app2641.moonlovers.model.MoonLoversApiStatus
import com.google.android.material.progressindicator.CircularProgressIndicator
import kotlin.math.roundToInt

object BindingAdapters {
    // 月齢の画像表示切り替え
    @BindingAdapter("age")
    @JvmStatic
    fun bindImage(imageView: ImageView, age: String?) {
        if (age == null) {
            imageView.setImageResource(R.drawable.img_moon15)
        } else {
            val context = imageView.context
            val imgNumber: String = age.toDouble().roundToInt().toString()
            val resource = context.resources.getIdentifier(
                "img_moon$imgNumber",
                "drawable",
                context.packageName
            )
            imageView.setImageResource(resource)
        }
    }

    // 月齢の表示
    @BindingAdapter("age")
    @JvmStatic
    fun bindText(textView: TextView, age: String?) {
        age?.let {
            textView.text = age
        }
    }

    // 月齢テキストビューの表示切り替え
    @BindingAdapter("apiStatus")
    @JvmStatic
    fun bindTextViewStatus(textView: TextView, status: MoonLoversApiStatus?) {
        when(status) {
            MoonLoversApiStatus.LOADING, MoonLoversApiStatus.ERROR -> {
                textView.visibility = View.INVISIBLE
            }
            MoonLoversApiStatus.DONE -> {
                textView.visibility = View.VISIBLE
            }
        }
    }

    // APIリクエストインジケーターの表示切り替え
    @BindingAdapter("apiStatus")
    @JvmStatic
    fun bindIndicatorStatus(indicator: CircularProgressIndicator, status: MoonLoversApiStatus) {
        when(status) {
            MoonLoversApiStatus.LOADING -> {
                indicator.visibility = View.VISIBLE
            }
            MoonLoversApiStatus.ERROR, MoonLoversApiStatus.DONE -> {
                indicator.visibility = View.INVISIBLE
            }
        }
    }

    // APIリクエストエラーのテキストビューとトースト表示の切り替え
    @BindingAdapter("toastStatus")
    @JvmStatic
    fun bindErrorViewStatus(textView: TextView, status: MoonLoversApiStatus) {
        if (status == MoonLoversApiStatus.ERROR) {
            textView.visibility = View.VISIBLE
            Toast.makeText(textView.context, "時間を空けてから再度お試しください",Toast.LENGTH_LONG).show()
        } else {
            textView.visibility = View.INVISIBLE
        }
    }

    // APIリクエストエラーのボタン表示の切り替え
    @BindingAdapter("apiStatus")
    @JvmStatic
    fun bindRefreshButtonStatus(button: Button, status: MoonLoversApiStatus) {
        if (status == MoonLoversApiStatus.ERROR) {
            button.visibility = View.VISIBLE
        } else {
            button.visibility = View.INVISIBLE
        }
    }
}
