package com.example.pmdm_04;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private ReservaViewModel reservaViewModel;
    public static final int ADD_RESERVA_REQUEST = 1;
    public static final int EDIT_RESERVA_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddReserva = findViewById(R.id.button_add_reserva);

        buttonAddReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, AddEditReservaActivity.class);
                startActivityForResult(intent, ADD_RESERVA_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        ReservaAdapter adapter = new ReservaAdapter();
        recyclerView.setAdapter(adapter);

        reservaViewModel = ViewModelProviders.of(this).get(ReservaViewModel.class);
        reservaViewModel.getAllReservas().observe(this, new Observer<List<Reserva>>()
        {
            @Override
            public void onChanged(List<Reserva> reservas)
            {
                //Update RecyclerView
                adapter.setReservas(reservas);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT |ItemTouchHelper.RIGHT)
        {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target)
            {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction)
            {
                reservaViewModel.delete(adapter.getReservaAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Reserva borrada correctamente!", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new ReservaAdapter.OnItemClickListener() {
            @Override
            public void onIntemClick(Reserva reserva) {
                Intent intent = new Intent(MainActivity.this, AddEditReservaActivity.class);
                intent.putExtra(AddEditReservaActivity.EXTRA_ID, reserva.getId());
                intent.putExtra(AddEditReservaActivity.EXTRA_NOMBRE,reserva.getPersona());
                intent.putExtra(AddEditReservaActivity.EXTRA_TELEFONO,reserva.getNumerotlf());
                intent.putExtra(AddEditReservaActivity.EXTRA_NOMBRECABALLO,reserva.getNombreCaballo());
                intent.putExtra(AddEditReservaActivity.EXTRA_DESCRIPCION,reserva.getDescripcion());
                intent.putExtra(AddEditReservaActivity.EXTRA_DIA,reserva.getDia());
                intent.putExtra(AddEditReservaActivity.EXTRA_HORA,reserva.getHora());
                startActivityForResult(intent, EDIT_RESERVA_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_RESERVA_REQUEST && resultCode == RESULT_OK)
        {
            String nombre = data.getStringExtra(AddEditReservaActivity.EXTRA_NOMBRE);
            String telefono = data.getStringExtra(AddEditReservaActivity.EXTRA_TELEFONO);
            String nombreCaballo = data.getStringExtra(AddEditReservaActivity.EXTRA_NOMBRECABALLO);
            String descripcion = data.getStringExtra(AddEditReservaActivity.EXTRA_DESCRIPCION);
            int dia = data.getIntExtra(AddEditReservaActivity.EXTRA_DIA, 1);
            int hora = data.getIntExtra(AddEditReservaActivity.EXTRA_HORA, 16);

            LiveData<List<Reserva>> listaTodas;
            listaTodas = reservaViewModel.getAllReservas();
            List<Reserva> listaReservas = listaTodas.getValue();
            Reserva reservaAcomprobar;
            boolean repetida = false;
            for (int i = 0; i < listaReservas.size(); i++) {
                reservaAcomprobar = listaReservas.get(i);
                if (reservaAcomprobar.getDia() == dia && reservaAcomprobar.getHora() == hora) {
                    repetida = true;
                }
            }

            if (repetida == false)
            {
                Reserva reserva = new Reserva(dia, hora, nombre, telefono, nombreCaballo, descripcion);
                reservaViewModel.insert(reserva);

                LocalDate today = LocalDate.now();
                int month = today.getMonthValue();
                int year = today.getYear();


                String customerPhoneNumber = String.format("%s%s", 34, telefono);
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setPackage("com.whatsapp");
                String url = "https://api.whatsapp.com/send?phone=" + customerPhoneNumber + "&text=" + "Nombre: " + nombre + "\nNombre del caballo: " + nombreCaballo + "\nFecha: " + dia + "-" + month + "-" + year + "\nHora: " + hora + ":00\nObservaciones: " + descripcion;
                sendIntent.setData(Uri.parse(url));

                if(sendIntent.resolveActivity(getPackageManager()) == null)
                {
                    Toast.makeText(this, "Necesitas tener Whatsapp instalado en tu dispositivo.", Toast.LENGTH_SHORT).show();
                    return;
                }

                startActivity(sendIntent);


                Toast.makeText(this, "Reserva guardada", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "Ya existe una reserva para esa hora, intenta reservar a una hora disponible", Toast.LENGTH_SHORT).show();
            }
        }
        else if(requestCode == EDIT_RESERVA_REQUEST && resultCode == RESULT_OK)
        {
            int id = data.getIntExtra(AddEditReservaActivity.EXTRA_ID, -1);

            if(id == -1)
            {
                Toast.makeText(this, "La reserva no ha podido ser guardada", Toast.LENGTH_SHORT).show();
                return;
            }

            String nombre = data.getStringExtra(AddEditReservaActivity.EXTRA_NOMBRE);
            String telefono = data.getStringExtra(AddEditReservaActivity.EXTRA_TELEFONO);
            String nombreCaballo = data.getStringExtra(AddEditReservaActivity.EXTRA_NOMBRECABALLO);
            String descripcion = data.getStringExtra(AddEditReservaActivity.EXTRA_DESCRIPCION);
            int dia = data.getIntExtra(AddEditReservaActivity.EXTRA_DIA, 1);
            int hora = data.getIntExtra(AddEditReservaActivity.EXTRA_HORA, 16);

            LiveData<List<Reserva>> listaTodas;
            listaTodas = reservaViewModel.getAllReservas();
            List<Reserva> listaReservas = listaTodas.getValue();
            Reserva reservaAcomprobar;
            boolean repetida = false;
            for (int i = 0; i < listaReservas.size(); i++) {
                reservaAcomprobar = listaReservas.get(i);
                if (reservaAcomprobar.getDia() == dia && reservaAcomprobar.getHora() == hora) {
                    repetida = true;
                }
            }

            if (repetida == false) {
                Reserva reserva = new Reserva(dia, hora, nombre, telefono, nombreCaballo, descripcion);
                reserva.setId(id);
                reservaViewModel.update(reserva);

                Toast.makeText(this, "Reserva actualizada", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "Ya existe una reserva para esa hora, intenta reservar a una hora disponible", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(this, "La reserva no ha podido ser guardada", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.delete_all_reservas:
                reservaViewModel.deleteAllReservas();
                Toast.makeText(this, "Todas las reservas han sido borradas con exito", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}