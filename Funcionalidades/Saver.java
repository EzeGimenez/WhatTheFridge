package ezequiel.whatthefridge.Funcionalidades;

import java.util.List;

import ezequiel.whatthefridge.Objetos.StockElement;

public interface Saver {

    void save(List<StockElement> list);

    void setStorage(Storage storage);

    List<StockElement> get();

    String getPlaceToSaveName();
}
