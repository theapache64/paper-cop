package com.theapache64.papercop.widget

import android.content.Context
import android.graphics.Color
import android.media.MediaPlayer
import android.util.AttributeSet
import android.view.Gravity
import com.google.android.material.button.MaterialButton
import com.theapache64.papercop.R

/**
 * Created by theapache64 : Dec 11 Fri,2020 @ 17:57
 */
class PrimaryButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : MaterialButton(context, attrs, defStyleAttr) {

    private val clickSound by lazy {
        MediaPlayer.create(context, R.raw.click_2)
    }

    init {
        gravity = Gravity.CENTER
        textSize = context.resources.getDimension(R.dimen.primary_button_text_size)
    }

    override fun setOnClickListener(l: OnClickListener?) {
        super.setOnClickListener {
            clickSound.start()
            l?.onClick(this)
        }
    }

}