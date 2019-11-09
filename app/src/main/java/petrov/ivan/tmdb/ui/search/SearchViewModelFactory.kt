package petrov.ivan.tmdb.ui.search

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import petrov.ivan.tmdb.service.TmdbApi

class SearchViewModelFactory(private val movieService: TmdbApi,
                             private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(movieService, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}