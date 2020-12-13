package com.theapache64.papercop.feature.find

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.theapache64.papercop.R
import com.theapache64.papercop.core.Director
import com.theapache64.papercop.data.local.entities.players.PlayerEntity
import com.theapache64.papercop.data.repo.PlayersRepo
import com.theapache64.papercop.feature.base.BaseViewModel
import com.theapache64.papercop.model.Role
import com.theapache64.papercop.utils.livedata.SingleLiveEvent
import kotlinx.coroutines.launch
import java.util.*

/**
 * Created by theapache64 : Dec 12 Sat,2020 @ 13:05
 */
class FindThiefViewModel @ViewModelInject constructor(
    private val playersRepo: PlayersRepo
) : BaseViewModel() {

    companion object {
        const val RESULT_CORRECT = "Correct 🎊"
        const val RESULT_INCORRECT = "Incorrect ❌"
    }

    private lateinit var players: List<PlayerEntity>
    private val _rolesMap = SingleLiveEvent<HashMap<PlayerEntity, Role>>()
    val rolesMap: LiveData<HashMap<PlayerEntity, Role>> = _rolesMap

    private val _isNextVisible = MutableLiveData<Boolean>()
    val isNextVisible: LiveData<Boolean> = _isNextVisible

    private val _adapterState = SingleLiveEvent<FindThiefAdapter.State>()
    val adapterState: LiveData<FindThiefAdapter.State> = _adapterState

    private val _result = MutableLiveData("Find Thief!")
    val result: LiveData<String> = _result

    private val _shouldFinishFind = MutableLiveData<Boolean>()
    val shouldFinishFind: LiveData<Boolean> = _shouldFinishFind


    fun init(rolesMap: HashMap<PlayerEntity, Role>) {
        _rolesMap.value = rolesMap
        players = rolesMap.keys.toList()
    }

    fun onNextClicked() {
        _shouldFinishFind.value = true
    }

    fun onItemClicked(position: Int) {
        _rolesMap.value?.let { rolesMap ->
            val player = players[position]
            val role = rolesMap[player]!!

            if (role.name == Director.ROLE_NAME_POLICE) {
                _toastMsg.value = R.string.find_thief_error_self
                return
            }

            val isGuessCorrect = role.name == Director.ROLE_NAME_THIEF

            if (isGuessCorrect) {
                _result.value = RESULT_CORRECT
                _adapterState.value = FindThiefAdapter.State(
                    shouldRevealRole = true,
                    shouldSwitchPoliceThiefPoints = false
                )
            } else {

                // wrong guess
                _result.value = RESULT_INCORRECT
                _adapterState.value = FindThiefAdapter.State(
                    shouldRevealRole = true,
                    shouldSwitchPoliceThiefPoints = true
                )

                // Set police point to zero
                rolesMap.values.forEach {
                    when (it.name) {
                        Director.ROLE_NAME_POLICE -> {
                            it.point = Director.ROLE_POINT_THIEF
                        }
                        Director.ROLE_NAME_THIEF -> {
                            it.point = Director.ROLE_POINT_POLICE
                        }
                    }
                }
            }

            // Save points
            for (p in players) {
                p.totalScore += rolesMap[p]!!.point
            }

            viewModelScope.launch {
                playersRepo.update(players)
                _isNextVisible.value = true
            }

        }
    }

}