package com.example.pmdm_04;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity(tableName = "reserva_table")
public class Reserva
{
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String persona, numerotlf, nombreCaballo, descripcion;
    private int dia,hora;

    public Reserva(int dia, int hora, String persona, String numerotlf,String nombreCaballo,String descripcion) {
        this.dia = dia;

        this.hora = hora;
        this.persona = persona;
        this.numerotlf = numerotlf;
        this.nombreCaballo = nombreCaballo;
        this.descripcion = descripcion;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getDia() {
        return dia;
    }

    public int getHora() {
        return hora;
    }

    public String getPersona() {
        return persona;
    }

    public String getNumerotlf() {
        return numerotlf;
    }

    public String getNombreCaballo()
    {
        return  nombreCaballo;
    }

    public String getDescripcion() { return descripcion; }


}
