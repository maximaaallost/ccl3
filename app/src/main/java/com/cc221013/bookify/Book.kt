package com.cc221013.bookify

data class Book (
    val title: String,
    val author: String,
    val genre: String,
    val color: String,
    val cover: String,
    val shelf: String,
    val rating: Int?,
    val review: String?,
    val quote: String?,
    val language: String?,
    val pages: Int?,
    val days: Int?,
    val mediaType: String?,
    val id: Int = 0
){
}