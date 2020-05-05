package ezequiel.whatthefridge.Funcionalidades;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ezequiel.whatthefridge.Utils.Constantes;

public class BuscadorIngredientesBebidas implements BuscadorIngredientes {

    @Override
    public void getStockElements(final IngredientesRetriever s, final int requestCode) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(Constantes.FIREBASE_INGREDIENTES_BEBIDA_NODE_NAME);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Storage database = new StorageDatabase(dataSnapshot);
                SaverStockElement saver = new SaverStockElement(database);
                s.receiveElements(saver.get(), requestCode);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
