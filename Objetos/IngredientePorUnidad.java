package ezequiel.whatthefridge.Objetos;

import android.view.View;
import android.widget.TextView;

import ezequiel.whatthefridge.R;
import ezequiel.whatthefridge.Visitors.Visitor;

/**
 * Clase IngredientePorPeso
 */
public class IngredientePorUnidad extends StockElement {
    private int precedencia;
    private TablaNutricional tablaNutricional;

    public IngredientePorUnidad(String nombre, String id) {
        super(nombre, id);
        this.detailLayoutResource = R.layout.alertdialog_detail_stockelement;
    }

    @Override
    public void fillDetailView(View v) {
        TextView tvNombre = v.findViewById(R.id.tvNombre);
        TextView tvCant = v.findViewById(R.id.tvCant);

        tvNombre.setText(getNombre() + "");
        tvCant.setText(cantidadToString() + "");
    }

    @Override
    public void colide(StockElement i) {
        setCantidad(i.getCantidad());
    }

    @Override
    public int getCantidad() {
        return super.getCantidad();
    }

    @Override
    public void aumentarCantidad() {
        setCantidad(getCantidad() + 1);
    }

    @Override
    public void decrementarCantidad() {
        setCantidad(getCantidad() - 1);
    }

    @Override
    public String cantidadToString() {
        return getCantidad() + " un";
    }

    @Override
    public boolean estaVacio() {
        return getCantidad() == 0;
    }

    @Override
    public boolean estaLleno() {
        return getCantidad() == 12;
    }

    @Override
    public void aceptar(Visitor v) {
        v.visit(this);
    }
}