package emcafoz.com.snackatap;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import emcafoz.com.snackatap.modelos.Producto;

/**
 * Created by aranchafoz on 13/03/16.
 */
public class TabFragment extends Fragment {
    private RecyclerView reciclador;
    private RecyclerView.LayoutManager lmanager;
    private RecyclerView.Adapter adaptador;

    private ArrayList<Producto> datos;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.content_lista_productos,container,false);

        new Thread(new Runnable() {
            @Override
            public void run() {
                cargar();
            }
        }).run();
        return v;
    }

    private void cargar() {

        //Inicializa los datos de productos
        datos = Producto.ALL;

        // Crea un RecyclerView
        //reciclador = (RecyclerView) findViewById(R.id.reciclador);

        // Administrador de LinearLayout
       // lmanager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        reciclador.setLayoutManager(lmanager);

        // Nuevo adaptador
        adaptador = new ProductoAdaptador(datos);
        reciclador.setAdapter(adaptador);
        reciclador.refreshDrawableState();
    }
}
