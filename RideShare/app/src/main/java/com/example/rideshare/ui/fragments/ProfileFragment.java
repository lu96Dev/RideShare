package com.example.rideshare.ui.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rideshare.R;
import com.example.rideshare.data.dto.RespuestaInicio;
import com.example.rideshare.data.dto.SolicitudActualizacion;
import com.example.rideshare.data.dto.SolicitudCambioPassword;
import com.example.rideshare.data.network.RetrofitCliente;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private ImageView avatar;
    private TextView nombreUsuarioPerfil;
    private Integer idUsuarioLogueado;

    // Biografía
    private LinearLayout btnBiblio;
    private TextView resultadoBiblio;

    // Nombre
    private LinearLayout btnNombre;
    private TextView resultadoNombre;

    // Apellidos
    private LinearLayout btnApellidos;
    private TextView resultadoApellidos;

    // Teléfono
    private LinearLayout btnTelefono;
    private TextView resultadoTelefono;

    // Correo (solo lectura)
    private TextView resultadoCorreo;

    // Contraseña
    private LinearLayout btnContrasena;

    private final ActivityResultLauncher<String> getContent = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    avatar.setImageURI(uri);
                    avatar.setPadding(0, 0, 0, 0);

                    guardarFotoEnServidor(uri);
                }
            });

    public ProfileFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        SharedPreferences preferences = getActivity().getSharedPreferences("sesion_usuario", Context.MODE_PRIVATE);
        idUsuarioLogueado = preferences.getInt("id_usuario", -1);

        Log.d("PROFILE_DEBUG", "ID cargado de SharedPreferences: " + idUsuarioLogueado);

        // Inicializar vistas
        inicializarVistas(view);

        // Configurar listeners
        configurarListeners();

        // Cargar datos del usuario
        cargarDatosUsuario();

        return view;
    }

    private void inicializarVistas(View view) {
        avatar = view.findViewById(R.id.imagenPerfil);
        nombreUsuarioPerfil = view.findViewById(R.id.nombreUsuario);

        // Biografía
        btnBiblio = view.findViewById(R.id.btnPublicarBiblio);
        resultadoBiblio = view.findViewById(R.id.ResultadoBiblio);

        // Nombre
        btnNombre = view.findViewById(R.id.btnEditarNombre);
        resultadoNombre = view.findViewById(R.id.ResultadoNombre);

        // Apellidos
        btnApellidos = view.findViewById(R.id.btnEditarApellidos);
        resultadoApellidos = view.findViewById(R.id.ResultadoApellidos);

        // Teléfono
        btnTelefono = view.findViewById(R.id.btnEditarTelefono);
        resultadoTelefono = view.findViewById(R.id.ResultadoTelefono);

        // Correo (solo lectura)
        resultadoCorreo = view.findViewById(R.id.ResultadoCorreo);

        // Contraseña
        btnContrasena = view.findViewById(R.id.btnEditarContrasena);
    }

    private void configurarListeners() {
        avatar.setOnClickListener(v -> getContent.launch("image/*"));

        // Biografía
        btnBiblio.setOnClickListener(v -> abrirDialogoEditar("Editar Biografía", resultadoBiblio, "biografia"));
        resultadoBiblio.setOnClickListener(v -> abrirDialogoEditar("Editar Biografía", resultadoBiblio, "biografia"));

        // Nombre
        btnNombre.setOnClickListener(v -> abrirDialogoEditar("Editar Nombre", resultadoNombre, "nombre"));
        resultadoNombre.setOnClickListener(v -> abrirDialogoEditar("Editar Nombre", resultadoNombre, "nombre"));

        // Apellidos
        btnApellidos.setOnClickListener(v -> abrirDialogoEditar("Editar Apellidos", resultadoApellidos, "apellidos"));
        resultadoApellidos.setOnClickListener(v -> abrirDialogoEditar("Editar Apellidos", resultadoApellidos, "apellidos"));

        // Teléfono
        btnTelefono.setOnClickListener(v -> abrirDialogoEditar("Editar Teléfono", resultadoTelefono, "telefono"));
        resultadoTelefono.setOnClickListener(v -> abrirDialogoEditar("Editar Teléfono", resultadoTelefono, "telefono"));

        // Contraseña
        btnContrasena.setOnClickListener(v -> abrirDialogoCambiarContrasena());
    }

    // ★ NUEVO METODO - Convertir imagen a Base64 y guardar
    private void guardarFotoEnServidor(Uri uri) {
        try {
            InputStream inputStream = getContext().getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();

            // Redimensionar para no guardar imágenes muy grandes
            Bitmap bitmapRedimensionado = redimensionarImagen(bitmap, 300);

            // Convertir a Base64
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmapRedimensionado.compress(Bitmap.CompressFormat.JPEG, 70, baos);
            byte[] bytes = baos.toByteArray();
            String imagenBase64 = Base64.encodeToString(bytes, Base64.DEFAULT);

            Log.d("FOTO_DEBUG", "Tamaño Base64: " + imagenBase64.length() + " caracteres");

            // Enviar al servidor
            SolicitudActualizacion solicitud = new SolicitudActualizacion();
            solicitud.setFotoPerfil(imagenBase64);

            RetrofitCliente.getUsuarioAPI().actualizarPerfil(idUsuarioLogueado, solicitud)
                    .enqueue(new Callback<RespuestaInicio>() {
                        @Override
                        public void onResponse(Call<RespuestaInicio> call, Response<RespuestaInicio> response) {

                            Log.d("FOTO_DEBUG", "Código respuesta: " + response.code());

                            if (response.errorBody() != null) {
                                try {
                                    Log.e("FOTO_DEBUG", "Error body: " + response.errorBody().string());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            if (response.isSuccessful()) {
                                Toast.makeText(getContext(), "Foto actualizada", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Error al guardar foto", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<RespuestaInicio> call, Throwable t) {
                            Log.e("FOTO_DEBUG", "onFailure: " + t.getMessage());
                            Toast.makeText(getContext(), "Sin conexión", Toast.LENGTH_SHORT).show();
                        }
                    });

        } catch (Exception e) {
            Log.e("FOTO_ERROR", "Error al procesar imagen: " + e.getMessage());
            Toast.makeText(getContext(), "Error al procesar imagen", Toast.LENGTH_SHORT).show();
        }
    }

    // ★ NUEVO METODO - Redimensionar imagen
    private Bitmap redimensionarImagen(Bitmap imagen, int maxSize) {
        int ancho = imagen.getWidth();
        int alto = imagen.getHeight();

        float ratio = Math.min((float) maxSize / ancho, (float) maxSize / alto);

        int nuevoAncho = Math.round(ancho * ratio);
        int nuevoAlto = Math.round(alto * ratio);

        return Bitmap.createScaledBitmap(imagen, nuevoAncho, nuevoAlto, true);
    }

    // ★ NUEVO METODO - Cargar imagen desde Base64
    private void cargarImagenDesdeBase64(String base64) {
        if (base64 == null || base64.isEmpty()) {
            return;
        }

        try {
            byte[] bytes = Base64.decode(base64, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            avatar.setImageBitmap(bitmap);
            avatar.setPadding(0, 0, 0, 0);
        } catch (Exception e) {
            Log.e("FOTO_ERROR", "Error al cargar imagen: " + e.getMessage());
        }
    }

    private void cargarDatosUsuario() {
        if (idUsuarioLogueado == -1) {
            return;
        }

        RetrofitCliente.getUsuarioAPI().obtenerUsuario(idUsuarioLogueado)
                .enqueue(new Callback<RespuestaInicio>() {
                    @Override
                    public void onResponse(Call<RespuestaInicio> call, Response<RespuestaInicio> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            RespuestaInicio usuario = response.body();

                            // Actualizar la card del perfil
                            String nombreCompleto = usuario.getNombre() + " " + usuario.getApellidos();
                            nombreUsuarioPerfil.setText(nombreCompleto);

                            // Cargar cada campo
                            actualizarCampo(resultadoBiblio, btnBiblio, usuario.getBiografia());
                            actualizarCampo(resultadoNombre, btnNombre, usuario.getNombre());
                            actualizarCampo(resultadoApellidos, btnApellidos, usuario.getApellidos());
                            actualizarCampo(resultadoTelefono, btnTelefono, usuario.getTelefono());

                            // Correo solo muestra (no editable)
                            resultadoCorreo.setText(usuario.getEmail());

                            // ★ CARGAR FOTO DE PERFIL
                            cargarImagenDesdeBase64(usuario.getFotoPerfil());
                        }
                    }

                    @Override
                    public void onFailure(Call<RespuestaInicio> call, Throwable t) {
                        Log.e("PROFILE_ERROR", "Error al cargar perfil: " + t.getMessage());
                    }
                });
    }

    private void abrirDialogoEditar(String titulo, TextView resultadoTextView, String campo) {
        if (idUsuarioLogueado == -1) {
            Toast.makeText(getContext(), "Error: Sesión no válida", Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(titulo);

        final EditText ventanaTexto = new EditText(getContext());
        String textoActual = resultadoTextView.getText().toString();

        if (!textoActual.isEmpty()) {
            ventanaTexto.setText(textoActual);
            ventanaTexto.setSelection(ventanaTexto.getText().length());
        }

        builder.setView(ventanaTexto);
        builder.setPositiveButton("Guardar", (dialog, which) -> {
            String nuevoTexto = ventanaTexto.getText().toString().trim();
            actualizarCampoEnServidor(campo, nuevoTexto, resultadoTextView);
        });

        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    private void actualizarCampoEnServidor(String campo, String valor, TextView resultadoTextView) {
        SolicitudActualizacion solicitud = new SolicitudActualizacion();

        // Asignar el valor al campo correspondiente
        switch (campo) {
            case "biografia":
                solicitud.setBiografia(valor);
                break;
            case "nombre":
                solicitud.setNombre(valor);
                break;
            case "apellidos":
                solicitud.setApellidos(valor);
                break;
            case "telefono":
                solicitud.setTelefono(valor);
                break;
        }

        Log.d("API_DEBUG", "Actualizando campo: " + campo + " con valor: " + valor);

        RetrofitCliente.getUsuarioAPI().actualizarPerfil(idUsuarioLogueado, solicitud)
                .enqueue(new Callback<RespuestaInicio>() {
                    @Override
                    public void onResponse(Call<RespuestaInicio> call, Response<RespuestaInicio> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            // Obtener el botón correspondiente
                            LinearLayout boton = obtenerBotonParaCampo(campo);
                            actualizarCampo(resultadoTextView, boton, valor);

                            // Si se actualizó nombre o apellidos, actualizar la card
                            if (campo.equals("nombre") || campo.equals("apellidos")) {
                                actualizarNombreEnCard();
                            }

                            Toast.makeText(getContext(), response.body().getMensaje(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Error al guardar", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RespuestaInicio> call, Throwable t) {
                        Log.e("NETWORK_ERROR", "Causa: " + t.getMessage());
                        Toast.makeText(getContext(), "Sin conexión", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private LinearLayout obtenerBotonParaCampo(String campo) {
        switch (campo) {
            case "biografia":
                return btnBiblio;
            case "nombre":
                return btnNombre;
            case "apellidos":
                return btnApellidos;
            case "telefono":
                return btnTelefono;
            default:
                return null;
        }
    }

    private void actualizarNombreEnCard() {
        String nombre = resultadoNombre.getText().toString();
        String apellidos = resultadoApellidos.getText().toString();
        nombreUsuarioPerfil.setText(nombre + " " + apellidos);
    }

    private void actualizarCampo(TextView resultado, LinearLayout boton, String texto) {
        if (texto == null || texto.isEmpty()) {
            resultado.setText("");
            resultado.setVisibility(View.GONE);
            if (boton != null) {
                boton.setVisibility(View.VISIBLE);
            }
        } else {
            resultado.setText(texto);
            resultado.setVisibility(View.VISIBLE);
            if (boton != null) {
                boton.setVisibility(View.GONE);
            }
        }
    }

    private void abrirDialogoCambiarContrasena() {
        if (idUsuarioLogueado == -1) {
            Toast.makeText(getContext(), "Error: Sesión no válida", Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Cambiar Contraseña");

        // Crear layout con campos
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 20, 50, 20);

        final EditText inputActual = new EditText(getContext());
        inputActual.setHint("Contraseña actual");
        inputActual.setInputType(android.text.InputType.TYPE_CLASS_TEXT |
                android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
        layout.addView(inputActual);

        final EditText inputNueva = new EditText(getContext());
        inputNueva.setHint("Nueva contraseña");
        inputNueva.setInputType(android.text.InputType.TYPE_CLASS_TEXT |
                android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
        layout.addView(inputNueva);

        final EditText inputConfirmar = new EditText(getContext());
        inputConfirmar.setHint("Confirmar nueva contraseña");
        inputConfirmar.setInputType(android.text.InputType.TYPE_CLASS_TEXT |
                android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
        layout.addView(inputConfirmar);

        builder.setView(layout);

        builder.setPositiveButton("Cambiar", (dialog, which) -> {
            String actual = inputActual.getText().toString();
            String nueva = inputNueva.getText().toString();
            String confirmar = inputConfirmar.getText().toString();

            if (actual.isEmpty() || nueva.isEmpty() || confirmar.isEmpty()) {
                Toast.makeText(getContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!nueva.equals(confirmar)) {
                Toast.makeText(getContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                return;
            }

            if (nueva.length() < 4) {
                Toast.makeText(getContext(), "La contraseña debe tener al menos 4 caracteres", Toast.LENGTH_SHORT).show();
                return;
            }

            cambiarContrasenaEnServidor(actual, nueva);
        });

        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    private void cambiarContrasenaEnServidor(String actual, String nueva) {
        SolicitudCambioPassword solicitud = new SolicitudCambioPassword(actual, nueva);

        RetrofitCliente.getUsuarioAPI().cambiarPassword(idUsuarioLogueado, solicitud)
                .enqueue(new Callback<RespuestaInicio>() {
                    @Override
                    public void onResponse(Call<RespuestaInicio> call, Response<RespuestaInicio> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Toast.makeText(getContext(), response.body().getMensaje(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "La contraseña actual es incorrecta", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RespuestaInicio> call, Throwable t) {
                        Log.e("NETWORK_ERROR", "Causa: " + t.getMessage());
                        Toast.makeText(getContext(), "Sin conexión", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}