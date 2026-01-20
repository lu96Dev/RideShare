package com.example.rideshare;
import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PublicationActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CuadroAdapter adapter;
    List<Usuario> usuarios;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        usuarios = new ArrayList<>();
        usuarios.add(new Usuario("Juan Pérez", "Disponible", "08:30 AM", R.drawable.ic_user_placeholder));
        usuarios.add(new Usuario("María López", "No disponible", "09:15 AM", R.drawable.ic_user_placeholder));
        usuarios.add(new Usuario("Carlos Gómez", "Disponible", "10:00 AM", R.drawable.ic_user_placeholder));
        usuarios.add(new Usuario("Ana Torres", "Disponible", "11:45 AM", R.drawable.ic_user_placeholder));


        adapter = new CuadroAdapter(this, usuarios);
        recyclerView.setAdapter(adapter);
    }
}