package eu.rudisch.oauthadmin.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface OAuthAdminDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAccessToken(oAuthTokenDataEntity: OAuthTokenDataEntity)

    @Query("SELECT * FROM oauth_token_data WHERE userName = :key")
    fun getAccessToken(key: String): OAuthTokenDataEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllOAuthUsers(vararg oAuthUsers: OAuthUserEntity)

    @Query("SELECT * FROM oauth_users")
    fun getOAuthUsersLiveData(): LiveData<List<OAuthUserEntity>>

    @Query("SELECT * FROM oauth_users")
    fun getOAuthUsers(): List<OAuthUserEntity>
}

@Database(entities = [OAuthTokenDataEntity::class, OAuthUserEntity::class], version = 1)
@TypeConverters(Converters::class)
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