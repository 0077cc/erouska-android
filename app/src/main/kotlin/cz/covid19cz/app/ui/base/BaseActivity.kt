package cz.covid19cz.app.ui.base

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import cz.covid19cz.app.di.activityModule
import com.google.android.material.snackbar.Snackbar
import cz.covid19cz.app.utils.REQUEST_ENABLE_BT
import org.kodein.di.Copy
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.android.retainedKodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

abstract class BaseActivity : AppCompatActivity(), KodeinAware {

    protected val viewModelFactory: ViewModelProvider.Factory by instance()

    private val _parentKodein by closestKodein()

    override val kodein: Kodein by retainedKodein {
        extend(_parentKodein, copy = Copy.All)
        bind<Activity>() with singleton { this@BaseActivity }
        bind<Context>("ActivityContext") with singleton { this@BaseActivity }
        import(activityModule)
    }

    protected fun showSnackbar(@StringRes stringRes: Int) {
        showSnackbar(getString(stringRes))
    }

    protected fun showSnackbar(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
    }
}