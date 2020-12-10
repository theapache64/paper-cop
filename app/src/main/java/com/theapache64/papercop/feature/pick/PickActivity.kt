package com.theapache64.papercop.feature.pick

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.theapache64.papercop.R
import com.theapache64.papercop.databinding.ActivityPickBinding
import com.theapache64.papercop.feature.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PickActivity : BaseActivity<ActivityPickBinding, PickViewModel>(R.layout.activity_pick) {
    override val viewModel: PickViewModel by viewModels()

    fun getStartIntent(context: Context): Intent {
        return Intent(context, PickActivity::class.java).apply{
            // data goes here
        }
    }

    override fun onCreate() {

    }

}