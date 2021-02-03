package digital.metro.companion.interviewapp.data.services.github

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {

    @GET("users")
    fun getUsers(
        @Query("since") since: Int = 0
    ): Observable<List<GithubUser>>
}
