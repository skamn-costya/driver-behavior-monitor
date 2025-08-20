package com.example.driverbehaviormonitor.auth

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import com.example.driverbehaviormonitor.backend.ApiClient
import kotlinx.coroutines.CoroutineExceptionHandler

class LoginViewModel : ViewModel() {

    sealed class QrState {
        object Loading : QrState()
        data class Success(val qrData: String) : QrState()
        data class Error(val message: String? = null) : QrState()
    }

    private val _qrCodeState = MutableLiveData<QrState>()
    val qrCodeState: LiveData<QrState> get() = _qrCodeState

    fun loadQr() {
        _qrCodeState.postValue(QrState.Loading)

        viewModelScope.launch {
            try {
                val response = ApiClient.authApi.getQr()
                _qrCodeState.postValue(QrState.Success(response.qrData))
            } catch (e: Exception) {
                Log.e("LoginVM", "Error loading QR", e)
                _qrCodeState.postValue(QrState.Error(e.localizedMessage))
            }
        }
    }
}

//    fun loadQr() {
//        viewModelScope.launch {
//            try {
//                val response = ApiClient.authApi.getQr()
//                _qrCode.postValue(response.qrData)
//            } catch (e: Exception) {
//                Log.e("LoginVM", "Error loading QR", e)
//                _qrCode.postValue("ERROR")
//            }
//        }
//    }

//        viewModelScope.launch {
//            try {
//                // убедимся, что authApi инициализирован
//                val api = ApiClient.authApi ?: run {
//                    Log.e("LoginVM", "AuthApi is null")
//                    return@launch
//                }
//
//                // вызов сетевой функции
//                val response = api.getQr() // <- здесь должно быть suspend
//
//                if (response == null) {
//                    Log.e("LoginVM", "Response is null")
//                    return@launch
//                }
//
//                val qrString = response.qrData ?: run {
//                    Log.e("LoginVM", "QR data is null")
//                    return@launch
//                }
//
//                _qrCode.postValue(qrString)
//
//            } catch (e: Exception) {
//                Log.e("LoginVM", "Failed to load QR", e)
//            }
//        }



//        viewModelScope.launch {
//            try {
////                val response = ApiClient.authApi.getQr()
////                _qrCode.postValue(response.qrData)
//                _qrCode.postValue("Konstantin")
//            } catch (e: Exception) {
//                Log.e("LoginVM", "Error loading QR", e)
//                _qrCode.postValue("ERROR")
//            }
//        }
