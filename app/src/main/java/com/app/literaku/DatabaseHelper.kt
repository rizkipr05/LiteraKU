package com.example.yourapp.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // Database name
    companion object {
        private const val DATABASE_NAME = "UserDB"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "users"
        private const val COL_ID = "id"
        private const val COL_FULL_NAME = "full_name"
        private const val COL_EMAIL = "email"
        private const val COL_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // SQL query to create the users table
        val CREATE_TABLE = ("CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COL_FULL_NAME TEXT, "
                + "$COL_EMAIL TEXT, "
                + "$COL_PASSWORD TEXT)")
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Drop the table if it exists and create it again
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // Method to insert user data into the database
    fun insertUser(fullName: String, email: String, password: String): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_FULL_NAME, fullName)
        values.put(COL_EMAIL, email)
        values.put(COL_PASSWORD, password)

        // Insert the data into the table and return the inserted row ID
        return db.insert(TABLE_NAME, null, values)
    }

    // Method to check if the email already exists in the database
    fun isEmailExists(email: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COL_EMAIL = ?", arrayOf(email))
        val emailExists = cursor.count > 0
        cursor.close()
        return emailExists
    }
}
