package com.bidfrail.provider.dialogsingleselect;

import android.widget.Filter;
import java.util.ArrayList;

public class SingleSelectDialogCustomFilter extends Filter {

    public SingleSelectDialogRecyclerViewAdapter adapter;
    public ArrayList<SingleSelect> filterList;

    public SingleSelectDialogCustomFilter(ArrayList<SingleSelect> filterList, SingleSelectDialogRecyclerViewAdapter adapter) {
        this.adapter = adapter;
        this.filterList = filterList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();

        if (constraint != null && constraint.length() > 0) {
            constraint = constraint.toString().toUpperCase();
            ArrayList<SingleSelect> filteredPlayers = new ArrayList<>();

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
        adapter.data = (ArrayList<SingleSelect>) results.values;
        adapter.notifyDataSetChanged();
    }
}
