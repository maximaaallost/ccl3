package com.cc221013.bookify.ui

data class MainViewState(
    val books: List<Book> = emptyList(),
    val editBook: Book = Book("","","", "", "", "", 0, "", "", "", 0, 0, ""),
    val selectedScreen: Screen = Screen.Read,
    val openDialogEditBook: Boolean = false
)
