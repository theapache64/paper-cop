package com.theapache64.papercop.feature.find


import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.theapache64.papercop.R
import com.theapache64.papercop.databinding.ActivityFindThiefBinding
import com.theapache64.papercop.feature.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FindThiefActivity :
    BaseActivity<ActivityFindThiefBinding, FindThiefViewModel>(R.layout.activity_find_thief) {
    override val viewModel: FindThiefViewModel by viewModels()

    companion object {
        private const val KEY_CHAR_MAP = "char_map"

        fun getStartIntent(context: Context, charMap: HashMap<String, String>): Intent {
            return Intent(context, FindThiefActivity::class.java).apply {
                // data goes here
                putExtra(KEY_CHAR_MAP, charMap)
            }
        }
    }

    override fun onCreate() {
        val charMap = if (isDebugActivity()) {
            mapOf(
                "John" to "Police",
                "Jake" to "Thief",
                "Tim" to "King"
            )
        } else {
            getSerializableOrThrow<HashMap<String, String>>(KEY_CHAR_MAP)
        }


    }

}