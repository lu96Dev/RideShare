package com.example.rideshare;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Arrays;
import java.util.List;

public class ContainerActivity extends AppCompatActivity {

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
}
