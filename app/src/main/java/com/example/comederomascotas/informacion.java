package com.example.comederomascotas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class informacion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion);
    }

    public void interfazHome(View view) {
        Intent intent = new Intent(view.getContext(), home.class);
        view.getContext().startActivity(intent);
    }

    public void interfazPendientes(View view) {
        Intent intent = new Intent(view.getContext(), pendientes.class);
        view.getContext().startActivity(intent);
    }

    public void interfazReistroM(View view) {
        Intent intent = new Intent(view.getContext(), registroMascotas.class);
        view.getContext().startActivity(intent);
    }

    //Arreglar despues de agregar login
    public void interfazLogin(View view) {
        Intent intent = new Intent(view.getContext(), login.class);
        view.getContext().startActivity(intent);
    }
}