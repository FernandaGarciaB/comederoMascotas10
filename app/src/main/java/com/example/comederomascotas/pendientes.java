package com.example.comederomascotas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.Objects;

public class pendientes extends AppCompatActivity {

    FirebaseFirestore db;
    LinearLayout contenedorRegistros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendientes);

        db = FirebaseFirestore.getInstance();
        contenedorRegistros = findViewById(R.id.contenedorRegistros);

        obtenerRegistrosMascotas();
    }

    private void obtenerRegistrosMascotas() {
        db.collection("mascotas")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                String nombreMascota = document.getString("nombre");
                                String horaInicio = document.getString("horaInicio");

                                View registroView = crearVistaRegistroMascota(nombreMascota, horaInicio);
                                contenedorRegistros.addView(registroView);
                            }
                        } else {
                            Log.d("pendientes", "Error al obtener registros de mascotas: ", task.getException());
                        }
                    }
                });
    }

    private View crearVistaRegistroMascota(String nombreMascota, String horaInicio) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View registroView = inflater.inflate(R.layout.pendiente_perro_layout, contenedorRegistros, false);

        TextView textViewNombre = registroView.findViewById(R.id.nombreMascota);
        TextView textViewHorario = registroView.findViewById(R.id.horario);

        textViewNombre.setText(nombreMascota);
        textViewHorario.setText(horaInicio);

        return registroView;
    }

    public void interfazHome(View view) {
        Intent intent = new Intent(view.getContext(), home.class);
        view.getContext().startActivity(intent);
    }

    public void interfazInformacion(View view) {
        Intent intent = new Intent(view.getContext(), informacion.class);
        view.getContext().startActivity(intent);
    }

    public void interfazReistroM(View view) {
        Intent intent = new Intent(view.getContext(), registroMascotas.class);
        view.getContext().startActivity(intent);
    }

    public void interfazLogin(View view) {
        Intent intent = new Intent(view.getContext(), login.class);
        view.getContext().startActivity(intent);
    }
}
