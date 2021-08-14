package com.iraqsoft.mawgood.util

import android.graphics.Color
import android.util.Log
import android.widget.TextView
import androidx.databinding.BindingAdapter


@BindingAdapter("android:backgroundColor")
fun TextView.setBackground(backgroundColor: String) {
    Log.e("********background",backgroundColor)
    val color: Int = try {
        Color.parseColor(backgroundColor)
    } catch (e: Exception) {

        Color.parseColor("$backgroundColor")
    }
    setBackgroundColor(color)
}