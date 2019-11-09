package petrov.ivan.tmdb.data

import com.squareup.moshi.Json

data class TmdbMovie(
    var id: Int,
    @field:Json(name = "vote_average")
    val voteAverage: Double,
    @field:Json(name = "vote_count")
    val voteCount: Int,
    val title: String,
    val overview: String,
    val adult: Boolean,
    val video: Boolean,
    @field:Json(name = "poster_path")
    val posterPath: String,
    @field:Json(name = "release_date")
    val releaseDate: String,
    @field:Json(name = "backdrop_path")
    val backdropPath: String?
)

data class TmdbMovieResponse (
    val results: List<TmdbMovie>
)