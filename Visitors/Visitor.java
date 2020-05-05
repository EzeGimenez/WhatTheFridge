package ezequiel.whatthefridge.Visitors;

import ezequiel.whatthefridge.Objetos.IngredientePorPeso;
import ezequiel.whatthefridge.Objetos.IngredientePorUnidad;
import ezequiel.whatthefridge.Objetos.Receta;

public interface Visitor {

    /**
     * Realiza la accion sobre IngredientePorUnidad
     *
     * @param e ingredientePorUnidad
     */
    void visit(IngredientePorUnidad e);

    /**
     * Realiza la accion sobre IngredientePorPeso
     *
     * @param e ingredientePorPeso
     */
    void visit(IngredientePorPeso e);

    /**
     * Realiza la accion sobre la receta
     *
     * @param r receta
     */
    void visit(Receta r);
}
