package petrov.ivan.tmdb.ui.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import petrov.ivan.tmdb.data.TmdbMovie
import petrov.ivan.tmdb.database.FavoritesDatabaseDao
import petrov.ivan.tmdb.ui.utils.launchOnIO
import petrov.ivan.tmdb.utils.MovieConverter

class FavoritesViewModel(private val database: FavoritesDatabaseDao, application: Application) : AndroidViewModel(application) {
    val favoritesList = MutableLiveData<List<TmdbMovie>>()

    fun loadData() {
        viewModelScope.launchOnIO(
            runOnIO = ::getFavorites,
            resultOnMain = ::setFavorites
        )
    }

    private fun getFavorites(): ArrayList<TmdbMovie> {
        val result = ArrayList<TmdbMovie>()
        database.getAllRecords()?.forEach {
            result.add(MovieConverter.convertToTmdbMovie(it))
        }
        return result
    }

    private fun setFavorites(value: ArrayList<TmdbMovie>) {
        favoritesList.value = value
    }
}
