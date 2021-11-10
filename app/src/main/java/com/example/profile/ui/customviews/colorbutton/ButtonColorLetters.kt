package com.example.profile.ui.customviews.colorbutton

import android.graphics.Color
import android.graphics.drawable.Drawable

class ButtonColorLetters(
    private val text: String,
    textSize : Float,
    private val img: Drawable?
) {

     var lettersList = mutableListOf<Letter>()
    private val colors =
        listOf(Color.BLUE, Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN, Color.RED)

    init {
        var iterator = 0
        for (_letter in text) {
            lettersList.add(Letter(_letter.toString().uppercase(), colors[iterator++], textSize) )
            if (iterator == colors.size - 1) {
                iterator = 0
            }
        }
    }

    fun getText() : String{
        return text.uppercase()
    }

    fun getImage(): Drawable? {
        return img
    }

}