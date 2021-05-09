package petrov.ivan.tmdb.ui.movieInfo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import petrov.ivan.tmdb.data.TmdbMovie
import petrov.ivan.tmdb.database.FavoritesDatabaseDao
import petrov.ivan.tmdb.utils.MovieConverter

class MovieInfoViewModel(private val database: FavoritesDatabaseDao, application: Application, movie: TmdbMovie) : AndroidViewModel(application) {
    val tmdbMovie = MutableLiveData<TmdbMovie>()
    val eventNeedShowDialog =  MutableLiveData<Boolean>()
    val isFavorite = MutableLiveData<Boolean>()

    init {
        tmdbMovie.value = movie
        updateFavoriteValue(movie)
    }

    fun fbFavoriteClick() {
        val movie = tmdbMovie.value ?: return
        if (isFavorite.value?: false) {
            eventNeedShowDialog.value = true
        } else {
            insertToDatabase(movie)
        }
    }

    fun getMovie() : TmdbMovie {
        return tmdbMovie.value!!
    }

    private fun insertToDatabase(movie: TmdbMovie) {
        viewModelScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                database.insert(MovieConverter.converToMovie(movie))
                updateFavoriteValue(movie)
            }
        }
    }

    fun deleteMovieFromFavorite() {
        val movie = tmdbMovie.value ?: return
        deleteFromDatabase(movie)
    }

    private fun deleteFromDatabase(movie: TmdbMovie) {
        viewModelScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                database.delete(movie.id)
                updateFavoriteValue(movie)
            }
        }
    }

    private fun updateFavoriteValue(movie: TmdbMovie) {
        viewModelScope.launch(Dispatchers.Main) {
            isFavorite.value = getById(movie)
        }
    }

    suspend fun getById(movie: TmdbMovie) : Boolean {
        return withContext(Dispatchers.IO) {
            val tmdbMovie = database.getById(movie.id)
            tmdbMovie != null
        }
    }

    fun dialogShowed() {
        eventNeedShowDialog.value = false
    }
}