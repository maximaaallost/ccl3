package com.cc221013.bookify.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.cc221013.bookify.ui.BccStudent


class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, dbName, null, 1) {
    companion object StudentDatabase{
        private const val dbName = "StudentDatabase"
        private const val tableName = "students"
        private const val id = "_id"
        private const val name = "name"
        private const val uid = "uid"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS $tableName ($id INTEGER PRIMARY KEY, $name VARCHAR(30), $uid VARCHAR(10));")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $tableName")
        onCreate(db)
    }

    fun insertStudent(student: BccStudent){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(name,student.name)
        values.put(uid,student.uid)

        db.insert(tableName,null, values)
    }

    fun updateStudent(student:BccStudent){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(name, student.name)
        values.put(uid, student.uid)

        db.update(tableName,values,"_id = ?", arrayOf(student.id.toString()))
    }

    fun deleteStudent(student: BccStudent){
        val db = writableDatabase
        db.delete(tableName,"_id = ?", arrayOf(student.id.toString()))
    }

    fun getStudents(): List<BccStudent>{
        var allStudents = mutableListOf<BccStudent>()

        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $tableName",null)
        while(cursor.moveToNext()){
            val idID = cursor.getColumnIndex(id)
            val nameID = cursor.getColumnIndex(name)
            val uidID = cursor.getColumnIndex(uid)
            if(nameID >= 0 && uidID >=0)
                allStudents.add(BccStudent(cursor.getString(nameID),cursor.getString(uidID),cursor.getInt(idID)))
        }

        return allStudents.toList()
    }
}