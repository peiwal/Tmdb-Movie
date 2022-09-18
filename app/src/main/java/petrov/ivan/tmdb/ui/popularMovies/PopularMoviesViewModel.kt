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
import petrov.ivan.tmdb.database.MovieData
import petrov.ivan.tmdb.service.TmdbApi
import petrov.ivan.tmdb.ui.popularMovies.repository.MovieRepository

class PopularMoviesViewModel(movieService: TmdbApi, application: Application) : AndroidViewModel(application) {
    private val _pagingData: MutableLiveData<PagingData<MovieData>> = MutableLiveData<PagingData<MovieData>>(PagingData.empty())
    val pagingData: LiveData<PagingData<MovieData>> = _pagingData

    private val repository = MovieRepository(movieService)

    init {
        viewModelScope.launch {
            searchRepo().collectLatest {
                _pagingData.value = it
            }
        }
    }

    private fun searchRepo(): Flow<PagingData<MovieData>> {
        val responseLanguage = getApplication<Application>().getString(R.string.response_language)
        return repository.getSearchResultStream(responseLanguage).cachedIn(viewModelScope)
    }
}
