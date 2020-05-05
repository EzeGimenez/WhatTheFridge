package ezequiel.whatthefridge.Adicion;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ezequiel.whatthefridge.Objetos.Receta;
import ezequiel.whatthefridge.R;
import ezequiel.whatthefridge.Utils.Constantes;

public class ConfiguradorRecetaProcedimiento extends Configurador<Receta> {

    private RecyclerView rvProcedimiento;
    private Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_adicion_receta_procedimiento, container, false);
    }

    @Override
    public boolean finalizar() {
        List<String> steps = adapter.getSteps();
        if (steps.size() == 0) {
            Toast.makeText(getContext(), getString(R.string.ingreseprocedimiento), Toast.LENGTH_SHORT).show();
            return false;
        }

        Receta receta = super.getElement();

        receta.setPreparacion(steps);
        return true;
    }

    @Override
    public void iniciar() {
        View view = getView();

        rvProcedimiento = view.findViewById(R.id.rvProcedimiento);
        rvProcedimiento.setHasFixedSize(true);
        rvProcedimiento.setLayoutManager(new LayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        adapter = new Adapter();
        rvProcedimiento.setAdapter(adapter);

        view.findViewById(R.id.btnAddStep).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.addStep();
                rvProcedimiento.smoothScrollToPosition(adapter.getItemCount() - 1);
            }
        });
    }

    @Override
    public String id() {
        return Constantes.FRAGMENT_TAG_CONFIGURADOR_RECETA_PROCEDIMIENTO;
    }

    private class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

        private List<String> elements;
        private SparseArray<ViewHolder> holders;

        public Adapter() {
            this.elements = new ArrayList<>();
            this.holders = new SparseArray<>();

            addStep();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new ViewHolder(inflater.inflate(R.layout.procedure_item_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.setIsRecyclable(false);
            holders.put(position, holder);
            holder.number.setText("" + (position + 1));
            if (position < elements.size() - 1) {
                holder.etStep.setText(elements.get(position));
            } else {
                holder.etStep.requestFocus();
                holder.etStep.setText("");
                holder.etStep.setHint(getString(R.string.addStep));
            }
        }

        public void addStep() {
            if (elements.isEmpty()) {
                elements.add("");
            } else {
                String paso = holders.get(elements.size() - 1).etStep.getText().toString();
                if (!paso.isEmpty()) {
                    elements.set(elements.size() - 1, paso);
                    elements.add("");
                }
            }
            notifyItemInserted(elements.size() - 1);
        }

        public List<String> getSteps() {
            List<String> output = new ArrayList<>();
            addStep();

            for (String s : elements) {
                if (!s.isEmpty()) {
                    output.add(s);
                }
            }


            return output;
        }

        @Override
        public int getItemCount() {
            return elements.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private TextView number;
            private EditText etStep;

            public ViewHolder(View itemView) {
                super(itemView);

                number = itemView.findViewById(R.id.tvNumber);
                etStep = itemView.findViewById(R.id.etStep);
            }
        }
    }

    public class LayoutManager extends LinearLayoutManager {

        public LayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            try {
                super.onLayoutChildren(recycler, state);
            } catch (IndexOutOfBoundsException e) {

            }
        }
    }
}
