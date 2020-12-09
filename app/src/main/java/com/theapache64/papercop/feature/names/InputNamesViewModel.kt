package com.theapache64.papercop.feature.names

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.theapache64.papercop.feature.base.BaseViewModel
import com.theapache64.papercop.utils.livedata.SingleLiveEvent

/**
 * Created by theapache64 : Dec 09 Wed,2020 @ 00:13
 */
class InputNamesViewModel @ViewModelInject constructor() : BaseViewModel() {

    private val _generateInputFields = SingleLiveEvent<Int>()
    val generateInputFields: LiveData<Int> = _generateInputFields

    fun init(count: Int) {
        _generateInputFields.value = count
    }

}