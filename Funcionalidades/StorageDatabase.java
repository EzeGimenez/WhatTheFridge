package ezequiel.whatthefridge.Funcionalidades;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

/**
 * Implementación de lugar de almacenamiento, tomando como el mismo
 * una database
 */
public class StorageDatabase implements Storage {

    private DatabaseReference ref;
    private DataSnapshot dataSnapshot;

    public StorageDatabase(DatabaseReference ref) {
        this.ref = ref;
    }

    public StorageDatabase(DataSnapshot ds) {
        this.dataSnapshot = ds;
    }

    @Override
    public void save(final String a, String b) {

        if (a.equals("size")) {
            ref.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    long size = dataSnapshot.getChildrenCount();
                    if (dataSnapshot.hasChild("~size")) {
                        size--;
                    }
                    ref.child("~size").setValue(size + "");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            });
        } else {
            ref.child(a).setValue(b);
        }

    }

    @Override
    public String get(String a) {
        if (a.equals("size")) {
            return (String) dataSnapshot.child("~size").getValue();
        } else {
            Iterator<DataSnapshot> it = dataSnapshot.getChildren().iterator();

            int counter = 0, max = Integer.parseInt(a);
            DataSnapshot index = null;
            while (counter <= max) {
                index = it.next();
                counter++;
            }

            return index == null ? null : (String) index.getValue();
        }
    }

    @Override
    public String getName() {
        return "database";
    }
}
