package eu.rudisch.oauthadmin.oauthusers

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import eu.rudisch.oauthadmin.database.getDatabase
import eu.rudisch.oauthadmin.repository.OAuthAdminRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber

class OAuthUserViewModel(application: Application) : AndroidViewModel(application) {

    private val viewModelJob = SupervisorJob()

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val database = getDatabase(application)
    private val oAuthAdminRepository = OAuthAdminRepository(database)


//    init {
//        viewModelScope.launch {
//            val accessToken = oAuthAdminRepository.getAccessToken("eru")
//            Timber.i("accessTokenDBResult2: $accessToken")
//            if (accessToken != null)
//                oAuthAdminRepository.refreshOAuthUsers(accessToken.accessToken)
//        }
//    }

//    val oAuthUsers = oAuthAdminRepository.oAuthUsers

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(OAuthUserViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return OAuthUserViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}