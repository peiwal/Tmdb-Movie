package petrov.ivan.tmdb.ui.favorites

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

class FavoritesViewModel(private val database: FavoritesDatabaseDao, application: Application) : AndroidViewModel(application) {
    val favoritesList = MutableLiveData<List<TmdbMovie>>()

    fun loadData() {
        viewModelScope.launch(Dispatchers.Main) {
            favoritesList.value = getFavoritesListConverted()
        }
    }

    private suspend fun getFavoritesListConverted(): List<TmdbMovie> {
        return withContext(Dispatchers.IO) {
            val movieList = database.getAllRecords()
            val mutableList = ArrayList<TmdbMovie>()
            with(movieList) {
                this?.forEach {
                    mutableList.add(MovieConverter.convertToTmdbMovie(it))
                }
                mutableList
            }
        }
    }
}