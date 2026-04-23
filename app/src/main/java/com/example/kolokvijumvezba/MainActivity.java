package com.example.kolokvijumvezba;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.MenuProvider;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Build;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    public static final String ACTION_TASKS_CHANGED = "com.example.kolokvijumvezba.ACTION_TASKS_CHANGED";
    public static final String ACTION_MAKE_KORISNICI_YELLOW = "com.example.kolokvijumvezba.ACTION_MAKE_KORISNICI_YELLOW";
    public static final String EXTRA_TASK_COUNT = "extra_task_count";

    private static final int REQ_NOTIF = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(Menu menu, android.view.MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.main_menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.menu_zadaci) {
                    showZadaci();
                    return true;
                } else if (id == R.id.menu_korisnici) {
                    showKorisnici();
                    return true;
                }
                return false;
            }
        });

        if (savedInstanceState == null) {
            showZadaci();
        }

        if (Build.VERSION.SDK_INT >= 33) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, REQ_NOTIF);
            }
        }

        startService(new Intent(this, MinuteCheckService.class));
    }

    private void showZadaci() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new ZadaciFragment())
                .commit();
    }

    private void showKorisnici() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new KorisniciFragment())
                .commit();
    }
}