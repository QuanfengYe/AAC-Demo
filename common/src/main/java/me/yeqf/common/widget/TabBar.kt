package me.yeqf.common.widget

import android.animation.ArgbEvaluator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSmoothScroller
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.yeqf.expandrecyclerview.Dividers.LinearDecoration
import me.yeqf.common.R
import me.yeqf.common.utils.Tools
import java.lang.ref.WeakReference

/**
 * Created by yeqf on 2017/9/16.
 * <me.yeqf.common.widget.TabBar
 *     android:id="@+id/mTabBar"
 *     android:layout_width="match_parent"
 *     android:layout_height="wrap_content"
 *     app:selectedColor="@color/vip_main"
 *     app:textColor="@android:color/black"
 *     app:textPaddingBottom="9dp"
 *     app:textPaddingLeft="12dp"
 *     app:textPaddingRight="12dp"
 *     app:textPaddingTop="9dp"
 *     app:textSize="17sp" />
 */
class TabBar : RecyclerView {

    /**字体大小*/
    private var textSize = 0f
    /**字体颜色*/
    private var textColor = 0
    /**item间距*/
    private var textSpacing = 0
    /**item内边距*/
    private var textPadding = arrayOf(0, 0, 0, 0)
    /**选中颜色*/
    private var selectedColor = Color.parseColor("#00CD00")
    private val STATE_DRAW_NORMAL = 0
    private val STATE_DRAW_DRAGGING = 1
    private var state = STATE_DRAW_NORMAL
    private var mScrollState = SCROLL_STATE_IDLE
    private var mAdapter: TabAdapter
    private var listener: OnItemSelectListener? = null
    private val mPaint = Paint()
    private var mViewPager: ViewPager? = null
    private var mPageChangeListener: TabBarOnPageChangeListener? = null

    private val mIndicatorWidth = 36
    private val mIndicatorHeight = 12
    private var mIndicatorLeft = 0f
    private var mIndicatorRight = 0f
    private var mIndicatorRightOffset = 0f
    private var mIndicatorLeftOffset = 0f
    private var mIndicatorY = 0f
    private var mIndicatorExtendWidth = 0f
    /**是否绘制下划线*/
    private var isDrawIndicator = true
    private var targetView: TextView? = null
    private var nearView: TextView? = null
    /**ViewPager滑动系数*/
    private var dragFactor = 0f

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.TabBar, defStyle, 0)
        textSize = ta.getDimension(R.styleable.TabBar_textSize, 0f)
        textColor = ta.getColor(R.styleable.TabBar_textColor, Color.parseColor("#000000"))
        textSpacing = ta.getDimensionPixelOffset(R.styleable.TabBar_textSpacing, 0)
        selectedColor = ta.getColor(R.styleable.TabBar_selectedColor, Color.parseColor("#00CD00"))
        textPadding[0] = ta.getDimensionPixelOffset(R.styleable.TabBar_textPaddingLeft, Tools.dp2px(context, 10f))
        textPadding[1] = ta.getDimensionPixelOffset(R.styleable.TabBar_textPaddingTop, Tools.dp2px(context, 8f))
        textPadding[2] = ta.getDimensionPixelOffset(R.styleable.TabBar_textPaddingRight, Tools.dp2px(context, 10f))
        textPadding[3] = ta.getDimensionPixelOffset(R.styleable.TabBar_textPaddingBottom, Tools.dp2px(context, 8f))
        ta.recycle()

        layoutManager = MyLinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        addItemDecoration(LinearDecoration(LinearLayoutManager.HORIZONTAL, textSpacing, Color.parseColor("#00000000")))
        mAdapter = TabAdapter(onItemSelectListener)
        adapter = mAdapter

        mPaint.isAntiAlias = true
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 12f
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.color = selectedColor
    }

    fun setData(data: List<String>) {
        mAdapter.setData(data)
    }

    fun setOnSelectListener(listener: OnItemSelectListener) {
        this.listener = listener
    }

    fun setupWithViewPager(viewPager: ViewPager) {
        if (mViewPager != null) {
            // If we've already been setup with a ViewPager, remove us from it
            if (mPageChangeListener != null) {
                mPageChangeListener?.let { mViewPager?.removeOnPageChangeListener(it) }
            }
        }
        mViewPager = viewPager

        // Add our custom OnPageChangeListener to the ViewPager
        if (mPageChangeListener == null) {
            mPageChangeListener = TabBarOnPageChangeListener(this)
        }
        mPageChangeListener?.reset()

        mPageChangeListener?.let { viewPager.addOnPageChangeListener(it) }
    }

    fun getSelectedTabPosition(): Int {
        return mAdapter.selectedTabPosition
    }

    fun getTabCount(): Int {
        return mAdapter.itemCount
    }

    private fun onDragging(position: Int, positionOffset: Float) {
        val selectedPosition = mAdapter.selectedTabPosition
        val v = layoutManager?.findViewByPosition(position)
        if (v != null) {
            if (positionOffset > 0) {
                if (selectedPosition > position) {
                    //向右滑动position == selectedPosition - 1
                    targetView = layoutManager?.findViewByPosition(position) as TextView?
                    if (targetView != null) {
                        nearView = layoutManager?.findViewByPosition(position + 1) as TextView?
                        mIndicatorExtendWidth = (getViewDecorated(nearView, VIEW_CENTER_X).toFloat() - mIndicatorWidth / 2) -
                                (getViewDecorated(targetView!!, VIEW_CENTER_X) - mIndicatorWidth / 2)

                        dragFactor = 1 - positionOffset
                        targetView?.setTextColor(ArgbEvaluator().evaluate(dragFactor, textColor, selectedColor) as Int)
                        nearView?.setTextColor(ArgbEvaluator().evaluate(dragFactor, selectedColor, textColor) as Int)
                        if (positionOffset >= 0.5) {
                            mIndicatorRightOffset = 0f
                            mIndicatorLeftOffset = -(1 - positionOffset) * 2 * mIndicatorExtendWidth
                        } else {
                            mIndicatorLeftOffset = -1 * mIndicatorExtendWidth
                            mIndicatorRightOffset = (positionOffset - 0.5f) * 2 * mIndicatorExtendWidth
                        }

                        mIndicatorLeft = getViewDecorated(nearView, VIEW_CENTER_X).toFloat() - mIndicatorWidth / 2 + mIndicatorLeftOffset
                        mIndicatorRight = getViewDecorated(nearView, VIEW_CENTER_X).toFloat() + mIndicatorWidth / 2 + mIndicatorRightOffset
                        mIndicatorY = getViewDecorated(nearView, VIEW_BOTTOM) - mIndicatorHeight / 2f
                        invalidate()
                    }
                } else {
                    //左滑动position == selectedPosition
                    targetView = layoutManager?.findViewByPosition(position + 1) as TextView?
                    if (targetView != null) {
                        nearView = layoutManager?.findViewByPosition(position) as TextView?
                        mIndicatorExtendWidth = getViewDecorated(targetView!!, VIEW_CENTER_X) + mIndicatorWidth / 2 -
                                (getViewDecorated(nearView, VIEW_CENTER_X).toFloat() + mIndicatorWidth / 2)

                        dragFactor = positionOffset
                        targetView?.setTextColor(ArgbEvaluator().evaluate(dragFactor, textColor, selectedColor) as Int)
                        nearView?.setTextColor(ArgbEvaluator().evaluate(dragFactor, selectedColor, textColor) as Int)
                        if (positionOffset <= 0.5) {
                            mIndicatorLeftOffset = 0f
                            mIndicatorRightOffset = positionOffset * 2 * mIndicatorExtendWidth
                        } else {
                            mIndicatorRightOffset = mIndicatorExtendWidth
                            mIndicatorLeftOffset = (positionOffset - 0.5f) * 2 * mIndicatorExtendWidth
                        }

                        mIndicatorLeft = getViewDecorated(nearView, VIEW_CENTER_X).toFloat() - mIndicatorWidth / 2 + mIndicatorLeftOffset
                        mIndicatorRight = getViewDecorated(nearView, VIEW_CENTER_X).toFloat() + mIndicatorWidth / 2 + mIndicatorRightOffset
                        mIndicatorY = getViewDecorated(nearView, VIEW_BOTTOM) - mIndicatorHeight / 2f
                        invalidate()
                    }
                }
            }
        } else {

        }
    }

    private fun onDraggingStateChanged(scrollState: Int) {
        when (scrollState) {
            SCROLL_STATE_IDLE -> {
                targetView = null
                mIndicatorLeftOffset = 0f
                mIndicatorRightOffset = 0f
                state = STATE_DRAW_NORMAL
            }
            SCROLL_STATE_DRAGGING -> {
                state = STATE_DRAW_DRAGGING
            }
            SCROLL_STATE_SETTLING -> {

            }
        }
        mScrollState = scrollState
    }

    private fun scrollTo(position: Int) {
        isDrawIndicator = true
        val lastPosition = mAdapter.selectedTabPosition
        mAdapter.selectedTabPosition = position
        val lastView = layoutManager?.findViewByPosition(lastPosition) as TextView?
        val curView = layoutManager?.findViewByPosition((position)) as TextView?
        if (lastView != null && curView != null) {
            curView.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
            curView.isClickable = false
            lastView.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
            lastView.isClickable = true
            mAdapter.setSelectedView(curView)
            val dx = getViewDecorated(curView, VIEW_CENTER_X) - measuredWidth / 2
            smoothScrollBy(dx, 0)
            listener?.onSelect(curView as View, position)
        } else {
            mAdapter.nextSelectPosition = position
            smoothScrollToPosition(position)
            if (curView != null) {
                curView.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
                mAdapter.setSelectedView(curView)
            }
        }
    }

    override fun onChildAttachedToWindow(child: View) {
        super.onChildAttachedToWindow(child)
        val position = mAdapter.nextSelectPosition
        if (position == -1 && getChildAdapterPosition(child) == mAdapter.selectedTabPosition) {
            if (child is TextView) {
                child.setTextColor(selectedColor)
                child.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
                child.isClickable = false
            }
        }
        if (getChildAdapterPosition(child) == position) {
            mAdapter.selectTab(child, position)
            mAdapter.nextSelectPosition = -1
        }
        if (getChildAdapterPosition(child) == mAdapter.selectedTabPosition) {
            isDrawIndicator = true
        }
    }

    override fun onChildDetachedFromWindow(child: View) {
        super.onChildDetachedFromWindow(child)
        if (child is TextView) {
            child.setTextColor(textColor)
            child.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
            child.isClickable = true
        }
        if (getChildAdapterPosition(child) == mAdapter.selectedTabPosition) {
            isDrawIndicator = false
        }
    }

    private val onItemSelectListener = object : OnItemSelectListener {
        override fun onSelect(v: View, position: Int) {
            isDrawIndicator = true
            targetView = null
            listener?.onSelect(v, position)
            smoothScrollToPosition(position)
            mViewPager?.setCurrentItem(position, false)
        }
    }

    override fun onDraw(c: Canvas?) {
        super.onDraw(c)
        if (isDrawIndicator) {
            when (state) {
                STATE_DRAW_NORMAL -> onDrawIndicator(c)
                STATE_DRAW_DRAGGING -> onDrawIndicatorWithDragging(c)
            }
        }
    }

    private fun onDrawIndicator(c: Canvas?) {
        val v = layoutManager?.findViewByPosition(mAdapter.selectedTabPosition)
        if (v != null) {
            mIndicatorLeft = getViewDecorated(v, VIEW_CENTER_X).toFloat() - mIndicatorWidth / 2
            mIndicatorRight = getViewDecorated(v, VIEW_CENTER_X).toFloat() + mIndicatorWidth / 2

            mIndicatorY = getViewDecorated(v, VIEW_BOTTOM) - mIndicatorHeight / 2f
        }
        c?.drawLine(mIndicatorLeft, mIndicatorY, mIndicatorRight, mIndicatorY, mPaint)
    }

    private fun onDrawIndicatorWithDragging(c: Canvas?) {
        c?.drawLine(mIndicatorLeft, mIndicatorY, mIndicatorRight, mIndicatorY, mPaint)
    }

    private val VIEW_LEFT = 0
    private val VIEW_TOP = 1
    private val VIEW_RIGHT = 2
    private val VIEW_BOTTOM = 3
    private val VIEW_CENTER_X = 4
    private val VIEW_CENTER_Y = 5
    private fun getViewDecorated(v: View?, direction: Int): Int {
        if (v == null)
            return 0
        var result = 0
        val params = v.layoutParams as RecyclerView.LayoutParams
        val left = layoutManager?.getDecoratedLeft(v) ?: 0 - params.leftMargin
        val right = layoutManager?.getDecoratedRight(v) ?: 0 + params.rightMargin
        val top = layoutManager?.getDecoratedTop(v) ?: 0 - params.topMargin
        val bottom = layoutManager?.getDecoratedBottom(v) ?: 0 + params.bottomMargin
        when (direction) {
            VIEW_LEFT -> result = left
            VIEW_TOP -> result = top
            VIEW_RIGHT -> result = right
            VIEW_BOTTOM -> result = bottom
            VIEW_CENTER_X -> result = (left + right) / 2
            VIEW_CENTER_Y -> result = (top + bottom) / 2
        }
        return result
    }

    inner class TabAdapter(private var listener: OnItemSelectListener?) : Adapter<TabAdapter.Holder>() {
        private val data = arrayListOf<String>()
        private var clickView: TextView? = null
        private var isInit = false
        var selectedTabPosition = 0
        var nextSelectPosition = -1

        fun setData(data: List<String>) {
            this.data.clear()
            this.data.addAll(data)
            notifyDataSetChanged()
        }

        override fun onBindViewHolder(holder: Holder, position: Int) {
            if (0 == position && !isInit) {
                clickView = holder.tv
                clickView?.setTextColor(selectedColor)
                clickView?.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
                listener?.onSelect(holder.tv, position)
                isInit = true
            }
            if (selectedTabPosition == position) {
                clickView = holder.tv
                clickView?.setTextColor(selectedColor)
                clickView?.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
            } else {
                holder.tv.setTextColor(textColor)
                holder.tv.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
            }
            holder.tv.text = data[position]
            holder.tv.setOnClickListener {
                selectTab(it, position)
            }
        }

        fun setSelectedView(tv: TextView) {
            clickView = tv
        }

        fun selectTab(v: View?, position: Int) {
            if (clickView != v) {
                //上一个选中的view
                clickView?.setTextColor(textColor)
                clickView?.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
                clickView?.isClickable = true
                //当前选中的view
                clickView = v as TextView
                clickView?.setTextColor(selectedColor)
                clickView?.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
                clickView?.isClickable = false
                selectedTabPosition = position
                listener?.onSelect(v, position)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            val tv = TextView(parent.context)
            tv.setPadding(textPadding[0], textPadding[1], textPadding[2], textPadding[3])
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
            tv.setTextColor(textColor)
            tv.isFocusable = true
            tv.isClickable = true
            return Holder(tv)
        }

        override fun getItemCount(): Int {
            return data.size
        }

        inner class Holder(val tv: TextView) : RecyclerView.ViewHolder(tv)
    }

    interface OnItemSelectListener {
        fun onSelect(v: View, position: Int)
    }


    class TabBarOnPageChangeListener(tabBar: TabBar) : ViewPager.OnPageChangeListener {
        private var mTabLayoutRef: WeakReference<TabBar>? = null
        private var mPreviousScrollState: Int = 0
        private var mScrollState: Int = 0

        init {
            mTabLayoutRef = WeakReference(tabBar)
        }

        override fun onPageScrollStateChanged(state: Int) {
            mPreviousScrollState = mScrollState
            mScrollState = state
            val tabBar = mTabLayoutRef?.get()
            tabBar?.onDraggingStateChanged(state)
        }

        override fun onPageScrolled(position: Int, positionOffset: Float,
                                    positionOffsetPixels: Int) {
            if (mScrollState != SCROLL_STATE_IDLE) {
                val tabBar = mTabLayoutRef?.get()
                tabBar?.onDragging(position, positionOffset)
            }
        }

        override fun onPageSelected(position: Int) {
            val tabBar = mTabLayoutRef?.get()
            if (tabBar != null && tabBar.getSelectedTabPosition() != position
                    && position < tabBar.getTabCount()) {
                tabBar.scrollTo(position)
            }
        }

        internal fun reset() {
            mScrollState = SCROLL_STATE_IDLE
            mPreviousScrollState = mScrollState
        }
    }

    class MyLinearLayoutManager : LinearLayoutManager {
        constructor(context: Context) : super(context)
        constructor(context: Context, orientation: Int, reverseLayout: Boolean) : super(context, orientation, reverseLayout)
        constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

        override fun smoothScrollToPosition(recyclerView: RecyclerView?, state: State?, position: Int) {
            val linearSmoothScroller = LinearSmoothToCenterScroller(recyclerView?.context)
            linearSmoothScroller.targetPosition = position
            startSmoothScroll(linearSmoothScroller)
        }
    }

    class LinearSmoothToCenterScroller(context: Context?) : LinearSmoothScroller(context) {

        override fun calculateDtToFit(viewStart: Int, viewEnd: Int, boxStart: Int, boxEnd: Int, snapPreference: Int): Int {
            val boxCenter = (boxEnd + boxStart) / 2
            val viewCenter = (viewEnd + viewStart) / 2
            return boxCenter - viewCenter
        }

//        override fun calculateTimeForDeceleration(dx: Int): Int {
//            return Math.ceil(calculateTimeForScrolling(dx) / .1).toInt()
//        }
    }
}