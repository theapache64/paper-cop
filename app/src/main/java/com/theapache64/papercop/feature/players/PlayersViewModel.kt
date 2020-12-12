package com.theapache64.papercop.feature.players

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.theapache64.papercop.data.repo.PlayersRepo
import com.theapache64.papercop.feature.base.BaseViewModel
import com.theapache64.papercop.utils.livedata.SingleLiveEvent

/**
 * Created by theapache64 : Dec 10 Thu,2020 @ 10:28
 */
class PlayersViewModel @ViewModelInject constructor(
    private val playersRepo: PlayersRepo
) : BaseViewModel() {

    val players = playersRepo
        .getAll()
        .asLiveData()

    private val _shouldGoToPickActivity = SingleLiveEvent<Boolean>()
    val shouldGoToPickActivity: LiveData<Boolean> = _shouldGoToPickActivity

    fun onGoClicked() {
        _shouldGoToPickActivity.value = true
    }
}