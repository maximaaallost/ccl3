package com.cc221013.bookify.ui

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.cc221013.bookify.data.DatabaseHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel(private val db: DatabaseHandler): ViewModel() {
    private val _mainViewState = MutableStateFlow(MainViewState())
    val mainViewState: StateFlow<MainViewState> = _mainViewState.asStateFlow()

    private val _cameraState = MutableStateFlow(CameraState())
    val cameraState: StateFlow<CameraState> = _cameraState.asStateFlow()

    fun setCameraPermission(value: Boolean){
        _cameraState.update { it.copy(cameraPermissionGranted = value) }
    }
    fun setFilePermission(value: Boolean){
        _cameraState.update { it.copy(filePermissionGranted = value) }
    }

    fun enableCameraPreview(value: Boolean){
        _cameraState.update { it.copy(enableCameraPreview = value) }
    }

    fun setNewUri(value: Uri){
        _cameraState.update { it.copy(photosListState = it.photosListState + value) }
        enableCameraPreview(false)
    }

    fun save(book: Book){
        db.insertBook(book)
    }

    private val _selectedBook = mutableStateOf<Book?>(null)
    val selectedBook: State<Book?> = _selectedBook

    fun setSelectedBook(book: Book) {
        _selectedBook.value = book
    }

    fun getBooks() {
        _mainViewState.update { it.copy(books = db.getBooks()) }
    }

    fun selectScreen(screen: Screen){
        _mainViewState.update { it.copy(selectedScreen = screen) }
    }

    fun clickDelete(book: Book){
        db.deleteBook(book)
        getBooks()
    }

    fun editBook(book: Book){
        _mainViewState.update { it.copy(openDialog = true, editBook = book) }
    }

    fun saveBook(book: Book){
        _mainViewState.update { it.copy(openDialog = false) }
        db.updateBook(book)
        getBooks()
    }

    fun dismissDialog(){
        _mainViewState.update { it.copy(openDialog = false) }
    }
}