package com.theapache64.papercop.feature.count

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import com.theapache64.papercop.R
import com.theapache64.papercop.databinding.ActivityCountBinding
import com.theapache64.papercop.feature.base.BaseActivity
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

    override fun getSnackBarRoot(): View = binding.bNext

    override val viewModel: CountViewModel by viewModels()

    override fun onCreate() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }
}