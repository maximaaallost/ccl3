package com.cc221013.bookify.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.cc221013.bookify.ui.ReadingChallenge
class ReadingChallengeDatabase(context: Context) : SQLiteOpenHelper(context, dbName, null, 1) {
    companion object ReadingChallengeTable {
        private const val dbName = "ReadingChallengeDatabase"
        private const val tableName = "reading_challenges"
        private const val id = "_id"
        private const val title = "title"
        private const val days = "days"
        private const val bookCount = "book_count"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS $tableName ($id INTEGER PRIMARY KEY, " +
                "$title VARCHAR(50), " +
                "$days INT(3), " +
                "$bookCount INT(5));")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $tableName")
        onCreate(db)
    }

    fun insertChallenge(challenge: ReadingChallenge) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(title, challenge.title)
        values.put(days, challenge.days)
        values.put(bookCount, challenge.bookCount)

        db.insert(tableName, null, values)
    }

    fun updateChallenge(challenge: ReadingChallenge) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(title, challenge.title)
        values.put(days, challenge.days)
        values.put(bookCount, challenge.bookCount)

        db.update(tableName, values, "_id = ?", arrayOf(challenge.id.toString()))
    }

    fun deleteChallenge(challenge: ReadingChallenge) {
        val db = writableDatabase
        db.delete(tableName, "_id = ?", arrayOf(challenge.id.toString()))
    }

    fun getChallenges(): List<ReadingChallenge> {
        val allChallenges = mutableListOf<ReadingChallenge>()

        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $tableName", null)
        while (cursor.moveToNext()) {
            val idID = cursor.getColumnIndex(id)
            val titleID = cursor.getColumnIndex(title)
            val daysID = cursor.getColumnIndex(days)
            val bookCountID = cursor.getColumnIndex(bookCount)

            if (idID >= 0 && titleID >= 0 && daysID >= 0 && bookCountID >= 0) {
                allChallenges.add(
                    ReadingChallenge(
                        cursor.getString(titleID),
                        cursor.getInt(daysID),
                        cursor.getInt(bookCountID),
                        cursor.getInt(idID)
                    )
                )
            }
        }

        return allChallenges.toList()
    }
}