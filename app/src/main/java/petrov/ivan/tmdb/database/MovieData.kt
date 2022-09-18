package petrov.ivan.tmdb.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favorites_movie")
data class MovieData(
    val id: String,
    val rating: Double,
    val title: String,
    val overview: String,
    @ColumnInfo(name = "image_url")
    val imageUrl: String?,
    @ColumnInfo(name = "release_date")
    val releaseDate: String
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_movie")
    var movieId: Long = 0L
}
