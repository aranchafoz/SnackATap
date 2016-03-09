package emcafoz.com.snackatap;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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
        setContentView(R.layout.activity_lista_productos);

        Toolbar toolbar = (Toolbar) findViewById(R.id.miToolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();

        ab.setTitle("Productos");

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_menu_productos, menu);

        MenuItem searchItem = menu.findItem(R.id.action_filter);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:
                //region PONER EL PRODUCTO A FAVORITO
                SelectFilters dialog = new SelectFilters();
                dialog.show(getSupportFragmentManager(),"my dialog");
                return true;
            //endregion
        }
        return super.onOptionsItemSelected(item);
    }
}
