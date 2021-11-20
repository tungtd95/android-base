package com.sekiro.utils

import android.content.Intent
import android.graphics.Color
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.airbnb.deeplinkdispatch.DeepLink
import com.sekiro.R
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

fun String.isCurrentVersionOutdated(newVersion: String): Boolean {
    try {
        val currentSplit = ArrayList(this.split("."))
        val newSplit = ArrayList(newVersion.split("."))
        if (currentSplit.size > newSplit.size) {
            for (i in 1..(currentSplit.size - newSplit.size)) {
                newSplit.add("0")
            }
        } else if (currentSplit.size < newSplit.size) {
            for (i in 1..(newSplit.size - currentSplit.size)) {
                currentSplit.add("0")
            }
        }
        currentSplit.forEachIndexed { index, s ->
            if (s.orEmpty().toInt() < newSplit[index].orEmpty().toInt()) {
                return true
            } else if (s.orEmpty().toInt() > newSplit[index].orEmpty().toInt()) {
                return false
            }
        }
    } catch (e: Exception) {
        return false
    }
    return false
}

fun Intent.isDeepLink() = getBooleanExtra(DeepLink.IS_DEEP_LINK, false)
