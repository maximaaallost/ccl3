package com.cc221013.bookify.ui

import androidx.lifecycle.ViewModel
import com.cc221013.bookify.data.DatabaseHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel(private val db: DatabaseHandler): ViewModel() {
    private val _mainViewState = MutableStateFlow(MainViewState())
    val mainViewState: StateFlow<MainViewState> = _mainViewState.asStateFlow()

    fun save(book: Book){
        db.insertBook(book)
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