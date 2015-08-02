package com.lovejoy777.showcase.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.lovejoy777.showcase.beans.Layer;
import com.lovejoy777.showcase.filters.Filter;

import java.util.ArrayList;

public abstract class AbsFilteredCardViewAdapter extends RecyclerView.Adapter<AbsFilteredCardViewAdapter.AbsViewHolder> {

    protected ArrayList<Layer> layers;
    protected ArrayList<Layer> filteredLayers;
    private ArrayList<Filter> filters = new ArrayList<Filter>();
    protected int rowLayout;
    protected Context mContext;

    public AbsFilteredCardViewAdapter(ArrayList<Layer> layers, int rowLayout, Context context) {
        this.layers = layers;
        this.filteredLayers = new ArrayList<Layer>(layers);
        this.rowLayout = rowLayout;
        this.mContext = context;
    }

    public Layer getItem(int id) {
        return filteredLayers.get(id);
    }

    public void refreshFilteredList() {

        filteredLayers.clear();

        for (Layer layer : layers) {
            if (checkTheme(layer, filters)) {
                filteredLayers.add(layer);
            }
        }

        notifyDataSetChanged();

    }

    private boolean checkTheme(Layer layer, ArrayList<Filter> filters) {
        for (Filter filter : filters) {
            if (!filter.filterTheme(layer)) {
                return false;
            }
        }

        return true;
    }

    public void addFilter(Filter filter) {
        filters.remove(filter);
        filters.add(filter);
        refreshFilteredList();
    }

    public void removeFilter(Filter filter) {
        filters.remove(filter);
        refreshFilteredList();
    }

    @Override
    public int getItemCount() {
        return filteredLayers.size();
    }

    public abstract static class AbsViewHolder extends RecyclerView.ViewHolder {

        public AbsViewHolder(View itemView) {
            super(itemView);
        }

    }


}
