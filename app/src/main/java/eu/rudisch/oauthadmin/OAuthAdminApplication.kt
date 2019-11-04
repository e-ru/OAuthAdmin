package eu.rudisch.oauthadmin

import android.app.Application
import timber.log.Timber

class OAuthAdminApplication  : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}