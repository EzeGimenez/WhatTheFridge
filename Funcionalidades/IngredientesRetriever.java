package ezequiel.whatthefridge.Funcionalidades;

import java.util.List;

import ezequiel.whatthefridge.Objetos.StockElement;

/**
 * Interfaz para poder obtener los ingredientes de la base de datos
 */
public interface IngredientesRetriever {

    void receiveElements(List<StockElement> list, int requestCode);

}
