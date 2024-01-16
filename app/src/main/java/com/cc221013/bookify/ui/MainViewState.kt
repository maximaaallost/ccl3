package com.cc221013.bookify.ui

data class MainViewState(
    val students: List<BccStudent> = emptyList(),
    val editStudent: BccStudent = BccStudent("",""),
    val selectedScreen: Screen = Screen.First,
    val openDialog: Boolean = false
)
