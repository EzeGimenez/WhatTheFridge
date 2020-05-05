package ezequiel.whatthefridge.Objetos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ezequiel.whatthefridge.Visitors.Visitable;
import ezequiel.whatthefridge.Visitors.Visitor;

public class Receta implements Serializable, Visitable {
    private List<StockElement> ingredientes;
    private String photoURL;
    private List<String> preparacion;
    private String nombre;
    private String id;

    public Receta(String nombre, String id) {
        ingredientes = new ArrayList<>();
        this.nombre = nombre;
        this.id = id;
    }

    public List<StockElement> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<StockElement> l) {
        ingredientes = l;
    }

    public void addIngrediente(StockElement i) {
        ingredientes.add(i);
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public List<String> getPreparacion() {
        return preparacion;
    }

    public void setPreparacion(List<String> preparacion) {
        this.preparacion = preparacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String n) {
        this.nombre = n;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Receta) {
            return ((Receta) obj).getId().equals(id);
        }
        return false;
    }

    @Override
    public void aceptar(Visitor v) {
        v.visit(this);
    }
}
