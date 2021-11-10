package com.example.profile.ui.customviews.colorbutton

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.core.graphics.toRectF
import com.example.profile.R
import com.example.profile.utils.Constants
import kotlin.math.max
import kotlin.properties.Delegates

class ButtonColorLettersView(
    context: Context,
    attributesSet: AttributeSet?,
    defaultStyleAttr: Int,
    defaultStyleRes: Int
) : View(context, attributesSet, defaultStyleAttr, defaultStyleRes) {

    constructor(context: Context, attributesSet: AttributeSet?, defStyleAttr: Int) : this(
        context,
        attributesSet,
        defStyleAttr,
        R.style.DefaultButtonColorLettersStyle
    )

    constructor(context: Context, attributesSet: AttributeSet?) : this(
        context,
        attributesSet,
        R.attr.buttonColorLettersStyle
    )

    constructor(context: Context) : this(context, null)

    private lateinit var button: ButtonColorLetters
    private var colorBackground by Delegates.notNull<Int>()
    private var colorContainer by Delegates.notNull<Int>()
    private var strokeWidthContainer by Delegates.notNull<Float>()
    private var image: Drawable? = null
    private val paintBackground: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paintStroke: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paintText: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val space = 30f
    private var buttonBackground = Rect(0, 0, 0, 0)
    private var startImgX: Float = 0f
    private var startImgY: Float = 0f
    private var startTextX: Float = 0f
    private var startTextY: Float = 0f

    init {
        Log.e("AAAA", "init")
        if (attributesSet != null) {
            initializationAttr(attributesSet, defaultStyleAttr, defaultStyleRes)
        } else {
            defaultInitializationAttr()
        }

        paintText.apply {
            isAntiAlias = true
            textAlign = Paint.Align.CENTER
            textSize = button.lettersList[0].size
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        Log.e("AAAA", "onMeasure")
        val minWidth = suggestedMinimumWidth + paddingLeft + paddingRight
        val minHeight = suggestedMinimumHeight + paddingTop + paddingBottom

        val desiredHeightInPx =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 36F, resources.displayMetrics)
                .toInt()
        val desiredWidthInPx =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 700F, resources.displayMetrics)
                .toInt()

        val desiredWidth = max(minWidth, desiredWidthInPx + paddingLeft + paddingRight)
        val desiredHeight = max(minHeight, desiredHeightInPx + paddingTop + paddingBottom)

        setMeasuredDimension(
            resolveSize(desiredWidth, widthMeasureSpec),
            resolveSize(desiredHeight, heightMeasureSpec)
        )
    }

    override fun onSaveInstanceState(): Parcelable? {
        return super.onSaveInstanceState()
        Log.e("AAAA", "onSaveInstanceState()")
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        super.onRestoreInstanceState(state)
        Log.e("AAAA", "onRestoreInstanceState")
    }

    override fun onDraw(canvas: Canvas?) {
        setPosition()
        Log.e("AAAA", "onDraw")
        super.onDraw(canvas)
        paintBackground.apply {
            color = colorBackground
            isAntiAlias = true
        }
        buttonBackground.set(0, 0, width, height)
        canvas?.drawRoundRect(buttonBackground.toRectF(), 10f, 10f, paintBackground)

        if (colorContainer != 0) {
            paintStroke.apply {
                color = colorContainer
                style = Paint.Style.STROKE
                strokeWidth = strokeWidthContainer
            }
            canvas?.drawRoundRect(buttonBackground.toRectF(), 10f, 10f, paintStroke)
        }

        if (image != null) {
            image?.setBounds(
                startImgX.toInt(), startImgY.toInt(), 10, 10
            )
            if (canvas != null) {
                image?.draw(canvas)
            }
        }

        for (letter in button.lettersList) {
            paintText.apply {
                color = letter.color
                textSize = letter.size
            }
            canvas?.drawText(letter.char, startTextX, startTextY, paintText)
            startTextX += paintText.measureText(letter.char)
        }

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        Log.e("AAAA", "onsizeChanged")
        super.onSizeChanged(w, h, oldw, oldh)
        val safeWidth = w - paddingLeft - paddingRight
        val safeHeight = h - paddingTop - paddingRight
        buttonBackground.left = paddingLeft
        buttonBackground.right = paddingRight + safeWidth
        buttonBackground.top = paddingTop
        buttonBackground.bottom = paddingBottom + safeHeight
    }

    private fun setPosition() {
        val bound = Rect()
        paintText.getTextBounds(button.getText(), 0, button.getText().length, bound)
        val textWidth = paintText.measureText(button.getText())
        val textHeight = bound.height()

        image?.let {
            startImgX = (width - textWidth - it.intrinsicWidth - space) / 2
            startImgY = ((height - it.intrinsicHeight - paddingTop - paddingBottom) / 2).toFloat()
            startTextX = startImgX + it.intrinsicWidth + space
        }
        if (image == null) startTextX = buttonBackground.centerX() - (textWidth / 2f)
        startTextY = buttonBackground.centerY() + (textHeight / 2f)
    }

    private fun initializationAttr(
        attributesSet: AttributeSet?,
        defaultStyleAttr: Int,
        defaultStyleRes: Int
    ) {
        Log.e("AAAA", "initializationAttr")
        val typedArray = context.obtainStyledAttributes(
            attributesSet,
            R.styleable.ButtonColorLettersView,
            defaultStyleAttr,
            defaultStyleRes
        )
        colorBackground = typedArray.getColor(
            R.styleable.ButtonColorLettersView_customColorBackground,
            Constants.DEFAULT_COLOR_CUSTOM_BUTTON_BACKGROUND
        )
        colorContainer = typedArray.getColor(
            R.styleable.ButtonColorLettersView_customColorContainer,
            0
        )
        strokeWidthContainer =
            typedArray.getFloat(R.styleable.ButtonColorLettersView_customStrokeWidth, 5f)
        var text = typedArray.getText(
            R.styleable.ButtonColorLettersView_customText
        )
        val textSize =
            typedArray.getDimension(R.styleable.ButtonColorLettersView_customTextSize, 32f)
        image = typedArray.getDrawable(
            R.styleable.ButtonColorLettersView_customImage
        )
        if (text == null) {
            text = "google"
        }
        button = ButtonColorLetters(text.toString(), textSize, image)

        typedArray.recycle()
    }

    private fun defaultInitializationAttr() {
        Log.e("AAAA", "defaultInitializationAttr")
        colorBackground = Constants.DEFAULT_COLOR_CUSTOM_BUTTON_BACKGROUND
        colorContainer = Constants.DEFAULT_COLOR_CUSTOM_BUTTON_CONTAINER
    }
}