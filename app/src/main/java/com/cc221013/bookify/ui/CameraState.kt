package com.cc221013.bookify.ui

import android.net.Uri

data class CameraState (
    val photosListState: List<Uri> = emptyList(),
    val filePermissionGranted: Boolean = false
)