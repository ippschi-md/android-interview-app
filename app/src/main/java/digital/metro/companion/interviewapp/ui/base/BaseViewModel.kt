package digital.metro.companion.interviewapp.ui.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent

/**
 * Base View Model.
 */
abstract class BaseViewModel(
    application: Application
) : AndroidViewModel(application), KoinComponent {

    // Members

    protected val internalDisposables = CompositeDisposable()

    // AndroidViewModel

    override fun onCleared() {
        internalDisposables.clear()
    }
}
