package emcafoz.com.snackatap;

import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import emcafoz.com.snackatap.modelos.Edificio;
import emcafoz.com.snackatap.modelos.Producto;
import emcafoz.com.snackatap.sqlite.MySQLiteHelper;

public class ScrollingActivity extends AppCompatActivity {

    public GoogleMap googleMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        googleMap.getUiSettings().setZoomGesturesEnabled(false);

        googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                LatLng focus = new LatLng(location.getLatitude(), location.getLongitude());
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(focus, 15));
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //region inicializacion del producto

        Bundle bundle = getIntent().getBundleExtra("producto");

        MySQLiteHelper helper = new MySQLiteHelper(this);
        Producto producto = helper.getProducto(bundle.getString("nombre"));
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

                //region reemplazar con Google Maps
                // Snackbar.make(view, getResources().getString(R.string.snackbar), Snackbar.LENGTH_LONG)
                //         .setAction("Action", null).show();
                //endregion

            }
        });
        //endregion
    }
}