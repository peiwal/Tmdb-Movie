package petrov.ivan.tmdb.data

import com.squareup.moshi.Json
import petrov.ivan.tmdb.database.MovieData
import java.io.Serializable

data class TmdbMovieDataResponse(
    var id: String,
    @field:Json(name = "vote_average")
    val rating: Double,
    val title: String,
    val overview: String,
    @field:Json(name = "release_date")
    val releaseDate: String,
    @field:Json(name = "backdrop_path")
    val imageUrl: String?
) : Serializable {
    
    fun converToMovieData(): MovieData {
        return MovieData(
            id,
            rating,
            title,
            overview,
            imageUrl,
            releaseDate
        )
    }
}

data class TmdbMovieResponse(
    val results: List<TmdbMovieDataResponse>,
    @field:Json(name = "total_pages")
    val totalPages: Int,
    @field:Json(name = "page")
    val page: Int
)
