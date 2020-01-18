package eu.rudisch.oauthadmin.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import eu.rudisch.oauthadmin.database.OAuthAdminDatabase
import eu.rudisch.oauthadmin.database.asDomainModel
import eu.rudisch.oauthadmin.domain.OAuthTokenData
import eu.rudisch.oauthadmin.domain.OAuthUser
import eu.rudisch.oauthadmin.network.OAuthAdminApi
import eu.rudisch.oauthadmin.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OAuthAdminRepository(private val database: OAuthAdminDatabase) {

    val oAuthTokenData: LiveData<OAuthTokenData> =
        Transformations.map(database.oAuthAdminDao.getAccessToken("eru")) {
            it.asDomainModel()
        }

    suspend fun receiveAccessToken(code: String) {
        withContext(Dispatchers.IO) {
            val retrieveTokenDeferred = OAuthAdminApi.retrofitAccessTokenService.retrieveTokenAsync(code = code)
            val accessToken = retrieveTokenDeferred.await()
            database.oAuthAdminDao.insertAccessToken(accessToken.asDatabaseModel())
        }
    }

    val oAuthUsers: LiveData<List<OAuthUser>> =
        Transformations.map(database.oAuthAdminDao.getOAuthUsers()) {
            it.asDomainModel()
        }

    suspend fun refreshOAuthUsers(authorization: String) {
        withContext(Dispatchers.IO) {
            val oAuthUsers = OAuthAdminApi.retrofitOAuthUsersService.getOAuthUsers(authorization).await()
            database.oAuthAdminDao.insertAllOAuthUsers(*oAuthUsers.asDatabaseModel())
        }
    }
}