package petrov.ivan.tmdb.components

import dagger.Component
import petrov.ivan.tmdb.interfaces.Singleton
import petrov.ivan.tmdb.modules.TmdbModule
import petrov.ivan.tmdb.service.TmdbApi
import petrov.ivan.tmdb.widget.OneRandomMovieWidget

@Singleton
@Component(modules = arrayOf(TmdbModule::class))
interface TmdbComponents {
    fun getTmdbService(): TmdbApi

    fun inject(provider: OneRandomMovieWidget)
}
