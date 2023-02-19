package com.bidfrail.provider.dialogmultiselect;

public interface MultiSelectListener {
    void onMultiSelected(int position, MultiSelect multiSelect, String commaSeparatedIds, String commaSeparatedNames);
}
