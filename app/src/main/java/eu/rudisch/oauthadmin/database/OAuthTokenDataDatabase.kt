package eu.rudisch.oauthadmin.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface OAuthTokenDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(oAuthTokenDataEntity: OAuthTokenDataEntity)

    @Query("SELECT * from oauth_token_data WHERE userName = :key")
    fun get(key: String): LiveData<OAuthTokenDataEntity>
}

@Database(entities = [OAuthTokenDataEntity::class], version = 1)
abstract class OAuthTokenDataDatabase: RoomDatabase() {
    abstract val oAuthTokenDataDao: OAuthTokenDataDao
}

private lateinit var INSTANCE: OAuthTokenDataDatabase

fun getDatabase(context: Context): OAuthTokenDataDatabase {
    synchronized(OAuthTokenDataDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                OAuthTokenDataDatabase::class.java,
                "videos").build()
        }
    }
    return INSTANCE
}