package digital.metro.companion.interviewapp.ui.github

import android.app.Application
import android.util.Log
import com.jakewharton.rxrelay2.PublishRelay
import digital.metro.companion.interviewapp.data.services.github.GithubService
import digital.metro.companion.interviewapp.data.services.github.GithubUser
import digital.metro.companion.interviewapp.data.storage.GithubStorage
import digital.metro.companion.interviewapp.ui.base.BaseViewModel
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import org.koin.core.inject

class GithubViewModel(application: Application) : BaseViewModel(application) {

    private companion object {
        private val TAG = GithubViewModel::class.java.simpleName
    }

    private val service: GithubService by inject()
    private val storage: GithubStorage by inject()

    val onUsersAvailable = PublishRelay.create<List<GithubUser>>()
    val onError = PublishRelay.create<Unit>()

    fun load() {
        service.getUsers()
            .subscribeOn(Schedulers.io())
            .subscribe({ userList ->
                storage.saveUsers(userList)
                onUsersAvailable.accept(userList)
            }, {
                Log.e(TAG, "onError", it)
                onError.accept(Unit)
            })
            .addTo(internalDisposables)
    }
}
