package emcafoz.com.snackatap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import emcafoz.com.snackatap.modelos.Categoria;
import emcafoz.com.snackatap.modelos.Producto;
import emcafoz.com.snackatap.sqlite.MySQLiteHelper;

/**
 * Created by aranchafoz on 26/02/16.
 */
public class ListaProductos extends AppCompatActivity{
    private RecyclerView reciclador;
    private RecyclerView.LayoutManager lmanager;
    private RecyclerView.Adapter adaptador;

    private ArrayList<Producto> datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_productos);

        new Thread(new Runnable() {
            @Override
            public void run() {
                cargar();
            }
        }).run();
    }

    private void cargar() {
        //Inicializa los datos de productos
        /**datos = new ArrayList<>();
         //Rellenar con datos reales
         datos.add(new Producto("Cafe de cocaina", 0.5f, Categoria.Café, null));
         datos.add(new Producto("Cafe de marihuana", 0.5f, Categoria.Café, null));
         datos.add(new Producto("Agua",0.5f, Categoria.Agua,null));
         datos.add(new Producto("Cafe de vainilla",0.5f, Categoria.Café,null));
         datos.add(new Producto("Cafe de avellana",0.5f, Categoria.Café,null));
         datos.add(new Producto("Agua",0.5f, Categoria.Café,null));
         datos.add(new Producto("Cafe de vainilla",0.5f, Categoria.Café,null));
         datos.add(new Producto("Cafe de avellana",0.5f, Categoria.Café,null));
         datos.add(new Producto("Agua",0.5f, Categoria.Café,null));
         datos.add(new Producto("Cafe de vainilla",0.5f, Categoria.Café,null));
         datos.add(new Producto("Cafe de avellana",0.5f, Categoria.Café,null));
         datos.add(new Producto("Agua",0.5f, Categoria.Café,null));
         */
        MySQLiteHelper helper = new MySQLiteHelper(this);
        datos = helper.getAllProductos();

        // Crea un RecyclerView
        reciclador = (RecyclerView) findViewById(R.id.reciclador);

        // Administrador de LinearLayout
        lmanager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        reciclador.setLayoutManager(lmanager);

        // Nuevo adaptador
        adaptador = new ProductoAdaptador(datos);
        reciclador.setAdapter(adaptador);
        reciclador.refreshDrawableState();
    }
}
