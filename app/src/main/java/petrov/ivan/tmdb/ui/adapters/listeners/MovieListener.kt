package petrov.ivan.tmdb.ui.adapters.listeners

import android.view.View
import petrov.ivan.tmdb.data.TmdbMovie

class MovieListener(val clickListener: (movie: TmdbMovie, view: View) -> Unit) {
    fun onClick(movie: TmdbMovie, view: View) = clickListener(movie, view)
}
