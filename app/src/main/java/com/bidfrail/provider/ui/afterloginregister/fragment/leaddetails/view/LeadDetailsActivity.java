package com.bidfrail.provider.ui.afterloginregister.fragment.leaddetails.view;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.bidfrail.provider.AppConstants;
import com.bidfrail.provider.R;
import com.bidfrail.provider.data.local.sharedpreferences.SharedPreferencesHelper;
import com.bidfrail.provider.data.remote.ApiConfiguration;
import com.bidfrail.provider.data.remote.glide.GlideImageLoader;
import com.bidfrail.provider.data.remote.glide.GlideImageLoadingListener;
import com.bidfrail.provider.databinding.ActivityLeadDetailsBinding;
import com.bidfrail.provider.model.Cart;
import com.bidfrail.provider.model.Lead;
import com.bidfrail.provider.ui.afterloginregister.view.AfterLoginActivity;
import com.bidfrail.provider.ui.base.view.BaseActivity;
import com.bidfrail.provider.ui.afterloginregister.fragment.leaddetails.adapter.ServiceAddedAdapter;
import com.bidfrail.provider.ui.afterloginregister.fragment.leaddetails.viewmodel.LeadDetailsViewModel;
import com.bidfrail.provider.ui.chat.view.ChatActivity;
import com.bidfrail.provider.ui.login.view.LoginActivity;
import com.bidfrail.provider.ui.modelsheetbidonlead.BidOnLeadListener;
import com.bidfrail.provider.ui.modelsheetbidonlead.BidOnLeadModalBottomSheet;
import com.bidfrail.provider.ui.modelsheetleadotp.LeadOTPListener;
import com.bidfrail.provider.ui.modelsheetleadotp.LeadOTPModalBottomSheet;
import com.bidfrail.provider.ui.modelsheetsendbill.SendBillListener;
import com.bidfrail.provider.ui.modelsheetsendbill.SendBillModalBottomSheet;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.library.adapter.recyclerview.LayoutManagerUtils;
import com.library.navigator.activity.ActivityNavigator;
import com.library.navigator.fragment.FragmentNavigator;
import com.library.utilities.CallUtils;
import com.library.utilities.ResourcesUtils;
import com.library.utilities.ViewUtils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LeadDetailsActivity extends BaseActivity<ActivityLeadDetailsBinding, LeadDetailsViewModel> {

    private Lead leadObject;
    private ArrayList<Cart> cartArrayList = new ArrayList<Cart>();
    private CountDownTimer countDownTimerForLead;
    private GoogleMap mGoogleMap;
    private SupportMapFragment supportMapFragment;

    private boolean stop = true;
    private Handler handler;
    private Runnable runnable;

    @Inject
    SharedPreferencesHelper sharedPreferencesHelper;

    @Override
    protected String getActivityClassName() {
        return LeadDetailsActivity.class.getSimpleName();
    }

    @Override
    protected void doBeforeSetContentView() {
    }

    @Override
    protected ActivityNavigator getActivityNavigator() {
        return new ActivityNavigator(LeadDetailsActivity.this);
    }

    @Override
    protected FragmentNavigator getFragmentNavigator() {
        return null;
    }

    @Override
    protected void doInOnCreate(Bundle savedInstanceState) {
    }

    @Override
    protected LeadDetailsViewModel getViewModel() {
        return viewModelProvider(LeadDetailsViewModel.class);
    }

    @Override
    protected ActivityLeadDetailsBinding getViewBinding(LayoutInflater inflater) {
        return ActivityLeadDetailsBinding.inflate(inflater);
    }

    @Override
    protected void setupToolbar() {
        setupToolBar(mViewBinding.toolbar, ResourcesUtils.getColor(getApplicationContext(), R.color.colorToolbarBackground), R.drawable.ic_left_arrow, R.string.lead_details_toolbar_title);
    }

    @Override
    protected void init() {
        handleIntent(getIntent());
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void addTextChangedListener() {
    }

    @Override
    protected void setOnFocusChangeListener() {
    }

    @Override
    protected void setupObservers() {
        Observer<String> leadErrorObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String error) {
                if (error == null) {
                    showShortToast(getString(R.string.something_went_wrong_please_try_again));
                } else {
                    showShortToast(error);
                }
            }
        };
        mViewModel.leadError().observe(this, leadErrorObserver);

        final Observer<Lead> leadSuccessObserver = new Observer<Lead>() {
            @Override
            public void onChanged(Lead lead) {
                leadObject = lead;
                if (Objects.equals(leadObject.getProviderStatus(), "payment_received")) {
                    Bundle bundle = new Bundle();
                    bundle.putString(AppConstants.Screen.Extras.EXTRA_NAVIGATE_TO, "LeadsFragment");
                    activityNavigator.setBundle(bundle).clearBackStack().startActivity(AfterLoginActivity.class);
                } else {
                    setView(leadObject);
                }
            }
        };
        mViewModel.leadSuccess().observe(this, leadSuccessObserver);

        Observer<String> bidOnLeadErrorObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String error) {
                hideProgressDialog();
                if (error == null) {
                    showShortToast(getString(R.string.something_went_wrong_please_try_again));
                } else {
                    showShortToast(error);
                }
            }
        };
        mViewModel.bidOnLeadError().observe(this, bidOnLeadErrorObserver);

        final Observer<String> bidOnLeadSuccessObserver = new Observer<String>() {
            @Override
            public void onChanged(String successMessage) {
                hideProgressDialog();
                //showShortToast(successMessage);

                Bundle bundle = new Bundle();
                bundle.putString(AppConstants.Screen.Extras.EXTRA_NAVIGATE_TO, "LeadsFragment");
                activityNavigator.setBundle(bundle).clearBackStack().startActivity(AfterLoginActivity.class);
            }
        };
        mViewModel.bidOnLeadSuccess().observe(this, bidOnLeadSuccessObserver);

        Observer<String> acceptOrRejectLeadErrorObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String error) {
                hideProgressDialog();
                if (error == null) {
                    showShortToast(getString(R.string.something_went_wrong_please_try_again));
                } else {
                    showShortToast(error);
                }
            }
        };
        mViewModel.acceptOrRejectLeadError().observe(this, acceptOrRejectLeadErrorObserver);

        final Observer<String> acceptOrRejectLeadSuccessObserver = new Observer<String>() {
            @Override
            public void onChanged(String successMessage) {
                hideProgressDialog();
                //showShortToast(successMessage);

                Bundle bundle = new Bundle();
                bundle.putString(AppConstants.Screen.Extras.EXTRA_NAVIGATE_TO, "LeadsFragment");
                activityNavigator.setBundle(bundle).clearBackStack().startActivity(AfterLoginActivity.class);
            }
        };
        mViewModel.acceptOrRejectLeadSuccess().observe(this, acceptOrRejectLeadSuccessObserver);

        Observer<String> acceptOrRejectFinalOfferErrorObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String error) {
                hideProgressDialog();
                if (error == null) {
                    showShortToast(getString(R.string.something_went_wrong_please_try_again));
                } else {
                    showShortToast(error);
                }
            }
        };
        mViewModel.acceptOrRejectFinalOfferError().observe(this, acceptOrRejectFinalOfferErrorObserver);

        final Observer<String> acceptOrRejectFinalOfferSuccessObserver = new Observer<String>() {
            @Override
            public void onChanged(String successMessage) {
                hideProgressDialog();
                //showShortToast(successMessage);

                Bundle bundle = new Bundle();
                bundle.putString(AppConstants.Screen.Extras.EXTRA_NAVIGATE_TO, "LeadsFragment");
                activityNavigator.setBundle(bundle).clearBackStack().startActivity(AfterLoginActivity.class);
            }
        };
        mViewModel.acceptOrRejectFinalOfferSuccess().observe(this, acceptOrRejectFinalOfferSuccessObserver);

        Observer<String> setProviderStatusErrorObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String error) {
                hideProgressDialog();
                if (error == null) {
                    showShortToast(getString(R.string.something_went_wrong_please_try_again));
                } else {
                    showShortToast(error);
                }
            }
        };
        mViewModel.setProviderStatusError().observe(this, setProviderStatusErrorObserver);

        final Observer<String> setProviderStatusSuccessObserver = new Observer<String>() {
            @Override
            public void onChanged(String successMessage) {
                hideProgressDialog();
                //showShortToast(successMessage);

                Bundle bundle = new Bundle();
                bundle.putString(AppConstants.Screen.Extras.EXTRA_NAVIGATE_TO, "LeadsFragment");
                activityNavigator.setBundle(bundle).clearBackStack().startActivity(AfterLoginActivity.class);
            }
        };
        mViewModel.setProviderStatusSuccess().observe(this, setProviderStatusSuccessObserver);
    }

    @Override
    protected void setupListeners() {
        mViewBinding.messageMaterialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle chatDetails = new Bundle();
                chatDetails.putString(AppConstants.Screen.Extras.EXTRA_ORDER_ID, String.valueOf(leadObject.getId()));
                activityNavigator
                        .setBundle(chatDetails)
                        .startActivity(ChatActivity.class);
            }
        });

        mViewBinding.callMaterialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(CallUtils.dial(leadObject.getUser().getPhoneNumber()));
            }
        });

        mViewBinding.getDirectionMaterialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?q=loc:" + leadObject.getLatitude() + "," + leadObject.getLongitude()));
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                String mapsPackageName = "com.google.android.apps.maps";
                if (isPackageExisted(getApplicationContext(), mapsPackageName)) {
                    i.setClassName(mapsPackageName, "com.google.android.maps.MapsActivity");
                    i.setPackage(mapsPackageName);
                }
                startActivity(i);
            }
        });

        mViewBinding.bidFroThisJobOrAcceptMaterialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Objects.equals(leadObject.getOrderType(), "get_bids")) {
                    BidOnLeadModalBottomSheet bidOnLeadModalBottomSheet = new BidOnLeadModalBottomSheet(leadObject, new BidOnLeadListener() {
                        @Override
                        public void sendRates(int bidAmount, String message) {
                            showProgressDialog();
                            mViewModel.bidOnLead(sharedPreferencesHelper.getProviderId(), leadObject.getId(), bidAmount, message, leadObject.getUser().getFcmToken());
                        }
                    });
                    bidOnLeadModalBottomSheet.show(getSupportFragmentManager(), bidOnLeadModalBottomSheet.getTag());
                } else if (Objects.equals(leadObject.getOrderType(), "book_now")) {
                    showProgressDialog();
                    mViewModel.acceptOrRejectLead(leadObject.getId(), sharedPreferencesHelper.getProviderId(), "accept", "provider", sharedPreferencesHelper.getProviderId(), leadObject.getUser().getFcmToken());
                }
            }
        });

        mViewBinding.rejectMaterialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressDialog();
                mViewModel.acceptOrRejectLead(leadObject.getId(), sharedPreferencesHelper.getProviderId(), "reject", "provider", sharedPreferencesHelper.getProviderId(), leadObject.getUser().getFcmToken());
            }
        });

        mViewBinding.acceptFinalOfferMaterialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressDialog();
                mViewModel.acceptOrRejectFinalOffer(leadObject.getId(), sharedPreferencesHelper.getProviderId(), "accept", leadObject.getUser().getFcmToken());
            }
        });

        mViewBinding.rejectFinalOfferMaterialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressDialog();
                mViewModel.acceptOrRejectFinalOffer(leadObject.getId(), sharedPreferencesHelper.getProviderId(), "reject", leadObject.getUser().getFcmToken());
            }
        });

        mViewBinding.startAndRaiseBillMaterialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Objects.equals(leadObject.getUserStatus(), "confirmed")) {
                    showProgressDialog();
                    mViewModel.setProviderStatus(leadObject.getId(), sharedPreferencesHelper.getProviderId(), "start", leadObject.getUser().getFcmToken());
                } else if (Objects.equals(leadObject.getUserStatus(), "started")) {
                    SendBillModalBottomSheet sendBillModalBottomSheet = new SendBillModalBottomSheet(leadObject, new SendBillListener() {
                        @Override
                        public void sendRates(int billAmount, String description) {
                            LeadOTPModalBottomSheet leadOTPModalBottomSheet = new LeadOTPModalBottomSheet(leadObject, new LeadOTPListener() {
                                @Override
                                public void submitOTP(String otp) {
                                    showProgressDialog();
                                    mViewModel.raiseBill(leadObject.getId(), billAmount, description, leadObject.getUser().getFcmToken());
                                }
                            });
                            leadOTPModalBottomSheet.show(getSupportFragmentManager(), leadOTPModalBottomSheet.getTag());
                        }
                    });
                    sendBillModalBottomSheet.show(getSupportFragmentManager(), sendBillModalBottomSheet.getTag());
                }
            }
        });
    }

    @Override
    public void showProgressBar() {
    }

    @Override
    public void hideProgressBar() {
    }

    @Override
    public void showProgressDialog() {
        if (mViewBinding.progressDialog.pleaseWaitProgressBar.getVisibility() == View.GONE) {
            mViewBinding.progressDialog.pleaseWaitProgressBar.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    @Override
    public void hideProgressDialog() {
        if (mViewBinding.progressDialog.pleaseWaitProgressBar.getVisibility() == View.VISIBLE) {
            mViewBinding.progressDialog.pleaseWaitProgressBar.setVisibility(View.GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mViewBinding.shimmerFrameLayout.stopShimmer();
        stopHandler();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (countDownTimerForLead != null) {
            countDownTimerForLead.cancel();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (intent != null) {
            Bundle bundle = activityNavigator.getBundle();
            if (bundle != null) {

                String navigateToScreen = bundle.getString(AppConstants.Screen.Extras.EXTRA_NAVIGATE_TO);
                String title = bundle.getString(AppConstants.Screen.Extras.EXTRA_TITLE);
                String message = bundle.getString(AppConstants.Screen.Extras.EXTRA_MESSAGE);
                String orderType = bundle.getString(AppConstants.Screen.Extras.EXTRA_ORDER_TYPE);
                String orderId = bundle.getString(AppConstants.Screen.Extras.EXTRA_ORDER_ID);
                String dateRequiredForScreen = bundle.getString(AppConstants.Screen.Extras.EXTRA_DATA_REQUIRED);

                mViewBinding.shimmerFrameLayout.startShimmer();
                mViewBinding.shimmerFrameLayout.setVisibility(View.VISIBLE);
                mViewBinding.nestedScrollView.setVisibility(View.GONE);

                stop = true;
                call(AppConstants.Delay.API_CALL, Integer.parseInt(orderId));
            }
        }
    }

    private void setupServiceAddedRecyclerView(@NonNull Context context) {
        mViewBinding.serviceAddedRecyclerView.setLayoutManager(LayoutManagerUtils.getLinearLayoutManagerVertical(context));
        ServiceAddedAdapter serviceAddedAdapter = new ServiceAddedAdapter(context, leadObject.getOrderType());
        serviceAddedAdapter.addArrayList(cartArrayList);
        mViewBinding.serviceAddedRecyclerView.setAdapter(serviceAddedAdapter);
    }

    private Marker setMarkerOnMyCurrentLocation(GoogleMap googleMap, LatLng point, String titleString, String snippetString, BitmapDescriptor bitmapDescriptor) {
        googleMap.clear();
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(point);
        markerOptions.snippet(snippetString);
        markerOptions.title(titleString);
        markerOptions.visible(true);
        markerOptions.icon(bitmapDescriptor);
        Marker marker = googleMap.addMarker(markerOptions);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(point).zoom(15).bearing(90).tilt(30).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        return marker;
    }

    private void setView(Lead leadObject) {
        cartArrayList = (ArrayList<Cart>) leadObject.getCart();
        setupServiceAddedRecyclerView(this);

        GlideImageLoader.load(
                getApplicationContext(),
                ApiConfiguration.IMAGE_URL + leadObject.getSubCategory().getGif(),
                mViewBinding.subCategoryImageView,
                new GlideImageLoadingListener() {
                    @Override
                    public void imageLoadSuccess() {
                        mViewBinding.imageLoadingProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void imageLoadError() {
                        mViewBinding.imageLoadingProgressBar.setVisibility(View.GONE);
                    }
                });

        mViewBinding.orderNumberTextView.setText(leadObject.getOrderNumber());
        mViewBinding.orderSubCategoryTextView.setText(leadObject.getSubCategory().getName());

        if (Objects.equals(leadObject.getUserStatus(), "posted")) {
            mViewBinding.statusTextView.setVisibility(View.GONE);
        } else if (Objects.equals(leadObject.getUserStatus(), "confirmed")) {
            mViewBinding.statusTextView.setVisibility(View.VISIBLE);
            mViewBinding.statusTextView.setText("Accepted");
            mViewBinding.statusTextView.setTextColor(ResourcesUtils.getColor(getApplicationContext(), R.color.tag_green));
            ViewUtils.setBackgroundDrawable(mViewBinding.statusTextView, ResourcesUtils.getDrawable(getApplicationContext(), R.drawable.tag_green));
        } else if (Objects.equals(leadObject.getUserStatus(), "started")) {
            mViewBinding.statusTextView.setVisibility(View.VISIBLE);
            mViewBinding.statusTextView.setText("Started");
            mViewBinding.statusTextView.setTextColor(ResourcesUtils.getColor(getApplicationContext(), R.color.tag_green));
            ViewUtils.setBackgroundDrawable(mViewBinding.statusTextView, ResourcesUtils.getDrawable(getApplicationContext(), R.drawable.tag_green));
        }

        if (Objects.equals(leadObject.getOrderType(), "get_bids")) {
            mViewBinding.dateTimeTextView.setText(leadObject.getSlotDate() + ", " + leadObject.getSlotTime());
        } else if (Objects.equals(leadObject.getOrderType(), "book_now")) {
            mViewBinding.dateTimeTextView.setText("Today" + ", " + leadObject.getSlotTime());
        }

        if (Objects.equals(leadObject.getUserStatus(), "posted")) {
            if (leadObject.getBookingsBids() == null) {
                mViewBinding.timerTextView.setVisibility(View.VISIBLE);
                int time = leadObject.getExpireTime() - leadObject.getSeconds();
                countDownTimerForLead = new CountDownTimer(1000L * time, 1000) {
                    public void onTick(long millisUntilFinished) {
                        NumberFormat f = new DecimalFormat("00");
                        long min = (millisUntilFinished / 60000) % 60;
                        long sec = (millisUntilFinished / 1000) % 60;
                        mViewBinding.timerTextView.setText(f.format(min) + ":" + f.format(sec));
                    }

                    public void onFinish() {
                        mViewBinding.timerTextView.setText("00:00");
                        mViewBinding.timerTextView.setVisibility(View.GONE);
                    }
                }.start();
            } else {
                mViewBinding.timerTextView.setVisibility(View.GONE);
            }
        } else {
            mViewBinding.timerTextView.setVisibility(View.GONE);
        }


        if (Objects.equals(leadObject.getUserStatus(), "posted")) {
            mViewBinding.messageMaterialButton.setVisibility(View.GONE);
            mViewBinding.callMaterialButton.setVisibility(View.GONE);
        } else {
            mViewBinding.messageMaterialButton.setVisibility(View.VISIBLE);
            mViewBinding.callMaterialButton.setVisibility(View.VISIBLE);
        }

        if (leadObject.getBookingsBids() != null) {
            if (leadObject.getBookingsBids().getCommunicationStatus() == 1) {
                mViewBinding.messageMaterialButton.setVisibility(View.VISIBLE);
                mViewBinding.callMaterialButton.setVisibility(View.VISIBLE);
            }
        }

        mViewBinding.locationImageView.setVisibility(View.VISIBLE);
        mViewBinding.locationTextView.setVisibility(View.VISIBLE);

        if (Objects.equals(leadObject.getUserStatus(), "posted")) {
            mViewBinding.getDirectionMaterialButton.setVisibility(View.GONE);
        } else {
            mViewBinding.getDirectionMaterialButton.setVisibility(View.VISIBLE);
        }

        if (Objects.equals(leadObject.getUserStatus(), "posted")) {
            mViewBinding.locationTextView.setText(leadObject.getLatLonAddress());
        } else {
            mViewBinding.locationTextView.setText(leadObject.getLatLonAddress() + ", " + leadObject.getFlatBuildingStreet() + ", " + leadObject.getAddress());
        }

        if (Objects.equals(leadObject.getUserStatus(), "posted")) {
            if (leadObject.getBookingsBids() == null) {
                mViewBinding.creditsSpendImageView.setVisibility(View.VISIBLE);
                mViewBinding.creditsSpendHeadingTextView.setVisibility(View.VISIBLE);
                mViewBinding.creditsSpendTextView.setVisibility(View.VISIBLE);
                mViewBinding.bidFroThisJobOrAcceptMaterialButton.setVisibility(View.VISIBLE);
                mViewBinding.rejectMaterialButton.setVisibility(View.VISIBLE);
                mViewBinding.creditsSpendTextView.setText("" + leadObject.getCredit());

                if (Objects.equals(leadObject.getOrderType(), "get_bids")) {
                    mViewBinding.bidFroThisJobOrAcceptMaterialButton.setText(ResourcesUtils.getString(getApplicationContext(), R.string.leads_button_bid_for_this_job));
                } else if (Objects.equals(leadObject.getOrderType(), "book_now")) {
                    mViewBinding.bidFroThisJobOrAcceptMaterialButton.setText(ResourcesUtils.getString(getApplicationContext(), R.string.leads_button_accept));
                }
            } else {
                mViewBinding.creditsSpendImageView.setVisibility(View.GONE);
                mViewBinding.creditsSpendHeadingTextView.setVisibility(View.GONE);
                mViewBinding.creditsSpendTextView.setVisibility(View.GONE);
                mViewBinding.bidFroThisJobOrAcceptMaterialButton.setVisibility(View.GONE);
                mViewBinding.rejectMaterialButton.setVisibility(View.GONE);
            }
        } else {
            mViewBinding.creditsSpendImageView.setVisibility(View.GONE);
            mViewBinding.creditsSpendHeadingTextView.setVisibility(View.GONE);
            mViewBinding.creditsSpendTextView.setVisibility(View.GONE);
            mViewBinding.bidFroThisJobOrAcceptMaterialButton.setVisibility(View.GONE);
            mViewBinding.rejectMaterialButton.setVisibility(View.GONE);
        }

        if (Objects.equals(leadObject.getUserStatus(), "posted")) {
            mViewBinding.startAndRaiseBillMaterialButton.setVisibility(View.GONE);
        } else if (Objects.equals(leadObject.getUserStatus(), "confirmed")) {
            mViewBinding.startAndRaiseBillMaterialButton.setVisibility(View.VISIBLE);
            mViewBinding.startAndRaiseBillMaterialButton.setText("Start");
        } else if (Objects.equals(leadObject.getUserStatus(), "started")) {
            mViewBinding.startAndRaiseBillMaterialButton.setVisibility(View.VISIBLE);
            mViewBinding.startAndRaiseBillMaterialButton.setText("Raise Bill");
        } else if (Objects.equals(leadObject.getUserStatus(), "pay_now")) {
            mViewBinding.startAndRaiseBillMaterialButton.setVisibility(View.VISIBLE);
            mViewBinding.startAndRaiseBillMaterialButton.setText("Waiting For Payment");
        }

        if (leadObject.getBookingsFinalOffer() != null) {
            mViewBinding.waitingForResponseOrYouHaveGotFinalOfferForResponseTextView.setText(ResourcesUtils.getString(getApplicationContext(), R.string.leads_toolbar_label_you_have_got_final_offer));
            mViewBinding.finalAmountTextView.setVisibility(View.VISIBLE);
            mViewBinding.messageTextView.setVisibility(View.VISIBLE);
            mViewBinding.acceptFinalOfferMaterialButton.setVisibility(View.VISIBLE);
            mViewBinding.rejectFinalOfferMaterialButton.setVisibility(View.VISIBLE);

            mViewBinding.finalAmountTextView.setText("Final Amount : \u20B9" + leadObject.getBookingsFinalOffer().getAmount());
            mViewBinding.messageTextView.setText("Message : " + leadObject.getBookingsFinalOffer().getMessage());

            if (Objects.equals(leadObject.getUserStatus(), "posted")) {
                mViewBinding.waitingForResponseOrYouHaveGotFinalOfferForResponseLinearLayout.setVisibility(View.VISIBLE);
            } else {
                mViewBinding.waitingForResponseOrYouHaveGotFinalOfferForResponseLinearLayout.setVisibility(View.GONE);
            }
        } else if (leadObject.getBookingsBids() != null) {
            mViewBinding.waitingForResponseOrYouHaveGotFinalOfferForResponseTextView.setText(ResourcesUtils.getString(getApplicationContext(), R.string.leads_toolbar_label_waiting_for_response));
            mViewBinding.finalAmountTextView.setVisibility(View.GONE);
            mViewBinding.messageTextView.setVisibility(View.GONE);
            mViewBinding.acceptFinalOfferMaterialButton.setVisibility(View.GONE);
            mViewBinding.rejectFinalOfferMaterialButton.setVisibility(View.GONE);

            if (Objects.equals(leadObject.getUserStatus(), "posted")) {
                mViewBinding.waitingForResponseOrYouHaveGotFinalOfferForResponseLinearLayout.setVisibility(View.VISIBLE);
            } else {
                mViewBinding.waitingForResponseOrYouHaveGotFinalOfferForResponseLinearLayout.setVisibility(View.GONE);
            }
        } else {
            mViewBinding.waitingForResponseOrYouHaveGotFinalOfferForResponseLinearLayout.setVisibility(View.GONE);
        }

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.supportMapFragment);

        if (supportMapFragment != null) {
            supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(@NonNull GoogleMap googleMap) {
                    mGoogleMap = googleMap;

                    mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    mGoogleMap.setTrafficEnabled(true);
                    mGoogleMap.setIndoorEnabled(true);
                    mGoogleMap.setBuildingsEnabled(true);

                    mGoogleMap.getUiSettings().setZoomControlsEnabled(false);
                    mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
                    mGoogleMap.getUiSettings().setRotateGesturesEnabled(false);

                    LatLng order = new LatLng(Double.parseDouble(leadObject.getLatitude()), Double.parseDouble(leadObject.getLongitude()));
                    BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
                    setMarkerOnMyCurrentLocation(mGoogleMap, order, "Job", leadObject.getLatLonAddress(), bitmapDescriptor);
                }
            });

            mViewBinding.shimmerFrameLayout.stopShimmer();
            mViewBinding.shimmerFrameLayout.setVisibility(View.GONE);
            mViewBinding.nestedScrollView.setVisibility(View.VISIBLE);
        }

        if (Objects.equals(leadObject.getUserStatus(), "posted")) {
            supportMapFragment.getView().setVisibility(View.GONE);
        } else {
            supportMapFragment.getView().setVisibility(View.VISIBLE);
        }
    }

    private boolean isPackageExisted(Context context, String targetPackage) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(targetPackage, PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        return true;
    }

    public void call(long delayMillis, int orderId) {
        mViewModel.leadDetails(orderId, sharedPreferencesHelper.getProviderId());

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (stop) {
                    mViewModel.leadDetails(orderId, sharedPreferencesHelper.getProviderId());
                    if (handler != null) {
                        handler.postDelayed(this, delayMillis);
                    }
                }
            }
        };
        handler.postDelayed(runnable, delayMillis);
    }

    private void stopHandler() {
        stop = false;
        if (handler != null) {
            handler.removeCallbacks(runnable);
        }
    }
}