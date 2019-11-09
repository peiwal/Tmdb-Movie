package petrov.ivan.tmdb.ui.search

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import petrov.ivan.tmdb.R
import petrov.ivan.tmdb.data.TmdbMovie
import petrov.ivan.tmdb.service.TmdbApi
import timber.log.Timber

class SearchViewModel(private val movieService: TmdbApi, application: Application) : AndroidViewModel(application) {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    val searchText = MutableLiveData<String>()
    val searchItems = MutableLiveData<ArrayList<TmdbMovie>>()
    val eventErrorLoadData = MutableLiveData<Boolean>()

    fun loadSuggest(searchText: String?) {
        if (TextUtils.isEmpty(searchText)) {
            searchItems.value = ArrayList()
            return
        }

        uiScope.launch(Dispatchers.Main) {
            val request = movieService.getMovieByQuery(searchText!!, getApplication<Application>().getString(R.string.response_language))

            try {
                val response = request.await()
                if (response.isSuccessful) {
                    val movieResponse = response.body()
                    val suggestions = movieResponse?.results
                    searchItems.value = suggestions?.let{ArrayList(it.filter { it.backdropPath != null })} ?: ArrayList()
                } else Timber.i("loadSuggest: ${response.errorBody().toString()}")
            } catch (e: Exception){
                Timber.e("loadSuggest: $e")
                eventErrorLoadData.value = true
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}