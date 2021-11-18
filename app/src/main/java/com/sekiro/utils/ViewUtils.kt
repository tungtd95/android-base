package com.sekiro.utils

 import android.annotation.SuppressLint
 import android.app.Activity
import android.content.Context
import android.os.Build
 import android.os.Handler
 import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

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

fun Activity.changeStatusBarIconColor(isLight: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // from marshmallow
        if (isLight) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN and
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        } else {
            window.decorView.systemUiVisibility = window.decorView.systemUiVisibility or
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    } else { // lollipop
        if (isLight) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
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
