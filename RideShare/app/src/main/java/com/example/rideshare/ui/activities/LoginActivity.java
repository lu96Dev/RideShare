package com.example.rideshare.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rideshare.R;
import com.example.rideshare.data.dto.RespuestaInicio;
import com.example.rideshare.data.dto.SolicitudInicio;
import com.example.rideshare.data.network.AuthAPI;
import com.example.rideshare.data.network.RetrofitCliente;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    // Variable privada para guardar la contraseña real que se va escribiendo de forma oculta
    private final StringBuilder contraseniaReal = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        EditText editCorreo = findViewById(R.id.editTextCorreoLogin);
        EditText editContrasenia = findViewById(R.id.editTextContraseniaLogin);
        Button botonLogin = findViewById(R.id.botonLoginLogin);

        editContrasenia.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (end - start == 0) {
                    if (contraseniaReal.length() > 0 && dstart < contraseniaReal.length()) {
                        contraseniaReal.delete(dstart, dend);
                    }
                    return null;
                }

                for (int i = start; i < end; i++) {
                    contraseniaReal.insert(dstart + (i - start), source.charAt(i));
                }

                StringBuilder puntos = new StringBuilder();
                for (int i = start; i < end; i++) {
                    puntos.append("●");
                }
                return puntos.toString();
            }
        }});

        botonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo = editCorreo.getText().toString().trim();

                String contrasenia = contraseniaReal.toString().trim();

                boolean valido = true;
                editCorreo.setError(null);
                editContrasenia.setError(null);

                if (correo.isEmpty()) {
                    editCorreo.setError("El correo es obligatorio");
                    valido = false;
                }
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                    editCorreo.setError("Correo no válido");
                    valido = false;
                }
                if (contrasenia.isEmpty()) {
                    editContrasenia.setError("La contraseña es obligatoria");
                    valido = false;
                }
                if (!valido) return;

                botonLogin.setEnabled(false);
                botonLogin.setText(R.string.cargando);

                SolicitudInicio solicitudInicio = new SolicitudInicio(correo, contrasenia);
                AuthAPI authAPI = RetrofitCliente.getClient().create(AuthAPI.class);

                Call<RespuestaInicio> call = authAPI.Inicio(solicitudInicio);

                call.enqueue(new Callback<RespuestaInicio>() {
                    @Override
                    public void onResponse(Call<RespuestaInicio> call, Response<RespuestaInicio> response) {
                        botonLogin.setEnabled(true);
                        botonLogin.setText(R.string.iniciar_sesion);

                        if (response.isSuccessful() && response.body() != null) {
                            RespuestaInicio respuesta = response.body();

                            if (respuesta.esCorrecto()) {
                                SharedPreferences prefs = getSharedPreferences("sesion_usuario", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putInt("id_usuario", respuesta.getId());
                                editor.apply();

                                Toast.makeText(LoginActivity.this, respuesta.getMensaje(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, ContainerActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, respuesta.getMensaje(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, R.string.credenciales_incorrectas, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RespuestaInicio> call, Throwable throwable) {
                        botonLogin.setEnabled(true);
                        botonLogin.setText(R.string.iniciar_sesion);
                        Toast.makeText(LoginActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}