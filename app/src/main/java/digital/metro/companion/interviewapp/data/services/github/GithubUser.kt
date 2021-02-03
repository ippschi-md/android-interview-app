package digital.metro.companion.interviewapp.data.services.github

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class GithubUser(
    @PrimaryKey
    @SerializedName("id") var id: Long? = null,
    @SerializedName("login") var login: String? = null,
    @SerializedName("avatar_url") var avatarUrl: String? = null,
    @SerializedName("html_url") var htmlUrl: String? = null,
) : RealmObject()
