package emcafoz.com.snackatap;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
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
public class ListaProductosCategorias extends AppCompatActivity{
    private RecyclerView reciclador;
    private RecyclerView.LayoutManager lmanager;
    private RecyclerView.Adapter adaptador;

    private ArrayList<Producto> datos;

    ViewPager pager;
    ViewPagerAdapter adapter;
    TabLayout tabs;
    CharSequence Titles[]={ "Cafés",
                            "Comida sana",
                            "Refrescos",
                            "Snacks"};
    int Numboftabs = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);

        //region toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.miToolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();

        ab.setTitle(R.string.categorias);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //endregion

        new Thread(new Runnable() {
            @Override
            public void run() {
                //region Tabs

                // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
                adapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles,Numboftabs);

                // Assigning ViewPager View and setting the adapter
                pager = (ViewPager) findViewById(R.id.pager);
                pager.setAdapter(adapter);

                // Assiging the Sliding Tab Layout View
                tabs = (TabLayout) findViewById(R.id.tabs);
                //tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width
                /*
                // Setting Custom Color for the Scroll bar indicator of the Tab View
                tabs.setCustomTabColorizer(new TabLayout.TabColorizer() {
                    @Override
                    public int getIndicatorColor(int position) {
                        return getResources().getColor(R.color.tabsScrollColor);
                    }
                });*/
                tabs.setBackgroundColor(getResources().getColor(R.color.color_Primary));
                tabs.setTabTextColors(ColorStateList.valueOf(getResources().getColor(R.color.color_Icons)));
                // Setting the ViewPager For the SlidingTabsLayout
                tabs.setupWithViewPager(pager);

                //endregion
            }
        }).run();
        /*
        new Thread(new Runnable() {
            @Override
            public void run() {
                cargar(R.id.reciclador, Categoria.Agua);
            }
        }).run();
        */
    }

    private void cargar(int v, Categoria categoria) {
        //Inicializa los datos de productos
        datos = Producto.getFromCategoria(categoria);

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
