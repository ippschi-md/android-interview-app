package digital.metro.companion.interviewapp.utils

import io.realm.RealmMigration

abstract class RealmMigration : RealmMigration {

    // Object

    // https://stackoverflow.com/questions/36907001/open-realm-with-new-realmconfiguration

    override fun hashCode(): Int = 37

    override fun equals(other: Any?): Boolean = other is RealmMigration
}
