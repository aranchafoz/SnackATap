package emcafoz.com.snackatap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

import emcafoz.com.snackatap.modelos.Producto;

/**
 * Created by aranchafoz on 26/02/16.
 */
public class ProductoAdaptador extends RecyclerView.Adapter<ProductoAdaptador.productoViewHolder> {
    private ArrayList<Producto> item;

    public ProductoAdaptador(ArrayList<Producto> producto) {
        this.item = producto;
    }

    @Override
    public productoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_product,parent,false);
        productoViewHolder producto = new productoViewHolder(v);
        return producto;
    }

    @Override
    public void onBindViewHolder(final productoViewHolder holder, final int position) {
        holder.nombre.setText(item.get(position).getNombre());
        holder.categoria.setText(item.get(position).getCategoria().toString());
        holder.imagen.setImageResource(item.get(position).getImagen());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("nombre", item.get(position).getNombre());
                bundle.putString("categoria", item.get(position).getCategoria().toString());
                bundle.putInt("imagen", item.get(position).getImagen());
                bundle.putFloat("precio", item.get(position).getPrecio());


                Intent intent = new Intent(v.getContext(), ScrollingActivity.class);
                intent.putExtra("producto", bundle);

                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class productoViewHolder extends RecyclerView.ViewHolder {
        TextView nombre,categoria;
        ImageView imagen;
        CardView cardView;

        public productoViewHolder (View productoView){
            super(productoView);

            nombre = (TextView) itemView.findViewById(R.id.lblNombre);
            categoria = (TextView) itemView.findViewById(R.id.lblCategoria);
            imagen = (ImageView) itemView.findViewById(R.id.imgProducto);
            cardView = (CardView) itemView.findViewById(R.id.cardview_producto);
        }
    }
}
