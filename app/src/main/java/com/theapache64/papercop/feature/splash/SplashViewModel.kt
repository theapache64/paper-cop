package com.theapache64.papercop.feature.splash

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.theapache64.papercop.feature.base.BaseViewModel
import com.theapache64.papercop.utils.livedata.SingleLiveEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by theapache64 : Dec 08 Tue,2020 @ 21:24
 */
class SplashViewModel @ViewModelInject constructor() : BaseViewModel() {
    private val _shouldGoToCountScreen = SingleLiveEvent<Boolean>()
    val shouldGoToCountScreen: LiveData<Boolean> = _shouldGoToCountScreen

    init {
        viewModelScope.launch {
            delay(500)
            _shouldGoToCountScreen.value = true
        }
    }
}