package fr.fruitice.trome.feature.Objects.trome

import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database
import fr.fruitice.trome.feature.Objects.ratp.Line
import fr.fruitice.trome.feature.Objects.ratp.LineDao


@Database(entities = [ Line::class ], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun lineDao(): LineDao
}