package emcafoz.com.snackatap;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;

import java.util.ArrayList;

import emcafoz.com.snackatap.modelos.Producto;
import emcafoz.com.snackatap.sqlite.MySQLiteHelper;

/**
 * Created by aranchafoz on 26/02/16.
 */
public class ListaProductosCategorias extends AppCompatActivity{
    private RecyclerView reciclador;
    private RecyclerView.LayoutManager lmanager;
    private RecyclerView.Adapter adaptador;

    private ArrayList<Producto> datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_lista_productos_categorias);

        //region toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.miToolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();

        ab.setTitle("Productos");

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //endregion

        //region Tabs
        Resources res = getResources();

        TabHost tabs = (TabHost)findViewById(R.id.tabhost);
        tabs.setup();

        TabHost.TabSpec spec = tabs.newTabSpec("mitab1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Agua");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("mitab2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Cafés");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("mitab3");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Comida sana");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("mitab4");
        spec.setContent(R.id.tab4);
        spec.setIndicator("Refrescos");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("mitab5");
        spec.setContent(R.id.tab5);
        spec.setIndicator("Snacks");
        tabs.addTab(spec);

        tabs.setCurrentTab(0);


        tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                switch (tabId) {
                    case "mitab1":
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                cargar(R.id.reciclador1);
                            }
                        }).run();
                        //reciclador= (RecyclerView) findViewById(R.id.reciclador1);
                        //cargar(reciclador);
                        break;
                    case "mitab2":
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                cargar(R.id.reciclador2);
                            }
                        }).run();
                        //reciclador= (RecyclerView) findViewById(R.id.reciclador2);
                        //cargar(reciclador);
                        break;
                    case "mitab3":
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                cargar(R.id.reciclador3);
                            }
                        }).run();
                        //reciclador= (RecyclerView) findViewById(R.id.reciclador3);
                        //cargar(reciclador);
                        break;
                    case "mitab4":
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                cargar(R.id.reciclador4);
                            }
                        }).run();
                        //reciclador= (RecyclerView) findViewById(R.id.reciclador4);
                        //cargar(reciclador);
                        break;
                    case "mitab5":
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                cargar(R.id.reciclador5);
                            }
                        }).run();
                        //reciclador= (RecyclerView) findViewById(R.id.reciclador5);
                        //cargar(reciclador);
                        break;
                }
            }
        });
        //endregion

        new Thread(new Runnable() {
            @Override
            public void run() {
                cargar(R.id.reciclador1);
            }
        }).run();
    }

    private void cargar(int v) {
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
        reciclador = (RecyclerView) findViewById(v);

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
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.action_filter:
                //region Seleccionar filtros
                SelectFilters dialog = new SelectFilters();
                dialog.show(getSupportFragmentManager(),"my dialog");
                return true;
                //endregion
        }
        return super.onOptionsItemSelected(item);
    }
}
