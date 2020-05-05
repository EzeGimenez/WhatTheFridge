package ezequiel.whatthefridge;

import android.os.Bundle;
import android.support.annotation.Nullable;

import ezequiel.whatthefridge.Funcionalidades.BuscadorIngredientesCocina;
import ezequiel.whatthefridge.Funcionalidades.BuscadorRecetasHeladera;
import ezequiel.whatthefridge.Funcionalidades.SaverDummy;

public class StockActivityTemporaryHeladera extends StockActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setSaver(new SaverDummy());
        setBuscadorRecetas(new BuscadorRecetasHeladera());
        setBuscadorIngredientes(new BuscadorIngredientesCocina());

        super.onCreate(savedInstanceState);
    }
}
