package com.example.profile.ui.customviews.colorbutton

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.toRectF
import com.example.profile.R
import com.example.profile.utils.Constants
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
    private var buttonBackground = Rect(0, 0, 0, 0)
    private var startImgX: Float = 0f
    private var startImgY: Float = 0f
    private var startTextX: Float = 0f
    private var startTextY: Float = 0f
    private val paintBackground: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paintStroke: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paintText: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val spaceBetweenTextImg = 30f
    private var textWidth: Float = 0f
    private var textHeight = 0f

    init {
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

        val bound = Rect()
        paintText.getTextBounds(button.getText(), 0, button.getText().length, bound)
        textWidth = paintText.measureText(button.getText())
        textHeight = bound.height().toFloat()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = textWidth * Constants.RATE_OF_FREE_SPACE + paddingLeft + paddingRight
        val desiredHeight = textHeight * Constants.RATE_OF_FREE_SPACE + paddingTop + paddingBottom
        setMeasuredDimension(
            resolveSize(desiredWidth.toInt(), widthMeasureSpec),
            resolveSize(desiredHeight.toInt(), heightMeasureSpec)
        )
    }

    override fun onDraw(canvas: Canvas?) {
        setPosition()
        super.onDraw(canvas)

        paintBackground.apply {
            color = colorBackground
            isAntiAlias = true
        }
        buttonBackground.set(0, 0, width, height)
        canvas?.drawRoundRect(
            buttonBackground.toRectF(),
            Constants.DEFAULT_CORNER_RADIUS, Constants.DEFAULT_CORNER_RADIUS, paintBackground
        )

        if (colorContainer != 0) {
            paintStroke.apply {
                color = colorContainer
                style = Paint.Style.STROKE
                strokeWidth = strokeWidthContainer
            }
            canvas?.drawRoundRect(
                buttonBackground.toRectF(),
                Constants.DEFAULT_CORNER_RADIUS, Constants.DEFAULT_CORNER_RADIUS, paintStroke
            )
        }

        if (image != null) {
            image?.setBounds(
                startImgX.toInt(), startImgY.toInt(), 0, 0
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
        super.onSizeChanged(w, h, oldw, oldh)
        val safeWidth = w - paddingLeft - paddingRight
        val safeHeight = h - paddingTop - paddingRight
        buttonBackground.left = paddingLeft
        buttonBackground.right = paddingRight + safeWidth
        buttonBackground.top = paddingTop
        buttonBackground.bottom = paddingBottom + safeHeight
    }

    private fun setPosition() {
        image?.let {
            startImgX = (width - textWidth - it.intrinsicWidth - spaceBetweenTextImg) / 2
            startImgY = ((height - it.intrinsicHeight - paddingTop - paddingBottom) / 2).toFloat()
            startTextX = startImgX + it.intrinsicWidth + spaceBetweenTextImg
        }
        if (image == null) startTextX = buttonBackground.centerX() - (textWidth / 2f)
        startTextY = buttonBackground.centerY() + (textHeight / 2f)
    }

    private fun initializationAttr(
        attributesSet: AttributeSet?,
        defaultStyleAttr: Int,
        defaultStyleRes: Int
    ) {
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
            Constants.DEFAULT_CONTAINER_COLOR
        )
        strokeWidthContainer =
            typedArray.getFloat(
                R.styleable.ButtonColorLettersView_customStrokeWidth,
                Constants.DEFAULT_STROKE_WIDTH
            )
        var text = typedArray.getText(
            R.styleable.ButtonColorLettersView_customText
        )
        val textSize =
            typedArray.getDimension(
                R.styleable.ButtonColorLettersView_customTextSize,
                Constants.DEFAULT_TEXT_SIZE
            )
        image = typedArray.getDrawable(
            R.styleable.ButtonColorLettersView_customImage
        )
        if (text == null) {
            text = Constants.DEFAULT_TEXT
        }
        button = ButtonColorLetters(text.toString(), textSize, image)

        typedArray.recycle()
    }

    private fun defaultInitializationAttr() {
        colorBackground = Constants.DEFAULT_COLOR_CUSTOM_BUTTON_BACKGROUND
        colorContainer = Constants.DEFAULT_COLOR_CUSTOM_BUTTON_CONTAINER
    }
}