package petrov.ivan.tmdb.ui.movieInfo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import petrov.ivan.tmdb.database.FavoritesDatabaseDao
import petrov.ivan.tmdb.database.MovieData
import petrov.ivan.tmdb.ui.utils.launchOnIO

class MovieInfoViewModel(private val database: FavoritesDatabaseDao, application: Application, movie: MovieData) : AndroidViewModel(application) {
    val tmdbMovie = MutableLiveData<MovieData>()
    val eventNeedShowDialog = MutableLiveData<Boolean>()
    val isFavorite = MutableLiveData<Boolean>()

    init {
        tmdbMovie.value = movie
        updateFavoriteValue(movie)
    }

    fun fbFavoriteClick() {
        val movie = tmdbMovie.value ?: return
        if (isFavorite.value ?: false) {
            eventNeedShowDialog.value = true
        } else {
            insertToDatabase(movie)
        }
    }

    private fun insertToDatabase(movie: MovieData) {
        viewModelScope.launchOnIO(runOnIO = { database.insert(movie) }) {
            updateFavoriteValue(movie)
        }
    }

    fun deleteMovieFromFavorite() {
        val movie = tmdbMovie.value ?: return
        deleteFromDatabase(movie)
    }

    private fun deleteFromDatabase(movie: MovieData) {
        viewModelScope.launchOnIO(runOnIO = { database.delete(movie.id) }) {
            updateFavoriteValue(movie)
        }
    }

    private fun updateFavoriteValue(movie: MovieData) {
        viewModelScope.launchOnIO(runOnIO = { database.getById(movie.id) != null }) {
            isFavorite.value = it
        }
    }

    fun dialogShowed() {
        eventNeedShowDialog.value = false
    }
}
