package com.cc221013.bookify

import com.cc221013.bookify.ui.theme.BookifyTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.cc221013.bookify.data.DatabaseHandler
import com.cc221013.bookify.ui.MainView
import com.cc221013.bookify.ui.MainViewModel


class MainActivity : ComponentActivity() {
    private val db = DatabaseHandler(this)
    private val mainViewModel = MainViewModel(db)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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