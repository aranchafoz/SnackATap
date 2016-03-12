package emcafoz.com.snackatap;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import emcafoz.com.snackatap.modelos.Edificio;
import emcafoz.com.snackatap.modelos.Producto;
import emcafoz.com.snackatap.sqlite.MySQLiteHelper;

public class ScrollingActivity extends AppCompatActivity {

    public GoogleMap googleMap;

    private ArrayList<Pair<Edificio, Float>> edificios;

    private boolean favorito = false;

    private void sortEdificios(float x, float y) {
        for (Pair<Edificio, Float> edificio : edificios) {
            edificio = new Pair(edificio.first, (float) Math.sqrt((double) (
                    Math.pow(edificio.first.getCx() - x, 2)
                            + Math.pow(edificio.first.getCy() - y, 2))));
        }
    }

    private Edificio getNearest() {
        Pair<Edificio, Float> nearest = edificios.get(0);
        for (Pair<Edificio, Float> edificio : edificios)
            if (edificio.second < nearest.second)
                nearest = edificio;
        return nearest.first;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        edificios = new ArrayList<>();
        googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        googleMap.getUiSettings().setZoomGesturesEnabled(false);
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        googleMap.getUiSettings().setScrollGesturesEnabled(false);
        googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                LatLng current_location = new LatLng(location.getLatitude(), location.getLongitude());
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current_location, 16.5f));
                sortEdificios((float) current_location.latitude, (float) current_location.longitude);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //region inicializacion del producto

        Bundle bundle = getIntent().getBundleExtra("producto");
        MySQLiteHelper helper = new MySQLiteHelper(this);
        Producto producto = helper.getProducto(bundle.getString("nombre"));

        for (Edificio edificio : producto.getEdificios()) {
            edificios.add(new Pair<Edificio, Float>(edificio, -1f));
        }

        for (Edificio edificio : producto.getEdificios()) {
            googleMap.addMarker(new MarkerOptions().position(new LatLng(
                    edificio.getCx(), edificio.getCy())).title(edificio.getNombre()));
        }

        googleMap.setMyLocationEnabled(true);

        LatLng focus;
        try {
            Location location = googleMap.getMyLocation();
            focus = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (Exception e) {
            focus = new LatLng(producto.getEdificios().get(0).getCx(), producto.getEdificios().get(0).getCy());
        }
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(focus, 15));

        TextView nombreProducto = (TextView) findViewById(R.id.nombre_producto);
        nombreProducto.setText(producto.getNombre());

        nombreProducto = (TextView) findViewById(R.id.precio_producto);
        nombreProducto.setText(String.format("%.2f", producto.getPrecio()) + "€");

        //endregion

        //region FloatActionButton
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Edificio nearest = getNearest();
                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + Double.toString(nearest.getCx()) + "," + Double.toString(nearest.getCy()) + "&mode=w");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);

            }
        });
        //endregion
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_menu_scrolling, menu);

        MenuItem searchItem = menu.findItem(R.id.action_favorite);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.action_favorite:
                //region PONER EL PRODUCTO A FAVORITO
                if(!favorito){
                    item.setIcon(R.drawable.ic_action_favorite_filled_36dp);
                    favorito = true;
                } else {
                    item.setIcon(R.drawable.ic_action_favorite);
                    favorito = false;
                }
                return true;
                //endregion
        }
        return super.onOptionsItemSelected(item);
    }
}