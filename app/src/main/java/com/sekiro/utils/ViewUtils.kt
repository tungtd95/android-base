package com.sekiro.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.sekiro.R

fun Context.showKeyboardDelay(view: View, delayTime: Long = 0) {
    view.postDelayed({
        view.requestFocus()
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.showSoftInput(view, 0)
    }, delayTime)
}

fun Context.hideKeyBoard(view: View) {
    val inputManager: InputMethodManager =
        this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    Handler(this.mainLooper).post {
        inputManager.hideSoftInputFromWindow(
            view.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
}

@SuppressLint("CheckResult")
fun ImageView.loadImage(
    imageUrl: String?,
    cornerRadius: Float? = null,
    @DrawableRes placeHolder: Int? = null
) {
    Glide.with(context).load(imageUrl).apply {
        placeHolder?.let { placeholder(placeHolder) }
        cornerRadius?.let { transform(CenterCrop(), RoundedCorners(it.toInt())) }
    }.into(this)
}

fun View.backgroundColor(rawColor: String?) {
    rawColor?.let {
        DrawableCompat.setTint(
            background,
            rawColor.toColor(ContextCompat.getColor(context, R.color.icon_disable))
        )
    }
}
