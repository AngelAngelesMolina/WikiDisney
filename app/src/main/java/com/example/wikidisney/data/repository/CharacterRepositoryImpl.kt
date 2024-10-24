package com.example.wikidisney.data.repository

import android.util.Log
import com.example.wikidisney.common.Resource
import com.example.wikidisney.data.database.dao.CharacterDao
import com.example.wikidisney.data.database.entities.CharacterEntity
import com.example.wikidisney.data.remote.DisneyApi
import com.example.wikidisney.data.remote.dto.CharacterResponse
import com.example.wikidisney.data.remote.dto.CharactersResponse
import com.example.wikidisney.data.remote.dto.toCharacterEntity
import com.example.wikidisney.domain.repository.CharacterRepository
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val api: DisneyApi,
    private val characterDao: CharacterDao
) :
    CharacterRepository {
    override suspend fun getCharacterList(page: Int): CharactersResponse {
        return api.getCharactersList(page)
    }

    override suspend fun getCharacterListRoom(page: Int): Resource<List<CharacterEntity>> {
        return try {
            val response = api.getCharactersList(page)

            val characterEntities = response.data.map { characterInfo ->
                characterInfo.toCharacterEntity()
            }

            for (characterEntity in characterEntities) {
                val exists = characterDao.characterExists(characterEntity.id)
                if (exists == 0) {
                    characterDao.insertCharacter(characterEntity)
                }
            }

            Resource.Success(characterEntities)
        } catch (e: Exception) {
            val localData = characterDao.getCharacters()
            if (localData.isNotEmpty()) {
                Resource.Success(localData)
            } else {
                Resource.Error("Error de red y no hay datos locales disponibles.")
            }
        }
    }

    override suspend fun getCharacterInfoAlt(idChar: Int): CharacterResponse {
        return api.getCharacterInfo(idChar)
    }


    override suspend fun getCharacterInfo(idCharacter: Int): Resource<CharacterResponse> {
        val response = try {
            api.getCharacterInfo(idCharacter)
        } catch (e: Exception) {
            return Resource.Error("An unknow error occured")
        }
        return Resource.Success(response)

    }

    override suspend fun getCharacterInfoRoom(characterId: Int): Resource<CharacterEntity> {
        return try {
            // Intentamos obtener el personaje desde la base de datos local
            val localCharacter = characterDao.getCharacterById(characterId)

            if (localCharacter != null) {
                // Si el personaje existe en la base de datos local, devolvemos el dato local
                // y hacemos una actualización en la base de datos con los nuevos datos de la API
                val response = api.getCharacterInfo(characterId)
                val updatedCharacterEntity = response.data.toCharacterEntity()

                // Actualizamos la base de datos si hay nuevos datos
                characterDao.updateCharacter(updatedCharacterEntity)

                // Retornamos el personaje local inmediatamente
                Resource.Success(localCharacter)
            } else {
                // Si no hay datos locales, realizamos la llamada a la API directamente
                val response = api.getCharacterInfo(characterId)
                val characterEntity = response.data.toCharacterEntity()

                // Insertamos el personaje en la base de datos
                characterDao.insertCharacter(characterEntity)
                Resource.Success(characterEntity)
            }
        } catch (e: Exception) {
            // En caso de error, intentamos devolver los datos locales si están disponibles
            val localCharacter = characterDao.getCharacterById(characterId)
            if (localCharacter != null) {
                Resource.Success(localCharacter)
            } else {
                Resource.Error("Error de red y no hay datos locales disponibles.")
            }
        }
    }

}