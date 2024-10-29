package com.jaennova.recipebuddy.ui.screens.appcomponets

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.jaennova.recipebuddy.R

@Composable
fun rememberCoilPainter(request: String): Painter {
    return rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current).data(data = request)
            .apply(block = fun ImageRequest.Builder.() {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_foreground)
                error(R.drawable.ic_launcher_foreground)
            }).build()
    )
}