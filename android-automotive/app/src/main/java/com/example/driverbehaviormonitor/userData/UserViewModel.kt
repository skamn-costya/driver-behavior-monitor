package com.example.driverbehaviormonitor.userData

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.example.driverbehaviormonitor.SessionManager
import kotlinx.coroutines.launch
import com.example.driverbehaviormonitor.backend.ApiClient
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf

class UserViewModel(
    private val repo: UserRepository = UserRepository()
) : ViewModel() {

    var user by mutableStateOf<GoogleUser?>(null)
        private set

    fun loadUser(accessToken: String) {
        viewModelScope.launch {
            user = repo.getUserInfo(accessToken)
        }
    }
}
//
//class UserViewModel : ViewModel() {
//    private val repo = UserRepository()
//
//    var user by mutableStateOf<GoogleUser?>(null)
//        private set
//
//    fun loadUser() {
//        val token = SessionManager.accessToken // from your SessionManager
//        if (token.isNullOrEmpty()) return
//
//        viewModelScope.launch {
//            user = repo.getUserInfo(token)
//        }
//    }
//}
