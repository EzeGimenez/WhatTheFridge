package ezequiel.whatthefridge.Funcionalidades;

import java.util.ArrayList;
import java.util.List;

import ezequiel.whatthefridge.Objetos.StockElement;

public class SaverDummy implements Saver {

    public SaverDummy() {
    }

    @Override
    public void save(List<StockElement> list) {

    }

    @Override
    public void setStorage(Storage storage) {

    }

    @Override
    public List<StockElement> get() {
        return new ArrayList<>();
    }

    @Override
    public String getPlaceToSaveName() {
        return null;
    }
}
