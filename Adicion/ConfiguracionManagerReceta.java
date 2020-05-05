package ezequiel.whatthefridge.Adicion;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import ezequiel.whatthefridge.Funcionalidades.SaverStockElement;
import ezequiel.whatthefridge.Funcionalidades.Storage;
import ezequiel.whatthefridge.Funcionalidades.StorageDatabase;
import ezequiel.whatthefridge.Objetos.Receta;
import ezequiel.whatthefridge.Utils.Constantes;

public class ConfiguracionManagerReceta extends ConfiguracionManager<Receta> {

    public ConfiguracionManagerReceta(FragmentManager manager, int idContainer, Context context) {
        super(manager, idContainer, context);
    }

    @Override
    public void finalizar() {
        List<Configurador<Receta>> list = getConfiguradores();
        guardarEnDatabase(list.get(list.size() - 1).getElement());
    }

    private void guardarEnDatabase(final Receta receta) {
        Receta aux = new Receta(receta.getNombre(), receta.getId());
        aux.setPreparacion(receta.getPreparacion());
        final DatabaseReference ref = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Constantes.FIREBASE_RECETAS_NODE_NAME);

        ref.child(receta.getId()).setValue(aux).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Storage storage = new StorageDatabase(ref.child(receta.getId()).child("ingredientes"));
                SaverStockElement saver = new SaverStockElement(storage);
                saver.save(receta.getIngredientes());
            }
        });

    }
}
