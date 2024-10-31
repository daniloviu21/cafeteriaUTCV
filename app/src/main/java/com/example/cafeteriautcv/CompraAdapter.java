package com.example.cafeteriautcv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CompraAdapter extends ArrayAdapter<Compra> {

    public CompraAdapter(Context context, ArrayList<Compra> compras) {
        super(context, 0, compras);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_item_compra, parent, false);
        }

        Compra compra = getItem(position);

        TextView txtProducto = convertView.findViewById(R.id.txtProducto);
        TextView txtCantidad = convertView.findViewById(R.id.txtCantidad);
        TextView txtPrecio = convertView.findViewById(R.id.txtPrecio);
        TextView txtEstado = convertView.findViewById(R.id.txtEstado);

        txtProducto.setText(compra.getProducto());
        txtCantidad.setText("Cantidad: " + compra.getCantidad());
        txtPrecio.setText("Total: $" + compra.getPrecio() * compra.getPrecio());
        txtEstado.setText("Estado: " + compra.getEstado());

        if ("Finalizado".equals(compra.getEstado())) {
            convertView.setAlpha(0.5f);
        } else {
            convertView.setAlpha(1.0f);
        }

        return convertView;
    }
}

