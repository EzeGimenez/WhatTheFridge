package ezequiel.whatthefridge.Adicion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import ezequiel.whatthefridge.Objetos.Receta;
import ezequiel.whatthefridge.R;
import ezequiel.whatthefridge.Utils.Constantes;

public class ConfiguradorRecetaNombre extends Configurador<Receta> {
    private EditText etNombre;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_adicion_receta_nombre, container, false);
    }

    @Override
    public boolean finalizar() {
        String nombre = etNombre.getText().toString();

        if (nombre.length() == 0) {
            Toast.makeText(getContext(), getString(R.string.ingresenombrevalido), Toast.LENGTH_SHORT).show();
            return false;
        }

        Receta receta = super.getElement();

        receta.setNombre(nombre);
        receta.setId(nombre.trim().toLowerCase());

        return true;
    }

    @Override
    public void iniciar() {
        View view = getView();
        etNombre = view.findViewById(R.id.etNombre);
    }

    @Override
    public String id() {
        return Constantes.FRAGMENT_TAG_CONFIGURADOR_RECETA_NOMBRE;
    }
}
