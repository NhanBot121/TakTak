package com.example.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.mck.data.model.VideoDetails
import com.mck.data.model.VideoRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface HomeUiState {
    object Empty: HomeUiState
    object Success: HomeUiState
    object Loading : HomeUiState
}

class HomeViewModel(
    private val homeVideoRepository: VideoRepository
    ) : ViewModel() {

    private val _videoLiveData = MutableLiveData<List<VideoDetails>>()
    val videoLiveData: LiveData<List<VideoDetails>> = _videoLiveData



    fun getVideos() {
        viewModelScope.launch {
            _videoLiveData.value = homeVideoRepository.getVideoDetails()
        }
    }

    fun updateLike(like: Int, videoId: Int, user: String){
        viewModelScope.launch {
            homeVideoRepository.updateLike(like, videoId, user)
        }
    }


    companion object {
        fun provideFactory(videoRepository: VideoRepository): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                        @Suppress("UNCHECKED_CAST")
                        return HomeViewModel(videoRepository) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        }
    }
    }



