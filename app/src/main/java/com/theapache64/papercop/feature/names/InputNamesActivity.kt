package com.theapache64.papercop.feature.names

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.theapache64.papercop.R
import com.theapache64.papercop.databinding.ActivityInputNamesBinding
import com.theapache64.papercop.feature.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InputNamesActivity :
    BaseActivity<ActivityInputNamesBinding, InputNamesViewModel>(R.layout.activity_input_names) {

    companion object {
        private const val KEY_COUNT = "count"

        fun getStartIntent(context: Context, count: Int): Intent {
            return Intent(context, InputNamesActivity::class.java).apply {
                // data goes here
                putExtra(KEY_COUNT, count)
            }
        }
    }

    override val viewModel: InputNamesViewModel by viewModels()

    override fun onCreate() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val count = if (isDebugActivity()) {
            3
        } else {
            intent.getIntExtra(KEY_COUNT, -1)
        }

        viewModel.init(count)
    }
}