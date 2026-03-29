package com.example.rideshare;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PublicationFragment extends Fragment {

    private EditText etDireccion, etHora;
    private Button btnPublicar;

    public PublicationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_publication, container, false);

        // 1. Enlazamos los componentes del XML
        etDireccion = view.findViewById(R.id.hora);
        etHora = view.findViewById(R.id.direccion);
        btnPublicar = view.findViewById(R.id.btnContinuar);

        // 2. Configuramos el clic del botón
        btnPublicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dir = etDireccion.getText().toString().trim();
                String h = etHora.getText().toString().trim();

                if (!dir.isEmpty() && !h.isEmpty()) {
                    Toast.makeText(getContext(), "Viaje publicado", Toast.LENGTH_SHORT).show();

                    // Opcional: Limpiar los campos después de publicar
                    etDireccion.setText("");
                    etHora.setText("");
                } else {
                    Toast.makeText(getContext(), "Por favor, rellena todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}