package petrov.ivan.tmdb.ui.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import petrov.ivan.tmdb.database.FavoritesDatabaseDao
import petrov.ivan.tmdb.database.MovieData
import petrov.ivan.tmdb.ui.utils.launchOnIO

class FavoritesViewModel(private val database: FavoritesDatabaseDao, application: Application) : AndroidViewModel(application) {
    val favoritesList = MutableLiveData<List<MovieData>>()

    fun loadData() {
        viewModelScope.launchOnIO(
            runOnIO = ::getFavorites,
            resultOnMain = ::setFavorites
        )
    }

    private fun getFavorites(): ArrayList<MovieData> {
        val result = ArrayList<MovieData>()
        database.getAllRecords()?.forEach {
            result.add(it)
        }
        return result
    }

    private fun setFavorites(value: ArrayList<MovieData>) {
        favoritesList.value = value
    }
}
