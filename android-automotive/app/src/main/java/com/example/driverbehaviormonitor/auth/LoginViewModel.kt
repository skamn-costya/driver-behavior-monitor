package com.example.driverbehaviormonitor.auth

import android.R
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import com.example.driverbehaviormonitor.backend.ApiClient
import kotlinx.coroutines.CoroutineExceptionHandler

class LoginViewModel : ViewModel() {

    sealed class QrState {
        object Loading : QrState()
        data class Success(val qrData: String, val userCode: String) : QrState()
        data class Error(val message: String? = null) : QrState()
    }

    private val _qrCodeState = MutableLiveData<QrState>()
    val qrCodeState: LiveData<QrState> get() = _qrCodeState

    fun loadQr() {
        _qrCodeState.postValue(QrState.Loading)

        viewModelScope.launch {
            try {
                val response = ApiClient.authApi.getQr()
                Log.d("DBM", "Loading QR ".toString().plus(response))
                _qrCodeState.postValue(QrState.Success(response.qrData, response.userCode))
            } catch (e: Exception) {
                Log.e("DBM", "Error loading QR", e)
                _qrCodeState.postValue(QrState.Error(e.localizedMessage))
            }
        }
    }
}
