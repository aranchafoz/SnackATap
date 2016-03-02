package emcafoz.com.snackatap.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PRODUCTO_NOMBRE, producto.getNombre());
        values.put(PRODUCTO_PRECIO, producto.getPrecio());
        values.put(PRODUCTO_CATEGORIA, producto.getCategoria().toString());
        values.put(PRODUCTO_FAV, producto.isFav());

        db.insert(PRODUCTO_TABLA, null, values);

        db.close();

        addProductosInEdificio(producto);
    }

    public void addEdificio(Edificio edificio) {
        Log.d("addEdificio", edificio.toString());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(EDIFICIO_NOMBRE, edificio.getNombre());
        values.put(EDIFICIO_CX, edificio.getCx());
        values.put(EDIFICIO_CY, edificio.getCy());

        db.insert(EDIFICIO_TABLA, null, values);

        db.close();
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

        if (cursor != null)
            cursor.moveToFirst();

        Producto producto = new Producto();
        producto.setId(Integer.parseInt(cursor.getString(0)));
        producto.setNombre(cursor.getString(1));
        producto.setPrecio(Float.parseFloat(cursor.getString(2)));
        producto.setCategoria(Categoria.valueOf(cursor.getString(3)));
        producto.setFav(Boolean.parseBoolean(cursor.getString(4)));


        return producto;
    }

    public Producto getProducto(String nombre) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                PRODUCTO_TABLA,
                PRODUCTO_COLUMNAS,
                " nombre = ?",
                new String[]{String.valueOf(nombre)},
                null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Producto producto = new Producto();
        producto.setId(Integer.parseInt(cursor.getString(0)));
        producto.setNombre(cursor.getString(1));
        producto.setPrecio(Float.parseFloat(cursor.getString(2)));
        producto.setCategoria(Categoria.valueOf(cursor.getString(3)));
        producto.setFav(Boolean.parseBoolean(cursor.getString(4)));
        producto.setEdificios(getEdificiosOfProducto(producto.getId()));

        return producto;
    }

    public Edificio getEdificio(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                EDIFICIO_TABLA,
                EDIFICIO_COLUMNAS,
                " id = ?",
                new String[]{String.valueOf(id)},
                null, null, null, null);


        if (cursor != null)
            cursor.moveToFirst();

        Edificio edificio = new Edificio();
        edificio.setId(Integer.parseInt(cursor.getString(0)));
        edificio.setNombre(cursor.getString(1));
        edificio.setCoordenadas(Float.parseFloat(cursor.getString(2)),
                Float.parseFloat(cursor.getString(3)));

        return edificio;
    }

    public ArrayList<Producto> getProductosFromCategoria(Categoria categoria) {
        ArrayList<Producto> productos = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                PRODUCTO_TABLA,
                PRODUCTO_COLUMNAS,
                " categoria = ?",
                new String[]{String.valueOf(categoria)},
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

        Edificio edificio = new Edificio();
        edificio.setId(Integer.parseInt(cursor.getString(0)));
        edificio.setNombre(cursor.getString(1));
        edificio.setCoordenadas(Float.parseFloat(cursor.getString(2)),
                Float.parseFloat(cursor.getString(3)));

        return edificio;
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