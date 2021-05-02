package com.example.pmdm_04;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.util.List;

public class AddEditReservaActivity extends AppCompatActivity
{
    public static final String EXTRA_NOMBRE = "com.example.pmdm_04.EXTRA_NOMBRE";
    public static final String EXTRA_TELEFONO = "com.example.pmdm_04.EXTRA_TELEFONO";
    public static final String EXTRA_NOMBRECABALLO = "com.example.pmdm_04.EXTRA_NOMBRECABALLO";
    public static final String EXTRA_DESCRIPCION = "com.example.pmdm_04.EXTRA_DESCRIPCION";
    public static final String EXTRA_DIA = "com.example.pmdm_04.EXTRA_DIA";
    public static final String EXTRA_HORA = "com.example.pmdm_04.EXTRA_HORA";
    public static final String EXTRA_ID = "com.example.pmdm_04.EXTRA_ID";


    private EditText editTextNombre;
    private EditText editTextNumero;
    private EditText editTextNombreCaballo;
    private EditText editTextDescripcion;
    private NumberPicker numberPickerDia;
    private NumberPicker numberPickerHora;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reserva);

        editTextNombre = findViewById(R.id.editTextCrearNombre);
        editTextNumero = findViewById(R.id.editTextCrearNumero);
        editTextNombreCaballo = findViewById(R.id.editTextNombreCaballo);
        editTextDescripcion = findViewById(R.id.editTextDescripcion);

        numberPickerDia = findViewById(R.id.number_picker_dia);
        numberPickerDia.setMaxValue(31);
        numberPickerDia.setMinValue(1);

        numberPickerHora = findViewById(R.id.number_picker_hora);
        numberPickerHora.setMaxValue(20);
        numberPickerHora.setMinValue(17);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        Intent intent = getIntent();

        if(intent.hasExtra(EXTRA_ID))
        {
            setTitle("Editar reserva");
            editTextNombre.setText(intent.getStringExtra(EXTRA_NOMBRE));
            editTextNumero.setText(intent.getStringExtra(EXTRA_TELEFONO));
            editTextNombreCaballo.setText(intent.getStringExtra(EXTRA_NOMBRECABALLO));
            editTextDescripcion.setText(intent.getStringExtra(EXTRA_DESCRIPCION));
            numberPickerDia.setValue(intent.getIntExtra(EXTRA_DIA,1));
            numberPickerHora.setValue(intent.getIntExtra(EXTRA_HORA,17));
        }
        else
        {
            setTitle("Añadir reserva");
        }
    }

    private void saveReserva()
    {


        String nombre = editTextNombre.getText().toString();
        String telefono = editTextNumero.getText().toString();
        String nombreCaballo = editTextNombreCaballo.getText().toString();
        String descripcion = editTextDescripcion.getText().toString();
        int dia = numberPickerDia.getValue();
        int hora = numberPickerHora.getValue();

        if(nombre.trim().isEmpty() || telefono.trim().isEmpty() || nombreCaballo.trim().isEmpty() || descripcion.trim().isEmpty())
        {
            Toast.makeText(this,"Por favor, inserte los datos de su reserva",Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_NOMBRE,nombre);
        data.putExtra(EXTRA_TELEFONO,telefono);
        data.putExtra(EXTRA_NOMBRECABALLO,nombreCaballo);
        data.putExtra(EXTRA_DESCRIPCION,descripcion);
        data.putExtra(EXTRA_DIA,dia);
        data.putExtra(EXTRA_HORA,hora);

        int id = getIntent().getIntExtra(EXTRA_ID,-1);
        if(id != -1)
        {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK,data);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_reserva_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.save_reserva:
                saveReserva();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}