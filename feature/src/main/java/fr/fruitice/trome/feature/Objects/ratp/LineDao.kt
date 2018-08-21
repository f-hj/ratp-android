package fr.fruitice.trome.feature.Objects.ratp

import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query


interface LineDao {
    @Query("SELECT * FROM lines")
    fun loadAll(): List<Line>

    @Insert
    fun insertAll(lines: List<Line>)

    @Insert
    fun insert(line: Line)

    @Delete
    fun delete(line: Line)
}