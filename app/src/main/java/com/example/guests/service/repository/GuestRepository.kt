package com.example.guests.service.repository

import android.content.ContentValues
import android.content.Context
import com.example.guests.constants.DataBaseConstants
import com.example.guests.service.model.GuestModel
import java.lang.Exception

class GuestRepository private constructor(context: Context){

    //Singleton pattern

    private var mGuestDataBaseHelper: GuestDataBaseHelper = GuestDataBaseHelper(context)

    companion object {
        private lateinit var repository: GuestRepository //armazena instância da classe

        //captura instância e retorna instância da classe
        fun getInstance(context: Context) : GuestRepository {
            if (!::repository.isInitialized) {
                repository = GuestRepository(context)
            }
            return repository
        }
    }

    //

    //realiza consulta no DB de acordo com ID do convidado
    fun get(id: Int): GuestModel? {
        var guest: GuestModel? = null

        return try {
            val db = mGuestDataBaseHelper.readableDatabase

            //lista colunas do DB
            val projection = arrayOf(
                    DataBaseConstants.GUEST.COLUMNS.NAME,
                    DataBaseConstants.GUEST.COLUMNS.PRESENCE)

            //seleciona ID igual ao ID do convidado
            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            //realiza consulta no DB
            val cursor = db.query(
                    DataBaseConstants.GUEST.TABLE_NAME,
                    projection,
                    selection,
                    args,
                    null,
                    null,
                    null)

            //valida estado cursor e seleciona nome e presença do convidado na DB
            if (cursor != null && cursor.count > 0) {
                cursor.moveToFirst()

                val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                val presence = (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE)) == 1)

                guest = GuestModel(id, name, presence)
            }

            cursor?.close() //libera cursor após localizar o nome na DB

            guest
        } catch (e: Exception) {
            guest
        }
    }

    //realiza consulta de DB total
    fun getAll(): List<GuestModel> {
        val list: MutableList<GuestModel> = ArrayList()

        return try {
            val db = mGuestDataBaseHelper.readableDatabase

            //lista colunas do DB
            val projection = arrayOf(
                    DataBaseConstants.GUEST.COLUMNS.ID,
                    DataBaseConstants.GUEST.COLUMNS.NAME,
                    DataBaseConstants.GUEST.COLUMNS.PRESENCE)

            //realiza consulta no DB
            val cursor = db.query(
                    DataBaseConstants.GUEST.TABLE_NAME,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    null)

            //valida estado cursor e seleciona nome e presença do convidado na DB
            if (cursor != null && cursor.count > 0) {
                //enquanto tiver valor no cursor, move para o próximo e preenche a lista
                while (cursor.moveToNext()) {
                    val id = cursor.getInt((cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID)))
                    val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val presence = (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE)) == 1)

                    val guest = GuestModel(id, name, presence)
                    list.add(guest)
                }
            }

            cursor?.close() //libera cursor após localizar o nome na DB

            list
        } catch (e: Exception) {
            list
        }
    }

    //realiza consulta apenas de convidados presentes
    fun getPresent(): List<GuestModel> {
        val list: MutableList<GuestModel> = ArrayList()

        return try {
            val db = mGuestDataBaseHelper.readableDatabase


            //realiza consulta no DB (usando rawQuery ao invés de Query)
            val cursor = db.rawQuery("SELECT id, name, presence FROM Guest WHERE presence = 1", null)

            //valida estado cursor e seleciona nome e presença do convidado na DB
            if (cursor != null && cursor.count > 0) {
                //enquanto tiver valor no cursor, move para o próximo e preenche a lista
                while (cursor.moveToNext()) {
                    val id = cursor.getInt((cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID)))
                    val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val presence = (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE)) == 1)

                    val guest = GuestModel(id, name, presence)
                    list.add(guest)
                }
            }

            cursor?.close() //libera cursor após localizar o nome na DB

            list
        } catch (e: Exception) {
            list
        }
    }

    //realiza consulta apenas de convidados ausentes
    fun getAbsent(): List<GuestModel> {
        val list: MutableList<GuestModel> = ArrayList()

        return try {
            val db = mGuestDataBaseHelper.readableDatabase


            //realiza consulta no DB (usando rawQuery ao invés de Query)
            val cursor = db.rawQuery(
                    "SELECT id, name, presence FROM Guest WHERE presence = 0", null)

            //valida estado cursor e seleciona nome e presença do convidado na DB
            if (cursor != null && cursor.count > 0) {
                //enquanto tiver valor no cursor, move para o próximo e preenche a lista
                while (cursor.moveToNext()) {
                    val id = cursor.getInt((cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID)))
                    val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val presence = (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE)) == 1)

                    val guest = GuestModel(id, name, presence)
                    list.add(guest)
                }
            }

            cursor?.close() //libera cursor após localizar o nome na DB

            list
        } catch (e: Exception) {
            list
        }
    }

    // CRUD - Create, Read, Update, Delete

    fun save(guest: GuestModel): Boolean {
        return try {
            val db = mGuestDataBaseHelper.writableDatabase

            //cria valores para DB
            val contentValues = ContentValues()
            contentValues.put(DataBaseConstants.GUEST.COLUMNS.NAME, guest.name)
            contentValues.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, guest.presence)

            //insere valores na DB
            db.insert(DataBaseConstants.GUEST.TABLE_NAME, null, contentValues)

            true
        } catch (e: Exception) {
            false
        }
    }

    fun update(guest: GuestModel): Boolean {
        return try {
            val db = mGuestDataBaseHelper.writableDatabase

            val contentValues = ContentValues()
            contentValues.put(DataBaseConstants.GUEST.COLUMNS.NAME, guest.name)
            contentValues.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, guest.presence)

            //seleciona ID igual ao ID do convidado
            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(guest.id.toString())

            //atualiza DB
            db.update(DataBaseConstants.GUEST.TABLE_NAME, contentValues, selection, args)

            true
        } catch (e: Exception) {
            false
        }
    }

    fun delete(id: Int): Boolean {
        return try {
            val db = mGuestDataBaseHelper.writableDatabase

            //seleciona ID igual ao ID do convidado
            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            //deleta dados da DB
            db.delete(DataBaseConstants.GUEST.TABLE_NAME, selection, args)

            true
        } catch (e: Exception) {
            false
        }
    }

}