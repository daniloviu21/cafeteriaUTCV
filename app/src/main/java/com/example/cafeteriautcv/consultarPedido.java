package com.example.cafeteriautcv;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;

public class consultarPedido extends AppCompatActivity {
    Button btnFinalizar;
    ListView listViewCompras;
    static ArrayList<Compra> listaCompras = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_pedido);

        listViewCompras = findViewById(R.id.listViewCompras);
        btnFinalizar = findViewById(R.id.btnFinalizar);

        SharedPreferences prefs = getSharedPreferences("CafeteriaPrefs", MODE_PRIVATE);
        listaCompras = getComprasFromPrefs(prefs);

        ArrayList<Compra> comprasNuevas = (ArrayList<Compra>) getIntent().getSerializableExtra("colaCompras");
        if (comprasNuevas != null && !comprasNuevas.isEmpty()) {
            listaCompras.addAll(0, comprasNuevas);
            saveComprasToPrefs(prefs, listaCompras);
        } else if (listaCompras.isEmpty()) {
            Toast.makeText(this, "No hay compras en la cola", Toast.LENGTH_SHORT).show();
        }

        CompraAdapter adapter = new CompraAdapter(this, listaCompras);
        listViewCompras.setAdapter(adapter);

        verificarEstadoBotonFinalizar();

        btnFinalizar.setOnClickListener(v -> finalizarPedido(adapter));
    }

    private ArrayList<Compra> getComprasFromPrefs(SharedPreferences prefs) {
        String json = prefs.getString("compras", null);
        if (json != null) {
            return new Gson().fromJson(json, new TypeToken<ArrayList<Compra>>(){}.getType());
        }
        return new ArrayList<>();
    }

    private void finalizarPedido(CompraAdapter adapter) {
        if (!listaCompras.isEmpty()) {
            Compra primerPedido = listaCompras.remove(0);
            primerPedido.setEstado("Finalizado");
            listaCompras.add(primerPedido);

            adapter.notifyDataSetChanged();
            saveComprasToPrefs(getSharedPreferences("CafeteriaPrefs", MODE_PRIVATE), listaCompras); // Guardar después de finalizar

            verificarEstadoBotonFinalizar();
        }
    }

    private void verificarEstadoBotonFinalizar() {
        boolean todosFinalizados = true;
        for (Compra compra : listaCompras) {
            if (!"Finalizado".equals(compra.getEstado())) {
                todosFinalizados = false;
                break;
            }
        }
        btnFinalizar.setEnabled(!todosFinalizados);
    }

    private void saveComprasToPrefs(SharedPreferences prefs, ArrayList<Compra> compras) {
        String json = new Gson().toJson(compras);
        prefs.edit().putString("compras", json).apply();
    }

    public void salirprincipal(View view) {
        Intent intent = new Intent(consultarPedido.this, MainActivity.class);
        startActivity(intent);
    }
}
