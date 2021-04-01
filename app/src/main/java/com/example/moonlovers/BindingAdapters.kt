
package com.example.moonlovers

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
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
            textView.visibility = View.VISIBLE
            textView.text = age
        }
    }
}
