package com.example.apesteguia.a12_sqlite_1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alejandro on 30/06/2017.
 */

public class PersonaDAO {

    private final String TABLE = "PERSONA";
    private final String COL_ID = "ID";
    private final String COL_NOMBRE = "NOMBRE";
    private final String COL_APELLIDO_P = "APELLIDO_P";
    private final String COL_APELLIDO_M = "APELLIDO_M";
    private final String COL_EDAD = "EDAD";
    private final String COL_TELEFONO = "TELEFONO";

    private Context myContext;

    public PersonaDAO(Context myContext) {
        this.myContext = myContext;
    }

    private Persona cursorToPersona(Cursor cursor) {
        Persona persona = new Persona();

        persona.setId(cursor.isNull(cursor.getColumnIndex(COL_ID)) ? 0 : cursor.getInt(cursor.getColumnIndex(COL_ID)));
        persona.setNombre(cursor.isNull(cursor.getColumnIndex(COL_NOMBRE)) ? "" : cursor.getString(cursor.getColumnIndex(COL_NOMBRE)));
        persona.setApellidoP(cursor.isNull(cursor.getColumnIndex(COL_APELLIDO_P)) ? "" : cursor.getString(cursor.getColumnIndex(COL_APELLIDO_P)));
        persona.setApellidoM(cursor.isNull(cursor.getColumnIndex(COL_APELLIDO_M)) ? "" : cursor.getString(cursor.getColumnIndex(COL_APELLIDO_M)));
        persona.setEdad(cursor.isNull(cursor.getColumnIndex(COL_EDAD)) ? 0 : cursor.getInt(cursor.getColumnIndex(COL_EDAD)));
        persona.setTelefono(cursor.isNull(cursor.getColumnIndex(COL_TELEFONO)) ? "" : cursor.getString(cursor.getColumnIndex(COL_TELEFONO)));

        return persona;
    }

    public List<Persona> listarPersonas() {
        List<Persona> lista = new ArrayList<Persona>();
        Persona persona = null;
        try {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(myContext);
            Cursor cursor = dataBaseHelper.openDataBase().query(TABLE, null, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                do {
                    lista.add(cursorToPersona(cursor));
                }
                while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;

    }

    public Persona buscarPersona(int id){
        Persona persona = null;

        try {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(myContext);
            Cursor cursor = dataBaseHelper.openDataBase().query(TABLE, null, COL_ID+" = ?", new String[]{String.valueOf(id)}, null, null, null);
            if(cursor.moveToFirst()){
                persona = cursorToPersona(cursor);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return persona;
    }

    public void actualizar(Persona persona){

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NOMBRE, persona.getNombre());
        contentValues.put(COL_APELLIDO_P, persona.getApellidoP());
        contentValues.put(COL_APELLIDO_M, persona.getApellidoM());
        contentValues.put(COL_EDAD, persona.getEdad());
        contentValues.put(COL_TELEFONO, persona.getTelefono());

        try {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(myContext);
            dataBaseHelper.openDataBase().update(TABLE, contentValues, COL_ID +" = ?", new String[]{String.valueOf(persona.getId())});
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    public void eliminar(int id){

        try {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(myContext);
            dataBaseHelper.openDataBase().delete(TABLE, COL_ID+" = ?", new String[]{String.valueOf(id)});
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    public void ingresar(Persona persona){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NOMBRE, persona.getNombre());
        contentValues.put(COL_APELLIDO_P, persona.getApellidoP());
        contentValues.put(COL_APELLIDO_M, persona.getApellidoM());
        contentValues.put(COL_EDAD, persona.getEdad());
        contentValues.put(COL_TELEFONO, persona.getTelefono());

        try {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(myContext);
            dataBaseHelper.openDataBase().insert(TABLE, null, contentValues);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }



}
