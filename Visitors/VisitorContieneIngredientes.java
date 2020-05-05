package ezequiel.whatthefridge.Visitors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ezequiel.whatthefridge.Objetos.IngredientePorPeso;
import ezequiel.whatthefridge.Objetos.IngredientePorUnidad;
import ezequiel.whatthefridge.Objetos.Receta;
import ezequiel.whatthefridge.Objetos.StockElement;

public class VisitorContieneIngredientes implements Visitor {

    private List<StockElement> ingredientesUsuario;
    private List<Receta> output;

    public VisitorContieneIngredientes(List<StockElement> i) {
        this.ingredientesUsuario = i;
        output = new ArrayList<>();
    }

    public List<Receta> getOutput() {
        return output;
    }

    @Override
    public void visit(IngredientePorUnidad e) {

    }

    @Override
    public void visit(IngredientePorPeso e) {

    }

    @Override
    public void visit(Receta r) {
        if (contiene(ingredientesUsuario, r.getIngredientes())) {
            output.add(r);
        }
    }

    /**
     * Determina si los ingredientes de lista1 están en lista2, ademas de cumplir
     * con la cantidad necesaria
     *
     * @param lista1 lista menor
     * @param lista2 lista contenedora
     * @return
     */
    private boolean contiene(List<StockElement> lista1, List<StockElement> lista2) {
        boolean cantidad, contiene = true;

        Iterator<StockElement> it1;
        Iterator<StockElement> it2 = lista2.iterator();
        StockElement s1, s2;

        while (it2.hasNext() && contiene) {
            s2 = it2.next();
            it1 = lista1.iterator();

            cantidad = false;
            while (it1.hasNext() && !cantidad) {
                s1 = it1.next();
                if (s1.equals(s2)) {
                    if (s1.getCantidad() >= s2.getCantidad()) {
                        cantidad = true;
                    } else {
                        break;
                    }
                }
            }

            if (!cantidad) {
                contiene = false;
            }
        }
        return contiene;
    }
}
