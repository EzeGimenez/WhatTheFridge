package ezequiel.whatthefridge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ezequiel.whatthefridge.CustomViews.CustomDialog;
import ezequiel.whatthefridge.Objetos.Receta;
import ezequiel.whatthefridge.Objetos.StockElement;

public class MostrarRecetaActivity extends AppCompatActivity {

    private Receta receta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_receta);

        receta = (Receta) getIntent().getSerializableExtra("Receta");

        if (receta == null) {
            finish();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        RecyclerView recyclerView = findViewById(R.id.rvPasos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        recyclerView.setAdapter(new AdapterPasos(receta.getPreparacion()));

        TextView nombre = findViewById(R.id.tvNombre);
        nombre.setText(receta.getNombre());

        findViewById(R.id.btnIngredients).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarIngredientes();
            }
        });

        findViewById(R.id.btnCookReceip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                terminar(true, receta);
            }
        });
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

    private void mostrarIngredientes() {
        CustomDialog dialog = new CustomDialog(this);
        RecyclerView recyclerView = new RecyclerView(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new AdapterIngredientes(receta.getIngredientes()));

        dialog.setPositiveButton("OK", null)
                .setTitle(getString(R.string.ingredientes))
                .setView(recyclerView);

        dialog.show();
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

    private class AdapterPasos extends RecyclerView.Adapter<AdapterPasos.ViewHolder> {

        private List<String> elements;

        AdapterPasos(List<String> elements) {
            this.elements = elements;
        }

        @NonNull
        @Override
        public AdapterPasos.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new AdapterPasos.ViewHolder(inflater.inflate(R.layout.preparacion_item_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull AdapterPasos.ViewHolder holder, int position) {
            holder.setIsRecyclable(false);
            holder.number.setText("" + (position + 1));
            holder.etStep.setText(elements.get(position));
        }

        @Override
        public int getItemCount() {
            return elements.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private TextView number, etStep;

            public ViewHolder(View itemView) {
                super(itemView);

                number = itemView.findViewById(R.id.tvNumber);
                etStep = itemView.findViewById(R.id.etStep);
            }
        }
    }

    private class AdapterIngredientes extends RecyclerView.Adapter<AdapterIngredientes.ViewHolder> {

        private List<StockElement> elements;

        AdapterIngredientes(List<StockElement> e) {
            this.elements = e;
        }

        @NonNull
        @Override
        public AdapterIngredientes.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new ViewHolder(inflater.inflate(R.layout.ingrediente_receta_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull AdapterIngredientes.ViewHolder holder, int position) {
            StockElement aux = elements.get(position);

            holder.cantidad.setText(aux.cantidadToString());
            holder.nombre.setText(aux.getNombre());
        }

        @Override
        public int getItemCount() {
            return elements.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private TextView nombre, cantidad;

            public ViewHolder(View itemView) {
                super(itemView);

                nombre = itemView.findViewById(R.id.nombre);
                cantidad = itemView.findViewById(R.id.cantidad);
            }
        }
    }
}
