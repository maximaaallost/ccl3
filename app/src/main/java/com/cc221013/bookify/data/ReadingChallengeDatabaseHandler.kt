package com.cc221013.bookify.data


import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.cc221013.bookify.ui.ReadingChallenge


class ReadingChallengeDatabaseHandler(context: Context) : SQLiteOpenHelper(context, dbChallenge, null, 1) {
    companion object ReadingChallengeTable {
        private const val dbChallenge = "ReadingChallengeDatabase"
        private const val tableName = "reading_challenges"
        private const val id = "_id"
        private const val title = "title"
        private const val days = "days"
        private const val goalBookCount = "goal_book_count"
        private const val userBookCount = "user_book_count"
        private const val progress = "progress"
        private const val startDate = "start_date"


    }

    override fun onCreate(dbChallenge: SQLiteDatabase?) {
        dbChallenge?.execSQL("CREATE TABLE IF NOT EXISTS $tableName ($id INTEGER PRIMARY KEY, " +
                "$title VARCHAR(50), " +
                "$days INT(3), " +
                "$goalBookCount INT(5),"+
                "$userBookCount INT(5),"+
                "$progress FLOAT(10),"+
                "$startDate VARCHAR(10));")
    }

    override fun onUpgrade(dbChallenge: SQLiteDatabase?, p1: Int, p2: Int) {
        dbChallenge?.execSQL("DROP TABLE IF EXISTS $tableName")
        onCreate(dbChallenge)
    }

    fun insertChallenge(challenge: ReadingChallenge) {
        val dbChallenge = this.writableDatabase
        val values = ContentValues()
        values.put(title, challenge.title)
        values.put(days, challenge.days)
        values.put(goalBookCount, challenge.goalBookCount)
        values.put(userBookCount, challenge.userBookCount)
        values.put(progress, challenge.progress)
        values.put(startDate, challenge.startDate)

        dbChallenge.insert(tableName, null, values)
    }

    fun updateChallenge(challenge: ReadingChallenge) {
        val dbChallenge = this.writableDatabase
        val values = ContentValues()
        values.put(title, challenge.title)
        values.put(days, challenge.days)
        values.put(goalBookCount, challenge.goalBookCount)
        values.put(userBookCount, challenge.userBookCount)
        values.put(progress, challenge.progress)
        values.put(startDate, challenge.startDate)

        dbChallenge.update(tableName, values, "_id = ?", arrayOf(challenge.id.toString()))
    }

    fun deleteChallenge(challenge: ReadingChallenge) {
        val dbChallenge = writableDatabase
        dbChallenge.delete(tableName, "_id = ?", arrayOf(challenge.id.toString()))
    }

    fun getChallenges(): List<ReadingChallenge> {
        val allChallenges = mutableListOf<ReadingChallenge>()

        val dbChallenge = this.readableDatabase
        val cursor = dbChallenge.rawQuery("SELECT * FROM $tableName", null)
        while (cursor.moveToNext()) {
            val idID = cursor.getColumnIndex(id)
            val titleID = cursor.getColumnIndex(title)
            val daysID = cursor.getColumnIndex(days)
            val goalBookCountID = cursor.getColumnIndex(goalBookCount)
            val userBookCountID = cursor.getColumnIndex(userBookCount)
            val progressID = cursor.getColumnIndex(progress)
            val startDateID = cursor.getColumnIndex(startDate)

            if (idID >= 0 && titleID >= 0 && daysID >= 0 && goalBookCountID >= 0 &&  userBookCountID >= 0 && progressID >= 0 && startDateID >= 0) {
                allChallenges.add(
                    ReadingChallenge(
                        cursor.getString(titleID),
                        cursor.getInt(daysID),
                        cursor.getInt(goalBookCountID),
                        cursor.getInt(userBookCountID),
                        cursor.getFloat(progressID),
                        cursor.getString(startDateID),
                        cursor.getInt(idID)
                    )
                )
            }
        }
        return allChallenges.toList()
    }
//    fun getChallengeById(challengeId: Int): ReadingChallenge? {
//        val dbChallenge = this.readableDatabase
//        val cursor = dbChallenge.rawQuery("SELECT * FROM $tableName WHERE $id = ?", arrayOf(challengeId.toString()))
//
//        var challenge: ReadingChallenge? = null
//
//        if (cursor.moveToFirst()) {
//            val idID = cursor.getColumnIndex(id)
//            val titleID = cursor.getColumnIndex(title)
//            val daysID = cursor.getColumnIndex(days)
//            val goalBookCountID = cursor.getColumnIndex(goalBookCount)
//            val userBookCountID = cursor.getColumnIndex(userBookCount)
//            val progressID = cursor.getColumnIndex(progress)
//            val startDateID = cursor.getColumnIndex(startDate)
//
//            if (idID >= 0 && titleID >= 0 && daysID >= 0 && goalBookCountID >= 0  && userBookCountID >= 0 && progressID >= 0 && startDateID >= 0) {
//                challenge = ReadingChallenge(
//                    cursor.getString(titleID),
//                    cursor.getInt(daysID),
//                    cursor.getInt(goalBookCountID),
//                    cursor.getInt(userBookCountID),
//                    cursor.getFloat(progressID),
//                    cursor.getString(startDateID),
//                    cursor.getInt(idID)
//                )
//            }
//        }
//
//        cursor.close()
//        return challenge
//    }
}