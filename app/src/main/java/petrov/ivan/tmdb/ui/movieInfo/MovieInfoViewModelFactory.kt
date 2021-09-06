package petrov.ivan.tmdb.ui.movieInfo

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import petrov.ivan.tmdb.data.TmdbMovie
import petrov.ivan.tmdb.database.FavoritesDatabaseDao

class MovieInfoViewModelFactory(private val database: FavoritesDatabaseDao,
                                private val application: Application,
                                private val movie: TmdbMovie) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieInfoViewModel::class.java)) {
            return MovieInfoViewModel(database, application, movie) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}