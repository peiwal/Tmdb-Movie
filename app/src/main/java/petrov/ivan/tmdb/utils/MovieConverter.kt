package petrov.ivan.tmdb.utils

import petrov.ivan.tmdb.data.TmdbMovie
import petrov.ivan.tmdb.database.MovieDB

object MovieConverter {
    fun convertToTmdbMovie(movie: MovieDB): TmdbMovie {
        return TmdbMovie(
            movie.imdbId,
            movie.voteAverage,
            movie.voteCount,
            movie.title,
            movie.overview,
            movie.adult,
            movie.video,
            movie.posterPath,
            movie.releaseDate,
            movie.backdropPath
        )
    }

    fun converToMovie(tmdbMovie: TmdbMovie): MovieDB {
        return MovieDB(
            tmdbMovie.id,
            tmdbMovie.voteAverage,
            tmdbMovie.voteCount,
            tmdbMovie.title,
            tmdbMovie.overview,
            tmdbMovie.adult,
            tmdbMovie.video,
            tmdbMovie.posterPath,
            tmdbMovie.backdropPath,
            tmdbMovie.releaseDate
        )
    }
}
