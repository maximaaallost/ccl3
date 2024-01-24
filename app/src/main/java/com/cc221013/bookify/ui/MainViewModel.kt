package com.cc221013.bookify.ui

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.cc221013.bookify.data.DatabaseHandler
import com.cc221013.bookify.data.ReadingChallengeDatabaseHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.lang.Float.min

class MainViewModel(private val db: DatabaseHandler, private val dbChallenge: ReadingChallengeDatabaseHandler): ViewModel() {
    private val _mainViewState = MutableStateFlow(MainViewState())
    val mainViewState: StateFlow<MainViewState> = _mainViewState.asStateFlow()
    private val _cameraState = MutableStateFlow(CameraState())
    val cameraState: StateFlow<CameraState> = _cameraState.asStateFlow()

    fun setFilePermission(value: Boolean){
        _cameraState.update { it.copy(filePermissionGranted = value) }
    }

    fun saveReadingChallenge(challenge: ReadingChallenge) {
        dbChallenge.insertChallenge(challenge)
        dismissReadingChallengeDialog()
    }
    fun save(book: Book, readingChallenges: List<ReadingChallenge>){
        db.insertBook(book)
        getBooks()
        var newBookCount  = 0;
        readingChallenges.forEach { challenge ->
            Log.i ("challenge", challenge.title)
            newBookCount = (challenge.userBookCount + 1)
            updateChallenge(
                challenge.copy(
                    userBookCount = newBookCount,
                )
            )
        }
    }

    fun showReadingChallengeDialog() {
        _mainViewState.update { it.copy(openDialogEditReadingChallenge = true) }
    }

    fun dismissReadingChallengeDialog() {
        _mainViewState.update { it.copy(openDialogEditReadingChallenge = false) }
    }

    fun clickCancel(){
        _mainViewState.update { it.copy(openAlert = false, openReadBookAlert = false) }
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
    fun deleteReadBookAlert(book: Book){
        _mainViewState.update { it.copy(openDialogEditBook = false) }
        _mainViewState.update { it.copy(openDialogEditReadBook = false) }
        _mainViewState.update { it.copy(openReadBookAlert = true, editBook = book) }
    }
    fun deleteAlert(book: Book){
        _mainViewState.update { it.copy(openDialogEditBook = false) }
        _mainViewState.update { it.copy(openDialogEditReadBook = false) }
        _mainViewState.update { it.copy(openAlert = true, editBook = book) }
    }
    fun deleteChallenge(challenge: ReadingChallenge){
        dbChallenge.deleteChallenge(challenge)
        getChallenges()
    }


    fun saveBook(book: Book, readingChallenges: List<ReadingChallenge>){
        _mainViewState.update { it.copy(openDialogEditBook = false) }
        _mainViewState.update { it.copy(openDialogEditReadBook = false) }
        db.updateBook(book)
        getBooks()
        var newBookCount  = 0;
        readingChallenges.forEach { challenge ->
            Log.i ("challenge", challenge.title)
            newBookCount = (challenge.userBookCount + 1)
            updateChallenge(
                challenge.copy(
                     userBookCount = newBookCount,
                )
            )
        }
    }

    fun updateChallenge(challenge: ReadingChallenge){
        dbChallenge.updateChallenge(challenge)
        getChallenges()
    }
    fun dialogEditBook(book: Book) {
        _mainViewState.update { it.copy(openDialogEditBook = true, editBook = book) }
    }

    fun dialogEditReadBook(book: Book) {
        _mainViewState.update { it.copy(openDialogEditReadBook = true, editBook = book) }
    }

    fun dismissDialog(){
        _mainViewState.update { it.copy(openDialogEditBook = false) }
        _mainViewState.update { it.copy(openDialogEditReadBook = false) }
        _mainViewState.update { it.copy(openReadBookAlert = false) }
        _mainViewState.update { it.copy(openAlert = false) }
    }
}