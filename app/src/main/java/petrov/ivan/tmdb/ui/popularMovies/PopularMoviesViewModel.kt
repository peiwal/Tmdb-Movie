package petrov.ivan.tmdb.ui.popularMovies

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import petrov.ivan.tmdb.R
import petrov.ivan.tmdb.data.TmdbMovie
import petrov.ivan.tmdb.service.TmdbApi
import timber.log.Timber

class PopularMoviesViewModel(private val movieService: TmdbApi, application: Application) : AndroidViewModel(application) {
    val recyclerViewPosition = MutableLiveData<Int>()
    val movieList = MutableLiveData<List<TmdbMovie>>()
    val eventLoadComplete = MutableLiveData<Boolean>()
    val eventErrorLoadData = MutableLiveData<Boolean>()

    fun loadData() {
        viewModelScope.launch(Dispatchers.Main) {
            val request = movieService.getPopularMovie(getApplication<Application>().getString(R.string.response_language))

            try {
                val response = request.await()
                if(response.isSuccessful){
                    movieList.value = response.body()?.results ?: ArrayList()
                } else {
                    Timber.i("loadData ${response.errorBody().toString()}")
                }
            } catch (e: Throwable){
                Timber.e("loadData ${e}")
                eventErrorLoadData.value = true
            }

            eventLoadComplete.value = true
        }
    }
}