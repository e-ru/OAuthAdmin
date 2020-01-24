package eu.rudisch.oauthadmin.network

import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OAuthAdminApiTest {


// to test network container, see commit 96258cf7ccb312398ee8a03ecdeaf91e1a600daf
//@JsonClass(generateAdapter = true)
//data class NetworkOAuthUserContainer(val oAuthUsers: List<NetworkOAuthUser>)


    private val baseUrl = "/"
    private val mockWebServer = MockWebServer()
    private val httpUrl = mockWebServer.url(baseUrl)

    private lateinit var retrofitOAuthUsersService: OAuthUsersApiService

    @Before
    fun setUp() {
        retrofitOAuthUsersService =
            retrofitServiceFactory(httpUrl.toString()).create(OAuthUsersApiService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getRetrofitAccessTokenService() {
    }

    @Test
    fun shouldGetOAuthUsers() = runBlocking {
        val testData = NetworkOAuthUser(
            id = 1,
            username = "testUser",
            password = "testPassword",
            passwordRepeat = "testPassword",
            email = "testEmail",
            enabled = true,
            accountExpired = false,
            credentialsExpired = false,
            accountLocked = false,
            roleNames = listOf("testRole")
        )

        val testDataJson = """
            [
            {
            "id":${testData.id},
            "username":"${testData.username}",
            "password":"${testData.password}",
            "passwordRepeat":"${testData.passwordRepeat}",
            "email":"${testData.email}",
            "enabled":${testData.enabled},
            "accountExpired":${testData.accountExpired},
            "credentialsExpired":${testData.credentialsExpired},
            "accountLocked":${testData.accountLocked},
            "roleNames":["testRole"]
            }
            ]""".trimIndent()

        val successResponse = MockResponse().setBody(testDataJson)
        mockWebServer.enqueue(successResponse)

        val response = retrofitOAuthUsersService.getOAuthUsersAsync("").await()

        mockWebServer.takeRequest()
        assertThat(response, equalTo(listOf(testData)))
    }
}