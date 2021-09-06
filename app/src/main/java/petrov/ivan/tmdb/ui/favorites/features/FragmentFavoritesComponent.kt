package petrov.ivan.tmdb.ui.favorites.features

import dagger.Component
import petrov.ivan.tmdb.ui.adapters.MovieListAdapter

@Component(modules = arrayOf(FragmentFavoritesModule::class))
@FragmentFavoritesScope
interface FragmentFavoritesComponent {

    fun getMovieListAdapter(): MovieListAdapter

}