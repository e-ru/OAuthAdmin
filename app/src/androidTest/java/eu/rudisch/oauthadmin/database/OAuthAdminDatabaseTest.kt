package eu.rudisch.oauthadmin.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class OAuthAdminDatabaseTest {
    private lateinit var oAuthAdminDao: OAuthAdminDao
    private lateinit var db: OAuthAdminDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, OAuthAdminDatabase::class.java).build()
        oAuthAdminDao = db.oAuthAdminDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeTokenAndReadToken() {
        val token = OAuthTokenDataEntity(
            userName="testUser",
            accessToken="foo",
            expiresIn=1,
            refreshToken="bar",
            clientID="foobar",
            tokenType="foofoo"
        )
        oAuthAdminDao.insertAccessToken(token)
        val t = oAuthAdminDao.getAccessToken("testUser")
        assertThat(t?.userName, equalTo("testUser"))
    }
}
