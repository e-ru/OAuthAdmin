package eu.rudisch.oauthadmin.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import eu.rudisch.oauthadmin.database.OAuthTokenDataDatabase
import eu.rudisch.oauthadmin.database.asDomainModel
import eu.rudisch.oauthadmin.domain.OAuthTokenData
import eu.rudisch.oauthadmin.network.OAuthApi
import eu.rudisch.oauthadmin.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OAuthTokenDataRepository(private val database: OAuthTokenDataDatabase) {

    val oAuthTokendata: LiveData<OAuthTokenData> =
        Transformations.map(database.oAuthTokenDataDao.get("eru")) {
            it.asDomainModel()
        }

    suspend fun recieveAccessToken(code: String) {
        withContext(Dispatchers.IO) {
            val retrieveTokenDeferred = OAuthApi.retrofitService.retrieveTokenAsync(code = code)
            val accessToken = retrieveTokenDeferred.await()
            database.oAuthTokenDataDao.insert(accessToken.asDatabaseModel())
        }
    }
}