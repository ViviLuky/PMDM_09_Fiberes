package com.vivianafemenia.pmdm_09_fiberes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText txtFrase;
    private TextView lblFrase;
    private Button btnSave;

    private ArrayList<Persona> personas;

    private FirebaseDatabase database;

    private DatabaseReference refFrase;

    private  DatabaseReference refpersona;

    private DatabaseReference refPersonas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtFrase= findViewById(R.id.txtFrase);
        lblFrase= findViewById(R.id.lblFrase);
        btnSave=findViewById(R.id.btnSave);

        database = FirebaseDatabase.getInstance("https://pmdm09fiberes-default-rtdb.europe-west1.firebasedatabase.app");

        //Inicializar
        refFrase = database.getReference("frase");
        refpersona = database.getReference("persona");
        refPersonas = database.getReference("personas");

        personas = new ArrayList<>();
         crearPersona();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 //Escrituras

                refFrase.setValue(txtFrase.getText().toString());
                int random = (int)(Math.random()* 100);
                Persona p = new Persona(txtFrase.getText().toString(),random);
                refpersona.setValue(p);

                refPersonas.setValue(personas);
            }
        });

 // Lecturas
        refPersonas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    GenericTypeIndicator < ArrayList <Persona> > gti = new GenericTypeIndicator<ArrayList <Persona>>() {};
                    ArrayList<Persona> lista = snapshot.getValue(gti);
                    Toast.makeText(MainActivity.this, "Descargados: "+lista.size(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        refpersona.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Persona p = snapshot.getValue(Persona.class);

                    Toast.makeText(MainActivity.this, p.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


         refFrase.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {

                 if(snapshot.exists()){
                     String frase = snapshot.getValue(String.class);
                     lblFrase.setText(frase);
                 }
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });

    }

    private void crearPersona() {
        for (int i = 0; i < 1000; i++) {
            personas.add(new Persona("Persona"+i,(int)(Math.random()*100)));

        }
    }

}