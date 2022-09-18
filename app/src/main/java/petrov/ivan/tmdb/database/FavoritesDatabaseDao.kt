package petrov.ivan.tmdb.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoritesDatabaseDao {
    @Insert
    fun insert(movie: MovieData)

    @Query("DELETE from favorites_movie WHERE id = :id ")
    fun delete(id: String)

    @Query("SELECT * from favorites_movie ORDER BY id_movie desc")
    fun getAllRecords(): List<MovieData>?

    @Query("SELECT * from favorites_movie WHERE id = :id")
    fun getById(id: String): MovieData?
}
