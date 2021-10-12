package petrov.ivan.tmdb.ui.adapters.listeners

import petrov.ivan.tmdb.data.TmdbMovie

class SuggestionListener(val clickListener: (movie: TmdbMovie) -> Unit) {
    fun onClick(movie: TmdbMovie) = clickListener(movie)
}
