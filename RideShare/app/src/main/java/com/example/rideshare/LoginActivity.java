package com.example.rideshare;

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

import com.example.rideshare.dto.RespuestaInicio;
import com.example.rideshare.dto.SolicitudInicio;
import com.example.rideshare.network.AuthAPI;
import com.example.rideshare.network.RetrofitCliente;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        EditText editCorreo = findViewById(R.id.editTextCorreo);
        EditText editContrasenia = findViewById(R.id.editTextContrasenia);
        Button botonLogin = findViewById(R.id.botonLoginLogin);

        botonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo = editCorreo.getText().toString().trim();
                String contrasenia = editContrasenia.getText().toString().trim();

                SolicitudInicio solicitudInicio = new SolicitudInicio(correo, contrasenia);
                AuthAPI authAPI = RetrofitCliente.getClient().create(AuthAPI.class);

                Call<RespuestaInicio> call = authAPI.Inicio(solicitudInicio);

                call.enqueue(new Callback<RespuestaInicio>() {
                    @Override
                    public void onResponse(Call<RespuestaInicio> call, Response<RespuestaInicio> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Toast.makeText(LoginActivity.this,
                                    response.body().getMensaje(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this,
                                    "Credenciales incorrectas",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RespuestaInicio> call, Throwable throwable) {
                        Toast.makeText(LoginActivity.this,
                                "Error de conexión (prueba)",
                                Toast.LENGTH_SHORT).show();
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