package com.android.news.util

import android.content.res.Resources
import android.util.DisplayMetrics
import android.view.View

fun View.convertPixelsToDp(px: Float): Float {
    val metrics = Resources.getSystem().getDisplayMetrics()
    val dp = px / (metrics.densityDpi / 160f)
    return Math.round(dp).toFloat()
}

fun View.convertDpToPixel(dp: Float): Float {
    val metrics = Resources.getSystem().getDisplayMetrics()
    val px = dp * (metrics.densityDpi / 160f)
    return Math.round(px).toFloat()
}
//http://stackoverflow.com/questions/4605527/converting-pixels-to-dp
//The above method results accurate method compared to below methods
//http://stackoverflow.com/questions/8309354/formula-px-to-dp-dp-to-px-android

fun View.convertDpToPx(dp: Int): Int {
    return Math.round(dp * (Resources.getSystem().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT))

}

fun View.convertPxToDp(px: Int): Int {
    return Math.round(px / (Resources.getSystem().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT))
}