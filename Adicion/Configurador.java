package ezequiel.whatthefridge.Adicion;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Interfaz para la configuracion de Elementos
 * Está determinado para que su disposicion sea a través de fragments
 * Cada subclase deberá definir su propio onCreateView,
 */
public abstract class Configurador<E> extends Fragment {

    private E element;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        iniciar();
    }

    /**
     * Obtiene el element
     *
     * @return element
     */
    public E getElement() {
        return element;
    }

    /**
     * Debe ser llamado post creacion
     *
     * @param r
     */
    public void setElement(E r) {
        this.element = r;
    }

    /**
     * Termina la configuracion del configurador
     *
     * @return true si se pudo finalizar correctamente, false en caso contrario
     */
    public abstract boolean finalizar();

    /**
     * Setea e inicializa la configuracion
     */
    public abstract void iniciar();

    public abstract String id();

}
