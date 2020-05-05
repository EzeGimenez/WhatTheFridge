package ezequiel.whatthefridge.Adicion;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Administra las configuraciones
 * Patron de diseño Chain of responsibility, tomando como rol de Manager / Invoker
 * Y la clase abstracta Configurador como Command
 *
 * @param <E> tipo de objeto a configurar
 */
public abstract class ConfiguracionManager<E> {

    private List<Configurador<E>> configuradores;
    private FragmentManager manager;
    private int current;
    private int idContainer;
    private Context context;

    public ConfiguracionManager(FragmentManager manager, int idContainer, Context context) {
        configuradores = new ArrayList<>();
        this.manager = manager;
        this.context = context;
        this.idContainer = idContainer;
        current = -1;
    }

    public void add(Configurador<E> configurador) {
        configuradores.add(configurador);
    }

    /**
     * Buscar siguiente configurador
     *
     * @return true si pudo realizar la accion, false en caso contrario
     */
    public boolean seguir() {
        if (!configuradores.isEmpty()) {
            if (current < configuradores.size() - 1) {
                Configurador<E> configuradorSiguiente = configuradores.get(current + 1);
                FragmentTransaction transaction = manager.beginTransaction();

                if (current > -1) {
                    if (!configuradores.get(current).finalizar()) {
                        return false;
                    }

                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(configuradores.get(current).getView().getRootView().getWindowToken(), 0);

                    configuradorSiguiente.setElement(configuradores.get(current).getElement());

                    transaction
                            .addToBackStack(configuradores.get(current).id());
                }

                transaction
                        .replace(idContainer, configuradorSiguiente, configuradorSiguiente.id())
                        .commit();

                current++;
            } else {

                if (!configuradores.get(current).finalizar()) {
                    return false;
                }
                finalizar();
                current++;
            }
            return true;
        }
        return false;
    }

    public boolean finalizo() {

        return current == configuradores.size();
    }

    /**
     * Volver al configurador previo
     *
     * @return true si pudo realizar la accion, false en caso contrario
     */
    public boolean volver() {
        if (!configuradores.isEmpty()) {
            if (current > 0) {
                manager.popBackStack();
                current--;
                return true;
            }
        }
        return false;
    }

    protected List<Configurador<E>> getConfiguradores() {
        return configuradores;
    }

    protected Context getContext() {
        return context;
    }

    public abstract void finalizar();
}
