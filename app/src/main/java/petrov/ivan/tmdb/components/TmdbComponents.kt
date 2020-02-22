package petrov.ivan.tmdb.components

import android.view.View
import com.squareup.moshi.Moshi
import dagger.Component
import petrov.ivan.tmdb.interfaces.Singleton
import petrov.ivan.tmdb.modules.TmdbModule
import petrov.ivan.tmdb.service.TmdbApi


@Singleton
@Component(modules = arrayOf(TmdbModule::class))
interface TmdbComponents {
    fun getTmdbService(): TmdbApi
    fun getMoshi(): Moshi
}