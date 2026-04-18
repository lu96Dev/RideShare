package com.example.rideshare.ui.activities;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.rideshare.R;
import com.example.rideshare.ui.adapters.SectionsPagerAdapter;
import com.example.rideshare.model.Trip;
import com.example.rideshare.ui.fragments.TripsFragment;
import com.example.rideshare.ui.fragments.ChatFragment;
import com.example.rideshare.ui.fragments.ProfileFragment;
import com.example.rideshare.ui.fragments.PublicationFragment;
import com.example.rideshare.ui.fragments.SearchFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList; // Añadido
import java.util.Arrays;
import java.util.List;

public class ContainerActivity extends AppCompatActivity {

    // NUEVO: Variable para almacenar los viajes encontrados y compartirlos con el fragmento
    private List<Trip> listaViajesResultados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_container);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager2 viewPager = findViewById(R.id.viewPager);

        List<Fragment> fragments = Arrays.asList(
                new SearchFragment(),
                new ProfileFragment(),
                new ChatFragment(),
                new PublicationFragment()
        );

        SectionsPagerAdapter adapter = new SectionsPagerAdapter(this, fragments);
        viewPager.setAdapter(adapter);

        String[] nombres = {"Buscar", "Perfil", "Chat", "Publicar"};
        int[] iconos = {
                R.drawable.icon_buscar,
                R.drawable.icon_perfil,
                R.drawable.icon_chat,
                R.drawable.icon_publicar
        };

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    tab.setText(nombres[position]);
                    tab.setIcon(iconos[position]);
                }
        ).attach();
    }

    // MODIFICADO: Ahora recibe la lista de viajes por parámetro
    public void mostrarResultados(List<Trip> viajesEncontrados) {
        // Guardamos los datos recibidos en la variable de la clase
        this.listaViajesResultados = viajesEncontrados;

        TripsFragment tripsFragment = new TripsFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.main, tripsFragment)
                .addToBackStack(null)
                .commit();
    }
    public List<Trip> getListaViajesResultados() {
        return listaViajesResultados;
    }

    public void irAlChat() {
        ViewPager2 viewPager = findViewById(R.id.viewPager);
        if (viewPager != null) {
            viewPager.setCurrentItem(2, true);
            getSupportFragmentManager().popBackStack();
        }
    }
}