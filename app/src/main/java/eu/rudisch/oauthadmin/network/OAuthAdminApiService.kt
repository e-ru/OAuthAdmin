package eu.rudisch.oauthadmin.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val OAUTH_SERVER = "http://192.168.188.109:9191"
private const val OAUTH_TOKEN_PATH = "oauth/token"
private const val RESPONSE_TYPE = "code"
private const val CLIENT_ID = "auth_app"
private const val SCOPE = "create_oauth read_oauth update_oauth delete_oauth"
private const val REDIRECT_URL = "oauthadmin://redirect"
private const val GRANT_TYPE = "authorization_code"

const val OAUTH_AUTHORIZE_PATH = "oauth/authorize"
const val OAUTH_SCHEME = "oauthadmin"
const val CODE_URL =
    "${OAUTH_SERVER}/${OAUTH_AUTHORIZE_PATH}?response_type=${RESPONSE_TYPE}&scope=${SCOPE}&client_id=${CLIENT_ID}&redirect_uri=${REDIRECT_URL}"

private const val OAUTH_ADMIN_SERVER = "http://192.168.188.109:9292/admin"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofitBuilder = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())

private val retrofitAccessToken = retrofitBuilder
    .baseUrl(OAUTH_SERVER)
    .build()

private val retrofitOAuthUsers = retrofitBuilder
    .baseUrl(OAUTH_ADMIN_SERVER)
    .build()

interface OAuthAccessTokenService {
    @FormUrlEncoded
    @POST(OAUTH_TOKEN_PATH)
    fun retrieveTokenAsync(
        @Field("grant_type") grantType: String = GRANT_TYPE,
        @Field("code") code: String,
        @Field("redirect_uri") redirectUri: String = REDIRECT_URL,
        @Field("client_id") clientId: String = CLIENT_ID,
        @Field("scope") scope: String = SCOPE
    ): Deferred<NetworkOAuthTokenData>
}

interface OAuthUsersApiService {
    @GET("/users")
    fun getOAuthUsers(@Header("Authorization") authorization: String): Deferred<NetworkOAuthUserContainer>
}

object OAuthAdminApi {
    val retrofitAccessTokenService: OAuthAccessTokenService by lazy {
        retrofitAccessToken.create(
            OAuthAccessTokenService::class.java
        )
    }

    val retrofitOAuthUsersService: OAuthUsersApiService by lazy {
        retrofitOAuthUsers.create(
            OAuthUsersApiService::class.java
        )
    }
}