package com.example.movie.customview

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.example.movie.R


class RatioImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {
    var ration = 0f
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }

    init {
        setupAttributes(context, attrs)
    }

    private fun setupAttributes(context: Context, attrs: AttributeSet?) {
        val obtainStyledAttributes =
            context.obtainStyledAttributes(attrs, R.styleable.RatioImageView)
        ration = try {
            obtainStyledAttributes.getFloat(R.styleable.RatioImageView_ratio, 1f)
        } finally {
            obtainStyledAttributes.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(measuredWidth, ((measuredWidth / ration)).toInt())
    }
}
