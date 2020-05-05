package ezequiel.whatthefridge.Adicion;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ezequiel.whatthefridge.Funcionalidades.BuscadorIngredientes;
import ezequiel.whatthefridge.Funcionalidades.BuscadorIngredientesBebidas;
import ezequiel.whatthefridge.Funcionalidades.BuscadorIngredientesCocina;
import ezequiel.whatthefridge.Funcionalidades.IngredientesRetriever;
import ezequiel.whatthefridge.Funcionalidades.StockManager;
import ezequiel.whatthefridge.Objetos.Receta;
import ezequiel.whatthefridge.Objetos.StockElement;
import ezequiel.whatthefridge.R;
import ezequiel.whatthefridge.Utils.Constantes;

public class ConfiguradorRecetaIngredientes extends Configurador<Receta> implements IngredientesRetriever {
    private final int RC_COCINA = 1, RC_BEBIDA = 2;
    private StockManager stockManager;
    private List<StockElement> toAdd;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_adicion_receta_ingredientes, container, false);
    }

    @Override
    public boolean finalizar() {
        List<StockElement> list = stockManager.getStockElements();

        if (list.isEmpty()) {
            Toast.makeText(getContext(), getString(R.string.ingreseingrediente), Toast.LENGTH_SHORT).show();
            return false;
        }

        Receta receta = super.getElement();

        receta.setIngredientes(list);
        stockManager.exitSelectMode();
        return true;
    }

    @Override
    public void iniciar() {
        View view = getView();
        stockManager = new StockManager(getActivity(), (ViewGroup) view.findViewById(R.id.ContainerStockManager), view.findViewById(R.id.btnAddIngrediente));
        setStockElementsToAdd();

        for (StockElement s : getElement().getIngredientes()) {
            stockManager.addStockElement(s);
        }
    }

    private void setStockElementsToAdd() {
        BuscadorIngredientes buscadorIngredientesCocina = new BuscadorIngredientesCocina();
        buscadorIngredientesCocina.getStockElements(this, RC_COCINA);
    }

    @Override
    public String id() {
        return Constantes.FRAGMENT_TAG_CONFIGURADOR_RECETA_INGREDIENTES;
    }

    @Override
    public void receiveElements(List<StockElement> list, int requestCode) {
        if (requestCode == RC_COCINA) {
            BuscadorIngredientes buscadorIngredientesBebida = new BuscadorIngredientesBebidas();
            buscadorIngredientesBebida.getStockElements(this, RC_BEBIDA);
            toAdd = new ArrayList<>(list);
        } else {
            toAdd.addAll(list);
            stockManager.setStockElementsToAdd(toAdd);
        }
    }
}
