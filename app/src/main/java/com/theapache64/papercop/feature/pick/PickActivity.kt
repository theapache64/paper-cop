package com.theapache64.papercop.feature.pick

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateInterpolator
import androidx.activity.viewModels
import androidx.core.animation.addListener
import com.theapache64.papercop.R
import com.theapache64.papercop.databinding.ActivityPickBinding
import com.theapache64.papercop.feature.base.BaseActivity
import com.theapache64.papercop.feature.find.FindThiefActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class PickActivity : BaseActivity<ActivityPickBinding, PickViewModel>(R.layout.activity_pick) {


    override val viewModel: PickViewModel by viewModels()

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, PickActivity::class.java).apply {
                // data goes here
            }
        }
    }


    private val progressSound by lazy {
        MediaPlayer.create(this, R.raw.progress)
    }

    private val rewardSound by lazy {
        MediaPlayer.create(this, R.raw.reward)
    }

    private val progressValueAnimator by lazy {
        ValueAnimator.ofInt(0, 100)
            .apply {
                duration = PickViewModel.DURATION_REVEAL
                interpolator = AccelerateInterpolator()

                addUpdateListener {
                    Timber.d("onCreate: Animated value : ${it.animatedValue}")
                    binding.pbReveal.progress = it.animatedValue as Int
                }
                addListener(
                    onStart = {
                        Timber.d("Started: ")
                        binding.pbReveal.visibility = View.VISIBLE
                    },
                    onEnd = {
                        Timber.d("Ended: ")
                        progressSound.stop()
                        progressSound.prepareAsync()
                        
                        binding.pbReveal.visibility = View.INVISIBLE
                        if (binding.pbReveal.progress == 100) {
                            viewModel.onHoldFinished()
                            rewardSound.start()
                        }
                    },
                    onCancel = {
                        Timber.d("Cancelled")
                        binding.pbReveal.visibility = View.INVISIBLE
                    }
                )
            }

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.bHoldMe.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    progressValueAnimator.start()
                    progressSound.start()
                }
                MotionEvent.ACTION_UP -> {
                    binding.pbReveal.progress = 0
                    progressValueAnimator.cancel()
                }
            }
            false
        }

        viewModel.shouldLaunchFindThiefActivity.observe(this, {
            startActivity(FindThiefActivity.getStartIntent(this, it))
            finish()
        })
    }

}