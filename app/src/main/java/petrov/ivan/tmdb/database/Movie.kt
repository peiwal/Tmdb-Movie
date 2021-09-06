package petrov.ivan.tmdb.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites_movie")
data class MovieDB(
    @ColumnInfo(name = "id_imgb")
    val imdbId: Int,
    @ColumnInfo(name = "vote_average")
    val voteAverage: Double,
    @ColumnInfo(name = "vote_count")
    val voteCount: Int,
    val title: String,
    val overview: String,
    val adult: Boolean,
    val video: Boolean,
    @ColumnInfo(name = "poster_path")
    val posterPath: String,
    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String?,
    @ColumnInfo(name = "release_date")
    val releaseDate: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_movie")
    var movieId: Long = 0L
}
