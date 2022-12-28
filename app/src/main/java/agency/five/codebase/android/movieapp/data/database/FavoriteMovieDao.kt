package agency.five.codebase.android.movieapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMovieDao {
    @Query("SELECT * FROM DbFavoriteMovie")
    fun favorites(): Flow<List<DbFavoriteMovie>>

    @Query("DELETE FROM DbFavoriteMovie WHERE id = :movieId")
    fun deleteMovie(movieId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(dbFavoriteMovie: DbFavoriteMovie)
}
