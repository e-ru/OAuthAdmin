package eu.rudisch.oauthadmin.authwindow

import android.app.Application
import androidx.lifecycle.*
import eu.rudisch.oauthadmin.database.getDatabase
import eu.rudisch.oauthadmin.domain.OAuthTokenData
import eu.rudisch.oauthadmin.network.NetworkOAuthTokenData
import eu.rudisch.oauthadmin.network.asDomainModel
import eu.rudisch.oauthadmin.repository.OAuthAdminRepository
import kotlinx.coroutines.*
import timber.log.Timber

enum class OAuthApiStatus { LOADING, ERROR, DONE }

class AuthWindowViewModel(application: Application) : AndroidViewModel(application) {

    private val _status = MutableLiveData<OAuthApiStatus>()
    val status: LiveData<OAuthApiStatus>
        get() = _status

    private val _accessToken = MutableLiveData<OAuthTokenData>()
    val accessToken: LiveData<OAuthTokenData>
        get() = _accessToken

    private val _loggedIn = MutableLiveData<Boolean>()
    val loggedIn: LiveData<Boolean>
        get() = _loggedIn

    private var viewModelJob = SupervisorJob()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val database = getDatabase(application)
    private val oAuthAdminRepository = OAuthAdminRepository(database)

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
            try {
                _status.value = OAuthApiStatus.LOADING
                oAuthAdminRepository.receiveAccessToken(code)
                val result = oAuthAdminRepository.oAuthTokenData.value
                Timber.i("accessTokenNetwork: $result")

                _status.value = OAuthApiStatus.DONE
                _accessToken.value = result
                Timber.i("OAuthTokenData: ${_accessToken.value}")
                _loggedIn.value = result?.accessToken != ""
            } catch (e: Exception) {
                _status.value = OAuthApiStatus.ERROR
                _accessToken.value = NetworkOAuthTokenData().asDomainModel()
            }
        }
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AuthWindowViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AuthWindowViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}