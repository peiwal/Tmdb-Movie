package petrov.ivan.tmdb.ui.search

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import petrov.ivan.tmdb.R
import petrov.ivan.tmdb.database.MovieData
import petrov.ivan.tmdb.service.TmdbApi
import timber.log.Timber

class SearchViewModel(
    private val movieService: TmdbApi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main,
    application: Application
) :
    AndroidViewModel(application) {

    val searchText = MutableStateFlow<String?>(null)
    val searchItems = MutableStateFlow<ArrayList<MovieData>>(arrayListOf())
    val eventErrorLoadData = MutableStateFlow(false)

    init {
        viewModelScope.launch(dispatcher) {
            searchText.debounce(DEBOUNCE)
                .collectLatest {
                    loadSuggest(it)
                }
        }
    }

    private fun loadSuggest(searchText: String?) {
        if (TextUtils.isEmpty(searchText)) {
            searchItems.value = ArrayList()
            return
        }

        viewModelScope.launch(dispatcher) {
            try {
                val response = movieService.getMovieByQuery(searchText!!, getApplication<Application>().getString(R.string.response_language))
                if (response.isSuccessful) {
                    val movieResponse = response.body()
                    val suggestions = movieResponse?.results?.map { it.converToMovieData() }
                    searchItems.value = suggestions?.let { ArrayList(it.filter { it.imageUrl != null }) } ?: ArrayList()
                } else Timber.i("loadSuggest: ${response.errorBody()}")
            } catch (e: Exception) {
                Timber.e("loadSuggest: $e")
                eventErrorLoadData.value = true
            }
        }
    }

    companion object {
        const val DEBOUNCE = 500L
    }
}
