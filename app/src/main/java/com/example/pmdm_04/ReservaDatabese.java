package com.example.pmdm_04;

import android.content.Context;
import android.media.audiofx.PresetReverb;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;

@Database(entities = Reserva.class, version = 4)
public abstract class ReservaDatabese extends RoomDatabase
{
    private static ReservaDatabese instance;

    public abstract ReservaDao reservaDao();

    public static synchronized ReservaDatabese getInstance(Context context)
    {
        if(instance == null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(),ReservaDatabese.class,"reserva_database").fallbackToDestructiveMigration().addCallback(roomCallback).build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback()
    {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>
    {
        private ReservaDao reservaDao;

        private PopulateDbAsyncTask(ReservaDatabese db)
        {
            reservaDao = db.reservaDao();
        }

        @Override
        protected Void doInBackground(Void... voids)
        {
            reservaDao.insert(new Reserva(01,17,"Carlos","662250557","Rocinante", "Paseo por el campo"));
            reservaDao.insert(new Reserva(04,18,"Carlos","662250557","Rayo", "Paseo por el campo"));
            return null;
        }
    }
}
