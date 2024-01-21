package com.cc221013.bookify.ui

data class ReadingChallenge (
    val title: String,
    val days: Int,
    val goalBookCount: Int,
    val userBookCount: Int,
    var progress: Float,
    val startDate: String,
    val id: Int = 0
    ) {
}