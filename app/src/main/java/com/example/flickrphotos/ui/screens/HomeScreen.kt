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

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridCells.*
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.flickrphotos.R
import com.example.flickrphotos.model.FlickrPhoto
import com.example.flickrphotos.ui.theme.FlickrPhotosTheme

@Composable
fun HomeScreen(
    flickrUiState: FlickrUiState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    when (flickrUiState) {
        is FlickrUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is FlickrUiState.Success -> PhotosGridScreen(
            photos = flickrUiState.photos, modifier = modifier.fillMaxWidth()
        )
        is FlickrUiState.Error -> ErrorScreen(modifier = modifier.fillMaxSize())
    }
}

/**
 * The home screen displaying the loading message.
 */
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

/**
 * The home screen displaying error message with re-attempt button.
 */
@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
    }
}

/**
 * ResultScreen displaying number of photos retrieved.
 */
@Composable
//fun ResultScreen(photos: String, modifier: Modifier = Modifier) {
//    Box(
//        contentAlignment = Alignment.Center,
//        modifier = modifier
//    ) {
//        Text(text = photos)
//    }
//}
fun PhotosGridScreen(photos: List<FlickrPhoto>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(128.dp),
        modifier = modifier
    ) {
        items(photos) { photo ->
            photo.imgSrc?.let { url ->
                AsyncImage(
                    model = url,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(4.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.surface)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    FlickrPhotosTheme {
        LoadingScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    FlickrPhotosTheme {
        ErrorScreen()
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PhotosGridScreenPreview() {
//    FlickrPhotosTheme {
//        val mockData = List(10) { FlickrPhoto("$it", "") }
//        ResultScreen(stringResource(R.string.placeholder_success))
//    }
//}
//user_id: 201970008@N08
//key: 52ddad1635e1f58ff1a1fade105faaf2
//secret: 8e39f2864b3f57ea

/*
Overview
The Flickr API consists of a set of callable methods, and some API endpoints.
To perform an action using the Flickr API, you need to select a calling convention, send a request to its endpoint specifying a method and some arguments, and will receive a formatted response.
All request formats, listed on the API index page, take a list of named parameters.
The REQUIRED parameter method is used to specify the calling method.
The REQUIRED parameter api_key is used to specify your API Key.
The optional parameter format is used to specify a response format.
The arguments, responses and error codes for each method are listed on the method's spec page. Methods are lists on the API index page.
Note: The Flickr API exposes identifiers for users, photos, photosets and other uniquely identifiable objects.
These IDs should always be treated as opaque strings, rather than integers of any specific type.
The format of the IDs can change over time, so relying on the current format may cause you problems in the future.
Endpoint
https://api.flickr.com/services
*/