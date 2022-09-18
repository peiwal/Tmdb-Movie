package petrov.ivan.tmdb.ui.adapters.listeners

import petrov.ivan.tmdb.database.MovieData

class SuggestionListener(val clickListener: (movie: MovieData) -> Unit) {
    fun onClick(movie: MovieData) = clickListener(movie)
}
