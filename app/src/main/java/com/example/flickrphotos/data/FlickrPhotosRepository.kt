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

import android.util.Log
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
//class NetworkFlickrPhotosRepository(
//    private val flickrApiService: FlickrApiService
//) : FlickrPhotosRepository {
//    /** Fetches list of FlickrPhoto from flickrApi*/
//    override suspend fun getFlickrPhotos(): List<FlickrPhoto> {
//        try {
//            val response = flickrApiService.getPhotos(
//                apiKey = "52ddad1635e1f58ff1a1fade105faaf2", // Your API key
//                userId = "201970008@N08" // Replace with your Flickr user ID
//            )
//            Log.d("FlickrAPI", "Response: $response")
//            return response.photos.photo
//        } catch (e: Exception) {
//            Log.e("FlickrAPI", "Error fetching photos", e)
//            throw e
//        }
//    }
//}
class NetworkFlickrPhotosRepository(
    private val flickrApiService: FlickrApiService
) : FlickrPhotosRepository {
    override suspend fun getFlickrPhotos(): List<FlickrPhoto> {
        try {
            val response = flickrApiService.getPhotos(
                apiKey = "YOUR_API_KEY",  // Replace with your actual API key
                userId = "YOUR_USER_ID"   // Replace with your actual Flickr user ID
            )

            // Log the imgSrc of each photo in the response
            response.photos.photo.forEach { photo ->
                Log.d("FlickrAPI", "Image URL: ${photo.imgSrc}") // Log imgSrc
            }

            return response.photos.photo  // Return the list of FlickrPhoto objects
        } catch (e: Exception) {
            Log.e("FlickrAPI", "Error fetching photos", e)
            throw e
        }
    }
}
