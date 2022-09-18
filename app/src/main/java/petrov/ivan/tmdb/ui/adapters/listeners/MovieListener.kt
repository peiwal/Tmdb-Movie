package petrov.ivan.tmdb.ui.adapters.listeners

import android.view.View
import petrov.ivan.tmdb.database.MovieData

class MovieListener(val clickListener: (movie: MovieData, view: View) -> Unit) {
    fun onClick(movie: MovieData, view: View) = clickListener(movie, view)
}
