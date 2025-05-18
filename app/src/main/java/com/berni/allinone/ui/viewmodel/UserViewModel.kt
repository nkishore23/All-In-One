package com.berni.allinone.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPrefs = application.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    private val _userName = MutableStateFlow(sharedPrefs.getString("name", "") ?: "")
    val userName: StateFlow<String> = _userName

    private val _email = MutableStateFlow(sharedPrefs.getString("email", "") ?: "")
    val email: StateFlow<String> = _email

    private val _phone = MutableStateFlow(sharedPrefs.getString("phone", "") ?: "")
    val phone: StateFlow<String> = _phone

    private val _photoUrl = MutableStateFlow(sharedPrefs.getString("photoUrl", "") ?: "")
    val photoUrl: StateFlow<String> = _photoUrl

    fun setUser(name: String, email: String, phone: String, photoUrl: String) {
        _userName.value = name
        _email.value = email
        _phone.value = phone
        _photoUrl.value = photoUrl

        sharedPrefs.edit().apply {
            putString("name", name)
            putString("email", email)
            putString("phone", phone)
            putString("photoUrl", photoUrl)
            apply()
        }
    }

    fun clearUser() {
        _userName.value = ""
        _email.value = ""
        _phone.value = ""
        _photoUrl.value = ""

        sharedPrefs.edit().clear().apply()
    }
}
