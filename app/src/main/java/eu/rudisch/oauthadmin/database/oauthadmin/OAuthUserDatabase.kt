package eu.rudisch.oauthadmin.database.oauthadmin

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface OAuthUserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg oAuthUsers: DatabaseOAuthUser)

    @Query("select * from oauth_users")
    fun getOAuthUsers(): LiveData<List<DatabaseOAuthUser>>
}

@Database(entities = [DatabaseOAuthUser::class], version = 1)
abstract class OAuthUserDatabase : RoomDatabase() {
    abstract val oAuthUserDao: OAuthUserDao
}

private lateinit var INSTANCE: OAuthUserDatabase

fun getDatabase(context: Context): OAuthUserDatabase {
    synchronized(OAuthUserDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                OAuthUserDatabase::class.java,
                "videos"
            ).build()
        }
    }
    return INSTANCE
}
