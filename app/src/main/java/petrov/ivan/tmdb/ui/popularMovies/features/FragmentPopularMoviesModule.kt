package petrov.ivan.tmdb.ui.popularMovies.features

import android.content.Context
import dagger.Module
import dagger.Provides
import petrov.ivan.tmdb.ui.adapters.MovieListAdapter

@Module
class FragmentPopularMoviesModule(private val context: Context, private val itemClickListener: MovieListAdapter.MovieListener) {

    @Provides
    @FragmentPopularMoviesScope
    fun movieListAdapter(): MovieListAdapter {
        return MovieListAdapter(context, itemClickListener)
    }
}
