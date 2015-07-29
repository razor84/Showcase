package com.lovejoy777.showcase.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.lovejoy777.showcase.Theme;
import com.lovejoy777.showcase.filters.Filter;

import java.util.ArrayList;
import java.util.HashSet;

public abstract class AbsFilteredCardViewAdapter extends RecyclerView.Adapter<AbsFilteredCardViewAdapter.AbsViewHolder> {

    protected ArrayList<Theme> themes;
    protected ArrayList<Theme> filteredThemes;
    protected int rowLayout;
    protected Context mContext;
    private ArrayList<Filter> filters = new ArrayList<Filter>();

    public AbsFilteredCardViewAdapter(ArrayList<Theme> themes, int rowLayout, Context context) {
        this.themes = themes;
        this.filteredThemes = new ArrayList<Theme>(themes);
        this.rowLayout = rowLayout;
        this.mContext = context;
    }

    public Theme getItem(int id) {
        return filteredThemes.get(id);
    }

    public void refreshFilteredList() {

        filteredThemes.clear();

        for (Theme theme : themes) {
            if (checkTheme(theme)) {
                filteredThemes.add(theme);
            }
        }


        notifyDataSetChanged();

    }

    private boolean checkTheme(Theme theme) {
        for (Filter filter : filters) {
            if (!filter.filterTheme(theme)) {
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

    @Override
    public int getItemCount() {
        return filteredThemes.size();
    }

    public abstract static class AbsViewHolder extends RecyclerView.ViewHolder {

        public AbsViewHolder(View itemView) {
            super(itemView);
        }
    }


}
