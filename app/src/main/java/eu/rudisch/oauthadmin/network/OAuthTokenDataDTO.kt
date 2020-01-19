package eu.rudisch.oauthadmin.network

import android.os.Parcelable
import android.util.Base64
import com.squareup.moshi.Json
import eu.rudisch.oauthadmin.database.OAuthTokenDataEntity
import eu.rudisch.oauthadmin.domain.OAuthTokenData
import kotlinx.android.parcel.Parcelize
import org.json.JSONObject
import timber.log.Timber

@Parcelize
data class NetworkOAuthTokenData(
    @Json(name = "access_token") val accessToken: String = "",
    @Json(name = "expires_in") val expiresIn: Int = -1,
    val jti: String = "",
    @Json(name = "refresh_token") val refreshToken: String = "",
    val scope: String = "",
    @Json(name = "token_type") val tokenType: String = ""
) : Parcelable

fun NetworkOAuthTokenData.asDomainModel(): OAuthTokenData {
    // TODO: move to util class
    val json = JSONObject(String(Base64.decode(accessToken.split(".")[1], Base64.DEFAULT)))
    return OAuthTokenData(
        accessToken = accessToken,
        expiresIn = expiresIn,
        refreshToken = refreshToken,
        tokenType = tokenType,
        userName = json.getString("user_name"),
        clientID = json.getString("client_id")
    )
}

fun NetworkOAuthTokenData.asDatabaseModel(): OAuthTokenDataEntity {
    // TODO: move to util class
    val json = JSONObject(String(Base64.decode(accessToken.split(".")[1], Base64.DEFAULT)))
    Timber.i("json: $json")

    return OAuthTokenDataEntity(
        accessToken = accessToken,
        expiresIn = expiresIn,
        refreshToken = refreshToken,
        tokenType = tokenType,
        userName = json.getString("user_name"),
        clientID = json.getString("client_id")
    )
}

/*

access_token: "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsib2F1dGgyLWNvbnRyb2wtcmVzb3VyY2UiXSwidXNlcl9uYW1lIjoiZXJ1Iiwic2NvcGUiOlsidXBkYXRlX29hdXRoIiwiY3JlYXRlX29hdXRoIiwicmVhZF9vYXV0aCIsImRlbGV0ZV9vYXV0aCJdLCJvcmdhbml6YXRpb24iOiJlcnVQZWFPIiwiZXhwIjoxNTc1MzI4MTE5LCJhdXRob3JpdGllcyI6WyJ1cGRhdGVfb2F1dGgiLCJjcmVhdGVfb2F1dGgiLCJST0xFX29hdXRoX2FkbWluIiwicmVhZF9vYXV0aCIsImRlbGV0ZV9vYXV0aCJdLCJqdGkiOiJmZGQ4NTZjZi1lYWVhLTRlMzgtODUwZi1lYzA4ZjNjY2RkMWIiLCJjbGllbnRfaWQiOiJhdXRoX3NlcnZlciJ9.V9LUFR6P9npR1mXhtE4NDxoFm9POYaL-TAalLx4TL30ZLsoXVsW8O_VAKRrlgakNIToy9vGGoTIBqWMfBoxpUuK7u81n2o0frdbqztnyFowQDH-vWQcp_fqyXihUCP9ZxlW6ovBMjqv_da_lMBt11zWg9lXM5ZA5yi3aIMSsB0GrzUJSMHmaeMpt8l2Ip1yE1rczarFCVrpGLtBolRzeyMMge7cwr4O9Je3zp-nl_INGcwu0iynWja2MJnScd3BsLiT9WD2dsyoOgvFhQEjX9UIVFHcWihAIGV_plE5-zmBfKEw_BF95XGok7wr1RRPWFX0YPJSTkgkSZr7MSi_ARg"
​
expires_in: 3599
​
jti: "fdd856cf-eaea-4e38-850f-ec08f3ccdd1b"
​
organization: "eruPeaO"
​
refresh_token: "3c405e52-0551-4fd1-93f9-1794e8df1d47"
​
scope: "update_oauth create_oauth read_oauth delete_oauth"
​
token_type: "bearer"

*/