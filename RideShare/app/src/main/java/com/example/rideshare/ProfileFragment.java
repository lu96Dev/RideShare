package com.example.rideshare;
import android.app.AlertDialog;
import android.os.Bundle;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileFragment extends Fragment {

    private ImageView avatar;
    private LinearLayout btnBiblio;
    private TextView ResultadoBiblio;
    private final ActivityResultLauncher<String> getContent = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    avatar.setImageURI(uri);
                    avatar.setPadding(0, 0, 0, 0);
                }
            });

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        avatar = view.findViewById(R.id.imagenPerfil);
        btnBiblio = view.findViewById(R.id.btnPublicarBiblio);
        ResultadoBiblio = view.findViewById(R.id.ResultadoBiblio);

        avatar.setOnClickListener(v -> getContent.launch("image/*"));
        btnBiblio.setOnClickListener(v -> abrirDialogoTexto());
        ResultadoBiblio.setOnClickListener(v -> abrirDialogoTexto());

        return view;
    }

    private void abrirDialogoTexto() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Añadir Bibliografía");

        EditText ventanaTexto = new EditText(getContext());
        String textoActual = ResultadoBiblio.getText().toString();

        if (!textoActual.isEmpty()) {
            ventanaTexto.setText(textoActual);
            ventanaTexto.setSelection(ventanaTexto.getText().length());
        }

        builder.setView(ventanaTexto);
        builder.setPositiveButton("Guardar", (dialog, which) -> {
            // 1. Obtenemos el texto y quitamos espacios en blanco
            String nuevoTexto = ventanaTexto.getText().toString().trim();

            if (nuevoTexto.isEmpty()) {
                // --- CASO A: EL USUARIO BORRÓ TODO ---
                ResultadoBiblio.setText("");              // Limpiamos el texto para que no se vea nada
                ResultadoBiblio.setVisibility(View.GONE); // Lo escondemos por completo
                btnBiblio.setVisibility(View.VISIBLE);    // Mostramos el botón de "Añadir"

                Toast.makeText(getContext(), "Biografía eliminada", Toast.LENGTH_SHORT).show();
            } else {
                // --- CASO B: EL USUARIO ESCRIBIÓ ALGO ---
                ResultadoBiblio.setText(nuevoTexto);         // Ponemos el nuevo texto
                ResultadoBiblio.setVisibility(View.VISIBLE); // Lo mostramos
                btnBiblio.setVisibility(View.GONE);          // Escondemos el botón de "Añadir"

                Toast.makeText(getContext(), "Biografía actualizada", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }
}