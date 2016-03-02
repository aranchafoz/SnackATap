package emcafoz.com.snackatap.modelos;

import java.util.ArrayList;

/**
 * Created by enrique on 4/02/16.
 */
public class Producto {
    private int id;
    private String nombre;
    private float precio;
    private Categoria categoria;
    private ArrayList<Edificio> edificios;
    private boolean fav = false;
    int imagen;

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

    public void setEdificios(ArrayList<Edificio> edificios) {
        this.edificios = edificios;
    }

    @Override
    public String toString() {
        return "Producto [id=" + id + ", nombre=" + nombre + ", precio=" + precio
                + ", categoria=" + categoria + "]";
    }

}
