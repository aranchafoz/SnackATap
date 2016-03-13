package emcafoz.com.snackatap.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Point;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import emcafoz.com.snackatap.modelos.Categoria;
import emcafoz.com.snackatap.modelos.Edificio;
import emcafoz.com.snackatap.modelos.Producto;

/**
 * Created by enrique on 4/02/16.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "ProductosDB";

    private static final String PRODUCTO_TABLA = "productos";
    private static final String PRODUCTO_ID = "id";
    private static final String PRODUCTO_NOMBRE = "nombre";
    private static final String PRODUCTO_PRECIO = "precio";
    private static final String PRODUCTO_CATEGORIA = "categoria";
    private static final String PRODUCTO_FAV = "favorito";
    private static final String[] PRODUCTO_COLUMNAS = {PRODUCTO_ID, PRODUCTO_NOMBRE,
            PRODUCTO_PRECIO, PRODUCTO_CATEGORIA, PRODUCTO_FAV};

    private static final String EDIFICIO_TABLA = "edificios";
    private static final String EDIFICIO_ID = "id";
    private static final String EDIFICIO_NOMBRE = "nombre";
    private static final String EDIFICIO_CX = "cx";
    private static final String EDIFICIO_CY = "cy";
    private static final String[] EDIFICIO_COLUMNAS = {EDIFICIO_ID, EDIFICIO_NOMBRE, EDIFICIO_CX,
            EDIFICIO_CY};


    private static final String RELACION_TABLA = "productos_en_edificio";
    private static final String RELACION_PRODUCTO = "producto_id";
    private static final String RELACION_EDIFICIO = "edificio_id";
    private static final String[] RELACION_COLUMNAS = {RELACION_PRODUCTO, RELACION_EDIFICIO};

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }


    public static void createDB(Context context) {
        MySQLiteHelper helper = new MySQLiteHelper(context);
        helper.copyDatabaseFromAssets(context, DATABASE_NAME, false);
        helper.close();

        helper = new MySQLiteHelper(context);

        /*
        //region cosas pasadas
        Log.d("Edificios", Integer.toString(helper.getAllEdificios().size()));
        Log.d("Productos", Integer.toString(helper.getAllProductos().size()));

        helper.addEdificio(new Edificio("Polideportivo", 38.388595, -0.515524));
        helper.addEdificio(new Edificio("Ciencias II", 38.386749, -0.515188));
        helper.addEdificio(new Edificio("Derecho", 38.385601, -0.517009));
        helper.addEdificio(new Edificio("Pabellón 12", 38.386244, -0.513714));
        helper.addEdificio(new Edificio("EPS III", 38.386755, -0.512054));
        helper.addEdificio(new Edificio("EPS II", 38.387349, -0.512307));
        helper.addEdificio(new Edificio("EPS IV", 38.382972, -0.509874));
        helper.addEdificio(new Edificio("Filosofía y Letras I", 38.386002, -0.514336));
        helper.addEdificio(new Edificio("Filosofía y Letras II", 38.385415, -0.514367));
        helper.addEdificio(new Edificio("Filosofía y Letras III", 38.385005, -0.515461));
        helper.addEdificio(new Edificio("Pabellón de Alumnado", 38.385769, -0.513585));
        helper.addEdificio(new Edificio("Aulario I", 38.383056, -0.516116));
        helper.addEdificio(new Edificio("Torre de Control", 38.384638, -0.513127));
        helper.addEdificio(new Edificio("Rectorado", 38.384802, -0.511599));
        helper.addEdificio(new Edificio("Colegio Mayor", 38.385177, -0.509383));
        helper.addEdificio(new Edificio("Aulario II", 38.384512, -0.510159));
        helper.addEdificio(new Edificio("Económicas", 38.382941, -0.514229));
        helper.addEdificio(new Edificio("Biblioteca", 38.383268, -0.512104));
        helper.addEdificio(new Edificio("Ciencias Sociales", 38.383577, -0.510936));
        helper.addEdificio(new Edificio("Germán Benácer", 38.382366, -0.512325));
        helper.addEdificio(new Edificio("Institutos Universitarios", 38.382686, -0.511640));
        helper.addEdificio(new Edificio("Óptica", 38.382770, -0.510792));
        helper.addEdificio(new Edificio("Aulario III", 38.382331, -0.508505));
        helper.addEdificio(new Edificio("Educación", 38.387747, -0.519891));
        helper.addEdificio(new Edificio("Club Social 1", 38.384459, -0.516095));


        ArrayList<Edificio> cafeXXL = new ArrayList<>();
        cafeXXL.add(helper.getEdificio("Club Social 1"));

        helper.addProducto(new Producto("Choco-Latte Frape XXL", 1.5f, Categoria.CaféXXL, cafeXXL));
        helper.addProducto(new Producto("Café Avellana Frío XXL", 1.5f, Categoria.CaféXXL, cafeXXL));
        helper.addProducto(new Producto("Frappe Intensa XXL", 1.5f, Categoria.CaféXXL, cafeXXL));
        helper.addProducto(new Producto("Cafe-Latte Ice Expresso XXL", 1.5f, Categoria.CaféXXL, cafeXXL));
        helper.addProducto(new Producto("Cafe-Latte Ice Descafeinado XXL", 1.5f, Categoria.CaféXXL, cafeXXL));
        helper.addProducto(new Producto("White Frapuccio XXL", 1.5f, Categoria.CaféXXL, cafeXXL));
        helper.addProducto(new Producto("Ice Chocolate Cream XXL", 1.5f, Categoria.CaféXXL, cafeXXL));
        helper.addProducto(new Producto("Manhattan Frape XXL", 1.5f, Categoria.CaféXXL, cafeXXL));
        helper.addProducto(new Producto("Café con Leche Descafeinado XXL", 1f, Categoria.CaféXXL, cafeXXL));
        helper.addProducto(new Producto("Capuccino Descafeinado XXL", 1.2f, Categoria.CaféXXL, cafeXXL));
        helper.addProducto(new Producto("Leche Manchada", 0.5f, Categoria.CaféXXL, cafeXXL));
        helper.addProducto(new Producto("Descafeinado Largo", 0.5f, Categoria.CaféXXL, cafeXXL));
        helper.addProducto(new Producto("Descafeinado Corto", 0.5f, Categoria.CaféXXL, cafeXXL));
        helper.addProducto(new Producto("Ristretto Finca Las Morentas", 0.7f, Categoria.CaféXXL, cafeXXL));
        helper.addProducto(new Producto("Capuccino Finca Las Morenitas XXL", 1.2f, Categoria.CaféXXL, cafeXXL));
        helper.addProducto(new Producto("Latte Macciatto Finca Las Morenitas XXL", 1.2f, Categoria.CaféXXL, cafeXXL));
        helper.addProducto(new Producto("Café Cortado Finca Las Morenitas", 0.7f, Categoria.CaféXXL, cafeXXL));
        helper.addProducto(new Producto("Café Largo Finca Las Morenitas", 0.7f, Categoria.CaféXXL, cafeXXL));
        helper.addProducto(new Producto("Café Corto Finca Las Morenitas", 1.5f, Categoria.CaféXXL, cafeXXL));
        helper.addProducto(new Producto("Café Corto Clásico", 0.5f, Categoria.CaféXXL, cafeXXL));
        helper.addProducto(new Producto("Café Largo Clásico", 0.5f, Categoria.CaféXXL, cafeXXL));
        helper.addProducto(new Producto("Café Cortado Clásico", 0.5f, Categoria.CaféXXL, cafeXXL));
        helper.addProducto(new Producto("Café con Leche Clásico", 0.5f, Categoria.CaféXXL, cafeXXL));
        helper.addProducto(new Producto("Capuccino con Chocolate", 0.6f, Categoria.CaféXXL, cafeXXL));
        helper.addProducto(new Producto("Leche Manchada Clásica", 0.5f, Categoria.CaféXXL, cafeXXL));
        helper.addProducto(new Producto("Café con Leche XXL", 1f, Categoria.CaféXXL, cafeXXL));
        helper.addProducto(new Producto("Café Americano XXL", 1f, Categoria.CaféXXL, cafeXXL));
        helper.addProducto(new Producto("Café a la Avellana XXL", 1.2f, Categoria.CaféXXL, cafeXXL));
        helper.addProducto(new Producto("Crema de Avellana XXL", 1.2f, Categoria.CaféXXL, cafeXXL));
        helper.addProducto(new Producto("Café Mocca XXL", 1.2f, Categoria.CaféXXL, cafeXXL));
        helper.addProducto(new Producto("Smooth Cappuccino XXL", 1.2f, Categoria.CaféXXL, cafeXXL));
        helper.addProducto(new Producto("White Capuccino XXL", 1.2f, Categoria.CaféXXL, cafeXXL));
        helper.addProducto(new Producto("Capuccino Finca Las Morenitas XXL", 1.2f, Categoria.CaféXXL, cafeXXL));
        helper.addProducto(new Producto("Cappuccino Descafeinado XXL", 1.2f, Categoria.CaféXXL, cafeXXL));
        helper.addProducto(new Producto("Crema de Cacao XXL", 1.2f, Categoria.CaféXXL, cafeXXL));
        helper.addProducto(new Producto("Chocolate Blanco XXL", 1.2f, Categoria.CaféXXL, cafeXXL));
        helper.addProducto(new Producto("Chocolate Small", 0.5f, Categoria.CaféXXL, cafeXXL));
        helper.addProducto(new Producto("Chocolate con Leche Small", 0.5f, Categoria.CaféXXL, cafeXXL));
        helper.addProducto(new Producto("Chocolate Premium XXL", 1.2f, Categoria.CaféXXL, cafeXXL));
        helper.addProducto(new Producto("Chocolate Con Leche Premium XXL", 1.2f, Categoria.CaféXXL, cafeXXL));
        helper.addProducto(new Producto("Crema de Cacao XXL", 1.2f, Categoria.CaféXXL, cafeXXL));
        helper.addProducto(new Producto("Chocolate Blanco XXL", 1.2f, Categoria.CaféXXL, cafeXXL));

        ArrayList<Edificio> cafes = helper.getAllEdificios();

        helper.addProducto(new Producto("Café solo", 0.4f, Categoria.Café, cafes));
        helper.addProducto(new Producto("Café largo", 0.4f, Categoria.Café, cafes));
        helper.addProducto(new Producto("Café cortado", 0.5f, Categoria.Café, cafes));
        helper.addProducto(new Producto("Café con leche", 0.5f, Categoria.Café, cafes));
        helper.addProducto(new Producto("Capuccino", 0.5f, Categoria.Café, cafes));
        helper.addProducto(new Producto("Café solo (Comercio Justo)", 0.4f, Categoria.Café, cafes));
        helper.addProducto(new Producto("Café cortado (Comercio Justo)", 0.4f, Categoria.Café, cafes));
        helper.addProducto(new Producto("Café con leche (Comercio Justo)", 0.4f, Categoria.Café, cafes));
        helper.addProducto(new Producto("Capuccino (Comercio Justo)", 0.4f, Categoria.Café, cafes));
        helper.addProducto(new Producto("Té con limón", 0.2f, Categoria.Café, cafes));
        helper.addProducto(new Producto("Café solo descafeinado", 0.4f, Categoria.Café, cafes));
        helper.addProducto(new Producto("Café largo descafeinado", 0.4f, Categoria.Café, cafes));
        helper.addProducto(new Producto("Café cortado descafeinado", 0.5f, Categoria.Café, cafes));
        helper.addProducto(new Producto("Café con leche descafeinado", 0.5f, Categoria.Café, cafes));
        helper.addProducto(new Producto("Capuccino descafeinado", 0.5f, Categoria.Café, cafes));
        helper.addProducto(new Producto("Leche", 0.2f, Categoria.Café, cafes));
        helper.addProducto(new Producto("Chocolate con leche", 0.5f, Categoria.Café, cafes));
        helper.addProducto(new Producto("Chocolate", 0.5f, Categoria.Café, cafes));
        helper.addProducto(new Producto("Leche manchada", 0.2f, Categoria.Café, cafes));
        helper.addProducto(new Producto("Capuccino vainilla", 0.5f, Categoria.Café, cafes));

        ArrayList<Edificio> refrescos = helper.getAllEdificios();
        refrescos.remove(helper.getEdificio("EPS III"));
        refrescos.remove(helper.getEdificio("Filosofía y Letras III"));
        refrescos.remove(helper.getEdificio("Pabellón de Alumnado"));
        refrescos.remove(helper.getEdificio("Colegio Mayor"));
        refrescos.remove(helper.getEdificio("Óptica"));

        ArrayList<Edificio> snacks = helper.getAllEdificios();
        snacks.remove(helper.getEdificio("Pabellón de Alumnado"));
        snacks.remove(helper.getEdificio("Pabellón 12"));
        snacks.remove(helper.getEdificio("EPS II"));
        snacks.remove(helper.getEdificio("Torre de Control"));

        helper.addProducto(new Producto("Agua", 0.5f, Categoria.Refresco, refrescos));
        helper.addProducto(new Producto("Coca Cola lata", 0.7f, Categoria.Refresco, refrescos));

        helper.addProducto(new Producto("Nestea", 1f, Categoria.Refresco, snacks));
        helper.addProducto(new Producto("Red Bull", 1f, Categoria.Refresco, snacks));
        helper.addProducto(new Producto("Coca Cola 0.5l", 1f, Categoria.Refresco, snacks));
        helper.addProducto(new Producto("Coca Cola Zero 0.5l", 1f, Categoria.Refresco, snacks));
        helper.addProducto(new Producto("Cacaolat", 0.5f, Categoria.Refresco, snacks));
        helper.addProducto(new Producto("Disfruta Piña", 0.35f, Categoria.Refresco, snacks));
        helper.addProducto(new Producto("Disfruta Naranja", 0.35f, Categoria.Refresco, snacks));
        helper.addProducto(new Producto("Bifrutas Tropical", 0.6f, Categoria.Refresco, snacks));
        helper.addProducto(new Producto("Bifrutas Mediterráneo", 0.6f, Categoria.Refresco, snacks));
        helper.addProducto(new Producto("Fanta Naranja", 0.7f, Categoria.Refresco, snacks));
        helper.addProducto(new Producto("Monster", 1.7f, Categoria.Refresco, snacks));

        helper.addProducto(new Producto("Pan de Pipas Velarte", 0.8f, Categoria.Snacks, snacks));
        helper.addProducto(new Producto("Patatas Onduladas", 0.5f, Categoria.Snacks, snacks));
        helper.addProducto(new Producto("Palitos Velarte", 0.8f, Categoria.Snacks, snacks));
        helper.addProducto(new Producto("Milka Leo", 0.8f, Categoria.Snacks, snacks));
        helper.addProducto(new Producto("Mister Corn Mix5", 0.5f, Categoria.Snacks, snacks));
        helper.addProducto(new Producto("Pastelito Dulcesol", 0.8f, Categoria.Snacks, snacks));
        helper.addProducto(new Producto("Tic Break", 0.7f, Categoria.Snacks, snacks));
        helper.addProducto(new Producto("Galletas El Gorriaga", 0.8f, Categoria.Snacks, snacks));
        helper.addProducto(new Producto("Mini Digestive", 0.8f, Categoria.Snacks, snacks));
        helper.addProducto(new Producto("Chips Ahoy", 0.8f, Categoria.Snacks, snacks));
        helper.addProducto(new Producto("Oreo", 0.8f, Categoria.Snacks, snacks));
        helper.addProducto(new Producto("M&M's", 0.8f, Categoria.Snacks, snacks));
        helper.addProducto(new Producto("Twix", 0.8f, Categoria.Snacks, snacks));
        helper.addProducto(new Producto("Chicles Orbit", 0.6f, Categoria.Snacks, snacks));
        helper.addProducto(new Producto("Galletas Krit Cuetara", 0.8f, Categoria.Snacks, snacks));
        helper.addProducto(new Producto("Donuts", 1f, Categoria.Snacks, snacks));
        helper.addProducto(new Producto("Donuts chocolate", 1f, Categoria.Snacks, snacks));
        helper.addProducto(new Producto("Galletas Belvita", 0.8f, Categoria.Snacks, snacks));
        helper.addProducto(new Producto("Mini Palmera Dulcesol", 0.5f, Categoria.Snacks, snacks));
        helper.addProducto(new Producto("Skittles", 0.8f, Categoria.Snacks, snacks));
        helper.addProducto(new Producto("Ositos Haribo", 0.8f, Categoria.Snacks, snacks));
        helper.addProducto(new Producto("Sándwiches varios", 1f, Categoria.Snacks, snacks));
        helper.addProducto(new Producto("Bocadillos varios", 1.5f, Categoria.Snacks, snacks));
        helper.addProducto(new Producto("Palmera Dulcesol", 0.8f, Categoria.Snacks, snacks));
        helper.addProducto(new Producto("Alba Panetines", 0.8f, Categoria.Snacks, snacks));
        helper.addProducto(new Producto("Doritos", 0.95f, Categoria.Snacks, snacks));
        helper.addProducto(new Producto("Fantasmitas Chetos", 0.95f, Categoria.Snacks, snacks));
        helper.addProducto(new Producto("Papa Delta", 0.8f, Categoria.Snacks, snacks));
        helper.addProducto(new Producto("Kinder Bueno", 1.10f, Categoria.Snacks, snacks));
        helper.addProducto(new Producto("KitKat", 0.9f, Categoria.Snacks, snacks));
        helper.addProducto(new Producto("Mister Corn Quicos", 0.8f, Categoria.Snacks, snacks));
        helper.addProducto(new Producto("Ensaladas varias", 1.2f, Categoria.Snacks, snacks));
        helper.addProducto(new Producto("Galletas Principe", 0.9f, Categoria.Snacks, snacks));
        helper.addProducto(new Producto("M&M's Cine", 0.8f, Categoria.Snacks, snacks));
        helper.addProducto(new Producto("Chocolatina Milka Oreo", 0.9f, Categoria.Snacks, snacks));
        helper.addProducto(new Producto("Nestlé Snack", 0.8f, Categoria.Snacks, snacks));
        helper.addProducto(new Producto("Galletas Principe", 0.9f, Categoria.Snacks, snacks));
        helper.addProducto(new Producto("Donetes", 1.25f, Categoria.Snacks, snacks));


        ArrayList<Edificio> comida_sana = new ArrayList<>();
        comida_sana.add(helper.getEdificio("Aulario II"));
        comida_sana.add(helper.getEdificio("Derecho"));
        comida_sana.add(helper.getEdificio("Polideportivo"));
        comida_sana.add(helper.getEdificio("Biblioteca"));

        helper.addProducto(new Producto("Tortitas Bicentury", 0.8f, Categoria.ComidaSana, comida_sana));
        helper.addProducto(new Producto("Mini Tortitas", 0.8f, Categoria.ComidaSana, comida_sana));
        helper.addProducto(new Producto("Galletas Muesli", 0.8f, Categoria.ComidaSana, comida_sana));
        helper.addProducto(new Producto("Ensaladillas Varias", 2f, Categoria.ComidaSana, comida_sana));
        helper.addProducto(new Producto("Galletas con Fibra", 0.8f, Categoria.ComidaSana, comida_sana));
        helper.addProducto(new Producto("Tortitas Bicentury", 0.8f, Categoria.ComidaSana, comida_sana));

        Log.d("Edificios", Integer.toString(helper.getAllEdificios().size()));
        Log.d("Productos", Integer.toString(helper.getAllProductos().size()));

*/
        //endregion
    }

    /**
     * Copy database file from assets folder inside the apk to the system database path.
     * @param context Context
     * @param databaseName Database file name inside assets folder
     * @param overwrite True to rewrite on the database if exists
     * @return True if the database have copied successfully or if the database already exists without overwrite, false otherwise.
     */
    public boolean copyDatabaseFromAssets(Context context, String databaseName , boolean overwrite)  {
        File outputFile = context.getDatabasePath(databaseName);

        if (outputFile.exists() && !overwrite) {
            return true;
        }

        outputFile = context.getDatabasePath(databaseName);
        Log.d("Path: ", outputFile.toString());
        outputFile.getParentFile().mkdirs();

        try {
            InputStream inputStream = context.getAssets().open(databaseName);
            OutputStream outputStream = new FileOutputStream(outputFile);


            // transfer bytes from the input stream into the output stream
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            // Close the streams
            outputStream.flush();
            outputStream.close();
            inputStream.close();

            outputFile.renameTo(context.getDatabasePath(databaseName));

        } catch (IOException e) {
            if (outputFile.exists()) {
                outputFile.delete();
            }
            Log.d("IOException", e.toString());
            return false;
        }
        return true;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d("onCreate", "Creating Database");
        String CREATE_TABLE_PRODUCTO = "CREATE TABLE " + PRODUCTO_TABLA + " ( " +
                PRODUCTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PRODUCTO_NOMBRE + " TEXT, " +
                PRODUCTO_PRECIO + " REAL, " +
                PRODUCTO_CATEGORIA + " TEXT, " +
                PRODUCTO_FAV + " INTEGER )";

        db.execSQL(CREATE_TABLE_PRODUCTO);

        String CREATE_TABLE_EDIFICIO = "CREATE TABLE " + EDIFICIO_TABLA + " ( " +
                EDIFICIO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                EDIFICIO_NOMBRE + " TEXT, " +
                EDIFICIO_CX + " REAL, " +
                EDIFICIO_CY + " REAL )";

        db.execSQL(CREATE_TABLE_EDIFICIO);

        String CREATE_TABLE_RELACION = "CREATE TABLE " + RELACION_TABLA + " ( " +
                "edificio_id" + " INTEGER, " +
                "producto_id" + " INTEGER, " +
                "PRIMARY KEY ( " +
                RELACION_PRODUCTO + ", " + RELACION_EDIFICIO + " )" +
                " )";

        db.execSQL(CREATE_TABLE_RELACION);

        Log.d("onCreate", "Database Created" + " in " + db.getPath());



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PRODUCTO_TABLA);
        db.execSQL("DROP TABLE IF EXISTS " + EDIFICIO_TABLA);

        this.onCreate(db);
    }

    /**
     * CRUD operations
     */

    public void addProducto(Producto producto) {
        SQLiteDatabase check = this.getReadableDatabase();
        Producto p = getProducto(producto.getNombre());
        if (p == null) {
            check.close();

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(PRODUCTO_NOMBRE, producto.getNombre());
            values.put(PRODUCTO_PRECIO, producto.getPrecio());
            values.put(PRODUCTO_CATEGORIA, producto.getCategoria().toString());
            values.put(PRODUCTO_FAV, producto.isFav());

            db.insert(PRODUCTO_TABLA, null, values);

            db.close();

            addProductosInEdificio(producto);

            Log.d("addProducto", producto.toString());
        } else {
            Log.d("Producto ya añadido", producto.toString());
        }
    }

    public void addEdificio(Edificio edificio) {
        SQLiteDatabase check = this.getReadableDatabase();
        Edificio e = getEdificio(edificio.getNombre());
        if (e == null) {
            check.close();

            Log.d("addEdificio", edificio.toString());
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(EDIFICIO_NOMBRE, edificio.getNombre());
            values.put(EDIFICIO_CX, edificio.getCx());
            values.put(EDIFICIO_CY, edificio.getCy());

            db.insert(EDIFICIO_TABLA, null, values);

            db.close();
        } else {
            Log.d("Edificio ya añadido", edificio.toString());
        }

    }

    private void addProductosInEdificio(Producto producto) {
        SQLiteDatabase db = this.getWritableDatabase();

        int producto_id = getProducto(producto.getNombre()).getId();

        for (Edificio edificio : producto.getEdificios()) {
            int edificio_id = getEdificio(edificio.getNombre()).getId();
            ContentValues values = new ContentValues();

            values.put(RELACION_PRODUCTO, producto_id);
            values.put(RELACION_EDIFICIO, edificio_id);

            db.insert(RELACION_TABLA, null, values);
        }

        db.close();
    }

    public Producto getProducto(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =
                db.query(PRODUCTO_TABLA,
                        PRODUCTO_COLUMNAS,
                        " id = ?",
                        new String[]{String.valueOf(id)},
                        null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            Producto producto = new Producto();
            producto.setId(Integer.parseInt(cursor.getString(0)));
            producto.setNombre(cursor.getString(1));
            producto.setPrecio(Float.parseFloat(cursor.getString(2)));
            producto.setCategoria(Categoria.valueOf(cursor.getString(3)));
            producto.setFav(Boolean.parseBoolean(cursor.getString(4)));
            cursor.close();
            producto.setEdificios(getEdificiosOfProducto(producto.getId()));
            return producto;
        } else
            return null;
    }

    public Producto getProducto(String nombre) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                PRODUCTO_TABLA,
                PRODUCTO_COLUMNAS,
                " nombre = ?",
                new String[]{String.valueOf(nombre)},
                null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            try {
                Producto producto = new Producto();
                producto.setId(Integer.parseInt(cursor.getString(0)));
                producto.setNombre(cursor.getString(1));
                producto.setPrecio(Float.parseFloat(cursor.getString(2)));
                producto.setCategoria(Categoria.valueOf(cursor.getString(3)));
                producto.setFav(Boolean.parseBoolean(cursor.getString(4)));
                cursor.close();
                producto.setEdificios(getEdificiosOfProducto(producto.getId()));
                return producto;
            } catch (Exception e) {
                return null;
            }

        }
        return null;
    }

    public Edificio getEdificio(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                EDIFICIO_TABLA,
                EDIFICIO_COLUMNAS,
                " id = ?",
                new String[]{String.valueOf(id)},
                null, null, null, null);


        if (cursor != null) {
            cursor.moveToFirst();
            try {
                Edificio edificio = new Edificio();
                edificio.setId(Integer.parseInt(cursor.getString(0)));
                edificio.setNombre(cursor.getString(1));
                edificio.setCoordenadas(Float.parseFloat(cursor.getString(2)),
                        Float.parseFloat(cursor.getString(3)));
                cursor.close();
                return edificio;
            } catch (Exception e) {
                return null;
            }
        } else
            return null;

    }

    public ArrayList<Producto> getProductosFromCategoria(Categoria categoria) {
        ArrayList<Producto> productos = new ArrayList<>();
        Log.d("DB", "getting productos from categoría");
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                PRODUCTO_TABLA,
                PRODUCTO_COLUMNAS,
                " categoria = ?",
                new String[]{categoria.toString()},
                null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            do {
                Producto producto = new Producto();
                producto.setId(Integer.parseInt(cursor.getString(0)));
                producto.setNombre(cursor.getString(1));
                producto.setPrecio(Float.parseFloat(cursor.getString(2)));
                producto.setCategoria(Categoria.valueOf(cursor.getString(3)));
                producto.setFav(Boolean.parseBoolean(cursor.getString(4)));
                producto.setEdificios(getEdificiosOfProducto(producto.getId()));
            } while (cursor.moveToNext());
        }
        return productos;
    }

    public Edificio getEdificio(String nombre) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                EDIFICIO_TABLA,
                EDIFICIO_COLUMNAS,
                " nombre = ?",
                new String[]{String.valueOf(nombre)},
                null, null, null, null);


        if (cursor != null)
            cursor.moveToFirst();

        try {
            Edificio edificio = new Edificio();
            edificio.setId(Integer.parseInt(cursor.getString(0)));
            edificio.setNombre(cursor.getString(1));
            edificio.setCoordenadas(Float.parseFloat(cursor.getString(2)),
                    Float.parseFloat(cursor.getString(3)));
            cursor.close();
            return edificio;
        } catch (Exception e) {
            return null;
        }

    }

    public ArrayList<Producto> getAllProductos() {
        ArrayList<Producto> productos = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + PRODUCTO_TABLA;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Producto producto = new Producto();
                producto.setId(Integer.parseInt(cursor.getString(0)));
                producto.setNombre(cursor.getString(1));
                producto.setPrecio(Float.parseFloat(cursor.getString(2)));
                producto.setCategoria(Categoria.valueOf(cursor.getString(3)));
                producto.setFav(Boolean.parseBoolean(cursor.getString(4)));
                producto.setEdificios(getEdificiosOfProducto(producto.getId()));

                productos.add(producto);
            } while (cursor.moveToNext());
        }
        return productos;
    }

    public ArrayList<Edificio> getAllEdificios() {
        ArrayList<Edificio> edificios = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + EDIFICIO_TABLA;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Edificio edificio = new Edificio();
                edificio.setId(Integer.parseInt(cursor.getString(0)));
                edificio.setNombre(cursor.getString(1));
                edificio.setCoordenadas(Float.parseFloat(cursor.getString(2)),
                        Float.parseFloat(cursor.getString(3)));

                edificios.add(edificio);
            } while (cursor.moveToNext());
        }

        return edificios;
    }


    private ArrayList<Edificio> getEdificiosOfProducto(int id) {
        ArrayList<Edificio> edificios = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + RELACION_EDIFICIO + " FROM " + RELACION_TABLA + " WHERE "
                + RELACION_PRODUCTO + " = " + String.valueOf(id);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int edificio_id = Integer.parseInt(cursor.getString(0));
                Edificio edificio = getEdificio(edificio_id);
                edificios.add(edificio);
            } while (cursor.moveToNext());
        }

        return edificios;
    }

    public void updateProducto(Producto producto) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PRODUCTO_NOMBRE, producto.getNombre());
        values.put(PRODUCTO_PRECIO, producto.getPrecio());
        values.put(PRODUCTO_CATEGORIA, producto.getCategoria().toString());
        values.put(PRODUCTO_FAV, producto.isFav());

        db.update(PRODUCTO_TABLA,
                values,
                PRODUCTO_ID + " = ?",
                new String[]{String.valueOf(producto.getId())});

        db.close();

        deleteProductosInEdificio(producto.getId());
        addProductosInEdificio(producto);
    }

    public void updateEdificio(Edificio edificio) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(EDIFICIO_NOMBRE, edificio.getNombre());
        values.put(EDIFICIO_CX, String.valueOf(edificio.getCx()));
        values.put(EDIFICIO_CY, String.valueOf(edificio.getCy()));

        db.update(EDIFICIO_TABLA,
                values,
                EDIFICIO_ID + " = ?",
                new String[]{String.valueOf(edificio.getId())});

        db.close();
    }

    public void deleteProducto(Producto producto) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PRODUCTO_TABLA,
                PRODUCTO_ID + " = ?",
                new String[]{String.valueOf(producto.getId())});
        db.close();
    }

    public void deleteEdificio(Edificio edificio) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(EDIFICIO_TABLA,
                PRODUCTO_ID + " = ?",
                new String[]{String.valueOf(edificio.getId())});
        db.close();
    }

    private void deleteProductosInEdificio(int producto_id) {
        String query = "DELETE FROM " + RELACION_TABLA + " WHERE " + RELACION_PRODUCTO + " = "
                + String.valueOf(producto_id);
        SQLiteDatabase db = getWritableDatabase();
        db.rawQuery(query, null);
        db.close();
    }
}