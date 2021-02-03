package digital.metro.companion.interviewapp.data.database

import digital.metro.companion.interviewapp.data.services.github.GithubUser
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.annotations.RealmModule

class DatabaseManager() {

    // Public api

    fun getDatabase(): Realm = Realm.getInstance(getConfig())

    // Private api

    private fun getConfig() =
        RealmConfiguration.Builder()
            .name("General.realm")
            .modules(GeneralModule())
            .schemaVersion(2)
            .migration(DatabaseMigrations)
            .build()

    @RealmModule(
        classes = [
            GithubUser::class,
        ]
    )
    class GeneralModule
}
