package com.example.toolbar_menu_naw_02;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar toolbar = findViewById(R.id.toolbarAbout);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("O aplikacji");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationOnClickListener(v -> finish());

        TextView text = findViewById(R.id.tvAbout);
        text.setText("Zamówienie pizzy\n\n"
                + "Aplikacja umożliwia wybór pizzy, rozmiaru, "
                + "dodatków oraz adresu dostawy.\n\n"
                + "Wersja: 1.0");
    }
}
