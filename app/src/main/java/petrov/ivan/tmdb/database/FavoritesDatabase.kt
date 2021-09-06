package petrov.ivan.tmdb.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MovieDB::class], version = 2,  exportSchema = false)
abstract class FavoritesDatabase : RoomDatabase() {
    abstract val favoritesDatabaseDao: FavoritesDatabaseDao

    companion object {

        @Volatile private var instance: FavoritesDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            FavoritesDatabase::class.java,
            "favorites_database")
            .fallbackToDestructiveMigration()
            .build()
    }
}