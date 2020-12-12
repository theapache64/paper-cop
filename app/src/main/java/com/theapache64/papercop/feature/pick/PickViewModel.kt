package com.theapache64.papercop.feature.pick

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.theapache64.papercop.core.Director
import com.theapache64.papercop.data.local.entities.players.PlayerEntity
import com.theapache64.papercop.data.repo.PlayersRepo
import com.theapache64.papercop.feature.base.BaseViewModel
import com.theapache64.papercop.model.Role
import com.theapache64.papercop.utils.livedata.SingleLiveEvent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Created by theapache64 : Dec 09 Wed,2020 @ 21:21
 */
class PickViewModel @ViewModelInject constructor(
    private val playersRepo: PlayersRepo
) : BaseViewModel() {


    private val _role = MutableLiveData<Role>()
    val role: LiveData<Role> = _role

    private val _player = MutableLiveData<PlayerEntity>()
    val player: LiveData<PlayerEntity> = _player

    lateinit var players: List<PlayerEntity>
    private lateinit var charsMap: Map<PlayerEntity, Role>
    private lateinit var tempCharsMap: MutableMap<PlayerEntity, Role>

    private val _isCharNameVisible = MutableLiveData(false)
    val isCharNameVisible: LiveData<Boolean> = _isCharNameVisible

    private val _isHoldMeVisible = MutableLiveData(true)
    val isHoldMeVisible: LiveData<Boolean> = _isHoldMeVisible

    private val _isNextVisible = MutableLiveData(false)
    val isNextVisible: LiveData<Boolean> = _isNextVisible

    private val _shouldLaunchFindThiefActivity = SingleLiveEvent<HashMap<PlayerEntity, Role>>()
    val shouldLaunchFindThiefActivity: LiveData<HashMap<PlayerEntity, Role>> =
        _shouldLaunchFindThiefActivity

    init {
        viewModelScope.launch {
            playersRepo.getAll().collect { players ->
                this@PickViewModel.players = players
                this@PickViewModel.charsMap = Director.provideRoles(players)
                this@PickViewModel.tempCharsMap = charsMap.toMutableMap()

                onNextClicked()
            }
        }
    }

    fun onHoldFinished() {
        _isHoldMeVisible.value = false
        _isCharNameVisible.value = true
        _isNextVisible.value = true
    }


    fun onNextClicked() {
        if (tempCharsMap.isNotEmpty()) {
            // not finished
            prepareForNextRoleReveal()
        } else {
            // finished
            _shouldLaunchFindThiefActivity.value
        }
    }

    private fun prepareForNextRoleReveal() {
        val playerName = tempCharsMap.keys.first()

        _player.value = playerName
        _role.value = tempCharsMap[playerName]
        tempCharsMap.remove(playerName)

        _isCharNameVisible.value = false
        _isNextVisible.value = false
        _isHoldMeVisible.value = true
    }
}