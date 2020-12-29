package com.theapache64.papercop.feature.players


import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.core.os.postDelayed
import com.google.firebase.analytics.FirebaseAnalytics
import com.theapache64.papercop.R
import com.theapache64.papercop.databinding.ActivityPlayersBinding
import com.theapache64.papercop.feature.base.BaseActivity
import com.theapache64.papercop.feature.pick.PickActivity
import com.theapache64.papercop.utils.extensions.toast
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

    private val analytics by lazy {
        FirebaseAnalytics.getInstance(this)
    }


    override fun onCreate() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // Rendering players
        viewModel.players.observe(this, { players ->
            val adapter = PlayersAdapter(
                this,
                players
            )

            binding.rvPlayers.adapter = adapter
        })


        // Watching for pick activity
        viewModel.shouldGoToPickActivity.observe(this, {
            if (it) {
                analytics.logEvent("start_round", null)
                startActivity(PickActivity.getStartIntent(this))
            }
        })
    }

    private var isDoubleBackPressedOnce = false
    private val handler by lazy { Handler(Looper.getMainLooper()) }
    override fun onBackPressed() {
        if (isDoubleBackPressedOnce) {
            super.onBackPressed()
            return
        }
        isDoubleBackPressedOnce = true
        toast(R.string.msg_tap_twice_to_exit)

        handler.postDelayed(2000) {
            isDoubleBackPressedOnce = false
        }
    }

}