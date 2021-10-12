package petrov.ivan.tmdb.application

import android.app.Application
import petrov.ivan.tmdb.components.DaggerTmdbComponents
import petrov.ivan.tmdb.components.TmdbComponents
import timber.log.Timber

class TmdbApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

    private val tmdbComponents: TmdbComponents by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerTmdbComponents.builder()
            .build()
    }

    fun getTmdbComponent(): TmdbComponents {
        return tmdbComponents
    }
}
