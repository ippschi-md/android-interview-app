package digital.metro.companion.interviewapp.data.storage

import digital.metro.companion.interviewapp.data.database.DatabaseManager
import digital.metro.companion.interviewapp.data.services.github.GithubUser
import org.koin.core.KoinComponent
import org.koin.core.inject

class GithubStorage : KoinComponent {

    private val databaseManager:DatabaseManager by inject()

    fun saveUsers(userList: List<GithubUser>) {
        databaseManager.getDatabase().use { realm ->
            realm.executeTransaction { transaction ->
                userList.forEach {
                    transaction.insertOrUpdate(it)
                }
            }
        }
    }
}
