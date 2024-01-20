package com.cc221013.bookify.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.cc221013.bookify.data.DatabaseHandler
import com.cc221013.bookify.data.ReadingChallengeDatabaseHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel(private val db: DatabaseHandler, private val dbChallenge: ReadingChallengeDatabaseHandler): ViewModel() {
    private val _mainViewState = MutableStateFlow(MainViewState())
    val mainViewState: StateFlow<MainViewState> = _mainViewState.asStateFlow()
    private val _cameraState = MutableStateFlow(CameraState())
    val cameraState: StateFlow<CameraState> = _cameraState.asStateFlow()
    private val _bookCount = mutableStateOf(0)
    val bookCount: State<Int> = _bookCount

    fun setFilePermission(value: Boolean){
        _cameraState.update { it.copy(filePermissionGranted = value) }
    }


    fun save(book: Book){
        if (book.shelf == "Read") {
            _bookCount.value += 1
        }
        db.insertBook(book)
    }

    fun showReadingChallengeDialog() {
        _mainViewState.update { it.copy(openDialogEditReadingChallenge = true) }
    }

    fun dismissReadingChallengeDialog() {
        _mainViewState.update { it.copy(openDialogEditReadingChallenge = false) }
    }

    fun saveReadingChallenge(challenge: ReadingChallenge) {
        dbChallenge.insertChallenge(challenge)
        _bookCount.value = 0
        dismissReadingChallengeDialog()
    }


    private val _selectedBook = mutableStateOf<Book?>(null)
    val selectedBook: State<Book?> = _selectedBook

    fun setSelectedBook(book: Book) {
        _selectedBook.value = book
    }

    fun getBooks() {
        _mainViewState.update { it.copy(books = db.getBooks()) }
    }

    fun getChallenges() {
        _mainViewState.update { it.copy(challenges = dbChallenge.getChallenges()) }
    }

    fun selectScreen(screen: Screen){
        _mainViewState.update { it.copy(selectedScreen = screen) }
    }

    fun clickDelete(book: Book){
        db.deleteBook(book)
        getBooks()
    }


    fun saveBook(book: Book){
        _mainViewState.update { it.copy(openDialogEditBook = false) }
        db.updateBook(book)
        getBooks()
        if (book.shelf == "Read") {
            _bookCount.value += 1
        }
    }
    fun updateChallenge(challenge: ReadingChallenge){
        dbChallenge.updateChallenge(challenge)
        getChallenges()
    }

    fun dialogEditBook(book: Book) {
        _mainViewState.update { it.copy(openDialogEditBook = true, editBook = book) }
    }

    fun dismissDialog(){
        _mainViewState.update { it.copy(openDialogEditBook = false) }
    }
}