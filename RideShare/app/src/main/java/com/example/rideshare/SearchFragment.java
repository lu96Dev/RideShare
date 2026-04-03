package com.example.rideshare;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SearchFragment extends Fragment {

    private EditText etSalida;
    private Button btnContinuar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Asegúrate de que este sea el nombre de tu archivo XML de búsqueda
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        etSalida = view.findViewById(R.id.Salida);
        btnContinuar = view.findViewById(R.id.btnContinuar);

        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ubicacion = etSalida.getText().toString().trim();

                if (!ubicacion.isEmpty()) {
                    // Llamamos a la función de la actividad para que ella haga el cambio
                    if (getActivity() instanceof ContainerActivity) {
                        ((ContainerActivity) getActivity()).mostrarResultados();
                    }
                } else {
                    etSalida.setError("Dime desde dónde sales");
                }
            }
        });

        return view;
    }



}