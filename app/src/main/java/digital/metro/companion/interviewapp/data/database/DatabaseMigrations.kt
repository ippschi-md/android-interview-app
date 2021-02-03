package digital.metro.companion.interviewapp.data.database

import android.util.Log
import digital.metro.companion.interviewapp.utils.RealmMigration
import io.realm.DynamicRealm
import io.realm.FieldAttribute
import kotlin.reflect.KFunction1

@Suppress("MemberVisibilityCanBePrivate")
object DatabaseMigrations : RealmMigration() {

    // RealmMigration

    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        Log.i(
            DatabaseMigrations::class.java.simpleName,
            "Migration from version $oldVersion to $newVersion in progress"
        )

        // loop through all the migrations
        val allVersionsToMigrateTo = LongRange(oldVersion + 1, newVersion)
        allVersionsToMigrateTo.forEach {
            migrationsMap[it]?.invoke(realm)
        }
    }

    // Members

    val migrationsMap: Map<Long, KFunction1<DynamicRealm, Unit>> = mapOf(
        1L to DatabaseMigrations::migrateGeneralToVersion1,
        2L to DatabaseMigrations::migrateGeneralToVersion2,
    )

    // Public API

    fun migrateGeneralToVersion1(realm: DynamicRealm) {

        // Version 1.1

        realm.schema.create("GithubUser")
            .addField(
                "id",
                Long::class.javaObjectType,
                FieldAttribute.PRIMARY_KEY,
                FieldAttribute.REQUIRED
            )
            .addField("login", String::class.javaObjectType)
    }

    fun migrateGeneralToVersion2(realm: DynamicRealm) {

        // Version 1.2

        realm.schema.get("GithubUser")
            ?.addField("avatarUrl", String::class.javaObjectType)
            ?.addField("htmlUrl", String::class.javaObjectType)
    }
}
