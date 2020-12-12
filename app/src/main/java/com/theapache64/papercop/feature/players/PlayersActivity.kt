package com.theapache64.papercop.feature.players


import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.theapache64.papercop.R
import com.theapache64.papercop.databinding.ActivityPlayersBinding
import com.theapache64.papercop.feature.base.BaseActivity
import com.theapache64.papercop.feature.pick.PickActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayersActivity :
    BaseActivity<ActivityPlayersBinding, PlayersViewModel>(R.layout.activity_players) {

    override val viewModel: PlayersViewModel by viewModels()

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, PlayersActivity::class.java).apply {
                // data goes here
            }
        }
    }

    override fun onCreate() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // Rending players
        viewModel.players.observe(this, { players ->
            val adapter = PlayersAdapter(
                this,
                players
            ) { position ->

            }

            binding.rvPlayers.adapter = adapter
        })


        // Watching for pick activity
        viewModel.shouldGoToPickActivity.observe(this, {
            if (it) {
                startActivity(PickActivity.getStartIntent(this))
            }
        })
    }

}