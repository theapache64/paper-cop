package com.theapache64.papercop.feature.base

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.theapache64.papercop.utils.extensions.snackBar
import com.theapache64.papercop.utils.extensions.toast
import io.github.inflationx.viewpump.ViewPumpContextWrapper


/**
 * Created by theapache64 : Jul 26 Sun,2020 @ 21:57
 */
abstract class BaseActivity<B : ViewDataBinding, VM : BaseViewModel>(
    @LayoutRes
    private val layoutId: Int
) : AppCompatActivity() {

    companion object {
        private const val KEY_IS_DEBUG_ACTIVITY = "is_debug_activity"
    }

    protected lateinit var binding: B
    abstract val viewModel: VM
    abstract fun onCreate()

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase!!))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = DataBindingUtil.setContentView<B>(
            this,
            layoutId
        )

        // Watching for snackbar message
        viewModel.snackBarMsg.observe(this, Observer {
            val root = getSnackBarRoot()
            when (it) {
                is String -> {
                    root.snackBar(it)
                }
                is Int -> {
                    root.snackBar(it)
                }
                else -> throw IllegalArgumentException("snackBarMsg should be either Int or String")
            }
        })

        // Watching for toast message
        viewModel.toastMsg.observe(this, Observer {
            when (it) {
                is String -> {
                    toast(it)
                }
                is Int -> {
                    toast(it)
                }
                else -> throw IllegalArgumentException("toastMsg should be either Int or String")
            }
        })

        onCreate()
    }

    open fun getSnackBarRoot(): View = binding.root

    fun isDebugActivity(): Boolean {
        return intent.getBooleanExtra(KEY_IS_DEBUG_ACTIVITY, false)
    }

    @Throws(IllegalArgumentException::class)
    protected inline fun <reified T> getSerializableOrThrow(key: String): T {

        val extra = intent.getSerializableExtra(key)
            ?: throw IllegalArgumentException("No serialized found with key '$key'")

        if (extra is T) {
            return extra
        } else {
            throw IllegalArgumentException("'$key' is not ${T::class.java.simpleName}")
        }
    }

    @Throws(IllegalArgumentException::class)
    protected inline fun <reified T : Parcelable> getParcelableOrThrow(key: String): T {

        return intent.getParcelableExtra(key)
            ?: throw IllegalArgumentException("No parcelable found with key '$key'")
    }


}
