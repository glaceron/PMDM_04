package com.example.pmdm_04;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ReservaDao
{
    @Insert
    void insert(Reserva reserva);

    @Update
    void update(Reserva reserva);

    @Delete
    void delete(Reserva reserva);


    @Query("DELETE FROM reserva_table")
    void deleteAllReservas();

    @Query("SELECT * FROM reserva_table ORDER BY dia ASC")
    LiveData<List<Reserva>> getAllReservas();
}
