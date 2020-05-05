package ezequiel.whatthefridge.CustomViews;

import android.view.View;
import android.widget.TextView;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.flexibleadapter.items.IFilterable;
import eu.davidea.flexibleadapter.items.IFlexible;
import eu.davidea.viewholders.FlexibleViewHolder;
import ezequiel.whatthefridge.Objetos.StockElement;
import ezequiel.whatthefridge.R;

/**
 * Clase para representar una StockElement en una lista de adición
 */
public class StockElementViewNotAdded extends AbstractFlexibleItem<StockElementViewNotAdded.ViewHolder> implements IFilterable<String> {
    private StockElement element;

    public StockElementViewNotAdded(StockElement e) {
        element = e;
    }

    @Override
    public boolean equals(Object o) {
        return element.equals(o);
    }

    public StockElement getElement() {
        return element;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.toadd_stockelement_view_layout;
    }

    @Override
    public StockElementViewNotAdded.ViewHolder createViewHolder(View view, FlexibleAdapter<IFlexible> adapter) {
        return new ViewHolder(view, adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter<IFlexible> adapter, StockElementViewNotAdded.ViewHolder holder, int position, List<Object> payloads) {
        holder.att1.setText(element.getNombre() + "");
    }

    @Override
    public boolean filter(String constraint) {
        return element.getNombre().toLowerCase().trim().contains(constraint.toLowerCase().trim());
    }

    public class ViewHolder extends FlexibleViewHolder {
        TextView att1;

        public ViewHolder(View itemView, FlexibleAdapter adapter) {
            super(itemView, adapter);

            att1 = itemView.findViewById(R.id.tvTitle);
        }
    }
}