package petrov.ivan.tmdb.ui.favorites.features

import android.content.Context
import dagger.Module
import dagger.Provides
import petrov.ivan.tmdb.ui.adapters.MovieListAdapter
import petrov.ivan.tmdb.ui.adapters.listeners.MovieListener

@Module
class FragmentFavoritesModule(private val context: Context, private val itemClickLIstener: MovieListener) {

    @Provides
    @FragmentFavoritesScope
    fun movieListAdapter(): MovieListAdapter {
        return MovieListAdapter(context, itemClickLIstener).apply { setHasStableIds(true) }
    }
}
