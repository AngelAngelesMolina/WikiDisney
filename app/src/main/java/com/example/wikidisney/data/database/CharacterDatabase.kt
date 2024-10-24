package com.example.wikidisney.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.wikidisney.common.Converters
import com.example.wikidisney.data.database.dao.CharacterDao
import com.example.wikidisney.data.database.entities.CharacterEntity


@Database(entities = [CharacterEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class CharacterDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}