package com.example.apesteguia.a12_sqlite_1;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by apesteguia on 27/06/2017.
 */

public class PersonaAdapter extends ArrayAdapter<Persona> {

    public PersonaAdapter(@NonNull Context context) {
        super(context, 0, new ArrayList<Persona>());
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView tvNombreCompleto, tvTelefono, tvEdad;

        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.persona_registro, parent, false);
        }
        tvNombreCompleto = (TextView) convertView.findViewById(R.id.tvNombreCompleto);
        tvTelefono = (TextView) convertView.findViewById(R.id.tvTelefono);
        tvEdad = (TextView) convertView.findViewById(R.id.tvEdad);

        Persona persona = getItem(position);

        tvNombreCompleto.setText(persona.getApellidoP()+" "+persona.getApellidoM()+", "+persona.getNombre());
        tvTelefono.setText(persona.getTelefono());
        tvEdad.setText(""+persona.getEdad());

        return convertView;
    }
}
