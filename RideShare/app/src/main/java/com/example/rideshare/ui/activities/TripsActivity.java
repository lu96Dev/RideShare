package com.example.rideshare.ui.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rideshare.R;
import com.example.rideshare.model.Trip;
import com.example.rideshare.ui.adapters.TripsAdapter;

import java.util.ArrayList;

public class TripsActivity extends AppCompatActivity {

    private RecyclerView rvViajes;
    private TripsAdapter adapter;
    private ArrayList<Trip> listaViajes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trips);

        rvViajes = findViewById(R.id.rvViajes);

        listaViajes = (ArrayList<Trip>) getIntent().getSerializableExtra("TRIPS");
        if (listaViajes == null) listaViajes = new ArrayList<>();

        adapter = new TripsAdapter(listaViajes);

        rvViajes.setLayoutManager(new LinearLayoutManager(this));
        rvViajes.setAdapter(adapter);
    }
}