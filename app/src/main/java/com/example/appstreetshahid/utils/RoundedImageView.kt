package com.example.appstreetshahid.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView


class RoundedImageView : AppCompatImageView {
    private var borderWidth = 1
    private var viewWidth = 0
    private var viewHeight = 0
    private var image: Bitmap? = null
    private var paint: Paint = Paint()
    private var paintBorder: Paint = Paint()
    private var shader: BitmapShader? = null

    constructor(context: Context?) : super(context) {
        //setup()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        //setup()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        //setup()
    }

    private fun setup() {
        paint.isAntiAlias = true
        setBorderColor(Color.LTGRAY)
        paintBorder.isAntiAlias = true
        this.setLayerType(LAYER_TYPE_SOFTWARE, paintBorder)
        paintBorder.setShadowLayer(4.0f, 0.0f, 2.0f, Color.WHITE)
    }

    fun setBorderWidth(borderWidth: Int) {
        this.borderWidth = borderWidth
        this.invalidate()
    }

    fun setBorderColor(borderColor: Int) {
        paintBorder.color = borderColor
        this.invalidate()
    }

    private fun loadBitmap() {
        if (this.drawable is BitmapDrawable) image = (this.drawable as BitmapDrawable).bitmap
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        loadBitmap()
        if (image != null) {
            setup()
            shader = BitmapShader(Bitmap.createScaledBitmap(image!!, canvas.width, canvas.height, false), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            paint.shader = shader
            val circleCenter: Float = (viewWidth / 2).toFloat()
            canvas.drawCircle(circleCenter + borderWidth, circleCenter + borderWidth, circleCenter + borderWidth - 4.0f, paintBorder)
            canvas.drawCircle(circleCenter + borderWidth, circleCenter + borderWidth, circleCenter - 4.0f, paint)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = measureWidth(widthMeasureSpec)
        val height = measureHeight(heightMeasureSpec, widthMeasureSpec)
        viewWidth = width - borderWidth * 2
        viewHeight = height - borderWidth * 2
        setMeasuredDimension(width, height)
    }

    private fun measureWidth(measureSpec: Int): Int {
        var result = 0
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        result = if (specMode == MeasureSpec.EXACTLY) {
            specSize
        } else { // Measure the text
            viewWidth
        }
        return result
    }

    private fun measureHeight(measureSpecHeight: Int, measureSpecWidth: Int): Int {
        var result = 0
        val specMode = MeasureSpec.getMode(measureSpecHeight)
        val specSize = MeasureSpec.getSize(measureSpecHeight)
        result = if (specMode == MeasureSpec.EXACTLY) {
            specSize
        } else {
            viewHeight
        }
        return result + 2
    }
}