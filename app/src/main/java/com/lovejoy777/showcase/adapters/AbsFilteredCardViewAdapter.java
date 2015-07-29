package com.lovejoy777.showcase.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.lovejoy777.showcase.Theme;

import java.util.ArrayList;

public abstract class AbsFilteredCardViewAdapter extends RecyclerView.Adapter<AbsFilteredCardViewAdapter.AbsViewHolder> {

    protected ArrayList<Theme> themes;
    protected ArrayList<Theme> filteredThemes;
    protected int rowLayout;
    protected Context mContext;

    public AbsFilteredCardViewAdapter(ArrayList<Theme> themes, int rowLayout, Context context) {
        this.themes = themes;
        this.filteredThemes = new ArrayList<Theme>(themes);
        this.rowLayout = rowLayout;
        this.mContext = context;
    }

    public Theme getItem(int id) {
        return filteredThemes.get(id);
    }

    public void filter(String filter) {

        filteredThemes.clear();

        if (filter == null || filter.isEmpty() || filter.equals("")) {
            filteredThemes.addAll(themes);
            notifyDataSetChanged();
            return;
        }

        for (Theme theme : themes) {
            if (theme.getTitle().toLowerCase().contains(filter)
                    || theme.getAuthor().toLowerCase().contains(filter)) {
                filteredThemes.add(theme);
            }
        }

        notifyDataSetChanged();

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
