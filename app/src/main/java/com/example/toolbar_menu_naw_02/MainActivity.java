package com.example.toolbar_menu_naw_02;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private Spinner spPizza, spSize;
    private CheckBox cbCheese, cbHam, cbMushrooms, cbExpress;
    private EditText etAddress;
    private ActivityResultLauncher<Intent> orderLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setPopupTheme(com.google.android.material.R.style.ThemeOverlay_Material3_Light);
        setSupportActionBar(toolbar);

        spPizza = findViewById(R.id.spPizza);
        spSize = findViewById(R.id.spSize);
        cbCheese = findViewById(R.id.cbCheese);
        cbHam = findViewById(R.id.cbHam);
        cbMushrooms = findViewById(R.id.cbMushrooms);
        cbExpress = findViewById(R.id.cbExpress);
        etAddress = findViewById(R.id.etAddress);

        ArrayAdapter<CharSequence> pizzaAdapter =
                ArrayAdapter.createFromResource(this, R.array.pizzas,
                        android.R.layout.simple_spinner_item);
        pizzaAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spPizza.setAdapter(pizzaAdapter);

        ArrayAdapter<CharSequence> sizeAdapter =
                ArrayAdapter.createFromResource(this, R.array.sizes,
                        android.R.layout.simple_spinner_item);
        sizeAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spSize.setAdapter(sizeAdapter);

        orderLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Toast.makeText(this, "Zamówienie przyjęte!",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(this, "Zamówienie nie zostało potwierdzone",
                                Toast.LENGTH_SHORT).show();
                    }
                }
        );

        Button btnNext = findViewById(R.id.btnNext);

        btnNext.setOnClickListener(v -> {
            String address = etAddress.getText().toString().trim();

            if (address.isEmpty()) {
                etAddress.setError("Podaj adres dostawy");
                return;
            }

            Intent intent = new Intent(MainActivity.this, SummaryActivity.class);
            intent.putExtra(SummaryActivity.EXTRA_PIZZA,
                    spPizza.getSelectedItem().toString());
            intent.putExtra(SummaryActivity.EXTRA_SIZE,
                    spSize.getSelectedItemPosition());
            intent.putExtra(SummaryActivity.EXTRA_ADDRESS, address);
            intent.putExtra(SummaryActivity.EXTRA_CHEESE, cbCheese.isChecked());
            intent.putExtra(SummaryActivity.EXTRA_HAM, cbHam.isChecked());
            intent.putExtra(SummaryActivity.EXTRA_MUSHROOMS, cbMushrooms.isChecked());
            intent.putExtra(SummaryActivity.EXTRA_EXPRESS, cbExpress.isChecked());

            orderLauncher.launch(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_clear) {
            spPizza.setSelection(0);
            spSize.setSelection(0);
            cbCheese.setChecked(false);
            cbHam.setChecked(false);
            cbMushrooms.setChecked(false);
            cbExpress.setChecked(false);
            etAddress.setText("");

            Toast.makeText(this, "Formularz wyczyszczony",
                    Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.action_share) {
            String text = "Moje zamówienie pizzy:\n"
                    + "Pizza: " + spPizza.getSelectedItem()
                    + "\nRozmiar: " + spSize.getSelectedItem()
                    + "\nAdres: " + etAddress.getText().toString();

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, text);
            startActivity(Intent.createChooser(shareIntent, "Udostępnij"));
            return true;
        }

        if (id == R.id.action_about) {
            startActivity(new Intent(this, AboutActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
