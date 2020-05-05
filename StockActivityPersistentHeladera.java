package ezequiel.whatthefridge;

import android.os.Bundle;
import android.support.annotation.Nullable;

import ezequiel.whatthefridge.Funcionalidades.BuscadorIngredientesCocina;
import ezequiel.whatthefridge.Funcionalidades.BuscadorRecetasHeladera;
import ezequiel.whatthefridge.Funcionalidades.SaverStockElement;
import ezequiel.whatthefridge.Funcionalidades.Storage;
import ezequiel.whatthefridge.Funcionalidades.StorageInternal;
import ezequiel.whatthefridge.Utils.Constantes;

public class StockActivityPersistentHeladera extends StockActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Storage storage = new StorageInternal(this, Constantes.SHARED_PREF_NAME_HELADERA);

        setSaver(new SaverStockElement(storage));
        setBuscadorRecetas(new BuscadorRecetasHeladera());
        setBuscadorIngredientes(new BuscadorIngredientesCocina());

        super.onCreate(savedInstanceState);
    }
}
