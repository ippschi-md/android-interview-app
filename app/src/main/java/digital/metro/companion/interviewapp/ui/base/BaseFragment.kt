package digital.metro.companion.interviewapp.ui.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Base Fragment for all other fragment.
 */
abstract class BaseFragment : Fragment() {

    // Members

    private val compositeDisposable by lazy { CompositeDisposable() }

    // Fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Register LiveData
        bindLiveData()

        // Collect all Disposables
        bindFromViewModel().forEach {
            compositeDisposable.add(it)
        }
    }

    override fun onDestroyView() {

        // Clear all subscriptions
        compositeDisposable.clear()

        super.onDestroyView()
    }

    override fun onDestroy() {

        // Clear all subscriptions
        compositeDisposable.clear()

        super.onDestroy()
    }

    /**
     * Method to register LiveData observers
     */
    protected open fun bindLiveData() {}

    // Abstract API

    /**
     * Bind everything from the view model.
     */
    abstract fun bindFromViewModel(): List<Disposable>
}
