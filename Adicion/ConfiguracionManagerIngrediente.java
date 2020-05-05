package ezequiel.whatthefridge.Adicion;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import ezequiel.whatthefridge.Funcionalidades.SaverStockElement;
import ezequiel.whatthefridge.Funcionalidades.Storage;
import ezequiel.whatthefridge.Funcionalidades.StorageDatabase;
import ezequiel.whatthefridge.Objetos.StockElement;
import ezequiel.whatthefridge.Utils.Constantes;

public class ConfiguracionManagerIngrediente extends ConfiguracionManager<StockElement> {

    public ConfiguracionManagerIngrediente(FragmentManager manager, int idContainer, Context context) {
        super(manager, idContainer, context);
    }

    @Override
    public void finalizar() {
        List<Configurador<StockElement>> list = getConfiguradores();
        guardarEnDatabase(list.get(list.size() - 1).getElement());
    }

    private void guardarEnDatabase(StockElement i) {
        DatabaseReference ref;
        if (i.isEsDeCocina()) {
            ref = FirebaseDatabase
                    .getInstance()
                    .getReference()
                    .child(Constantes.FIREBASE_INGREDIENTES_COCINA_NODE_NAME);
        } else {
            ref = FirebaseDatabase
                    .getInstance()
                    .getReference()
                    .child(Constantes.FIREBASE_INGREDIENTES_BEBIDA_NODE_NAME);
        }

        List<StockElement> list = new ArrayList<>();
        list.add(i);

        Storage database = new StorageDatabase(ref);
        SaverStockElement saver = new SaverStockElement(database);
        saver.save(list);
    }
}
