package ezequiel.whatthefridge.Funcionalidades;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ezequiel.whatthefridge.Objetos.Receta;
import ezequiel.whatthefridge.Objetos.StockElement;
import ezequiel.whatthefridge.Utils.Constantes;
import ezequiel.whatthefridge.Visitors.VisitorContieneIngredientes;

/**
 * Implementacion de BuscadorRecetas
 * <p>
 * Busca recetas de cocina
 *
 * @author ezequiel
 */
public class BuscadorRecetasHeladera implements BuscadorRecetas {
    private List<Receta> recetas;

    public BuscadorRecetasHeladera() {
        recetas = getRecetasFromDatabase();
    }

    @Override
    public ArrayList<Receta> buscar(List<StockElement> source) {
        return new ArrayList<>(filterRecetas(source));
    }

    @Override
    public List<Receta> getRecetasFromDatabase() {
        final List<Receta> output = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(Constantes.FIREBASE_RECETAS_NODE_NAME);
        final SaverStockElement saver = new SaverStockElement(null);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot aux;
                Storage storage;

                //Iterando sobre recetas
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    aux = ds.child("ingredientes");
                    storage = new StorageDatabase(aux);
                    saver.setStorage(storage);

                    String nombre = (String) ds.child("nombre").getValue();
                    String id = (String) ds.child("id").getValue();

                    Receta receta = new Receta(nombre, id);
                    receta.setPreparacion((List<String>) ds.child("preparacion").getValue());
                    receta.setIngredientes(saver.get());
                    output.add(receta);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return output;
    }

    @Override
    public List<Receta> filterRecetas(List<StockElement> elements) {
        VisitorContieneIngredientes visitor = new VisitorContieneIngredientes(elements);
        for (Receta r : recetas) {
            r.aceptar(visitor);
        }

        return visitor.getOutput();
    }
}
