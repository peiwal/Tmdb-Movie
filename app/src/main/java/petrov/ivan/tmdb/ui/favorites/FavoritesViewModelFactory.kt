package petrov.ivan.tmdb.ui.favorites

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import petrov.ivan.tmdb.database.FavoritesDatabaseDao

class FavoritesViewModelFactory(
    private val database: FavoritesDatabaseDao,
    private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoritesViewModel::class.java)) {
            return FavoritesViewModel(database, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}