package petrov.ivan.tmdb.application

import android.app.Application
import petrov.ivan.tmdb.components.DaggerTmdbComponents
import petrov.ivan.tmdb.components.TmdbComponents
import petrov.ivan.tmdb.modules.ContextModule
import timber.log.Timber

class TmdbApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

    private val tmdbComponents: TmdbComponents by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerTmdbComponents.builder()
            .contextModule(ContextModule(this))
            .build()
    }

    fun getTmdbComponent() : TmdbComponents {
        return tmdbComponents
    }
}