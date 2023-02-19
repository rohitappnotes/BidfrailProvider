package com.bidfrail.provider.dialogmultiselect;

import android.widget.Filter;
import java.util.ArrayList;

public class MultiSelectDialogCustomFilter extends Filter {

    public MultiSelectDialogRecyclerViewAdapter adapter;
    public ArrayList<MultiSelect> filterList;

    public MultiSelectDialogCustomFilter(ArrayList<MultiSelect> filterList, MultiSelectDialogRecyclerViewAdapter adapter) {
        this.adapter = adapter;
        this.filterList = filterList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();

        if (constraint != null && constraint.length() > 0) {
            constraint = constraint.toString().toUpperCase();
            ArrayList<MultiSelect> filteredPlayers = new ArrayList<>();

            for (int i = 0; i < filterList.size(); i++) {
                if (filterList.get(i).getName().toUpperCase().contains(constraint) || filterList.get(i).getName().toUpperCase().contains(constraint)) {
                    filteredPlayers.add(filterList.get(i));
                }
            }

            results.count = filteredPlayers.size();
            results.values = filteredPlayers;
        } else {
            results.count = filterList.size();
            results.values = filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.data = (ArrayList<MultiSelect>) results.values;
        adapter.notifyDataSetChanged();
    }
}
