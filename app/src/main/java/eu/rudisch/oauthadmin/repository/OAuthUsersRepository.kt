package eu.rudisch.oauthadmin.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import eu.rudisch.oauthadmin.database.oauthadmin.OAuthUserDatabase
import eu.rudisch.oauthadmin.database.oauthadmin.asDomainModel
import eu.rudisch.oauthadmin.domain.OAuthUser
import eu.rudisch.oauthadmin.network.oauthadmin.OAuthAdminApi
import eu.rudisch.oauthadmin.network.oauthadmin.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OAuthUsersRepository(private val database: OAuthUserDatabase) {

    val oAuthUsers: LiveData<List<OAuthUser>> =
        Transformations.map(database.oAuthUserDao.getOAuthUsers()) {
            it.asDomainModel()
        }

    suspend fun refreshOAuthUsers(authorization: String) {
        withContext(Dispatchers.IO) {
            val oAuthUsers = OAuthAdminApi.retrofitService.getOAuthUsers(authorization).await()
            database.oAuthUserDao.insertAll(*oAuthUsers.asDatabaseModel())
        }
    }
}
