package com.bidfrail.provider.ui.afterloginregister.fragment.leads.adapter;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bidfrail.provider.AppConstants;
import com.bidfrail.provider.CircularTextView;
import com.bidfrail.provider.R;
import com.bidfrail.provider.data.local.sharedpreferences.SharedPreferencesHelper;
import com.bidfrail.provider.data.remote.ApiConfiguration;
import com.bidfrail.provider.data.remote.glide.GlideImageLoader;
import com.bidfrail.provider.data.remote.glide.GlideImageLoadingListener;
import com.bidfrail.provider.model.Lead;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.library.adapter.recyclerview.adapter.BaseSingleItemAdapter;
import com.library.adapter.recyclerview.adapter.BaseViewHolder;
import com.library.labelview.LabelView;
import com.library.utilities.ResourcesUtils;
import com.library.utilities.ViewUtils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Map;
import java.util.Objects;

public class LeadsAdapter extends BaseSingleItemAdapter<Lead, BaseViewHolder> {

    private final Context context;
    private SharedPreferencesHelper sharedPreferencesHelper;

    int unreadCount = 0;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference senderReference, receiverReference;
    public String senderId;
    public String receiverId;

    public LeadsAdapter(Context context, SharedPreferencesHelper sharedPreferencesHelper) {
        this.context = context;
        this.sharedPreferencesHelper = sharedPreferencesHelper;
        addChildClickViewIds(R.id.leadRowItem);
        addChildClickViewIds(R.id.messageMaterialButton);
        addChildClickViewIds(R.id.callMaterialButton);
        addChildClickViewIds(R.id.getDirectionMaterialButton);
        addChildClickViewIds(R.id.bidFroThisJobOrAcceptMaterialButton);
        addChildClickViewIds(R.id.rejectMaterialButton);
        addChildClickViewIds(R.id.startAndRaiseBillMaterialButton);
    }

    @Override
    protected int getViewHolderLayoutResId() {
        return R.layout.lead_row_items;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, Lead lead, int position) {
        LabelView labelView = viewHolder.findView(R.id.labelView);

        if (Objects.equals(lead.getOrderType(), "get_bids")) {
            labelView.setText("Get Bids");
            labelView.setBgColor(ResourcesUtils.getColor(context, R.color.tag_green));
        } else if (Objects.equals(lead.getOrderType(), "book_now")) {
            labelView.setText("Book Now");
            labelView.setBgColor(ResourcesUtils.getColor(context, R.color.tag_red));
        }

        FrameLayout subCategoryImageViewFrameLayout = viewHolder.findView(R.id.subCategoryImageViewFrameLayout);
        ImageView subCategoryImageView = viewHolder.findView(R.id.subCategoryImageView);
        ProgressBar imageLoadingProgressBar = viewHolder.findView(R.id.imageLoadingProgressBar);
        TextView orderNumberHeadingTextView = viewHolder.findView(R.id.orderNumberHeadingTextView);
        TextView orderNumberTextView = viewHolder.findView(R.id.orderNumberTextView);
        TextView orderSubCategoryTextView = viewHolder.findView(R.id.orderSubCategoryTextView);
        TextView statusTextView = viewHolder.findView(R.id.statusTextView);

        GlideImageLoader.load(
                context,
                ApiConfiguration.IMAGE_URL + lead.getSubCategory().getGif(),
                subCategoryImageView,
                new GlideImageLoadingListener() {
                    @Override
                    public void imageLoadSuccess() {
                        imageLoadingProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void imageLoadError() {
                        imageLoadingProgressBar.setVisibility(View.GONE);
                    }
                });

        orderNumberTextView.setText(lead.getOrderNumber());
        orderSubCategoryTextView.setText(lead.getSubCategory().getName());

        if (Objects.equals(lead.getUserStatus(), "confirmed")) {
            statusTextView.setVisibility(View.VISIBLE);
            statusTextView.setText("Accepted");
            statusTextView.setTextColor(ResourcesUtils.getColor(context, R.color.tag_green));
            ViewUtils.setBackgroundDrawable(statusTextView, ResourcesUtils.getDrawable(context, R.drawable.tag_green));
        } else if (Objects.equals(lead.getUserStatus(), "started")) {
            statusTextView.setVisibility(View.VISIBLE);
            statusTextView.setText("Started");
            statusTextView.setTextColor(ResourcesUtils.getColor(context, R.color.tag_green));
            ViewUtils.setBackgroundDrawable(statusTextView, ResourcesUtils.getDrawable(context, R.drawable.tag_green));
        } else {
            statusTextView.setVisibility(View.GONE);
        }

        ImageView scheduleImageView = viewHolder.findView(R.id.scheduleImageView);
        TextView dateTimeTextView = viewHolder.findView(R.id.dateTimeTextView);
        TextView scheduleHeadingTextView = viewHolder.findView(R.id.scheduleHeadingTextView);
        TextView timerTextView = viewHolder.findView(R.id.timerTextView);

        if (Objects.equals(lead.getOrderType(), "get_bids")) {
            dateTimeTextView.setText(lead.getSlotDate() + ", " + lead.getSlotTime());
        } else if (Objects.equals(lead.getOrderType(), "book_now")) {
            dateTimeTextView.setText("Today" + ", " + lead.getSlotTime());
        }

        if (Objects.equals(lead.getUserStatus(), "posted")) {
            if (lead.getBookingsBids() == null) {
                timerTextView.setVisibility(View.VISIBLE);
                int time = lead.getExpireTime() - lead.getSeconds();
                new CountDownTimer(1000L * time, 1000) {
                    public void onTick(long millisUntilFinished) {
                        NumberFormat f = new DecimalFormat("00");
                        long min = (millisUntilFinished / 60000) % 60;
                        long sec = (millisUntilFinished / 1000) % 60;
                        timerTextView.setText(f.format(min) + ":" + f.format(sec));
                    }

                    public void onFinish() {
                        timerTextView.setText("00:00");
                        timerTextView.setVisibility(View.GONE);
                    }
                }.start();
            } else {
                timerTextView.setVisibility(View.GONE);
            }
        } else {
            timerTextView.setVisibility(View.GONE);
        }

        MaterialButton messageMaterialButton = viewHolder.findView(R.id.messageMaterialButton);
        CircularTextView circularTextView = viewHolder.findView(R.id.chatCountCircularTextView);
        circularTextView.setStrokeWidth(1);
        circularTextView.setStrokeColor("#000000");
        circularTextView.setSolidColor("#ce2121");
        MaterialButton callMaterialButton = viewHolder.findView(R.id.callMaterialButton);

        if (Objects.equals(lead.getUserStatus(), "posted")) {
            messageMaterialButton.setVisibility(View.GONE);
            callMaterialButton.setVisibility(View.GONE);
        } else {
            messageMaterialButton.setVisibility(View.VISIBLE);
            callMaterialButton.setVisibility(View.VISIBLE);
        }

        if (lead.getBookingsBids() != null) {
            if (lead.getBookingsBids().getCommunicationStatus() == 1) {
                messageMaterialButton.setVisibility(View.VISIBLE);
                callMaterialButton.setVisibility(View.VISIBLE);
            }
        }

        senderId = lead.getOrderNumber() + sharedPreferencesHelper.getName();
        receiverId = lead.getOrderNumber() + lead.getUser().getName();

        firebaseDatabase = FirebaseDatabase.getInstance();
        senderReference = firebaseDatabase.getReference("/messages/" + senderId + "_" + receiverId);
        receiverReference = firebaseDatabase.getReference("/messages/" + receiverId + "_" + senderId);

        senderReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                unreadCount = 0;
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    GenericTypeIndicator<Map<String, String>>
                            genericTypeIndicator = new GenericTypeIndicator<Map<String, String>>() {
                    };
                    Map<String, String> map = child.getValue(genericTypeIndicator);
                    if (map.get(AppConstants.Chat.OneToOne.SEEN).equals("0")) {
                        unreadCount = unreadCount + 1;
                    }
                }

                if (unreadCount == 0) {
                    circularTextView.setVisibility(View.GONE);
                } else {
                    circularTextView.setVisibility(View.VISIBLE);
                    circularTextView.setText("" + unreadCount);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        ImageView locationImageView = viewHolder.findView(R.id.locationImageView);
        TextView locationTextView = viewHolder.findView(R.id.locationTextView);
        MaterialButton getDirectionMaterialButton = viewHolder.findView(R.id.getDirectionMaterialButton);

        locationImageView.setVisibility(View.VISIBLE);
        locationTextView.setVisibility(View.VISIBLE);

        if (Objects.equals(lead.getUserStatus(), "posted"))
        {
            locationTextView.setText(lead.getLatLonAddress());
            getDirectionMaterialButton.setVisibility(View.GONE);
        }
        else
        {
            locationTextView.setText(lead.getLatLonAddress() + ", " + lead.getFlatBuildingStreet() + ", " + lead.getAddress());
            getDirectionMaterialButton.setVisibility(View.VISIBLE);
        }

        ImageView creditsSpendImageView = viewHolder.findView(R.id.creditsSpendImageView);
        TextView creditsSpendHeadingTextView = viewHolder.findView(R.id.creditsSpendHeadingTextView);
        TextView creditsSpendTextView = viewHolder.findView(R.id.creditsSpendTextView);

        MaterialButton bidFroThisJobOrAcceptMaterialButton = viewHolder.findView(R.id.bidFroThisJobOrAcceptMaterialButton);
        MaterialButton rejectMaterialButton = viewHolder.findView(R.id.rejectMaterialButton);

        if (Objects.equals(lead.getUserStatus(), "posted")) {
            if (lead.getBookingsBids() == null) {
                creditsSpendImageView.setVisibility(View.VISIBLE);
                creditsSpendHeadingTextView.setVisibility(View.VISIBLE);
                creditsSpendTextView.setVisibility(View.VISIBLE);
                bidFroThisJobOrAcceptMaterialButton.setVisibility(View.VISIBLE);
                rejectMaterialButton.setVisibility(View.VISIBLE);
                creditsSpendTextView.setText("" + lead.getCredit());

                if (Objects.equals(lead.getOrderType(), "get_bids")) {
                    bidFroThisJobOrAcceptMaterialButton.setText(ResourcesUtils.getString(context, R.string.leads_button_bid_for_this_job));
                } else if (Objects.equals(lead.getOrderType(), "book_now")) {
                    bidFroThisJobOrAcceptMaterialButton.setText(ResourcesUtils.getString(context, R.string.leads_button_accept));
                }
            } else {
                creditsSpendImageView.setVisibility(View.GONE);
                creditsSpendHeadingTextView.setVisibility(View.GONE);
                creditsSpendTextView.setVisibility(View.GONE);
                bidFroThisJobOrAcceptMaterialButton.setVisibility(View.GONE);
                rejectMaterialButton.setVisibility(View.GONE);
            }
        } else {
            creditsSpendImageView.setVisibility(View.GONE);
            creditsSpendHeadingTextView.setVisibility(View.GONE);
            creditsSpendTextView.setVisibility(View.GONE);
            bidFroThisJobOrAcceptMaterialButton.setVisibility(View.GONE);
            rejectMaterialButton.setVisibility(View.GONE);
        }

        MaterialButton startAndRaiseBillMaterialButton = viewHolder.findView(R.id.startAndRaiseBillMaterialButton);

        if (Objects.equals(lead.getUserStatus(), "posted")) {
            startAndRaiseBillMaterialButton.setVisibility(View.GONE);
        } else if (Objects.equals(lead.getUserStatus(), "confirmed")) {
            startAndRaiseBillMaterialButton.setVisibility(View.VISIBLE);
            startAndRaiseBillMaterialButton.setText("Start");
        } else if (Objects.equals(lead.getUserStatus(), "started")) {
            startAndRaiseBillMaterialButton.setVisibility(View.VISIBLE);
            startAndRaiseBillMaterialButton.setText("Raise Bill");
        } else if (Objects.equals(lead.getUserStatus(), "pay_now")) {
            startAndRaiseBillMaterialButton.setVisibility(View.VISIBLE);
            startAndRaiseBillMaterialButton.setText("Waiting For Payment");
        }

        LinearLayout waitingForResponseOrYouHaveGotFinalOfferForResponseLinearLayout = viewHolder.findView(R.id.waitingForResponseOrYouHaveGotFinalOfferForResponseLinearLayout);
        TextView waitingForResponseOrYouHaveGotFinalOfferForResponseTextView = viewHolder.findView(R.id.waitingForResponseOrYouHaveGotFinalOfferForResponseTextView);

        if (lead.getBookingsFinalOffer() != null) {
            waitingForResponseOrYouHaveGotFinalOfferForResponseTextView.setText(ResourcesUtils.getString(context, R.string.leads_toolbar_label_you_have_got_final_offer));
            if (Objects.equals(lead.getUserStatus(), "posted")) {
                waitingForResponseOrYouHaveGotFinalOfferForResponseLinearLayout.setVisibility(View.VISIBLE);
            } else {
                waitingForResponseOrYouHaveGotFinalOfferForResponseLinearLayout.setVisibility(View.GONE);
            }
        } else if (lead.getBookingsBids() != null) {
            waitingForResponseOrYouHaveGotFinalOfferForResponseTextView.setText(ResourcesUtils.getString(context, R.string.leads_toolbar_label_waiting_for_response));
            if (Objects.equals(lead.getUserStatus(), "posted")) {
                waitingForResponseOrYouHaveGotFinalOfferForResponseLinearLayout.setVisibility(View.VISIBLE);
            } else {
                waitingForResponseOrYouHaveGotFinalOfferForResponseLinearLayout.setVisibility(View.GONE);
            }
        } else {
            waitingForResponseOrYouHaveGotFinalOfferForResponseLinearLayout.setVisibility(View.GONE);
        }
    }
}
