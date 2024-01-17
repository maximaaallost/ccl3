package com.cc221013.bookify.ui

import android.net.Uri

data class CameraState (
    val photosListState: List<Uri> = emptyList(),
    val enableCameraPreview: Boolean = false,
    val cameraPermissionGranted: Boolean = false,
    val filePermissionGranted: Boolean = false
)