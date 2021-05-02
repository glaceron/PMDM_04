package com.example.pmdm_04;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class ReservaViewModel extends AndroidViewModel
{
    private ReservaRepository repository;
    private LiveData<List<Reserva>> allReservas;

    public ReservaViewModel(@NonNull Application application)
    {
        super(application);
        repository = new ReservaRepository(application);
        allReservas = repository.getAllReservas();
    }

    public void insert(Reserva reserva)
    {
        repository.insert(reserva);
    }

    public void update(Reserva reserva)
    {
        repository.update(reserva);
    }

    public void delete(Reserva reserva)
    {
        repository.delete(reserva);
    }

    public void deleteAllReservas()
    {
        repository.deleteAllReservas();
    }

    public LiveData<List<Reserva>> getAllReservas()
    {
        return allReservas;
    }
}
