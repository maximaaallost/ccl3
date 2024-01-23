package com.cc221013.bookify.ui

data class MainViewState(
    val books: List<Book> = emptyList(),
    val challenges: List<ReadingChallenge> = emptyList(),
    var editBook: Book = Book("","","", "", "", "", 0, "", "", "", 0, 0, ""),
    val editReadingChallenge: ReadingChallenge = ReadingChallenge("",0, 0, 0,0f, ""),
    val selectedScreen: Screen = Screen.Read,
    val openDialogEditReadBook: Boolean = false,
    val openDialogEditReadingChallenge: Boolean = false,
    val openDialogEditBook: Boolean = false,
    val openAlert: Boolean = false,
    val openReadBookAlert: Boolean = false
)
