
package com.example.moonlovers

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import com.example.moonlovers.model.MoonLoversApiStatus
import com.google.android.material.progressindicator.CircularProgressIndicator
import kotlin.math.roundToInt

object BindingAdapters {
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

    @BindingAdapter("age")
    @JvmStatic
    fun bindText(textView: TextView, age: String?) {
        age?.let {
            textView.text = age
        }
    }

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

    @BindingAdapter("toastStatus")
    @JvmStatic
    fun bindErrorViewStatus(textView: TextView, status: MoonLoversApiStatus) {
        if (status == MoonLoversApiStatus.ERROR) {
            textView.visibility = View.VISIBLE
            Toast.makeText(textView.context, "時間を空けてから再度お試しください",Toast.LENGTH_SHORT).show()
        } else {
            textView.visibility = View.INVISIBLE
        }
    }
}
