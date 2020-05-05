package ezequiel.whatthefridge.Adicion;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import ezequiel.whatthefridge.Objetos.IngredientePorPeso;
import ezequiel.whatthefridge.Objetos.IngredientePorUnidad;
import ezequiel.whatthefridge.Objetos.StockElement;
import ezequiel.whatthefridge.R;
import ezequiel.whatthefridge.Utils.Constantes;

public class ConfiguradorIngrediente extends Configurador<StockElement> {

    private RadioGroup radioGroupUnit, radioGroupType;
    private EditText etNombre;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_adicion_ingrediente, container, false);
    }

    @Override
    public void iniciar() {
        View view = getView();
        etNombre = view.findViewById(R.id.etNombre);
        radioGroupUnit = view.findViewById(R.id.radioGroupUnit);
        radioGroupType = view.findViewById(R.id.radioGroup1);
    }

    @Override
    public boolean finalizar() {
        String nombre = etNombre.getText().toString();
        int idSelectionUnit = radioGroupUnit.getCheckedRadioButtonId();
        int idSelectionType = radioGroupType.getCheckedRadioButtonId();

        if (nombre.length() == 0) {
            Toast.makeText(getContext(), getString(R.string.ingresenombrevalido), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (idSelectionUnit == -1) {
            Toast.makeText(getContext(), getString(R.string.seleccioneUnidad), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (idSelectionType == -1) {
            Toast.makeText(getContext(), getString(R.string.seleccionepertenencia), Toast.LENGTH_SHORT).show();
            return false;
        }

        String id = nombre.toLowerCase().trim();
        StockElement i;
        if (idSelectionUnit == R.id.rbCantidad) {
            i = new IngredientePorPeso(nombre, id);
        } else {
            i = new IngredientePorUnidad(nombre, id);
        }

        i.setEsDeCocina(idSelectionType == R.id.rbCocina);

        super.setElement(i);
        return true;
    }

    @Override
    public String id() {
        return Constantes.FRAGMENT_TAG_CONFIGURADOR_INGREDIENTE;
    }
}
