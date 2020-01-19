package eu.rudisch.oauthadmin.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Dao
interface OAuthAdminDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAccessToken(oAuthTokenDataEntity: OAuthTokenDataEntity)

    @Query("SELECT * from oauth_token_data WHERE userName = :key")
    fun getAccessToken(key: String): OAuthTokenDataEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllOAuthUsers(vararg oAuthUsers: OAuthUserEntity)

    @Query("select * from oauth_users")
    fun getOAuthUsers(): LiveData<List<OAuthUserEntity>>
}

@Database(entities = [OAuthTokenDataEntity::class, OAuthUserEntity::class], version = 1)
abstract class OAuthAdminDatabase: RoomDatabase() {
    abstract val oAuthAdminDao: OAuthAdminDao
}

private lateinit var INSTANCE: OAuthAdminDatabase

fun getDatabase(context: Context): OAuthAdminDatabase {
    synchronized(OAuthAdminDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                OAuthAdminDatabase::class.java,
                "oauthadmin").build()
        }
    }
    return INSTANCE
}