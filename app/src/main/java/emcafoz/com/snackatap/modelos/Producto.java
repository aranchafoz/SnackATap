package emcafoz.com.snackatap.modelos;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import emcafoz.com.snackatap.sqlite.MySQLiteHelper;

/**
 * Created by enrique on 4/02/16.
 */
public class Producto {
    public static ArrayList<Producto> ALL;

    private int id;
    private String nombre;
    private float precio;
    private Categoria categoria;
    private ArrayList<Edificio> edificios;
    private boolean fav = false;
    int imagen;

    public static void getAll(Context context) {
        MySQLiteHelper db = new MySQLiteHelper(context);
        ALL = db.getAllProductos();
        for (Producto producto : getFromCategoria(Categoria.CaféXXL)) {
            producto.setCategoria(Categoria.Café);
            db.updateProducto(producto);
        }
    }

    public static ArrayList<Producto> getFromCategoria(Categoria categoria) {
        ArrayList<Producto> productos = new ArrayList<>();
        for (Producto producto : ALL) {
            if (producto.categoria == categoria)
                productos.add(producto);
        }
        return productos;
    }

    public static ArrayList<Producto> getFavs() {
        ArrayList<Producto> productos = new ArrayList<>();
        for (Producto producto : ALL) {
            if (producto.isFav())
                productos.add(producto);
        }
        return productos;
    }

    public Producto() {}

    public Producto(String nombre, float precio, Categoria categoria, ArrayList<Edificio> edificios) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.edificios = edificios;
    }

    public String getNombre() {
        return nombre;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() { return id; }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public ArrayList<Edificio> getEdificios() {
        return edificios;
    }

    public boolean isFav() { return fav; }

    public void setFav(boolean fav) { this.fav = fav; }

    public void changeFav(Context context) {
        fav = fav ? false : true;
        MySQLiteHelper helper = new MySQLiteHelper(context);
        helper.updateProducto(this);
        Log.d("Update", "fav set to " + this.fav + " to " + this.nombre);
    }

    public void setEdificios(ArrayList<Edificio> edificios) {
        this.edificios = edificios;
    }

    @Override
    public String toString() {
        return "Producto [id=" + id + ", nombre=" + nombre + ", precio=" + precio
                + ", categoria=" + categoria + "]";
    }

}
