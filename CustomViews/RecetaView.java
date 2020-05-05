package ezequiel.whatthefridge.CustomViews;

import android.view.View;
import android.widget.TextView;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.flexibleadapter.items.IFilterable;
import eu.davidea.flexibleadapter.items.IFlexible;
import eu.davidea.viewholders.FlexibleViewHolder;
import ezequiel.whatthefridge.Objetos.Receta;
import ezequiel.whatthefridge.R;

/**
 * Clase utilizada para representar a una receta en una lista
 *
 * @author ezequiel
 */
public class RecetaView extends AbstractFlexibleItem<RecetaView.ViewHolder> implements IFilterable<String> {
    private Receta receta;

    public RecetaView(Receta e) {
        this.receta = e;
    }

    @Override
    public boolean equals(Object o) {
        return receta.equals(o);
    }

    public Receta getReceta() {
        return receta;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.recetas_item;
    }

    @Override
    public RecetaView.ViewHolder createViewHolder(View view, FlexibleAdapter<IFlexible> adapter) {
        return new RecetaView.ViewHolder(view, adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter<IFlexible> adapter, ViewHolder holder, int position, List<Object> payloads) {
        holder.tvRecetaNombre.setText(receta.getNombre());
    }

    @Override
    public boolean filter(String constraint) {
        return receta.getNombre().toLowerCase().trim().contains(constraint.trim().toLowerCase());
    }

    public class ViewHolder extends FlexibleViewHolder {
        TextView tvRecetaNombre;

        public ViewHolder(View itemView, FlexibleAdapter adapter) {
            super(itemView, adapter);

            tvRecetaNombre = itemView.findViewById(R.id.tvRecetaNombre);
        }
    }
}