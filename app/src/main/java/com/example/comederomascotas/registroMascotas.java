package com.example.comederomascotas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class registroMascotas extends AppCompatActivity {

    EditText nombreMascota, horaIntervalo, horaInicio;
    Button btnRegistrar, btnCancelar;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_mascotas);

        nombreMascota = findViewById(R.id.nombreMascota);
        horaIntervalo = findViewById(R.id.horaIntervalo);
        btnRegistrar = findViewById(R.id.registrar);
        btnCancelar = findViewById(R.id.cancelar);
        horaInicio = findViewById(R.id.horaDeInicio);

        db = FirebaseFirestore.getInstance();

        horaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarSelectorHoraInicio();
            }
        });
    }

    public void mostrarSelectorHoraInicio() {
        final Calendar c = Calendar.getInstance();
        int hora = c.get(Calendar.HOUR_OF_DAY);
        int minuto = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String horaSeleccionada = String.format("%02d:%02d", hourOfDay, minute);
                        horaInicio.setText(horaSeleccionada);
                    }
                }, hora, minuto, true);
        timePickerDialog.show();
    }

    public void registrarMascota(View view) {
        String nombre = nombreMascota.getText().toString().trim();
        String intervaloHora = horaIntervalo.getText().toString().trim();
        String horaInicioStr = horaInicio.getText().toString().trim();

        if (nombre.isEmpty() || intervaloHora.isEmpty() || horaInicioStr.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> mascota = new HashMap<>();
        mascota.put("nombre", nombre);
        mascota.put("intervaloHora", intervaloHora);
        mascota.put("horaInicio", horaInicioStr);

        db.collection("mascotas")
                .add(mascota)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(registroMascotas.this, "Mascota registrada con éxito", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(registroMascotas.this, pendientes.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(registroMascotas.this, "Error al registrar la mascota", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void cancelarRegistro(View view) {
        startActivity(new Intent(this, home.class));
        finish();
    }
}
