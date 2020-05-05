package ezequiel.whatthefridge.Funcionalidades;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.SelectableAdapter;
import ezequiel.whatthefridge.CustomViews.CustomDialog;
import ezequiel.whatthefridge.CustomViews.StockElementViewAdded;
import ezequiel.whatthefridge.CustomViews.StockElementViewNotAdded;
import ezequiel.whatthefridge.Objetos.StockElement;
import ezequiel.whatthefridge.R;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Clase contenedor, posicionando StockElements en forma de stock
 * con una funcionalidad de seleccion, edicion y remocion
 */
public class StockManager implements FlexibleAdapter.OnItemClickListener, FlexibleAdapter.OnItemLongClickListener {

    private Menu cabMenu;
    private ActionMode actionMode;
    private FlexibleAdapter<StockElementViewAdded> stockAdapter;
    private FlexibleAdapter<StockElementViewNotAdded> addIngredientAdapter;
    private RecyclerView slidingPanelRecyclerView;
    private SearchView searchView;
    private SlidingUpPanelLayout slidingContainer;
    private View btnAddStockElement;
    private Activity act;
    private List<StockElementViewNotAdded> stockElementsToAdd;

    private ViewGroup container;
    private StockElement currentAdding;

    /////////// ACTIVITY ////////////////////////////////////////////

    public StockManager(Activity act, ViewGroup container) {
        this.act = act;
        this.container = container;

        setUp();
        slidingContainer.setDragView(container.findViewById(R.id.btnAddIngrediente));
    }

    public StockManager(Activity act, ViewGroup container, View btnToAdd) {
        this(act, container);

        slidingContainer.setDragView(btnToAdd);
        container.findViewById(R.id.btnAddIngrediente).setVisibility(GONE);
    }

    /**
     * Inicializa la GUI
     */
    private void setUp() {
        LayoutInflater inflater = act.getLayoutInflater();
        View view = inflater.inflate(R.layout.stock_manager, null);
        container.addView(view);

        slidingContainer = act.findViewById(R.id.slidingContainer);
        slidingPanelRecyclerView = act.findViewById(R.id.slidingPanel);
        searchView = act.findViewById(R.id.searchViewSlidingPanel);
        RecyclerView recyclerView = act.findViewById(R.id.recyclerView);
        btnAddStockElement = act.findViewById(R.id.btnAddIngrediente);

        stockAdapter = new FlexibleAdapter<>(null);

        recyclerView.setLayoutManager(new GridLayoutManager(act, 3));
        slidingPanelRecyclerView.setLayoutManager(new GridLayoutManager(act, 3));

        recyclerView.setAdapter(stockAdapter);
        stockAdapter.addListener(this);

        slidingContainer.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slidingContainer.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });

        actionMode = null;
    }

    /**
     * Maneja el back pressed, si no se pudo manejar la accion ,devuelve falso
     *
     * @return true si pudo manejar la accion, false en caso contrario
     */
    public boolean handleBackPressed() {
        SlidingUpPanelLayout.PanelState panelState = slidingContainer.getPanelState();
        if (panelState != SlidingUpPanelLayout.PanelState.COLLAPSED) {
            slidingContainer.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            return true;
        } else {
            return false;
        }
    }

    /////////// AGREGAR STOCKELEMENT ////////////////////////////////////////////

    private void populateSlidingPanel() {
        if (addIngredientAdapter == null) {
            addIngredientAdapter = new FlexibleAdapter<>(null);
            slidingPanelRecyclerView.setAdapter(addIngredientAdapter);
            addIngredientAdapter.addItems(0, stockElementsToAdd);

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    addIngredientAdapter.setFilter(newText);
                    addIngredientAdapter.filterItems();

                    return true;
                }
            });

            addIngredientAdapter.addListener(new FlexibleAdapter.OnItemClickListener() {
                @Override
                public boolean onItemClick(View view, int position) {
                    StockElement i = addIngredientAdapter.getItem(position).getElement();
                    mostrarDialogoCantidad(i);
                    return true;
                }
            });
        }
    }

    private void mostrarDialogoCantidad(final StockElement i) {
        CustomDialog dialog = new CustomDialog(act);

        View view = act.getLayoutInflater().inflate(R.layout.alertdialog_select_cant, null);

        final Button btnMenos = view.findViewById(R.id.btnMenos);
        final Button btnMas = view.findViewById(R.id.btnMas);
        final TextView textViewAlertDialog = view.findViewById(R.id.etCant);

        dialog.setTitle(act.getString(R.string.select_amount));
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!currentAdding.estaVacio()) {
                    textViewAlertDialog.setText("0");
                    addStockElement(currentAdding);
                }
            }
        });

        btnMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnMenos.setEnabled(true);
                if (!currentAdding.estaLleno()) {
                    currentAdding.aumentarCantidad();
                    textViewAlertDialog.setText(currentAdding.cantidadToString());
                } else {
                    btnMas.setEnabled(false);
                }
            }
        });

        btnMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnMas.setEnabled(true);
                if (!currentAdding.estaVacio()) {
                    currentAdding.decrementarCantidad();
                    textViewAlertDialog.setText(currentAdding.cantidadToString());
                } else {
                    btnMenos.setEnabled(false);
                }
            }
        });

        dialog.setView(view);
        CustomDialog alertDialogCantidad = dialog;
        this.currentAdding = i;
        textViewAlertDialog.setText(currentAdding.cantidadToString());
        alertDialogCantidad.show();
    }

    public void setStockElementsToAdd(Collection<StockElement> c) {
        stockElementsToAdd = new ArrayList<>();
        for (StockElement v : c) {
            stockElementsToAdd.add(new StockElementViewNotAdded(v));
        }

        populateSlidingPanel();
    }

    /////////// MODO SELECCIÓN ////////////////////////////////////////////

    private void mostrarInformacionStockElement(StockElement v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(act);
        View view = act.getLayoutInflater().inflate(R.layout.alertdialog_detail_stockelement, null);

        TextView tvNombre, cant;
        tvNombre = view.findViewById(R.id.tvNombre);
        cant = view.findViewById(R.id.tvCant);

        tvNombre.setText(v.getNombre());
        cant.setText(v.getCantidad() + "");

        builder.setView(view);
        builder.setPositiveButton("OK", null);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    @Override
    public boolean onItemClick(View view, int position) {
        if (actionMode != null && position != RecyclerView.NO_POSITION) {
            toggleSelection(position);
            return true;
        } else {
            mostrarInformacionStockElement(stockAdapter.getItem(position).getElement());
            return false;
        }
    }

    @Override
    public void onItemLongClick(int position) {
        if (actionMode == null) {
            actionMode = ((AppCompatActivity) act).startSupportActionMode(new ActionModeCallback());
        }
        toggleSelection(position);
    }

    /**
     * Editar cantidad que se posee de un StockElement
     */
    private void editarStockElement() {
        int selected = stockAdapter.getSelectedPositions().get(0);
        final StockElement i = stockAdapter.getItem(selected).getElement();

        mostrarDialogoCantidad(i);
    }

    /**
     * alterna la seleccion de un objeto
     *
     * @param pos posicion del objeto a alternar su seleccion, -1 si se selecciona/deselecciona
     */
    private void toggleSelection(int pos) {
        stockAdapter.toggleSelection(pos);
        int count = stockAdapter.getSelectedItemCount();
        if (pos != -1) {

            if (count == 0) {
                exitSelectMode();
            } else if (count != 1) {
                cabMenu.findItem(R.id.edit).setVisible(false);
            } else {
                cabMenu.findItem(R.id.edit).setVisible(true);
            }
        } else {

            if (count < stockAdapter.getItemCount()) {
                stockAdapter.selectAll();
                cabMenu.findItem(R.id.edit).setVisible(false);
            } else {
                stockAdapter.clearSelection();
            }

        }
        setContextTitle(stockAdapter.getSelectedItemCount());
    }

    private void setContextTitle(int count) {
        if (actionMode != null) {
            actionMode.setTitle(count + " " + (count == 1 ?
                    act.getString(R.string.count_selected_single) :
                    act.getString(R.string.count_selected_multiple)));
        }
    }

    private void eliminarViewsSeleccionados() {
        stockAdapter.removeAllSelectedItems();
    }

    public void exitSelectMode() {
        if (actionMode != null) {
            actionMode.finish();
            btnAddStockElement.setVisibility(VISIBLE);
        }
    }

    public void limpiar() {
        stockAdapter.clear();
    }

    /////////// EDICION STOCKELEMENT ////////////////////////////////////////////

    /**
     * Ingresa un nuevo StockElement en el adapter. En caso de encontrar un viewable
     * igual, lo colisiona con el encontrado.
     *
     * @param i StockElement a agregar
     */
    public void addStockElement(StockElement i) {
        Iterator<StockElementViewAdded> it = stockAdapter.getCurrentItems().iterator();
        StockElementViewAdded index = null;
        boolean contains = false;
        while (!contains && it.hasNext()) {
            index = it.next();
            if (index.getElement().equals(i)) {
                contains = true;
            }
        }
        if (!contains) {
            stockAdapter.addItem(new StockElementViewAdded(i));
        } else {
            index.getElement().colide(i);
            stockAdapter.notifyDataSetChanged();
        }
    }

    public ArrayList<StockElement> getStockElements() {
        ArrayList<StockElement> aux = new ArrayList<>();
        for (StockElementViewAdded v : stockAdapter.getCurrentItems()) {
            aux.add(v.getElement());
        }
        return aux;
    }

    private class ActionModeCallback implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.selection_menu, menu);
            stockAdapter.setMode(SelectableAdapter.Mode.MULTI);
            cabMenu = menu;
            btnAddStockElement.setVisibility(GONE);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return true;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    exitSelectMode();
                    break;
                case R.id.bin:
                    eliminarViewsSeleccionados();
                    exitSelectMode();
                    break;
                case R.id.edit:
                    editarStockElement();
                    break;
                case R.id.selectAll:
                    toggleSelection(-1);
                    break;
                default:
                    return false;
            }
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
            btnAddStockElement.setVisibility(VISIBLE);
            stockAdapter.clearSelection();
            stockAdapter.setMode(SelectableAdapter.Mode.IDLE);
        }
    }
}
