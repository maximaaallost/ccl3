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
import com.cc221013.bookify.data.ReadingChallengeDatabaseHandler
import com.cc221013.bookify.ui.MainView
import com.cc221013.bookify.ui.MainViewModel
import com.google.common.util.concurrent.ListenableFuture
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors




class MainActivity : ComponentActivity() {
    private val db = DatabaseHandler(this)
    private val dbChallenge = ReadingChallengeDatabaseHandler(this)
    private val mainViewModel = MainViewModel(db, dbChallenge)
    private val requestFilePermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){ mainViewModel.setFilePermission(it) }

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestFilePermission()
        setContent {
            BookifyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                        MainView(mainViewModel)
                    }

                    }
                }
            }
        }

