package com.example.tvexplorer.com.example.tvexplorer.ui.views

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.example.tvexplorer.R
import com.example.tvexplorer.core.enums.TvShowStatus


class TvShowStatusView : androidx.appcompat.widget.AppCompatTextView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        style(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        style(attrs)
    }

    private fun style(attrs: AttributeSet?) {
        val a: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.TvShowStatusView)
        val tvShowStatus = TvShowStatus.values().getOrElse(a.getInt(R.styleable.TvShowStatusView_status, 0)) { TvShowStatus.Unknown }
        setTvShowStatus(tvShowStatus)
        a.recycle()
    }

    fun setTvShowStatus(status: TvShowStatus) {
        when (status) {
            TvShowStatus.Ended -> {
                setTypeface(typeface, Typeface.BOLD)
                setTextColor(ContextCompat.getColor(context, R.color.red))
            }
            else -> {
                setTypeface(typeface, Typeface.NORMAL)
                setTextColor(ContextCompat.getColor(context, R.color.black))
            }
        }
    }

}