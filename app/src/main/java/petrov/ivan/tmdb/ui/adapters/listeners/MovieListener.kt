package petrov.ivan.tmdb.ui.adapters.listeners

import petrov.ivan.tmdb.data.TmdbMovie

class MovieListener(val clickListener: (movie: TmdbMovie) -> Unit) {
    fun onClick(movie: TmdbMovie) = clickListener(movie)
}