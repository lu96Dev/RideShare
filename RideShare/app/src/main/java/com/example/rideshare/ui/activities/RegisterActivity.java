package com.example.rideshare.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.example.rideshare.data.dto.RespuestaRegistro;
import com.example.rideshare.data.dto.SolicitudRegistro;
import com.example.rideshare.data.network.AuthAPI;
import com.example.rideshare.data.network.RetrofitCliente;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        EditText editCorreo = findViewById(R.id.editTextCorreoRegister);
        EditText editPassword = findViewById(R.id.editTextContraseñaRegister);
        EditText editPassword2 = findViewById(R.id.editTextRepetirContraseñaRegister);
        Button botonRegistro = findViewById(R.id.botonRegisterMain);

        botonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = editCorreo.getText().toString().trim();
                String password = editPassword.getText().toString().trim();
                String password2 = editPassword2.getText().toString().trim();

                boolean valido = true;

                editCorreo.setError(null);
                editPassword.setError(null);
                editPassword2.setError(null);

                if (email.isEmpty()) {
                    editCorreo.setError("El correo es obligatorio");
                    valido = false;
                }

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    editCorreo.setError("Correo no válido");
                    valido = false;
                }

                if (password.isEmpty()) {
                    editPassword.setError("La contraseña es obligatoria");
                    valido = false;
                }

                if (password2.isEmpty()) {
                    editPassword2.setError("Repite la contraseña");
                    valido = false;
                }


                if (!password.equals(password2)) {
                    editPassword2.setError("Las contraseñas no coinciden");
                    valido = false;
                }

                if (!valido) {
                    return;
                }

                botonRegistro.setEnabled(false);
                botonRegistro.setText(R.string.registrando);

                SolicitudRegistro solicitud = new SolicitudRegistro(email, password);

                AuthAPI authAPI = RetrofitCliente.getClient().create(AuthAPI.class);

                Call<RespuestaRegistro> call = authAPI.Registro(solicitud);

                call.enqueue(new Callback<RespuestaRegistro>() {

                    @Override
                    public void onResponse(Call<RespuestaRegistro> call, Response<RespuestaRegistro> response) {

                        botonRegistro.setEnabled(true);
                        botonRegistro.setText(R.string.registrarse);

                        if (response.isSuccessful() && response.body() != null) {

                            RespuestaRegistro respuesta = response.body();

                            Toast.makeText(RegisterActivity.this, respuesta.getMensaje(), Toast.LENGTH_SHORT).show();

                            if (respuesta.esCorrecto()) {

                                // --- GUARDAR ID DE USUARIO ---
                                SharedPreferences prefs = getSharedPreferences("sesion_usuario", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putInt("id_usuario", respuesta.getId());
                                editor.apply();
                                // -----------------------------

                                Intent intent = new Intent(RegisterActivity.this, ContainerActivity.class);
                                startActivity(intent);
                                finish();
                            }

                        } else {
                            Toast.makeText(RegisterActivity.this, "Error en el registro", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RespuestaRegistro> call, Throwable t) {

                        botonRegistro.setEnabled(true);
                        botonRegistro.setText(R.string.registrarse);

                        Toast.makeText(RegisterActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
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