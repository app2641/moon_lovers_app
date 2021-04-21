package com.app2641.moonlovers.utils

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.VectorDrawable
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat


class ResourceUtils {
    companion object {
        fun getBitmap(context: Context?, @DrawableRes drawableResId: Int): Bitmap? {
            val drawable = ContextCompat.getDrawable(context!!, drawableResId)
            return when (drawable) {
                is BitmapDrawable -> {
                    drawable.bitmap
                }
                is VectorDrawable -> {
                    drawable?.let { getBitmap(drawable as VectorDrawable) }
                }
                else -> {
                    throw IllegalArgumentException("Unsupported drawable type")
                }
            }
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        private fun getBitmap(vectorDrawable: VectorDrawable): Bitmap {
            val bitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth,
                    vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
            vectorDrawable.draw(canvas)
            return bitmap
        }
    }
}