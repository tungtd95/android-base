package com.moddroid.utils

import android.graphics.Color
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.moddroid.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

fun Boolean?.orElse(other: Boolean): Boolean = this ?: other

fun Long?.toSimpleDisplayDate(pattern: String = "MMM dd/yyyy HH:mm"): String {
    this ?: return ""
    val formatter = SimpleDateFormat(pattern, Locale.ENGLISH)
    val calendar: Calendar = Calendar.getInstance()
    calendar.timeInMillis = this * 1000
    calendar.timeZone = TimeZone.getDefault()
    return formatter.format(calendar.time)
}

fun View.backgroundColor(rawColor: String?) {
    rawColor?.let {
        DrawableCompat.setTint(
            background,
            rawColor.toColor(ContextCompat.getColor(context, R.color.icon_disable))
        )
    }
}

fun String.toColor(@ColorInt defaultColor: Int): Int = try {
    require(this.isNotEmpty())
    Color.parseColor(this)
} catch (ex: IllegalArgumentException) {
    defaultColor
}