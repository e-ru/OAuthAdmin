package eu.rudisch.oauthadmin.network.oauthadmin

import com.squareup.moshi.JsonClass
import eu.rudisch.oauthadmin.database.oauthadmin.DatabaseOAuthUser

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

fun NetworkOAuthUserContainer.asDatabaseModel(): Array<DatabaseOAuthUser> {
    return oAuthUsers.map {
        DatabaseOAuthUser(
            id = it.id,
            username = it.username,
            password = it.password,
            passwordRepeat = it.passwordRepeat,
            email = it.email,
            enabled = it.enabled,
            accountExpired = it.accountExpired,
            credentialsExpired = it.credentialsExpired,
            accountLocked = it.accountLocked
//            roleNames = it.roleNames
        )
    }.toTypedArray()
}