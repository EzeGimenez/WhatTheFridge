package ezequiel.whatthefridge.Funcionalidades;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import ezequiel.whatthefridge.Objetos.StockElement;

/**
 * Clase para el guardado de todos los stock elements
 */
public class SaverStockElement implements Saver {
    private Gson gson;
    private Storage storage;

    public SaverStockElement(Storage p) {
        this.storage = p;
        gson = StockElementGsoner.getGson();
    }

    public void save(List<StockElement> list) {
        for (StockElement s : list) {
            storage.save(s.getID(), gson.toJson(s, StockElement.class));
        }
        storage.save("size", list.size() + "");
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public List<StockElement> get() {
        int size = Integer.parseInt(storage.get("size"));

        final List<StockElement> output = new ArrayList<>();
        String data;

        for (int i = 0; i < size; i++) {
            data = storage.get(i + "");
            output.add(gson.fromJson(data, StockElement.class));
        }

        return output;
    }

    @Override
    public String getPlaceToSaveName() {
        return storage.getName();
    }
}