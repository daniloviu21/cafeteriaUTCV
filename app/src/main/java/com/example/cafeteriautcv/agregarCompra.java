package com.example.cafeteriautcv;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class agregarCompra extends AppCompatActivity {

    EditText txtCantidad, txtPrecio;
    Queue<Compra> compra = new ArrayDeque<Compra>();
    Spinner spinner;
    String producto;
    double cantidad, precio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_agregar_compra);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txtCantidad =  findViewById(R.id.txtCantidad);
        txtPrecio = findViewById(R.id.txtPrecio);
        spinner =  findViewById(R.id.spinner);
        List<String> productos = Arrays.asList("", "Tacos","Torta","Empanadas","Gorditas");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, productos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public boolean verificarCampos() {
        if (txtCantidad.getText().toString().isEmpty() || txtPrecio.getText().toString().isEmpty() || spinner.getSelectedItem() == "") {
            Toast.makeText(this, "Faltan datos!", Toast.LENGTH_LONG).show();
            return false;
        } else {
            producto = spinner.getSelectedItem().toString();
            cantidad = Integer.parseInt(txtCantidad.getText().toString());
            precio = Double.parseDouble(txtPrecio.getText().toString());
            return true;
        }
    }

    public void agregar(View view) {
        if (verificarCampos()) {
            Compra nuevacompra = new Compra(producto, (int) cantidad, precio, "Pendiente");
            SharedPreferences prefs = getSharedPreferences("CafeteriaPrefs", MODE_PRIVATE);
            ArrayList<Compra> compras = getComprasFromPrefs(prefs);
            compras.add(nuevacompra);
            saveComprasToPrefs(prefs, compras);
            Toast.makeText(this, "Pedido agregado correctamente!", Toast.LENGTH_LONG).show();
            txtCantidad.setText("");
            txtPrecio.setText("");
            spinner.setSelection(0);

            // Regresar a MainActivity
            Intent intent = new Intent(agregarCompra.this, MainActivity.class);
            startActivity(intent);
        }
    }

    private ArrayList<Compra> getComprasFromPrefs(SharedPreferences prefs) {
        String json = prefs.getString("compras", null);
        if (json != null) {
            return new Gson().fromJson(json, new TypeToken<ArrayList<Compra>>(){}.getType());
        }
        return new ArrayList<>();
    }

    private void saveComprasToPrefs(SharedPreferences prefs, ArrayList<Compra> compras) {
        String json = new Gson().toJson(compras);
        prefs.edit().putString("compras", json).apply();
    }

    public void salir(View view) {
        Intent intent = new Intent(agregarCompra.this, MainActivity.class);
        startActivity(intent);
    }
}