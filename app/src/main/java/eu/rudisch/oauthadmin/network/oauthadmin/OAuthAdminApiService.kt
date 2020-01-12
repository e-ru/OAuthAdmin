package eu.rudisch.oauthadmin.network.oauthadmin

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header

private const val OAUTH_ADMIN_SERVER = "http://192.168.188.109:9292/admin"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(OAUTH_ADMIN_SERVER)
    .build()


interface OAuthAdminApiService {

    @GET("/users")
    fun getOAuthUsers(@Header("Authorization") authorization: String): Deferred<NetworkOAuthUserContainer>
}

object OAuthAdminApi {
    val retrofitService: OAuthAdminApiService by lazy { retrofit.create(
        OAuthAdminApiService::class.java) }
}
