package petrov.ivan.tmdb.ui.popularMovies

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import petrov.ivan.tmdb.R
import petrov.ivan.tmdb.data.TmdbMovie
import petrov.ivan.tmdb.service.TmdbApi
import petrov.ivan.tmdb.ui.popularMovies.repository.MovieRepository

class PopularMoviesViewModel(movieService: TmdbApi, application: Application) : AndroidViewModel(application) {
    private val _pagingData: MutableLiveData<PagingData<TmdbMovie>> = MutableLiveData<PagingData<TmdbMovie>>(PagingData.empty())
    val pagingData: LiveData<PagingData<TmdbMovie>> = _pagingData

    private val repository = MovieRepository(movieService)

    init {
        viewModelScope.launch {
            searchRepo().collectLatest {
                _pagingData.value = it
            }
        }
    }

    private fun searchRepo(): Flow<PagingData<TmdbMovie>> {
        val responseLanguage = getApplication<Application>().getString(R.string.response_language)
        return repository.getSearchResultStream(responseLanguage).cachedIn(viewModelScope)
    }
}