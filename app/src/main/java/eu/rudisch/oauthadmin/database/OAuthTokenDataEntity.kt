package eu.rudisch.oauthadmin.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import eu.rudisch.oauthadmin.domain.OAuthTokenData

@Entity(tableName = "oauth_token_data")
class OAuthTokenDataEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    val accessToken: String,
    val expiresIn: Int,
    val refreshToken: String,
    val tokenType: String,
    val userName: String,
    val clientID: String
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