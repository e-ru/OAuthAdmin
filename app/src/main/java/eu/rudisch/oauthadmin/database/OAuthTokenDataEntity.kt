package eu.rudisch.oauthadmin.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import eu.rudisch.oauthadmin.domain.OAuthTokenData

@Entity(tableName = "oauth_token_data")
data class OAuthTokenDataEntity(
    @PrimaryKey
    val userName: String,
    val accessToken: String,
    val expiresIn: Int,
    val refreshToken: String,
    val clientID: String,
    val tokenType: String
)

fun OAuthTokenDataEntity.asDomainModel(): OAuthTokenData {
    return OAuthTokenData(
        accessToken = accessToken,
        expiresIn = expiresIn,
        refreshToken = refreshToken,
        tokenType = tokenType,
        userName = userName,
        clientID = clientID
    )
}