package eu.rudisch.oauthadmin.authwindow

import android.app.Application
import androidx.lifecycle.*
import eu.rudisch.oauthadmin.database.getDatabase
import eu.rudisch.oauthadmin.repository.OAuthAdminRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber

enum class OAuthApiStatus { LOADING, ERROR, DONE }

class AuthWindowViewModel(application: Application) : AndroidViewModel(application) {

    private var viewModelJob = SupervisorJob()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val database = getDatabase(application)
    private val oAuthAdminRepository = OAuthAdminRepository(database)

    private val _status = MutableLiveData<OAuthApiStatus>()
    val status: LiveData<OAuthApiStatus>
        get() = _status

    //    private val _accessToken = MutableLiveData<OAuthTokenData>()
//    val accessToken = oAuthAdminRepository.oAuthTokenData
//        get() = _accessToken

    private val _loggedIn = MutableLiveData<Boolean>()
    val loggedIn: LiveData<Boolean>
        get() = _loggedIn

    init {
        _loggedIn.value = false
    }

    fun checkUrlAndPostCode(url: String) {
        val codeExtractor = CodeExtractor()
        val code = codeExtractor.extractCodeFromUrl(url)
        Timber.i("code: $code")

        retrieveAccessToken(code)
//        val result = oAuthAdminRepository.oAuthTokenData
    }

    private fun retrieveAccessToken(code: String) {
        coroutineScope.launch {
            try {
                val accessToken = oAuthAdminRepository.getAccessToken("eru")
                Timber.i("accessTokenDBResult1: $accessToken")

                _status.value = OAuthApiStatus.LOADING
                oAuthAdminRepository.receiveAccessToken(code)

                _status.value = OAuthApiStatus.DONE
//                _accessToken.value = result.value
//                Timber.i("OAuthTokenData: ${result.value}")
                _loggedIn.value = true // result.value?.accessToken != ""
            } catch (e: Exception) {
                Timber.i("Ex: $e")
                _status.value = OAuthApiStatus.ERROR
//                _accessToken.value = NetworkOAuthTokenData().asDomainModel()
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