package com.mck.discovery.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mck.data.model.VideoDetails
import com.mck.discovery.repository.VideoRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val videoRepository: VideoRepository) : ViewModel() {

    private val _searchResults = MutableLiveData<List<VideoDetails>>()
    val searchResults: LiveData<List<VideoDetails>> = _searchResults

    private val _videoLiveData = MutableLiveData<List<VideoDetails>>()
    val videoLiveData: LiveData<List<VideoDetails>> = _videoLiveData
    fun updateLike(like: Int, documentId: String, user: String) {
        viewModelScope.launch {
            try {
                videoRepository.updateLike(like, documentId)
                Log.d("HomeViewModel", "Updated likes successfully for documentId: $documentId")
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error updating like: ${e.message}")
            }
        }
    }
    fun searchVideos(query: String) {
        viewModelScope.launch {
            try {
                val results = videoRepository.searchVideos(query)
                _searchResults.postValue(results) // Đảm bảo LiveData được cập nhật
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error during search: ${e.message}")
            }
        }
    }

    fun getVideosByTag(tags: String) {
        viewModelScope.launch {
            try {
                _searchResults.value = videoRepository.getVideosByTag(tags)
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error getting videos by tag: ${e.message}")
            }
        }
    }

    // Lấy tất cả video
    fun getVideos() {
        viewModelScope.launch {
            _videoLiveData.value = videoRepository.getVideoDetails()
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