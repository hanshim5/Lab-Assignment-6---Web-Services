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
package com.example.flickrphotos.data

import com.example.flickrphotos.model.FlickrPhoto
import com.example.flickrphotos.network.FlickrApiService

/**
 * Repository that fetch Flickr photos list from Flickr Api.
 */
interface FlickrPhotosRepository {
    /** Fetches list of FlickrPhoto from flickrApi */
    suspend fun getFlickrPhotos(): List<FlickrPhoto>
}

/**
 * Network Implementation of Repository that fetch Flickr photos list from marsApi.
 */
class NetworkFlickrPhotosRepository(
    private val flickrApiService: FlickrApiService,
    ) : FlickrPhotosRepository {

        /** Fetches list of FlickrPhoto from flickrApi*/
        override suspend fun getFlickrPhotos(): List<FlickrPhoto> {
            // Extract photos from the response
            val response = flickrApiService.getPhotos()
            return response.photos.photo
        }
    }
