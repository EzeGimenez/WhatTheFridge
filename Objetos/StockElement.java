package ezequiel.whatthefridge.Objetos;

import android.view.View;

import java.io.Serializable;

import ezequiel.whatthefridge.Visitors.Visitable;

public abstract class StockElement implements Serializable, Visitable {
    protected String nombre;
    protected int detailLayoutResource;
    private String ID;
    private boolean esDeCocina;
    private int cantidad;

    public StockElement(String nombre, String id) {
        this.nombre = nombre;
        this.ID = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDetailLayoutResource() {
        return detailLayoutResource;
    }

    /**
     * Rellena la informacion del view, se recibe por parámetro la view obtenida de detailLayoutResource
     *
     * @param v view inflada de detailLayoutResource
     */
    public abstract void fillDetailView(View v);

    /**
     * Llamado cuando ya se encontro un view igual a este
     *
     * @param i
     */
    public abstract void colide(StockElement i);

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int c) {
        this.cantidad = c;
    }

    public abstract void aumentarCantidad();

    public abstract void decrementarCantidad();

    public abstract String cantidadToString();

    public abstract boolean estaVacio();

    public abstract boolean estaLleno();

    public boolean equals(StockElement obj) {
        return obj.getID().equals(getID());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StockElement) {
            return equals((StockElement) obj);
        }
        return false;
    }

    public String getID() {
        return ID;
    }

    public boolean isEsDeCocina() {
        return esDeCocina;
    }

    public void setEsDeCocina(boolean esDeCocina) {
        this.esDeCocina = esDeCocina;
    }
}
