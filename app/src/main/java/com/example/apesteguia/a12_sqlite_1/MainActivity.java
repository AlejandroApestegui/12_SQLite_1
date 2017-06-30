package com.example.apesteguia.a12_sqlite_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final int LISTAR = 1;
    private ListView lvPersonas;
    private PersonaAdapter pAdapter;
    private View.OnClickListener btnAgregarOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(MainActivity.this, PersonaEdit.class);
            startActivityForResult(intent, LISTAR );

        }
    };
    private AdapterView.OnItemClickListener pAdapterOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Intent intent = new Intent(MainActivity.this, PersonaEdit.class);
            intent.putExtra("id", pAdapter.getItem(position).getId());
            startActivityForResult(intent, LISTAR );
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == LISTAR){
                listarPersonas();
            }
        }
    }

    public void listarPersonas() {
        pAdapter.clear();
        pAdapter.addAll(new PersonaDAO(MainActivity.this).listarPersonas());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnAgregar).setOnClickListener(btnAgregarOnClickListener);
        lvPersonas = (ListView) findViewById(R.id.lvPersonas);

        pAdapter = new PersonaAdapter(MainActivity.this);
        lvPersonas.setAdapter(pAdapter);
        lvPersonas.setOnItemClickListener(pAdapterOnItemClickListener);

        try {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
            dataBaseHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        listarPersonas();

    }
}
