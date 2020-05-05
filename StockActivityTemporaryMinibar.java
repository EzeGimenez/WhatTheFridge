package ezequiel.whatthefridge;

import android.os.Bundle;
import android.support.annotation.Nullable;

import ezequiel.whatthefridge.Funcionalidades.BuscadorIngredientesBebidas;
import ezequiel.whatthefridge.Funcionalidades.BuscadorRecetasMinibar;
import ezequiel.whatthefridge.Funcionalidades.SaverDummy;

public class StockActivityTemporaryMinibar extends StockActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setSaver(new SaverDummy());
        setBuscadorRecetas(new BuscadorRecetasMinibar());
        setBuscadorIngredientes(new BuscadorIngredientesBebidas());

        super.onCreate(savedInstanceState);
    }

}
