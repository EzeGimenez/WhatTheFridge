package ezequiel.whatthefridge.Funcionalidades;

import java.util.ArrayList;
import java.util.List;

import ezequiel.whatthefridge.Objetos.Receta;
import ezequiel.whatthefridge.Objetos.StockElement;

/**
 * Provee la interfaz para un buscador de recetas que concuerden con los StockElements
 */
public interface BuscadorRecetas {

    /**
     * Busca las recetas que contengan la lista de stockElements de source
     *
     * @param source lista de Elementos a buscar
     * @param source lista de Elementos a devolver los resultados
     */
    ArrayList<Receta> buscar(List<StockElement> source);

    /**
     * Busca todas las recetas
     *
     * @return
     */
    List<Receta> getRecetasFromDatabase();

    /**
     * Filtra la lista de todas las recetas
     *
     * @param recetas
     * @return
     */
    List<Receta> filterRecetas(List<StockElement> recetas);
}
