package com.theapache64.papercop.feature.pick

import androidx.hilt.lifecycle.ViewModelInject
import com.theapache64.papercop.feature.base.BaseViewModel

/**
 * Created by theapache64 : Dec 09 Wed,2020 @ 21:21
 */
class PickViewModel @ViewModelInject constructor() : BaseViewModel() {
    companion object {
        private val POINTS_TABLE = mapOf(
            "King" to 1000,
            "Queen" to 900,
            "Minister" to 800,
            "Police" to 700,
            "Thief" to 0
        )
    }
}