package com.theapache64.papercop.feature.names

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import com.theapache64.papercop.R
import com.theapache64.papercop.feature.base.BaseViewModel
import com.theapache64.papercop.utils.livedata.SingleLiveEvent

/**
 * Created by theapache64 : Dec 09 Wed,2020 @ 00:13
 */
class InputNamesViewModel @ViewModelInject constructor() : BaseViewModel() {

    private val _generateInputFields = SingleLiveEvent<Int>()
    val generateInputFields: LiveData<Int> = _generateInputFields

    private val _shouldValidateInputNames = SingleLiveEvent<Boolean>()
    val shouldValidateInputNames: LiveData<Boolean> = _shouldValidateInputNames

    private val _launchPickActivity = SingleLiveEvent<List<String>>()
    val launchPickActivity: LiveData<List<String>> = _launchPickActivity

    fun init(count: Int) {
        _generateInputFields.value = count
    }

    fun onNextClicked() {
        _shouldValidateInputNames.value = true
    }

    fun onInvalidNames() {
        _snackBarMsg.value = R.string.input_names_error_name
    }

    fun onValidNames(names: List<String>) {
        _launchPickActivity.value = names
    }

}