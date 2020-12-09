package com.theapache64.papercop.feature.count

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.theapache64.papercop.R
import com.theapache64.papercop.feature.base.BaseViewModel
import com.theapache64.papercop.utils.livedata.SingleLiveEvent

/**
 * Created by theapache64 : Dec 08 Tue,2020 @ 22:48
 */
class CountViewModel @ViewModelInject constructor() : BaseViewModel() {
    companion object {
        private const val MIN_COUNT = 3
    }

    var count = MutableLiveData(MIN_COUNT)

    private val _shouldPlayClickSound = SingleLiveEvent<Boolean>()
    val shouldPlayClickSound: LiveData<Boolean> = _shouldPlayClickSound

    private val _shouldPlayError = SingleLiveEvent<Boolean>()
    val shouldPlayError: LiveData<Boolean> = _shouldPlayError

    fun onIncrementClicked() {
        count.value = count.value?.plus(1)
        _shouldPlayClickSound.value = true
    }

    fun onDecrementClicked() {
        val nextValue = count.value?.minus(1) ?: 0
        if (nextValue < MIN_COUNT) {
            _snackBarMsg.value = R.string.count_error_message_min
            _shouldPlayError.value = true
        } else {
            count.value = nextValue
            _shouldPlayClickSound.value = true
        }
    }

    private val _launchNamesScreen = SingleLiveEvent<Int>()
    val launchNamesScreen: LiveData<Int> = _launchNamesScreen

    fun onNextClicked() {
        _launchNamesScreen.value = count.value ?: 0
    }
}