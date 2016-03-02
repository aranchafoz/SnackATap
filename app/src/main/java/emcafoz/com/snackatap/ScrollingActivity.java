package emcafoz.com.snackatap;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class ScrollingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //region inicializacion del producto

        Bundle bundle = getIntent().getBundleExtra("producto");

        TextView nombreProducto = (TextView) findViewById(R.id.nombre_producto);
        nombreProducto.setText(bundle.getString("nombre"));

        nombreProducto = (TextView) findViewById(R.id.precio_producto);
        nombreProducto.setText(bundle.getFloat("precio") + "");

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