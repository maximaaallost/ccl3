package com.cc221013.bookify.ui

data class ReadingChallenge (
    val title: String,
     val days: Int?,
     val bookCount: Int,
     val progress: Float,
     val startDate: String,
     val id: Int = 0
    ) {
}