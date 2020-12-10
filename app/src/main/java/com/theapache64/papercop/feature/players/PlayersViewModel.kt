package com.theapache64.papercop.feature.players

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.asLiveData
import com.theapache64.papercop.data.repo.PlayersRepo
import com.theapache64.papercop.feature.base.BaseViewModel

/**
 * Created by theapache64 : Dec 10 Thu,2020 @ 10:28
 */
class PlayersViewModel @ViewModelInject constructor(
    private val playersRepo: PlayersRepo
) : BaseViewModel() {

    val players = playersRepo
        .getAll()
        .asLiveData()

    fun onGoClicked() {

    }
}