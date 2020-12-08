package com.theapache64.papercop.feature.count

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.theapache64.papercop.R
import com.theapache64.papercop.feature.base.BaseViewModel

/**
 * Created by theapache64 : Dec 08 Tue,2020 @ 22:48
 */
class CountViewModel @ViewModelInject constructor() : BaseViewModel() {
    companion object {
        private const val MIN_COUNT = 3
    }

    var count = MutableLiveData(MIN_COUNT)


    fun onIncrementClicked() {
        count.value = count.value?.plus(1)
    }

    fun onDecrementClicked() {
        val nextValue = count.value?.minus(1) ?: 0
        if (nextValue < MIN_COUNT) {
            _snackBarMsg.value = R.string.count_error_message_min
        } else {
            count.value = nextValue
        }
    }
}