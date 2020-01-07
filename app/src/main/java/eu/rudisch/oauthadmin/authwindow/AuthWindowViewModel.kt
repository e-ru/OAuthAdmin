package eu.rudisch.oauthadmin.authwindow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import eu.rudisch.oauthadmin.domain.OAuthTokenData
import eu.rudisch.oauthadmin.network.OAuthApi
import eu.rudisch.oauthadmin.network.NetworkOAuthTokenData
import eu.rudisch.oauthadmin.network.asDomainModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

enum class OAuthApiStatus { LOADING, ERROR, DONE }

class AuthWindowViewModel : ViewModel() {

    private val _status = MutableLiveData<OAuthApiStatus>()
    val status: LiveData<OAuthApiStatus>
        get() = _status

    private val _accessToken = MutableLiveData<OAuthTokenData>()
    val accessToken: LiveData<OAuthTokenData>
        get() = _accessToken

    private val _loggedIn = MutableLiveData<Boolean>()
    val loggedIn: LiveData<Boolean>
        get() = _loggedIn

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        _loggedIn.value = false
    }

    fun checkUrlAndPostCode(url: String) {
        val codeExtractor = CodeExtractor()
        val code = codeExtractor.extractCodeFromUrl(url)
        Timber.i("code: $code")

        retrieveAccessToken(code)
    }

    private fun retrieveAccessToken(code: String) {
        coroutineScope.launch {
            val retrieveTokenDeferred = OAuthApi.retrofitService.retrieveTokenAsync(code = code)
            try {
                _status.value = OAuthApiStatus.LOADING
                val result = retrieveTokenDeferred.await()
                Timber.i("accessTokenNetwork: $result")

                _status.value = OAuthApiStatus.DONE
                _accessToken.value = result.asDomainModel()
                Timber.i("OAuthTokenData: ${_accessToken.value}")
                _loggedIn.value = result.accessToken != ""
            } catch (e: Exception) {
                _status.value = OAuthApiStatus.ERROR
                _accessToken.value = NetworkOAuthTokenData().asDomainModel()
            }
        }
    }
}