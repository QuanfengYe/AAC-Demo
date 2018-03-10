package com.yeqf.expandrecyclerview.Dividers

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by iPanel on 2017/7/11.
 */
open class LinearDecoration : RecyclerView.ItemDecoration {

    val ATTR: IntArray = intArrayOf(android.R.attr.listDivider)

    var mDivider: Drawable? = null
    var mDividerWidth: Int = 0
    var mDividerHeight: Int = 0
    var mOrientation: Int = LinearLayoutManager.VERTICAL
    var mPaint: Paint? = null

    constructor(context: Context, orientation: Int) : super() {
        setOrientation(orientation)
        val ta: TypedArray? = context.obtainStyledAttributes(ATTR)
        this.mDivider = ta?.getDrawable(0)
        this.mDividerWidth = mDivider!!.intrinsicWidth
        this.mDividerHeight = mDivider!!.intrinsicHeight
        ta?.recycle()
    }

    constructor(context: Context, orientation: Int, drawable: Int) : super() {
        setOrientation(orientation)
        this.mDivider = ContextCompat.getDrawable(context, drawable)
        this.mDividerWidth = mDivider!!.intrinsicWidth
        this.mDividerHeight = mDivider!!.intrinsicHeight
    }

    constructor(orientation: Int, mDividerHeight: Int, mDividerColor: Int) : super() {
        setOrientation(orientation)
        this.mDividerWidth = mDividerHeight
        this.mDividerHeight = mDividerHeight
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint?.color = mDividerColor
        mPaint?.style = Paint.Style.FILL
    }

    private fun setOrientation(orientation: Int) {
        if (orientation != LinearLayoutManager.VERTICAL && orientation != LinearLayoutManager.HORIZONTAL) {
            throw IllegalArgumentException("Invalid orientation")
        }
        mOrientation = orientation
    }

    override fun onDrawOver(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.onDrawOver(c, parent, state)
    }

    override fun onDraw(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        if(mOrientation == LinearLayoutManager.VERTICAL) {
            drawHorizontalDivider(c, parent, state)
        } else {
            drawVerticalDivider(c, parent, state)
        }
    }

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        val pos = parent!!.getChildAdapterPosition(view)
        var childCount: Int = 0
        val mAdapter = parent.adapter
        childCount = mAdapter.itemCount
        if(pos < childCount - 1) { //最后一行/列不绘制
            if(mOrientation == LinearLayoutManager.VERTICAL) {
                //画横线，就是往下偏移一个分割线的高度
                outRect?.set(0, 0, 0, mDividerHeight)
            } else {
                //画竖线，就是往右偏移一个分割线的宽度
                outRect?.set(0, 0, mDividerWidth, 0)
            }
        }
    }

    private fun drawHorizontalDivider(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        val childCount = parent!!.childCount
        val left = parent.paddingLeft
        val right = parent.measuredWidth - parent.paddingRight
        val topEdge = parent.paddingTop
        val bottomEdge = parent.measuredHeight - parent.paddingBottom
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams?
            var top = child.bottom  + params!!.bottomMargin
            var bottom = top + mDividerHeight
            //禁止在内边距绘制
            top = if(top < topEdge) topEdge else top
            bottom = if(bottom > bottomEdge) bottomEdge else bottom
            if(top < bottom) {
                if (mDivider != null) {
                    mDivider?.setBounds(left, top, right, bottom)
                    mDivider?.draw(c)
                }
                if (mPaint != null) {
                    c?.drawRect(Rect(left, top, right, bottom), mPaint)
                }
            }
        }
    }

    private fun drawVerticalDivider(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        val childCount = parent!!.childCount
        val top = parent.paddingTop
        val bottom = parent.measuredHeight - parent.paddingBottom
        val leftEdge = parent.paddingLeft
        val rightEdge = parent.measuredWidth - parent.paddingRight
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams?
            var left = child.right + params!!.rightMargin
            var right = left + mDividerWidth
            //禁止在内边距绘制
            left = if(left < leftEdge) leftEdge else left
            right = if(right > rightEdge) rightEdge else right
            if(left < right) {
                if (mDivider != null) {
                    mDivider?.setBounds(left, top, right, bottom)
                    mDivider?.draw(c)
                }
                if (mPaint != null) {
                    c?.drawRect(Rect(left, top, right, bottom), mPaint)
                }
            }
        }
    }
}