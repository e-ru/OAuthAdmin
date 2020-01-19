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
import timber.log.Timber
import java.lang.Exception

class OAuthAdminRepository(private val database: OAuthAdminDatabase) {

    suspend fun getAccessToken(userName: String): OAuthTokenData? {
       return withContext(Dispatchers.IO) {
            val oAuthTokenData =
                database.oAuthAdminDao.getAccessToken(key = userName)?.asDomainModel()
           oAuthTokenData
        }
    }


    suspend fun receiveAccessToken(code: String) {
        withContext(Dispatchers.IO) {
            val retrieveTokenDeferred = OAuthAdminApi.retrofitAccessTokenService.retrieveTokenAsync(code = code)
            val accessToken = retrieveTokenDeferred.await()

            Timber.i("accessTokenNetwork: $accessToken")

            val atdb = accessToken.asDatabaseModel()
            Timber.i("atdb: $atdb")

            database.oAuthAdminDao.insertAccessToken(atdb)
        }
    }

    val oAuthUsers: LiveData<List<OAuthUser>> =
        Transformations.map(database.oAuthAdminDao.getOAuthUsers()) {
            it.asDomainModel()
        }

    suspend fun refreshOAuthUsers(authorization: String) {
        withContext(Dispatchers.IO) {
            Timber.i("auth $authorization")
            try {
                val oAuthUsers = OAuthAdminApi.retrofitOAuthUsersService.getOAuthUsers(authorization).await()
                database.oAuthAdminDao.insertAllOAuthUsers(*oAuthUsers.asDatabaseModel())
            } catch (ex: Exception) {
                Timber.i("retro ex: $ex")
            }
        }
    }
}