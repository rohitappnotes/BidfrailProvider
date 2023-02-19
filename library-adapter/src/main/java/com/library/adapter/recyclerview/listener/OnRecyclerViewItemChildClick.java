package com.library.adapter.recyclerview.listener;

import android.view.View;

public interface OnRecyclerViewItemChildClick<T> {
    void OnItemChildClick(View viewChild, T t, int position);
}
