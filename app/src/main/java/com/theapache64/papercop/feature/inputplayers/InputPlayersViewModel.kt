package com.theapache64.papercop.feature.inputplayers

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.theapache64.papercop.R
import com.theapache64.papercop.data.repo.PlayersRepo
import com.theapache64.papercop.feature.base.BaseViewModel
import com.theapache64.papercop.utils.livedata.SingleLiveEvent
import kotlinx.coroutines.launch

/**
 * Created by theapache64 : Dec 09 Wed,2020 @ 00:13
 */
class InputPlayersViewModel @ViewModelInject constructor(
    private val playersRepo: PlayersRepo
) : BaseViewModel() {

    private val _generateInputFields = SingleLiveEvent<Int>()
    val generateInputFields: LiveData<Int> = _generateInputFields

    private val _shouldValidatePlayerNames = SingleLiveEvent<Boolean>()
    val shouldValidatePlayerNames: LiveData<Boolean> = _shouldValidatePlayerNames

    private val _shouldLaunchScoreActivity = SingleLiveEvent<Boolean>()
    val shouldLaunchScoreActivity: LiveData<Boolean> = _shouldLaunchScoreActivity

    fun init(count: Int) {
        _generateInputFields.value = count
    }

    fun onNextClicked() {
        _shouldValidatePlayerNames.value = true
    }

    fun onInvalidPlayers() {
        _snackBarMsg.value = R.string.input_player_names_error_name
    }

    fun onValidPlayers(names: List<String>) {
        viewModelScope.launch {
            // Remove all names first
            playersRepo.removeAllPlayers()

            // Add new names
            playersRepo.addPlayers(names)


        }
    }

}