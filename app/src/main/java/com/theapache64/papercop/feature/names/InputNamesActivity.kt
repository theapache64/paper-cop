package com.theapache64.papercop.feature.names

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.core.view.children
import com.google.android.material.textfield.TextInputLayout
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

        require(count != -1) { "$KEY_COUNT missing" }

        viewModel.init(count)


        viewModel.generateInputFields.observe(this, { inputCount ->
            val inflater = LayoutInflater.from(this)
            for (i in 0 until inputCount) {
                val textInputLayout = inflater.inflate(
                    R.layout.item_input_name,
                    binding.llInputNames,
                    false
                ) as TextInputLayout

                textInputLayout.hint = "Person ${i + 1}"

                binding.llInputNames.addView(textInputLayout)
            }
        })

        viewModel.shouldValidateInputNames.observe(this, {
            if (it) {
                val names = mutableListOf<String>()
                for (child in binding.llInputNames.children) {
                    child as TextInputLayout
                    val name = child.editText?.text?.trim().toString() ?: ""
                    if (name.isBlank()) {
                        // empty name
                        child.error = getString(R.string.input_names_error_empty)
                    } else {
                        if (names.contains(name)) {
                            // duplicate name
                            child.error = getString(R.string.input_names_error_duplicate, name)
                        } else {
                            // valid name
                            child.error = null
                            names.add(name)
                        }
                    }
                }

                if (binding.llInputNames.childCount == names.size) {
                    // All valid
                    viewModel.onValidNames(names)
                } else {
                    // Some names are invalid
                    viewModel.onInvalidNames()
                }
            }
        })

        viewModel.launchPickActivity.observe(this, {

        })

    }
}