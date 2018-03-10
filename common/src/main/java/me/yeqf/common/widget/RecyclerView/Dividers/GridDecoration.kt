package com.yeqf.expandrecyclerview.Dividers

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View

/**
 * Created by iPanel on 2017/7/11.
 */
class GridDecoration : RecyclerView.ItemDecoration {

    val ATTR: IntArray = intArrayOf(android.R.attr.listDivider)

    var mDivider: Drawable? = null
    var mDividerWidth: Int = 0
    var mDividerHeight: Int = 0
    var mPaint: Paint? = null


    constructor(context: Context) : super() {
        val ta: TypedArray? = context.obtainStyledAttributes(ATTR)
        this.mDivider = ta?.getDrawable(0)
        this.mDividerWidth = mDivider?.intrinsicWidth ?: 0
        this.mDividerHeight = mDivider?.intrinsicHeight ?: 0
        ta?.recycle()
    }

    constructor(context: Context, drawable: Int) : super() {
        this.mDivider = ContextCompat.getDrawable(context, drawable)
        this.mDividerWidth = mDivider?.intrinsicWidth ?: 0
        this.mDividerHeight = mDivider?.intrinsicHeight ?: 0
    }

    constructor(context: Context, mDividerHeight: Int, mDividerColor: Int) : super() {
        this.mDividerWidth = mDividerHeight
        this.mDividerHeight = mDividerHeight
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint?.color = mDividerColor
        mPaint?.style = Paint.Style.FILL
    }

    override fun onDrawOver(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.onDrawOver(c, parent, state)
    }

    override fun onDraw(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        drawHorizontalDivider(c, parent, state)
        drawVerticalDivider(c, parent, state)
    }

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        val pos = parent?.getChildAdapterPosition(view) ?: 0
        val spanCount = getSpanCount(parent)
        var childCount  = 0
        val mAdapter = parent?.adapter
       childCount = mAdapter?.itemCount ?: 0
        if (isLastColumn(parent, pos, spanCount, childCount)) {
            //最后一列不绘制
            outRect?.set(0, 0, 0, mDividerHeight)
        } else if (isLastRow(parent, pos, spanCount, childCount)) {
            //最后一行不绘制
            outRect?.set(0, 0, mDividerWidth, 0)
        } else {
            //其他
            outRect?.set(0, 0, mDividerWidth, mDividerHeight)
        }
    }

    private fun drawHorizontalDivider(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        val childCount = parent?.childCount ?: 0
        val edgeRect = getEdgeRect(parent)
        for (i in 0..childCount - 1) {
            val child = parent?.getChildAt(i)
            val params = child?.layoutParams as RecyclerView.LayoutParams?
            val left = (child?.left ?: 0) - (params?.leftMargin ?: 0)
            val top = (child?.bottom ?: 0) + (params?.bottomMargin ?: 0)
            val right = (child?.right ?: 0) + (params?.rightMargin ?: 0) + mDividerWidth
            val bottom = top + mDividerHeight
            //禁止在内边距绘制
            val targeRect = getTargetRect(edgeRect, Rect(left, top, right, bottom))
            if(targeRect != null) {
                if (mDivider != null) {
                    mDivider?.bounds = targeRect
                    mDivider?.draw(c)
                }
                if (mPaint != null) {
                    c?.drawRect(targeRect, mPaint)
                }
            }
        }
    }

    private fun drawVerticalDivider(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        val childCount = parent?.childCount ?: 0
        val edgeRect = getEdgeRect(parent)
        for (i in 0..childCount - 1) {
            val child = parent?.getChildAt(i)
            val params = child?.layoutParams as RecyclerView.LayoutParams?
            val left = (child?.right ?: 0) + (params?.rightMargin ?: 0)
            val top = (child?.top ?: 0) + (params?.topMargin ?: 0)
            val right = left + mDividerWidth
            val bottom = (child?.bottom ?: 0) + (params?.bottomMargin ?: 0)
            //禁止在内边距绘制
            val targeRect = getTargetRect(edgeRect, Rect(left, top, right, bottom))
            if(targeRect != null) {
                if (mDivider != null) {
                    mDivider?.bounds = targeRect
                    mDivider?.draw(c)
                }
                if (mPaint != null) {
                    c?.drawRect(targeRect, mPaint)
                }
            }
        }
    }

    private fun getSpanCount(parent: RecyclerView?): Int {
        var columnCount = -1
        val layoutManager = parent?.layoutManager
        when (layoutManager) {
            is GridLayoutManager -> columnCount = layoutManager.spanCount
            is StaggeredGridLayoutManager -> columnCount = layoutManager.spanCount
        }
        return columnCount
    }

    private fun isLastColumn(parent: RecyclerView?, pos: Int, spanCount: Int, childCount: Int): Boolean {
        val layoutManager = parent?.layoutManager
        when (layoutManager) {
            is GridLayoutManager -> return (pos + 1) % spanCount == 0
            is StaggeredGridLayoutManager -> return if (layoutManager.orientation == StaggeredGridLayoutManager.VERTICAL)
                (pos + 1) % spanCount == 0 else pos >= childCount - childCount % spanCount
        }
        return false
    }

    private fun isLastRow(parent: RecyclerView?, pos: Int, spanCount: Int, childCount: Int): Boolean {
        val layoutManager = parent?.layoutManager
        when (layoutManager) {
            is GridLayoutManager -> return pos >= childCount - childCount % spanCount
            is StaggeredGridLayoutManager -> return if (layoutManager.orientation == StaggeredGridLayoutManager.VERTICAL)
                pos >= childCount - childCount % spanCount else (pos + 1) % spanCount == 0
        }
        return false
    }

    /**
     * 获取控件内部绘制边界
     */
    private fun getEdgeRect(parent: RecyclerView?): Rect {
        val leftEdge = parent?.paddingLeft ?: 0
        val topEdge = parent?.paddingTop ?: 0
        val rightEdge = (parent?.measuredWidth ?: 0) - (parent?.paddingRight ?: 0)
        val bottomEdge = (parent?.measuredHeight ?: 0) - (parent?.paddingBottom ?: 0)
        return Rect(leftEdge, topEdge, rightEdge, bottomEdge)
    }

    /**
     * 限制不在内边距上绘制分割线
     */
    private fun getTargetRect(edgeRect: Rect, r: Rect): Rect? {
        val target = Rect()
        target.left = if(r.left < edgeRect.left) edgeRect.left else r.left
        target.top = if(r.top < edgeRect.top) edgeRect.top else r.top
        target.right = if(r.right > edgeRect.right) edgeRect.right else r.right
        target.bottom = if(r.bottom > edgeRect.bottom) edgeRect.bottom else r.bottom
        if(target.left < target.right && target.top < target.bottom)
            return target
        else
            return null
    }
}