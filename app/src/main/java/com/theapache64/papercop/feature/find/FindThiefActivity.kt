package com.theapache64.papercop.feature.find


import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.media.MediaPlayer
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.theapache64.papercop.R
import com.theapache64.papercop.core.Director
import com.theapache64.papercop.data.local.entities.players.PlayerEntity
import com.theapache64.papercop.databinding.ActivityFindThiefBinding
import com.theapache64.papercop.feature.base.BaseActivity
import com.theapache64.papercop.model.Role
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class FindThiefActivity :
    BaseActivity<ActivityFindThiefBinding, FindThiefViewModel>(R.layout.activity_find_thief) {

    private var adapter: FindThiefAdapter? = null
    private val dummyRolesMap by lazy {
        hashMapOf<PlayerEntity, Role>().apply {
            for ((index, role) in Director.availableRoles.withIndex()) {
                put(
                    PlayerEntity(0, "Player $index", 0),
                    role
                )
            }
        }
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
                R.string.find_correct -> {
                    correctSound.start()
                }

                R.string.find_incorrect -> {
                    incorrectSound.start()
                }
            }

        })

        viewModel.shouldFinishFind.observe(this, {
            if (it) {
                finish()
            }
        })

        viewModel.showConfirmDialog.observe(this, { thiefPosition ->
            val thief = adapter!!.keyList[thiefPosition]
            val dialog = AlertDialog.Builder(this)
                .setTitle(R.string.find_dialog_confirm_title)
                .setMessage(
                    getString(
                        R.string.find_dialog_confirm_message, thief.name.toLowerCase(
                            Locale.getDefault()
                        )
                    )
                )
                .setPositiveButton(R.string.action_yes) { _: DialogInterface, _: Int ->
                    viewModel.onThiefConfirmed(thiefPosition)
                }
                .setNegativeButton(R.string.action_no) { dialog: DialogInterface, _: Int ->
                }.create()

            dialog.show()
        })
    }

}