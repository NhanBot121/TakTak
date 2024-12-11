package com.mck.myprofile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mck.data.repository.UserRepository

class UserFactory(private val _repository: UserRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(UserViewModel::class.java) -> (UserViewModel(_repository) as T)
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}