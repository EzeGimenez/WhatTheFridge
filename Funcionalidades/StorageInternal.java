package ezequiel.whatthefridge.Funcionalidades;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Implementación de lugar de almacenamiento, utilizando
 * el almacenamiento interno del dispositivo (SharedPreferences)
 */
public class StorageInternal implements Storage {

    private String name;
    private SharedPreferences sharedPreferences;
    private int contador;

    public StorageInternal(Context context, String name) {
        this.name = name;
        sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        contador = 0;
    }

    @Override
    public void save(String a, String b) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (a.equals("size")) {
            editor.putString("size", b);
        } else {
            editor.putString("Ingrediente" + contador, b);
            contador++;
        }

        editor.apply();
    }

    @Override
    public String get(String a) {
        String output;

        if (a.equals("size")) {
            output = sharedPreferences.getString("size", "0");
        } else {
            output = sharedPreferences.getString("Ingrediente" + a, "");
        }

        return output;
    }

    @Override
    public String getName() {
        return name;
    }
}
