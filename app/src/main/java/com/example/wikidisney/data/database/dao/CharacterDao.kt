package com.example.wikidisney.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.wikidisney.data.database.entities.CharacterEntity
@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: CharacterEntity)
    @Query("SELECT * FROM characters WHERE id = :characterId")
    suspend fun getCharacterById(characterId: Int): CharacterEntity?
    @Query("SELECT * FROM characters")
    suspend fun getCharacters(): List<CharacterEntity>
    @Query("SELECT COUNT(*) FROM characters WHERE id = :characterId")
    suspend fun characterExists(characterId: Int): Int
    @Query("SELECT * FROM characters WHERE isFavorite = 1")
    suspend fun getFavoriteCharacters(): List<CharacterEntity>
    @Update
    suspend fun updateCharacter(character: CharacterEntity)

}