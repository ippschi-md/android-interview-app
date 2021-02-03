package digital.metro.companion.interviewapp

import android.app.Application
import digital.metro.companion.interviewapp.di.appModule
import io.realm.Realm
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

@Suppress("unused") // used in AndroidManifest.xml
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(appModule)
        }
    }
}
