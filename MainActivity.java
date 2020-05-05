package ezequiel.whatthefridge;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import ezequiel.whatthefridge.CustomViews.CustomButton;
import ezequiel.whatthefridge.CustomViews.CustomDialog;

public class MainActivity extends AppCompatActivity {

    private CustomButton btnMiHeladera, btnMiHeladeraAhora, btnMiFrigobar, btnMiFrigobarAhora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Asignacion de variables gráficas
        btnMiHeladera = findViewById(R.id.btnMiHeladera);
        btnMiHeladeraAhora = findViewById(R.id.btnHeladeraAhora);
        btnMiFrigobar = findViewById(R.id.btnMiBar);
        btnMiFrigobarAhora = findViewById(R.id.btnBarAhora);

        setUpGUI();
    }

    /**
     * Inicializa la Interfaz gráfica. Setea listeners y demás
     */
    private void setUpGUI() {
        Intent miHeladera = new Intent(this, StockActivityPersistentHeladera.class);
        Intent miHeladeraAhora = new Intent(this, StockActivityTemporaryHeladera.class);
        Intent miFrigobar = new Intent(this, StockActivityPersistentMinibar.class);
        Intent miFribobarAhora = new Intent(this, StockActivityTemporaryMinibar.class);

        miHeladera.putExtra("nombre", getString(R.string.mi_heladera));
        miFrigobar.putExtra("nombre", getString(R.string.mi_frigobar));
        miHeladeraAhora.putExtra("nombre", getString(R.string.mi_heladera_ahora));
        miFribobarAhora.putExtra("nombre", getString(R.string.mi_frigobar_ahora));

        btnMiHeladera.setIntent(miHeladera);
        btnMiHeladeraAhora.setIntent(miHeladeraAhora);
        btnMiFrigobar.setIntent(miFrigobar);
        btnMiFrigobarAhora.setIntent(miFribobarAhora);

        Toolbar toolbar = findViewById(R.id.ToolbarMain);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.sugerirAdicion:
                Intent intent = new Intent(this, AdicionActivity.class);
                startActivity(intent);
                break;
            case R.id.about:
                CustomDialog dialog = new CustomDialog(this);
                dialog.setView(getLayoutInflater().inflate(R.layout.about, null));
                dialog.show();
                break;
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}
