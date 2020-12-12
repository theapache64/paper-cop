package com.theapache64.papercop.feature.pick

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.theapache64.papercop.core.Director
import com.theapache64.papercop.data.local.entities.players.PlayerEntity
import com.theapache64.papercop.data.repo.PlayersRepo
import com.theapache64.papercop.feature.base.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Created by theapache64 : Dec 09 Wed,2020 @ 21:21
 */
class PickViewModel @ViewModelInject constructor(
    private val playersRepo: PlayersRepo
) : BaseViewModel() {


    private val _charName = MutableLiveData<String>()
    val charName: LiveData<String> = _charName

    private val _playerName = MutableLiveData<String>()
    val playerName: LiveData<String> = _playerName

    lateinit var players: List<PlayerEntity>
    lateinit var chars: Map<String, String>
    lateinit var tempChars: MutableMap<String, String>

    private val _isCharNameVisible = MutableLiveData(false)
    val isCharNameVisible: LiveData<Boolean> = _isCharNameVisible

    private val _isHoldMeVisible = MutableLiveData(true)
    val isHoldMeVisible: LiveData<Boolean> = _isHoldMeVisible

    private val _isNextVisible = MutableLiveData(false)
    val isNextVisible: LiveData<Boolean> = _isNextVisible


    init {
        viewModelScope.launch {
            playersRepo.getAll().collect { players ->
                this@PickViewModel.players = players
                this@PickViewModel.chars = Director.provideCharacters(players.map { it.name })
                this@PickViewModel.tempChars = chars.toMutableMap()

                onNextClicked()
            }
        }
    }

    fun onHoldFinished() {
        _isHoldMeVisible.value = false
        _isCharNameVisible.value = true
        if (tempChars.isNotEmpty()) {
            // not finished
            _isNextVisible.value = true
        } else {
            // finished
            _toastMsg.value = "Finished"
        }
    }


    fun onNextClicked() {
        val playerName = tempChars.keys.first()

        _playerName.value = playerName
        _charName.value = tempChars[playerName]
        tempChars.remove(playerName)

        _isCharNameVisible.value = false
        _isNextVisible.value = false
        _isHoldMeVisible.value = true
    }
}