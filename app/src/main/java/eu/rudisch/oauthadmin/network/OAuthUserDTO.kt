package eu.rudisch.oauthadmin.network

import com.squareup.moshi.JsonClass
import eu.rudisch.oauthadmin.database.OAuthUserEntity

@JsonClass(generateAdapter = true)
data class NetworkOAuthUserContainer(val oAuthUsers: List<NetworkOAuthUser>)

@JsonClass(generateAdapter = true)
data class NetworkOAuthUser(
    val id: Int,
    val username: String,
    val password: String,
    val passwordRepeat: String,
    val email: String,
    val enabled: Boolean,
    val accountExpired: Boolean,
    val credentialsExpired: Boolean,
    val accountLocked: Boolean,
    val roleNames: List<String>
)

fun List<NetworkOAuthUser>.asDatabaseModel(): Array<OAuthUserEntity> {
    return this.map {
        OAuthUserEntity(
            id = it.id,
            username = it.username,
            password = it.password,
            passwordRepeat = it.passwordRepeat,
            email = it.email,
            enabled = it.enabled,
            accountExpired = it.accountExpired,
            credentialsExpired = it.credentialsExpired,
            accountLocked = it.accountLocked,
            roleNames = it.roleNames
        )
    }.toTypedArray()
}

fun NetworkOAuthUserContainer.asDatabaseModel(): Array<OAuthUserEntity> {
    return oAuthUsers.map {
        OAuthUserEntity(
            id = it.id,
            username = it.username,
            password = it.password,
            passwordRepeat = it.passwordRepeat,
            email = it.email,
            enabled = it.enabled,
            accountExpired = it.accountExpired,
            credentialsExpired = it.credentialsExpired,
            accountLocked = it.accountLocked,
            roleNames = it.roleNames
        )
    }.toTypedArray()
}

fun NetworkOAuthUser.asDatabaseModel(): OAuthUserEntity {
    return OAuthUserEntity(
            id = id,
            username = username,
            password = password,
            passwordRepeat = passwordRepeat,
            email = email,
            enabled = enabled,
            accountExpired = accountExpired,
            credentialsExpired = credentialsExpired,
            accountLocked = accountLocked,
            roleNames = roleNames
        )
}