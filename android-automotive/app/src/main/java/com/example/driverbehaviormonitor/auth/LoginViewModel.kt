package com.example.driverbehaviormonitor.auth

import android.R
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import com.example.driverbehaviormonitor.backend.ApiClient
import kotlinx.coroutines.CoroutineExceptionHandler
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val repo = AuthRepository(baseUrl = "https://aaos.sorokolit.net")

    sealed class QrState {
        object Loading : QrState()
        data class Success(val session_id: String,
                           val verification_url: String,
                           val qrData: String,
                           val userCode: String) : QrState()
        data class Error(val message: String? = null) : QrState()
    }

    fun pollAuth(session_id: String) {
        viewModelScope.launch {
            val ok = repo.waitForAuthorization(session_id)
            if (ok) {
                Log.d("DBM", "OK")
            } else {
                Log.d("DBM", "not OK")
            }
        }
    }

    private val _qrCodeState = MutableLiveData<QrState>()
    val qrCodeState: LiveData<QrState> get() = _qrCodeState

    fun loadQr() {
        _qrCodeState.postValue(QrState.Loading)

        viewModelScope.launch {
            try {
                val response = ApiClient.authApi.getQr()
                Log.d("DBM", "Loading QR ".toString().plus(response))
                _qrCodeState.postValue(QrState.Success(response.session_id, response.verification_url, response.qrData, response.userCode))
            } catch (e: Exception) {
                Log.e("DBM", "Error loading QR", e)
                _qrCodeState.postValue(QrState.Error(e.localizedMessage))
            }
        }
    }
}
