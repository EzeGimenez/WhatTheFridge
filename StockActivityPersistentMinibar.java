package ezequiel.whatthefridge;

import android.os.Bundle;
import android.support.annotation.Nullable;

import ezequiel.whatthefridge.Funcionalidades.BuscadorIngredientesBebidas;
import ezequiel.whatthefridge.Funcionalidades.BuscadorRecetasMinibar;
import ezequiel.whatthefridge.Funcionalidades.SaverStockElement;
import ezequiel.whatthefridge.Funcionalidades.Storage;
import ezequiel.whatthefridge.Funcionalidades.StorageInternal;
import ezequiel.whatthefridge.Utils.Constantes;

public class StockActivityPersistentMinibar extends StockActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Storage storage = new StorageInternal(this, Constantes.SHARED_PREF_NAME_MINIBAR);

        setSaver(new SaverStockElement(storage));
        setBuscadorRecetas(new BuscadorRecetasMinibar());
        setBuscadorIngredientes(new BuscadorIngredientesBebidas());

        super.onCreate(savedInstanceState);
    }
}
