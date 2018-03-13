package me.yeqf.common.utils

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.support.v4.content.ContextCompat

/**
 *
 * @Author yeqf
 * @Time 2018/3/13 17:37
 */
object ShapeUtils {
    fun getShapeDrawable(context: Context, radius: Float, colorResId: Int): GradientDrawable {
        val drawable = GradientDrawable()
        drawable.shape = GradientDrawable.RECTANGLE
        drawable.cornerRadius = Tools.dp2px(context, radius).toFloat()
        drawable.setColor(ContextCompat.getColor(context, colorResId))
        return drawable
    }
}