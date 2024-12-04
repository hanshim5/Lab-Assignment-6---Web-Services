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

package com.example.flickrphotos.network

import com.example.flickrphotos.model.FlickrPhoto
import com.example.flickrphotos.model.FlickrResponse
import retrofit2.http.GET

/**
 * A public interface that exposes the [getPhotos] method
 */
interface FlickrApiService {
    /**
     * Returns a [List] of [FlickrPhoto] and this method can be called from a Coroutine.
     * The @GET annotation indicates that the "photos" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("?method=flickr.people.getPhotos&" +
            "api_key=52ddad1635e1f58ff1a1fade105faaf2&" +
            "user_id=201970008%40N08&format=json&" +
            "nojsoncallback=1")
    suspend fun getPhotos(): FlickrResponse
}