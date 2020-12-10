package com.theapache64.papercop.feature.inputplayers

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.core.view.children
import com.google.android.material.textfield.TextInputLayout
import com.theapache64.papercop.R
import com.theapache64.papercop.databinding.ActivityInputPlayersBinding
import com.theapache64.papercop.feature.base.BaseActivity
import com.theapache64.papercop.feature.score.ScoreActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InputPlayersActivity :
    BaseActivity<ActivityInputPlayersBinding, InputPlayersViewModel>(R.layout.activity_input_players) {

    companion object {
        private const val KEY_COUNT = "count"

        fun getStartIntent(context: Context, count: Int): Intent {
            return Intent(context, InputPlayersActivity::class.java).apply {
                // data goes here
                putExtra(KEY_COUNT, count)
            }
        }
    }

    override val viewModel: InputPlayersViewModel by viewModels()

    override fun onCreate() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val count = if (isDebugActivity()) {
            3
        } else {
            intent.getIntExtra(KEY_COUNT, -1)
        }

        require(count != -1) { "$KEY_COUNT missing" }

        viewModel.init(count)


        viewModel.generateInputFields.observe(this, { inputCount ->
            val inflater = LayoutInflater.from(this)
            for (i in 0 until inputCount) {
                val textInputLayout = inflater.inflate(
                    R.layout.item_input_name,
                    binding.llInputPlayers,
                    false
                ) as TextInputLayout

                textInputLayout.hint = "Person ${i + 1}"

                binding.llInputPlayers.addView(textInputLayout)
            }
        })

        viewModel.shouldValidatePlayerNames.observe(this, {
            if (it) {
                val players = mutableListOf<String>()
                for (child in binding.llInputPlayers.children) {
                    child as TextInputLayout
                    val name = child.editText?.text?.trim().toString() ?: ""
                    if (name.isBlank()) {
                        // empty name
                        child.error = getString(R.string.input_players_error_empty)
                    } else {
                        if (players.contains(name)) {
                            // duplicate name
                            child.error = getString(R.string.input_players_error_duplicate, name)
                        } else {
                            // valid name
                            child.error = null
                            players.add(name)
                        }
                    }
                }

                if (binding.llInputPlayers.childCount == players.size) {
                    // All valid
                    viewModel.onValidPlayers(players)
                } else {
                    // Some names are invalid
                    viewModel.onInvalidPlayers()
                }
            }
        })


        viewModel.shouldLaunchScoreActivity.observe(this, {
            startActivity(ScoreActivity.getStartIntent(this))
        })
    }
}