package com.example.guests.service.repository

import androidx.room.*
import com.example.guests.service.model.GuestModel

//armazenará todos os métodos de consulta ao DB

@Dao
interface GuestDAO {

    @Insert
    fun save(guest: GuestModel): Long

    @Update
    fun update(guest: GuestModel): Int

    @Delete
    fun delete(guest: GuestModel)

    @Query("SELECT * FROM Guests WHERE id = :id")
    fun get(id: Int): GuestModel

    @Query("SELECT * FROM Guests")
    fun getAll(): List<GuestModel>

    @Query("SELECT * FROM Guests WHERE presence = 1")
    fun getPresent(): List<GuestModel>

    @Query("SELECT * FROM Guests WHERE presence = 0")
    fun getAbsent(): List<GuestModel>

}