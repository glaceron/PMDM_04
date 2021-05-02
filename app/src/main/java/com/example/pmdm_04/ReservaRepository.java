package com.example.pmdm_04;

import android.app.Application;
import android.app.AsyncNotedAppOp;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ReservaRepository
{
    private ReservaDao reservaDao;
    private LiveData<List<Reserva>> allReservas;

    public ReservaRepository(Application application)
    {
        ReservaDatabese databese = ReservaDatabese.getInstance(application);
        reservaDao = databese.reservaDao();
        allReservas = reservaDao.getAllReservas();
    }

    public void insert(Reserva reserva)
    {
        new InsertReservaAsyncTask(reservaDao).execute(reserva);
    }

    public void update(Reserva reserva)
    {
        new UpdateReservaAsyncTask(reservaDao).execute(reserva);
    }
    public void delete(Reserva reserva)
    {
        new DeleteReservaAsyncTask(reservaDao).execute(reserva);
    }

    public void deleteAllReservas()
    {
        new DeleteAllReservasAsyncTask(reservaDao).execute();
    }

    public LiveData<List<Reserva>> getAllReservas()
    {
        return allReservas;
    }

    private static class UpdateReservaAsyncTask extends AsyncTask<Reserva,Void,Void>
    {
        private ReservaDao reservaDao;

        private UpdateReservaAsyncTask(ReservaDao reservaDao)
        {
            this.reservaDao = reservaDao;
        }

        @Override
        protected Void doInBackground(Reserva... reservas)
        {
            reservaDao.update(reservas[0]);
            return null;
        }
    }

    private static class DeleteReservaAsyncTask extends AsyncTask<Reserva,Void,Void>
    {
        private ReservaDao reservaDao;

        private DeleteReservaAsyncTask(ReservaDao reservaDao)
        {
            this.reservaDao = reservaDao;
        }

        @Override
        protected Void doInBackground(Reserva... reservas)
        {
            reservaDao.delete(reservas[0]);
            return null;
        }
    }

    private static class InsertReservaAsyncTask extends AsyncTask<Reserva,Void,Void>
    {
        private ReservaDao reservaDao;

        private InsertReservaAsyncTask(ReservaDao reservaDao)
        {
            this.reservaDao = reservaDao;
        }

        @Override
        protected Void doInBackground(Reserva... reservas)
        {
            reservaDao.insert(reservas[0]);
            return null;
        }
    }

    private static class DeleteAllReservasAsyncTask extends AsyncTask<Void,Void,Void>
    {
        private ReservaDao reservaDao;

        private DeleteAllReservasAsyncTask(ReservaDao reservaDao)
        {
            this.reservaDao = reservaDao;
        }

        @Override
        protected Void doInBackground(Void... voids)
        {
            reservaDao.deleteAllReservas();
            return null;
        }
    }
}
