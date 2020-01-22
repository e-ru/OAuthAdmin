package eu.rudisch.oauthadmin.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import eu.rudisch.oauthadmin.domain.OAuthUser

@Entity(tableName = "oauth_users")
data class OAuthUserEntity(
    @PrimaryKey
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

fun List<OAuthUserEntity>.asDomainModel(): List<OAuthUser> {
    return map {
        OAuthUser(
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
    }
}