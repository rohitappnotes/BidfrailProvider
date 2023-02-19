package com.bidfrail.provider.data.repository;

import android.content.Context;
import javax.inject.Inject;
import dagger.hilt.android.qualifiers.ApplicationContext;

public class LocalRepository {

    private final Context context;

    @Inject
    public LocalRepository(@ApplicationContext Context context) {
        this.context = context;
    }
}
