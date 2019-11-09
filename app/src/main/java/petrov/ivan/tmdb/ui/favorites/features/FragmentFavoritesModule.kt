package petrov.ivan.tmdb.ui.favorites.features

import android.content.Context
import dagger.Module
import dagger.Provides
import petrov.ivan.tmdb.ui.adapters.MovieListAdapter

@Module
class FragmentFavoritesModule(private val context: Context, private val itemClickLIstener: MovieListAdapter.MovieListener) {

    @Provides
    @FragmentFavoritesScope
    fun movieListAdapter(): MovieListAdapter {
        return MovieListAdapter(context, itemClickLIstener)
    }
}
