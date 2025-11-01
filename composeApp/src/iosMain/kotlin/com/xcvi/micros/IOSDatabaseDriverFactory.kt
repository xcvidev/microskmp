package com.xcvi.micros

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.xcvi.micros.data.DatabaseDriverFactory

class IOSDatabaseDriverFactory(private val dbName: String) : DatabaseDriverFactory {
    override fun createDriver(): SqlDriver {
        return NativeSqliteDriver(MicrosDB.Schema, dbName)
    }
}
