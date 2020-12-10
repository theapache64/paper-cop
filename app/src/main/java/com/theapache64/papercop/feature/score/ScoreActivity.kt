package com.theapache64.papercop.feature.score


import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.theapache64.papercop.R
import com.theapache64.papercop.databinding.ActivityScoreBinding
import com.theapache64.papercop.feature.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScoreActivity : BaseActivity<ActivityScoreBinding, ScoreViewModel>(R.layout.activity_score) {
    override val viewModel: ScoreViewModel by viewModels()

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, ScoreActivity::class.java).apply {
                // data goes here
            }
        }
    }

    override fun onCreate() {

    }

}