package ezequiel.whatthefridge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import ezequiel.whatthefridge.CustomViews.RecetaView;
import ezequiel.whatthefridge.Objetos.Receta;

public class RecetasActivity extends AppCompatActivity {

    private FlexibleAdapter adapterRecetas;
    private List<RecetaView> recetaViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recetas);

        setUpGUI();
    }

    private void setUpGUI() {
        Toolbar toolbar = findViewById(R.id.ToolbarRecetas);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewRecetas);
        List<Receta> listaRecetas = (ArrayList<Receta>) getIntent().getSerializableExtra("Recetas");
        recetaViews = new ArrayList<>();
        SearchView searchView = findViewById(R.id.searchViewRecetas);

        toolbar.requestFocus();

        if (!listaRecetas.isEmpty()) {
            for (Receta r : listaRecetas) {
                recetaViews.add(new RecetaView(r));
            }

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    adapterRecetas.setFilter(newText);
                    adapterRecetas.filterItems();
                    return true;
                }
            });

            adapterRecetas = new FlexibleAdapter<>(recetaViews);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            recyclerView.setAdapter(adapterRecetas);
            adapterRecetas.addListener(new FlexibleAdapter.OnItemClickListener() {
                @Override
                public boolean onItemClick(View view, int position) {
                    Intent intent = new Intent(getApplication(), MostrarRecetaActivity.class);
                    intent.putExtra("Receta", recetaViews.get(position).getReceta());
                    intent.putExtra("placeToSave", getIntent().getStringExtra("placeToSave"));
                    startActivityForResult(intent, 1);
                    return true;
                }
            });

        } else {
            recyclerView.setVisibility(View.GONE);
            findViewById(R.id.noseencontro).setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data.getBooleanExtra("cocino", false)) {
            terminar(true, (Receta) data.getSerializableExtra("Receta"));
        }
    }

    @Override
    public void onBackPressed() {
        terminar(false, null);
    }

    private void terminar(boolean cocino, Receta r) {
        Intent intent = new Intent();
        intent.putExtra("cocino", cocino);
        intent.putExtra("Receta", r);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recetas_menu, menu);
        return true;
    }

    private void finalizarActividad() {
        onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finalizarActividad();
                break;
        }

        return true;
    }
}
