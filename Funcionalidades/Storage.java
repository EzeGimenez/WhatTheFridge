package ezequiel.whatthefridge.Funcionalidades;

import java.io.Serializable;

/**
 * Interfaz para abstraer la nocion de lugar de guardado
 */
public interface Storage extends Serializable {

    /**
     * Guardado
     *
     * @param a direccion de guardado
     * @param b objeto a save
     */
    void save(String a, String b);

    /**
     * Obtencion
     *
     * @param a nombre de la clave
     * @return objeto hallado, null si no se hallo
     */
    String get(String a);

    /**
     * Retorna nombre del lugar;
     *
     * @return nombre
     */
    String getName();
}
