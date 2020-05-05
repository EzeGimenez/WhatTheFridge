package ezequiel.whatthefridge.Funcionalidades;

/**
 * Provee interfaz para la búsqueda de todos los stock elements
 * que se pueden utilizar
 */
public interface BuscadorIngredientes {

    /**
     * Busca y retorna los stockelements
     *
     * @return lista de stockElements
     */
    void getStockElements(IngredientesRetriever s, int requestCode);

}
