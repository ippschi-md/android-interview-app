package digital.metro.companion.interviewapp

import android.app.Application
import com.nhaarman.mockitokotlin2.atLeast
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import digital.metro.companion.interviewapp.data.services.github.GithubService
import digital.metro.companion.interviewapp.data.services.github.GithubUser
import digital.metro.companion.interviewapp.data.storage.GithubStorage
import digital.metro.companion.interviewapp.di.appModule
import digital.metro.companion.interviewapp.ui.github.GithubViewModel
import digital.metro.companion.interviewapp.utils.extensions.fromJson
import io.reactivex.Observable
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class GithubViewModelTest : KoinTest {

    // Members

    private val viewModel: GithubViewModel by inject()

    private val response: String =
        """[{"login":"mojombo","id":1,"node_id":"MDQ6VXNlcjE=","avatar_url":"https://avatars.githubusercontent.com/u/1?v=4","gravatar_id":"","url":"https://api.github.com/users/mojombo","html_url":"https://github.com/mojombo","followers_url":"https://api.github.com/users/mojombo/followers","following_url":"https://api.github.com/users/mojombo/following{/other_user}","gists_url":"https://api.github.com/users/mojombo/gists{/gist_id}","starred_url":"https://api.github.com/users/mojombo/starred{/owner}{/repo}","subscriptions_url":"https://api.github.com/users/mojombo/subscriptions","organizations_url":"https://api.github.com/users/mojombo/orgs","repos_url":"https://api.github.com/users/mojombo/repos","events_url":"https://api.github.com/users/mojombo/events{/privacy}","received_events_url":"https://api.github.com/users/mojombo/received_events","type":"User","site_admin":false},{"login":"defunkt","id":2,"node_id":"MDQ6VXNlcjI=","avatar_url":"https://avatars.githubusercontent.com/u/2?v=4","gravatar_id":"","url":"https://api.github.com/users/defunkt","html_url":"https://github.com/defunkt","followers_url":"https://api.github.com/users/defunkt/followers","following_url":"https://api.github.com/users/defunkt/following{/other_user}","gists_url":"https://api.github.com/users/defunkt/gists{/gist_id}","starred_url":"https://api.github.com/users/defunkt/starred{/owner}{/repo}","subscriptions_url":"https://api.github.com/users/defunkt/subscriptions","organizations_url":"https://api.github.com/users/defunkt/orgs","repos_url":"https://api.github.com/users/defunkt/repos","events_url":"https://api.github.com/users/defunkt/events{/privacy}","received_events_url":"https://api.github.com/users/defunkt/received_events","type":"User","site_admin":false},{"login":"pjhyett","id":3,"node_id":"MDQ6VXNlcjM=","avatar_url":"https://avatars.githubusercontent.com/u/3?v=4","gravatar_id":"","url":"https://api.github.com/users/pjhyett","html_url":"https://github.com/pjhyett","followers_url":"https://api.github.com/users/pjhyett/followers","following_url":"https://api.github.com/users/pjhyett/following{/other_user}","gists_url":"https://api.github.com/users/pjhyett/gists{/gist_id}","starred_url":"https://api.github.com/users/pjhyett/starred{/owner}{/repo}","subscriptions_url":"https://api.github.com/users/pjhyett/subscriptions","organizations_url":"https://api.github.com/users/pjhyett/orgs","repos_url":"https://api.github.com/users/pjhyett/repos","events_url":"https://api.github.com/users/pjhyett/events{/privacy}","received_events_url":"https://api.github.com/users/pjhyett/received_events","type":"User","site_admin":false},{"login":"wycats","id":4,"node_id":"MDQ6VXNlcjQ=","avatar_url":"https://avatars.githubusercontent.com/u/4?v=4","gravatar_id":"","url":"https://api.github.com/users/wycats","html_url":"https://github.com/wycats","followers_url":"https://api.github.com/users/wycats/followers","following_url":"https://api.github.com/users/wycats/following{/other_user}","gists_url":"https://api.github.com/users/wycats/gists{/gist_id}","starred_url":"https://api.github.com/users/wycats/starred{/owner}{/repo}","subscriptions_url":"https://api.github.com/users/wycats/subscriptions","organizations_url":"https://api.github.com/users/wycats/orgs","repos_url":"https://api.github.com/users/wycats/repos","events_url":"https://api.github.com/users/wycats/events{/privacy}","received_events_url":"https://api.github.com/users/wycats/received_events","type":"User","site_admin":false}]"""

    // Mocks

    private var githubServiceMock: GithubService = mock {
        on {
            getUsers()
        } doReturn Observable.just(
            response.fromJson()
        )
    }

    @Before
    fun before() {
        startKoin {
            modules(appModule.plus(module(override = true) {
                single { mock<Application>() }
                single { mock<GithubStorage>() }

                single { githubServiceMock }
            }))
        }
    }

    @After
    fun after() {
        stopKoin()
    }

    // Tests

    @Test
    fun `load github users`() {

        // GIVEN: a test observer on success
        val testObserver = viewModel.onUsersAvailable.test()

        // WHEN: trying to load users
        viewModel.load()

        val expected = response.fromJson() as List<GithubUser>

        // THEN: wait for result
        testObserver.awaitCount(1)
        testObserver.assertValueAt(0) {
            it[0].id == expected[0].id && it[0].login == expected[0].login
        }

        verify(githubServiceMock, atLeast(1)).getUsers(0)
    }
}
