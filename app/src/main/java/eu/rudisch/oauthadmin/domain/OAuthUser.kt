package eu.rudisch.oauthadmin.domain

data class OAuthUser(
    val id: Int,
    val username: String,
    val password: String,
    val passwordRepeat: String,
    val email: String,
    val enabled: Boolean,
    val accountExpired: Boolean,
    val credentialsExpired: Boolean,
    val accountLocked: Boolean
//    val roleNames: List<String>
)

//accountExpired: false
//​​​​
//accountLocked: false
//​​​​
//credentialsExpired: false
//​​​​
//email: "e@ru.org"
//​​​​
//enabled: true
//​​​​
//id: 1
//​​​​
//password: "{bcrypt}$2a$10$cwcOgGpz.UdW0Pd/rqnFlOf1bzII.G933uxfu2iZBa9rtiTNUOKgm"
//​​​​
//passwordRepeat: "{bcrypt}$2a$10$cwcOgGpz.UdW0Pd/rqnFlOf1bzII.G933uxfu2iZBa9rtiTNUOKgm"
//​​​​
//roleNames: (1) […]
//​​​​​
//0: "ROLE_oauth_admin"
//​​​​​
//length: 1
//​​​​​
//<prototype>: Array []
//​​​​
//username: "eru"