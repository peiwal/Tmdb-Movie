package petrov.ivan.tmdb.ui.popularMovies

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import petrov.ivan.tmdb.service.TmdbApi

class PopularMoviesViewModelFactory(
    private val movieService: TmdbApi,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PopularMoviesViewModel::class.java)) {
            return PopularMoviesViewModel(movieService, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
