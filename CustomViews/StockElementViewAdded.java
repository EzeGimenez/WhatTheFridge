package ezequiel.whatthefridge.CustomViews;

import android.view.View;
import android.widget.TextView;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.flexibleadapter.items.IFlexible;
import eu.davidea.viewholders.FlexibleViewHolder;
import ezequiel.whatthefridge.Objetos.StockElement;
import ezequiel.whatthefridge.R;

/**
 * Clase para representar un StockElement en una lista
 */
public class StockElementViewAdded extends AbstractFlexibleItem<StockElementViewAdded.ViewHolder> {
    private StockElement element;

    public StockElementViewAdded(StockElement e) {
        element = e;
    }

    public StockElement getElement() {
        return element;
    }

    @Override
    public boolean equals(Object o) {
        return element.equals(o);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.added_stockelement_view_layout;
    }

    @Override
    public StockElementViewAdded.ViewHolder createViewHolder(View view, FlexibleAdapter<IFlexible> adapter) {
        return new ViewHolder(view, adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter<IFlexible> adapter, StockElementViewAdded.ViewHolder holder, int position, List<Object> payloads) {
        holder.title.setText(element.getNombre());
        holder.description.setText(element.cantidadToString());
    }

    public class ViewHolder extends FlexibleViewHolder {
        TextView title, description;

        public ViewHolder(View itemView, FlexibleAdapter adapter) {
            super(itemView, adapter);

            title = itemView.findViewById(R.id.tvTitle);
            description = itemView.findViewById(R.id.tvDescription);
        }
    }
}
