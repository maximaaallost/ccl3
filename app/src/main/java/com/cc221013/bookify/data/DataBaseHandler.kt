package com.cc221013.bookify.data

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.cc221013.bookify.ui.Book


class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, dbName, null, 1) {
    companion object BookDatabase{
        private const val dbName = "BookDatabase"
        private const val tableName = "books"
        private const val id = "_id"
        private const val title = "title"
        private const val author = "author"
        private const val genre = "genre"
        private const val color = "color"
        private const val cover = "cover"
        private const val shelf = "shelf"
        private const val rating = "rating"
        private const val review = "review"
        private const val quote = "quote"
        private const val language = "language"
        private const val pages = "pages"
        private const val days = "days"
        private const val mediaType = "mediaType"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS $tableName ($id INTEGER PRIMARY KEY, " +
                "$title VARCHAR(50), " +
                "$author VARCHAR(20), " +
                "$genre VARCHAR(15), " +
                "$color VARCHAR(10), " +
                "$cover VARCHAR(40)," +
                "$shelf VARCHAR(10)," +
                "$rating INT(5)," +
                "$review VARCHAR(20)," +
                "$quote VARCHAR(60)," +
                "$language VARCHAR(10)," +
                "$pages INT(5)," +
                "$days INT(3)," +
                "$mediaType VARCHAR(10));")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $tableName")
        onCreate(db)
    }

    fun insertBook(book: Book){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(title,book.title)
        values.put(author,book.author)
        values.put(genre,book.genre)
        values.put(color,book.color)
        values.put(cover,book.cover)
        values.put(shelf,book.shelf)
        values.put(rating,book.rating)
        values.put(review,book.review)
        values.put(quote,book.quote)
        values.put(language,book.language)
        values.put(pages,book.pages)
        values.put(days,book.days)
        values.put(mediaType,book.mediaType)

        db.insert(tableName,null, values)
    }

    fun updateBook(book: Book){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(title,book.title)
        values.put(author,book.author)
        values.put(genre,book.genre)
        values.put(color,book.color)
        values.put(cover,book.cover)
        values.put(shelf,book.shelf)
        values.put(rating,book.rating)
        values.put(review,book.review)
        values.put(quote,book.quote)
        values.put(language,book.language)
        values.put(pages,book.pages)
        values.put(days,book.days)
        values.put(mediaType,book.mediaType)

        db.update(tableName,values,"_id = ?", arrayOf(book.id.toString()))
    }

    fun deleteBook(book: Book){
        val db = writableDatabase
        db.delete(tableName,"_id = ?", arrayOf(book.id.toString()))
    }

    @SuppressLint("Recycle")
    fun getBooks(): List<Book>{
        var allBooks = mutableListOf<Book>()

        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $tableName",null)
        while(cursor.moveToNext()){
            val idID = cursor.getColumnIndex(id)
            val titleID = cursor.getColumnIndex(title)
            val author = cursor.getColumnIndex(author)
            val genre = cursor.getColumnIndex(genre)
            val color = cursor.getColumnIndex(color)
            val cover = cursor.getColumnIndex(cover)
            val shelf = cursor.getColumnIndex(shelf)
            val rating = cursor.getColumnIndex(rating)
            val review = cursor.getColumnIndex(review)
            val quote = cursor.getColumnIndex(quote)
            val language = cursor.getColumnIndex(language)
            val pages = cursor.getColumnIndex(pages)
            val days = cursor.getColumnIndex(days)
            val mediaType = cursor.getColumnIndex(mediaType)
            if(idID >= 0 &&
                titleID >= 0 &&
                author >=0 &&
                genre >= 0 &&
                color >= 0 &&
                cover >= 0 &&
                shelf >= 0 &&
                rating >= 0 &&
                review >= 0 &&
                quote >= 0 &&
                language >= 0 &&
                pages >= 0 &&
                days >= 0 &&
                mediaType >= 0
                )
                allBooks.add(Book(
                    cursor.getString(titleID),
                    cursor.getString(author),
                    cursor.getString(genre),
                    cursor.getString(color),
                    cursor.getString(cover),
                    cursor.getString(shelf),
                    cursor.getInt(rating),
                    cursor.getString(review),
                    cursor.getString(quote),
                    cursor.getString(language),
                    cursor.getInt(pages),
                    cursor.getInt(days),
                    cursor.getString(mediaType),
                    cursor.getInt(idID)))
        }

        return allBooks.toList()
    }

}