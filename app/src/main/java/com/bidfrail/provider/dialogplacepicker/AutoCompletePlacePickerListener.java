package com.bidfrail.provider.dialogplacepicker;

import androidx.annotation.NonNull;
import com.google.android.libraries.places.api.model.Place;

public interface AutoCompletePlacePickerListener {
    void onPlaceSelected(@NonNull Place place);
}
