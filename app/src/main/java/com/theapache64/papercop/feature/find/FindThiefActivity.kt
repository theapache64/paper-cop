package com.theapache64.papercop.feature.find


import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import androidx.activity.viewModels
import com.theapache64.papercop.R
import com.theapache64.papercop.core.Director
import com.theapache64.papercop.data.local.entities.players.PlayerEntity
import com.theapache64.papercop.databinding.ActivityFindThiefBinding
import com.theapache64.papercop.feature.base.BaseActivity
import com.theapache64.papercop.model.Role
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FindThiefActivity :
    BaseActivity<ActivityFindThiefBinding, FindThiefViewModel>(R.layout.activity_find_thief) {

    private var adapter: FindThiefAdapter? = null
    private val dummyRolesMap by lazy {
        hashMapOf(
            Pair(
                PlayerEntity(0, "Daniel", 0),
                Director.availableRoles[0]
            ),
            Pair(
                PlayerEntity(0, "Albon", 0),
                Director.availableRoles[1]
            ),
            Pair(
                PlayerEntity(0, "Max", 0),
                Director.availableRoles[2]
            ),
        )
    }

    override val viewModel: FindThiefViewModel by viewModels()

    private val correctSound by lazy {
        MediaPlayer.create(this, R.raw.correct)
    }

    private val incorrectSound by lazy {
        MediaPlayer.create(this, R.raw.incorrect)
    }

    companion object {
        private const val KEY_ROLES_MAP = "roles_map"

        fun getStartIntent(context: Context, rolesMap: HashMap<PlayerEntity, Role>): Intent {
            return Intent(context, FindThiefActivity::class.java).apply {
                // data goes here
                putExtra(KEY_ROLES_MAP, rolesMap)
            }
        }
    }

    override fun onCreate() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        val rolesMap: HashMap<PlayerEntity, Role> = if (isDebugActivity()) {
            dummyRolesMap
        } else {
            getSerializableOrThrow(KEY_ROLES_MAP)
        }

        viewModel.init(rolesMap)

        viewModel.rolesMap.observe(this, {
            this.adapter = FindThiefAdapter(this, it) { position ->
                viewModel.onItemClicked(position)
            }
            binding.rvFindThief.adapter = adapter
        })



        viewModel.adapterState.observe(this, { newState ->
            adapter?.state = newState
            adapter?.notifyDataSetChanged()
        })

        viewModel.result.observe(this, { result ->
            when (result) {
                FindThiefViewModel.RESULT_CORRECT -> {
                    correctSound.start()
                }

                FindThiefViewModel.RESULT_INCORRECT -> {
                    incorrectSound.start()
                }
            }

        })

        viewModel.shouldFinishFind.observe(this, {
            if (it) {
                finish()
            }
        })
    }

}