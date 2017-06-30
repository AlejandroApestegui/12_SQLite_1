package com.example.apesteguia.a12_sqlite_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PersonaEdit extends AppCompatActivity {

    private EditText etNombre, etApellidoPaterno, etApellidoMaterno, etEdad, etTelefono;
    private Button btnEliminar;
    private View.OnClickListener btnGuardarOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(!validar()){
                return;
            }

            Intent intent = new Intent();

            Persona persona = new Persona();

            persona.setNombre(etNombre.getText().toString().trim());
            persona.setApellidoP(etApellidoPaterno.getText().toString().trim());
            persona.setApellidoM(etApellidoMaterno.getText().toString().trim());
            persona.setEdad(Integer.parseInt(etEdad.getText().toString()));
            persona.setTelefono(etTelefono.getText().toString().trim());

            if (getIntent().getIntExtra("id", 0) == 0) {
                new PersonaDAO(PersonaEdit.this).ingresar(persona);
            } else {
                persona.setId(getIntent().getIntExtra("id",0));
                new PersonaDAO(PersonaEdit.this).actualizar(persona);
            }
            setResult(RESULT_OK, intent);
            finish();

        }
    };

    private boolean validar() {
        boolean valida = true;
        String mensaje = "Ingresar : ";
        if(TextUtils.isEmpty(etNombre.getText())){
            mensaje = mensaje + "nombre ";
            valida = false;
        }
        if(TextUtils.isEmpty(etApellidoPaterno.getText())){
            mensaje = mensaje + "apellido paterno ";
            valida = false;
        }
        if(TextUtils.isEmpty(etApellidoMaterno.getText())){
            mensaje = mensaje + "apellido materno ";
            valida = false;
        }
        if(TextUtils.isEmpty(etEdad.getText())){
            mensaje = mensaje + "edad ";
            valida = false;
        }
        if(TextUtils.isEmpty(etTelefono.getText())){
            mensaje = mensaje + "telefono ";
            valida = false;
        }
        if(!valida){
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
        }
        return valida;
    }

    private View.OnClickListener btnEliminarOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new PersonaDAO(PersonaEdit.this).eliminar(getIntent().getIntExtra("id", 0));
            setResult(RESULT_OK, new Intent());
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persona_edit);
        etNombre = (EditText) findViewById(R.id.etNombre);
        etApellidoPaterno = (EditText) findViewById(R.id.etApellidoPaterno);
        etApellidoMaterno = (EditText) findViewById(R.id.etApellidoMaterno);
        etEdad = (EditText) findViewById(R.id.etEdad);
        etTelefono = (EditText) findViewById(R.id.etTelefono);
        btnEliminar = (Button) findViewById(R.id.btnEliminar);

        findViewById(R.id.btnGuardar).setOnClickListener(btnGuardarOnClickListener);

        if (getIntent().getIntExtra("id", 0) == 0) {
            btnEliminar.setVisibility(View.INVISIBLE);
            return;
        }

        Persona persona = new PersonaDAO(PersonaEdit.this).buscarPersona(getIntent().getIntExtra("id", 0));
        etNombre.setText(persona.getNombre());
        etApellidoPaterno.setText(persona.getApellidoP());
        etApellidoMaterno.setText(persona.getApellidoM());
        etEdad.setText("" + persona.getEdad());
        etTelefono.setText(persona.getTelefono());
        findViewById(R.id.btnEliminar).setOnClickListener(btnEliminarOnClickListener);


    }
}
