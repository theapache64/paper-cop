package com.theapache64.papercop.feature.count

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.google.firebase.analytics.FirebaseAnalytics
import com.theapache64.papercop.R
import com.theapache64.papercop.databinding.ActivityCountBinding
import com.theapache64.papercop.feature.base.BaseActivity
import com.theapache64.papercop.feature.inputplayers.InputPlayersActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountActivity : BaseActivity<ActivityCountBinding, CountViewModel>(R.layout.activity_count) {

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, CountActivity::class.java).apply {
                // data goes here
            }
        }
    }

    private val analytics by lazy {
        FirebaseAnalytics.getInstance(this)
    }

    private val clickSound by lazy {
        MediaPlayer.create(this, R.raw.click)
    }

    private val errorSound by lazy {
        MediaPlayer.create(this, R.raw.error)
    }

    override fun getSnackBarRoot(): View = binding.bNext

    override val viewModel: CountViewModel by viewModels()

    override fun onCreate() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.launchNamesScreen.observe(this, { count ->
            val analyticsData = Bundle().apply {
                putInt("count", count)
            }
            analytics.logEvent("player_count", analyticsData)
            startActivity(InputPlayersActivity.getStartIntent(this, count))
        })

        viewModel.shouldPlayClickSound.observe(this, {
            if (it) {
                clickSound.start()
            }
        })

        viewModel.shouldPlayError.observe(this, {
            if (it) {
                errorSound.start()
            }
        })
    }
}

