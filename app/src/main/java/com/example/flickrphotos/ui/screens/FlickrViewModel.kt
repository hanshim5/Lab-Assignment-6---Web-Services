/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.flickrphotos.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flickrphotos.FlickrPhotosApplication
import com.example.flickrphotos.data.FlickrPhotosRepository
import com.example.flickrphotos.model.FlickrPhoto
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

/**
 * UI state for the Home screen
 */
sealed interface FlickrUiState {
    data class Success(val photos: List<FlickrPhoto>) : FlickrUiState
    object Error : FlickrUiState
    object Loading : FlickrUiState
}

class FlickrViewModel(private val flickrPhotosRepository: FlickrPhotosRepository) : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var flickrUiState: FlickrUiState by mutableStateOf(FlickrUiState.Loading)
        private set

    /**
     * Call getFlickrPhotos() on init so we can display status immediately.
     */
    init {
        getFlickrPhotos()
    }

    /**
     * Gets Flickr photos information from the Flickr API Retrofit service and updates the
     * [FlickrPhoto] [List] [MutableList].
     */
    fun getFlickrPhotos() {
        viewModelScope.launch {
            flickrUiState = FlickrUiState.Loading
            flickrUiState = try {
                FlickrUiState.Success(flickrPhotosRepository.getFlickrPhotos())
            } catch (e: IOException) {
                FlickrUiState.Error
            } catch (e: HttpException) {
                FlickrUiState.Error
            }
        }
    }

    /**
     * Factory for [FlickrViewModel] that takes [FlickrPhotosRepository] as a dependency
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as FlickrPhotosApplication)
                val flickrPhotosRepository = application.container.flickrPhotosRepository
                FlickrViewModel(flickrPhotosRepository = flickrPhotosRepository)
            }
        }
    }
}
