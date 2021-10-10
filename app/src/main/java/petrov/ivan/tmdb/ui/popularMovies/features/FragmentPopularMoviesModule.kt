package petrov.ivan.tmdb.ui.popularMovies.features

import android.content.Context
import dagger.Module
import dagger.Provides
import petrov.ivan.tmdb.ui.adapters.MovieListPagingAdapter
import petrov.ivan.tmdb.ui.adapters.listeners.MovieListener

@Module
class FragmentPopularMoviesModule(private val context: Context, private val itemClickListener: MovieListener) {

    @Provides
    @FragmentPopularMoviesScope
    fun movieListAdapter(): MovieListPagingAdapter {
        return MovieListPagingAdapter(context, itemClickListener)
    }
}
