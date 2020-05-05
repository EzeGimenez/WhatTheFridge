package ezequiel.whatthefridge;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import ezequiel.whatthefridge.Adicion.ConfiguracionManager;
import ezequiel.whatthefridge.Adicion.ConfiguracionManagerIngrediente;
import ezequiel.whatthefridge.Adicion.ConfiguracionManagerReceta;
import ezequiel.whatthefridge.Adicion.ConfiguradorIngrediente;
import ezequiel.whatthefridge.Adicion.ConfiguradorRecetaIngredientes;
import ezequiel.whatthefridge.Adicion.ConfiguradorRecetaNombre;
import ezequiel.whatthefridge.Adicion.ConfiguradorRecetaProcedimiento;
import ezequiel.whatthefridge.Objetos.Receta;

public class AdicionActivity extends AppCompatActivity implements View.OnClickListener {

    //Componentes gráficas
    private LinearLayout fragmentContainer, containerButtons;
    private FloatingActionButton btnSend;
    private ConfiguracionManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicion);

        Toolbar toolbar = findViewById(R.id.ToolbarAdicion);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        iniciarGUI();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    private void iniciarGUI() {
        fragmentContainer = findViewById(R.id.AdicionFragmentContainer);
        containerButtons = findViewById(R.id.ContainerButtons);
        btnSend = findViewById(R.id.btnSend);

        findViewById(R.id.buttonAnadirIngrediente)
                .setOnClickListener(this);

        findViewById(R.id.buttonAnadirReceta)
                .setOnClickListener(this);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.seguir();
                if (manager.finalizo()) {
                    onBackPressed();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (manager != null) {
            if (!manager.finalizo() && manager.volver()) {
                return;
            }
        }

        if (fragmentContainer.getVisibility() == View.VISIBLE) {
            fragmentContainer.setVisibility(View.GONE);
            btnSend.setVisibility(View.GONE);
            containerButtons.setVisibility(View.VISIBLE);
            manager = null;
        } else {
            finish();
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.buttonAnadirIngrediente:
                manager = new ConfiguracionManagerIngrediente(getSupportFragmentManager(), R.id.AdicionFragmentContainer, this);

                manager.add(new ConfiguradorIngrediente());

                break;
            case R.id.buttonAnadirReceta:
                manager = new ConfiguracionManagerReceta(getSupportFragmentManager(), R.id.AdicionFragmentContainer, this);

                ConfiguradorRecetaNombre c1 = new ConfiguradorRecetaNombre();
                c1.setElement(new Receta(null, null));

                manager.add(c1);
                manager.add(new ConfiguradorRecetaIngredientes());
                manager.add(new ConfiguradorRecetaProcedimiento());

                break;
        }

        fragmentContainer.setVisibility(View.VISIBLE);
        btnSend.setVisibility(View.VISIBLE);
        containerButtons.setVisibility(View.GONE);

        manager.seguir();
    }

}
