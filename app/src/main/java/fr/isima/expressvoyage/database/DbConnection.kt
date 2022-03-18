package fr.isima.expressvoyage.database

import android.content.Context
import androidx.room.Room

// Singleton used to "lazy load" the connection to the database
// If a connection already exist, we don't recreate it
object DbConnection {
    private var db: AppDatabase? = null

    fun getDb(appContext: Context): AppDatabase? {
        if (db == null) {
            db = Room.databaseBuilder(
                appContext,
                AppDatabase::class.java,
                "expressvoyage-database"
            ).build()
        }
        return db
    }
}
