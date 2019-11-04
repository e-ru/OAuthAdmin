package eu.rudisch.oauthadmin.authwindow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AuthWindowViewModel : ViewModel() {

    companion object {

        private const val OAUTH_SERVER = "http://192.168.188.109:9191"
        const val OAUTH_PATH = "oauth/authorize"
        private const val RESPONSE_TYPE = "code"
        const val CLIENT_ID = "auth_app"
        //  private const      val SCOPE = "create_oauth read_oauth update_oauth delete_oauth"
        private const val SCOPE = "read_oauth"
        const val OAUTH_SCHEME = "oauthadmin"
        private const val REDIRECT_URL = "oauthadmin://redirect"

        // new intent "retrofit tutorial oauth authentication with github, 11:40"
        const val codeUrl =
            "${OAUTH_SERVER}/${OAUTH_PATH}?response_type=${RESPONSE_TYPE}&scope=${SCOPE}&client_id=${CLIENT_ID}&redirect_uri=${REDIRECT_URL}"
    }

    private val _loggedIn = MutableLiveData<Boolean>()
    val loggedIn: LiveData<Boolean>
        get() = _loggedIn

    init {
        _loggedIn.value = false
    }

    fun checkUrlAndPostCode(url: String) {
        val codeExtractor = CodeExtractor()
        val code = codeExtractor.extractDodeFromUrl(url)
        _loggedIn.value = code != ""
    }
}