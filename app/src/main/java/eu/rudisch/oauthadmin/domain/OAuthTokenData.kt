package eu.rudisch.oauthadmin.domain

data class OAuthTokenData(
    val accessToken: String,
    val expiresIn: Int,
    val refreshToken: String,
    val tokenType: String,
    val userName: String,
    val clientID: String
)
