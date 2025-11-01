package com.xcvi.micros

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.xcvi.micros.data.DatabaseDriverFactory

class AndroidDatabaseDriverFactory(private val context: Context) : DatabaseDriverFactory {
    override fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(MicrosDB.Schema, context, "micros.db")
    }
}
