package com.bidfrail.provider.dialogplacepicker;

import android.content.Context;
import android.graphics.Typeface;
import android.text.style.CharacterStyle;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bidfrail.provider.R;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Adapter that handles Autocomplete requests from the Places API.
 */
public class AutoCompletePlacePickerAdapter extends RecyclerView.Adapter<AutoCompletePlacePickerAdapter.PredictionHolder> implements Filterable {

    private static final String TAG = "PlacesAutoAdapter";

    private final CharacterStyle STYLE_BOLD = new StyleSpan(Typeface.BOLD);
    private final CharacterStyle STYLE_NORMAL = new StyleSpan(Typeface.NORMAL);

    private final Context mContext;
    private final AutoCompletePlacePickerListener mAutoCompletePlacePickerListener;

    /**
     * Current results returned by this adapter.
     */
    private ArrayList<PlaceAutocomplete> mResultList = new ArrayList<>();

    /**
     * Handles autocomplete requests.
     */
    private PlacesClient mPlacesClient;

    /**
     * The bounds used for Places Geo Data autocomplete API requests.
     */
    private LatLngBounds mBounds;

    /**
     * The autocomplete filter used to restrict queries to a specific set of place types.
     */
    private ArrayList<TypeFilter> mTypeFilters = new ArrayList<>();

    public AutoCompletePlacePickerAdapter(Context context, AutoCompletePlacePickerListener autoCompletePlacePickerListener) {
        mContext = context;
        mAutoCompletePlacePickerListener = autoCompletePlacePickerListener;
    }

    public void setPlacesClient(PlacesClient placesClient) {
        mPlacesClient = placesClient;
    }

    //https://gist.github.com/graydon/11198540
    public void setBounds(LatLngBounds bounds) {
        mBounds = bounds;
    }

    public void setTypeFilters(ArrayList<TypeFilter> typeFilters) {
        mTypeFilters = typeFilters;
    }

    /**
     * Returns the filter for the current set of autocomplete results.
     */
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                // Skip the autocomplete query if no constraints are given.
                if (constraint != null) {
                    // Query the autocomplete API for the (constraint) search string.
                    mResultList = getPredictions(constraint);
                    if (mResultList != null) {
                        // The API successfully returned results.
                        results.values = mResultList;
                        results.count = mResultList.size();
                    }
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    // The API returned at least one result, update the data.
                    notifyDataSetChanged();
                } else {
                    // The API did not return any results, invalidate the data set.
                    //notifyDataSetInvalidated();
                }
            }
        };
    }

    private ArrayList<PlaceAutocomplete> getPredictions(CharSequence constraint) {

        final ArrayList<PlaceAutocomplete> resultList = new ArrayList<>();

        /**
         * Create a new token for the autocomplete session. Pass this to FindAutocompletePredictionsRequest,
         * and once again when the user makes a selection (for example when calling fetchPlace()).
         */
        AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();

        // Use the builder to create a FindAutocompletePredictionsRequest.
        FindAutocompletePredictionsRequest.Builder builder = FindAutocompletePredictionsRequest.builder();

        if (mBounds != null) {
           /* //Create a RectangularBounds object.
            RectangularBounds bounds = RectangularBounds.newInstance(
                    new LatLng(-33.880490, 151.184363),
                    new LatLng(-33.858754, 151.229596)
            );*/

            RectangularBounds bounds = RectangularBounds.newInstance(mBounds);
            // Call either setLocationBias() OR setLocationRestriction().
            builder.setLocationBias(bounds);
            //builder.setLocationRestriction(bounds);
        }

        builder.setCountry("IN"); //Use only in specific country

        for (int i = 0; i < mTypeFilters.size(); i++) {
            builder.setTypeFilter(mTypeFilters.get(i));
        }

        builder.setSessionToken(token);
        builder.setQuery(constraint.toString());

        FindAutocompletePredictionsRequest request = builder.build();
        Task<FindAutocompletePredictionsResponse> autocompletePredictions = mPlacesClient.findAutocompletePredictions(request);

        /**
         * This method should have been called off the main UI thread. Block and wait for at most 60s
         * for a result from the API.
         */
        try {
            Tasks.await(autocompletePredictions, 60, TimeUnit.SECONDS);
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            e.printStackTrace();
        }

        if (autocompletePredictions.isSuccessful()) {
            FindAutocompletePredictionsResponse findAutocompletePredictionsResponse = autocompletePredictions.getResult();
            if (findAutocompletePredictionsResponse != null)
                for (AutocompletePrediction prediction : findAutocompletePredictionsResponse.getAutocompletePredictions()) {
                    Log.i(TAG, prediction.getPlaceId());
                    resultList.add(new PlaceAutocomplete(prediction.getPlaceId(), prediction.getPrimaryText(STYLE_NORMAL).toString(), prediction.getFullText(STYLE_BOLD).toString()));
                }
            return resultList;
        } else {
            return resultList;
        }
    }

    @NonNull
    @Override
    public PredictionHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = layoutInflater.inflate(R.layout.auto_complete_place_picker_row_item, viewGroup, false);
        return new PredictionHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull PredictionHolder mPredictionHolder, final int i) {
        mPredictionHolder.address.setText(mResultList.get(i).address);
        mPredictionHolder.area.setText(mResultList.get(i).area);
    }

    @Override
    public int getItemCount() {
        return mResultList.size();
    }

    public PlaceAutocomplete getItem(int position) {
        return mResultList.get(position);
    }

    public class PredictionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView address, area;

        PredictionHolder(View itemView) {
            super(itemView);
            area = itemView.findViewById(R.id.areaTextView);
            address = itemView.findViewById(R.id.addressTextView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            PlaceAutocomplete item = mResultList.get(getAdapterPosition());
            String placeId = String.valueOf(item.placeId);

            /**
             * To specify which data types to return, pass an array of Place.Fields in your FetchPlaceRequest
             * Use only those fields which are required.
             */
            List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
            FetchPlaceRequest request = FetchPlaceRequest.builder(placeId, placeFields).build();
            mPlacesClient.fetchPlace(request).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
                @Override
                public void onSuccess(FetchPlaceResponse response) {
                    Place place = response.getPlace();
                    mAutoCompletePlacePickerListener.onPlaceSelected(place);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    if (exception instanceof ApiException) {
                        Toast.makeText(mContext, exception.getMessage() + "", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    /**
     * Holder for Places Geo Data Autocomplete API results.
     */
    public static class PlaceAutocomplete {

        public CharSequence placeId;
        public CharSequence address, area;

        PlaceAutocomplete(CharSequence placeId, CharSequence area, CharSequence address) {
            this.placeId = placeId;
            this.area = area;
            this.address = address;
        }

        @NonNull
        @Override
        public String toString() {
            return area.toString();
        }
    }
}
