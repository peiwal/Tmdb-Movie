package petrov.ivan.tmdb.ui.popularMovies.features

import dagger.Component
import petrov.ivan.tmdb.components.TmdbComponents
import petrov.ivan.tmdb.service.TmdbApi
import petrov.ivan.tmdb.ui.adapters.MovieListAdapter

@Component(modules = arrayOf(FragmentPopularMoviesModule::class))
@FragmentPopularMoviesScope
interface FragmentPopularMoviesComponent {
    fun getMovieListAdapter(): MovieListAdapter
}