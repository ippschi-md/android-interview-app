package digital.metro.companion.interviewapp.di

import digital.metro.companion.interviewapp.data.api.ApiClient
import digital.metro.companion.interviewapp.data.database.DatabaseManager
import digital.metro.companion.interviewapp.data.services.github.GithubService
import digital.metro.companion.interviewapp.data.storage.GithubStorage
import digital.metro.companion.interviewapp.ui.github.GithubViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // ViewModels

    viewModel { GithubViewModel(get()) }

    // Services

    single {
        ApiClient<GithubService>().createApi(
            host = "https://api.github.com/",
            clazz = GithubService::class.java
        )
    }

    // Others

    single { DatabaseManager() }
    single { GithubStorage() }
}
