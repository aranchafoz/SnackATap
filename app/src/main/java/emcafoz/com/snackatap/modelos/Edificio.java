package emcafoz.com.snackatap.modelos;

import android.graphics.Point;

/**
 * Created by enrique on 4/02/16.
 */
public class Edificio {
    private int id;

    private String nombre;
    private double cx, cy;

    public Edificio() {}

    public Edificio(String nombre, double cx, double cy) {
        this.nombre = nombre;
        this.cx = cx;
        this.cy = cy;
    }

    public String getNombre() {
        return nombre;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCoordenadas(double cx, double cy) {
        this.cx = cx;
        this.cy = cy;
    }

    public double getCx() {
        return cx;
    }

    public double getCy() {
        return cy;
    }

    public int getId() { return id; }

    @Override
    public String toString() {
        return "Edificio [id=" + id + ", nombre" + nombre
                + ", cx=" + cx + ", cy=" + cy + "]";
    }
}

