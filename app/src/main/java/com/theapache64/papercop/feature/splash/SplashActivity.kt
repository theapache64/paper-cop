package com.theapache64.papercop.feature.splash

import androidx.activity.viewModels
import com.google.firebase.analytics.FirebaseAnalytics
import com.theapache64.papercop.R
import com.theapache64.papercop.databinding.ActivitySplashBinding
import com.theapache64.papercop.feature.base.BaseActivity
import com.theapache64.papercop.feature.count.CountActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity :
    BaseActivity<ActivitySplashBinding, SplashViewModel>(R.layout.activity_splash) {

    private val analytics by lazy {
        FirebaseAnalytics.getInstance(this)
    }

    override val viewModel: SplashViewModel by viewModels()

    override fun onCreate() {
        viewModel.shouldGoToCountScreen.observe(this, {
            startActivity(CountActivity.getStartIntent(this))
            analytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, null)
            finish()
        })
    }

}