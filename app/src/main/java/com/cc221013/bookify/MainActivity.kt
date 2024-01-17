package com.cc221013.bookify

import android.Manifest
import android.content.pm.PackageManager
import com.cc221013.bookify.ui.theme.BookifyTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.cc221013.bookify.data.DatabaseHandler
import com.cc221013.bookify.ui.CameraView
import com.cc221013.bookify.ui.MainView
import com.cc221013.bookify.ui.MainViewModel
import com.google.common.util.concurrent.ListenableFuture
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors



class MainActivity : ComponentActivity() {
    private val db = DatabaseHandler(this)
    private val mainViewModel = MainViewModel(db)
    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){ mainViewModel.setCameraPermission(it) }
    private val requestFilePermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){ mainViewModel.setFilePermission(it) }
    private lateinit var cameraProviderFuture : ListenableFuture<ProcessCameraProvider>
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var previewView: PreviewView
    private val imageCapture : ImageCapture = ImageCapture.Builder().build()
    private val preview : androidx.camera.core.Preview = androidx.camera.core.Preview.Builder().build()

    private fun setupCamera(){
        previewView = PreviewView(this)
        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener(Runnable {
            val cameraProvider = cameraProviderFuture.get()
            bindPreview(cameraProvider)
        }, ContextCompat.getMainExecutor(this))
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    private fun bindPreview(cameraProvider : ProcessCameraProvider) {
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            this as LifecycleOwner,
            CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build(),
            preview, imageCapture)
        preview.setSurfaceProvider(previewView.surfaceProvider)
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }

        return if (mediaDir != null && mediaDir.exists()) mediaDir else filesDir
    }

    private fun requestPermission(){
        ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA).let{ result ->
            if(result != PackageManager.PERMISSION_GRANTED){
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            } else {
                mainViewModel.setCameraPermission(true)
            }
        }
    }

    private fun requestFilePermission(){
        ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE).let{ result ->
            if(result != PackageManager.PERMISSION_GRANTED){
                requestFilePermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestFilePermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            } else {
                mainViewModel.setFilePermission(true)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermission()
        requestFilePermission()
        setContent {
            BookifyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val camState = mainViewModel.cameraState.collectAsState()
                    if (!camState.value.enableCameraPreview){
                        MainView(mainViewModel)
                    } else {
                        setupCamera()
                        CameraView(
                            mainViewModel,
                            previewView,
                            imageCapture,
                            cameraExecutor,
                            getOutputDirectory()
                        )
                    }
                }
            }
        }
    }
}