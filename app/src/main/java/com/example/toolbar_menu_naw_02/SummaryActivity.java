package com.example.toolbar_menu_naw_02;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SummaryActivity extends AppCompatActivity {

    public static final String EXTRA_PIZZA = "pizza";
    public static final String EXTRA_SIZE = "size";
    public static final String EXTRA_ADDRESS = "address";
    public static final String EXTRA_CHEESE = "cheese";
    public static final String EXTRA_HAM = "ham";
    public static final String EXTRA_MUSHROOMS = "mushrooms";
    public static final String EXTRA_EXPRESS = "express";

    private String summaryText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        Toolbar toolbar = findViewById(R.id.toolbarSummary);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationOnClickListener(v -> finish());

        TextView tvSummary = findViewById(R.id.tvSummary);
        Button btnShare = findViewById(R.id.btnShare);
        Button btnEdit = findViewById(R.id.btnEdit);
        Button btnConfirm = findViewById(R.id.btnConfirm);

        Intent intent = getIntent();

        String pizza = intent.getStringExtra(EXTRA_PIZZA);
        int sizePosition = intent.getIntExtra(EXTRA_SIZE, 0);
        String address = intent.getStringExtra(EXTRA_ADDRESS);

        String[] sizes = {
                "Mała - 25 zł",
                "Średnia - 32 zł",
                "Duża - 40 zł"
        };

        String size = sizes[Math.max(0, Math.min(sizePosition, 2))];

        boolean cheese = intent.getBooleanExtra(EXTRA_CHEESE, false);
        boolean ham = intent.getBooleanExtra(EXTRA_HAM, false);
        boolean mushrooms = intent.getBooleanExtra(EXTRA_MUSHROOMS, false);
        boolean express = intent.getBooleanExtra(EXTRA_EXPRESS, false);

        StringBuilder additions = new StringBuilder();

        if (cheese) additions.append("Dodatkowy ser\n");
        if (ham) additions.append("Szynka\n");
        if (mushrooms) additions.append("Pieczarki\n");
        if (express) additions.append("Dostawa ekspresowa\n");

        if (additions.length() == 0) {
            additions.append("Brak dodatków");
        }

        summaryText = "Pizza: " + pizza
                + "\nRozmiar: " + size
                + "\n\nDodatki:\n" + additions
                + "\nAdres dostawy: " + address;

        tvSummary.setText(summaryText);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(pizza);
        }

        btnShare.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, summaryText);
            startActivity(Intent.createChooser(shareIntent, "Udostępnij zamówienie"));
        });

        btnEdit.setOnClickListener(v -> finish());

        btnConfirm.setOnClickListener(v -> {
            setResult(RESULT_OK);
            finish();
        });
    }
}
