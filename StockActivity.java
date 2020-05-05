package ezequiel.whatthefridge;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ezequiel.whatthefridge.Funcionalidades.BuscadorIngredientes;
import ezequiel.whatthefridge.Funcionalidades.BuscadorRecetas;
import ezequiel.whatthefridge.Funcionalidades.IngredientesRetriever;
import ezequiel.whatthefridge.Funcionalidades.Saver;
import ezequiel.whatthefridge.Funcionalidades.StockManager;
import ezequiel.whatthefridge.Objetos.Receta;
import ezequiel.whatthefridge.Objetos.StockElement;

public abstract class StockActivity extends AppCompatActivity implements IngredientesRetriever {

    private StockManager stockManager;
    private BuscadorRecetas buscadorRecetas;
    private BuscadorIngredientes buscadorIngredientes;
    private Saver saver;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        stockManager = new StockManager(this, (ViewGroup) findViewById(R.id.ContainerStockManager));
        getStockElementsToAdd();
        String name = getIntent().getStringExtra("nombre");

        //Toolbar inicializacion
        Toolbar toolbar = findViewById(R.id.ToolbarContainer);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setTitle(name);

        recoverInstance();
    }

    @Override
    public void onBackPressed() {
        if (!stockManager.handleBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveInstance();
    }

    /////////// RECETAS ////////////////////////////////////////////

    protected void iniciarRecetas() {
        Intent intent = new Intent(this, RecetasActivity.class);
        intent.putExtra("Recetas", encontrarRecetas());


        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data.getBooleanExtra("cocino", false)) {
            Receta receta = (Receta) data.getSerializableExtra("Receta");

            List<StockElement> ingUsuario = stockManager.getStockElements();

            for (StockElement e : receta.getIngredientes()) {
                StockElement aux = get(e, ingUsuario);
                if (aux != null) {
                    int cantidad1 = e.getCantidad();
                    int cantidad2 = aux.getCantidad();
                    if (cantidad2 - cantidad1 > 0) {
                        aux.setCantidad(cantidad2 - cantidad1);
                    } else {
                        ingUsuario.remove(aux);
                    }
                }
            }

            stockManager.limpiar();
            for (StockElement e : ingUsuario) {
                stockManager.addStockElement(e);
            }
        }
    }

    private StockElement get(StockElement e, List<StockElement> a) {
        for (StockElement s : a) {
            if (e.equals(s)) {
                return s;
            }
        }
        return null;
    }

    protected ArrayList<Receta> encontrarRecetas() {
        return buscadorRecetas.buscar(stockManager.getStockElements());
    }

    /////////// STOCKELEMENTS TO ADD ///////////////////////////////////

    private void getStockElementsToAdd() {
        buscadorIngredientes.getStockElements(this, 1);
    }

    @Override
    public void receiveElements(List<StockElement> list, int requestCode) {
        stockManager.setStockElementsToAdd(list);
    }

    /////////// TOOLBAR ////////////////////////////////////////////

    /**
     * Listener de los iconos del menu (no contextual)
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.encontrarRecetas:
                iniciarRecetas();
                break;
        }

        return true;
    }

    /**
     * Cuando crea el menu, le seteamos el layout del mismo
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.container_menu, menu);
        Menu menu1 = menu;
        return true;
    }

    /**
     * Obtención del almacenamiento interno de los ingredientes ya ingresados
     */
    protected void recoverInstance() {
        stockManager.limpiar();

        Collection<StockElement> aux = saver.get();
        for (StockElement e : aux) {
            stockManager.addStockElement(e);
        }
    }

    protected void saveInstance() {
        List<StockElement> aux = new ArrayList<>(stockManager.getStockElements());
        saver.save(aux);
    }

    //GETTERS Y SETTERS
    public void setBuscadorRecetas(BuscadorRecetas buscadorRecetas) {
        this.buscadorRecetas = buscadorRecetas;
    }

    public void setSaver(Saver saver) {
        this.saver = saver;
    }

    public void setBuscadorIngredientes(BuscadorIngredientes buscadorIngredientes) {
        this.buscadorIngredientes = buscadorIngredientes;
    }
}
