package com.bidfrail.provider.ui.afterloginregister.fragment.leads.view;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import com.bidfrail.provider.AppConstants;
import com.bidfrail.provider.R;
import com.bidfrail.provider.data.local.sharedpreferences.SharedPreferencesHelper;
import com.bidfrail.provider.databinding.FragmentLeadsBinding;
import com.bidfrail.provider.model.Lead;
import com.bidfrail.provider.ui.afterloginregister.fragment.leads.adapter.LeadsAdapter;
import com.bidfrail.provider.ui.afterloginregister.fragment.leads.viewmodel.LeadsViewModel;
import com.bidfrail.provider.ui.base.view.BaseFragment;
import com.bidfrail.provider.ui.afterloginregister.fragment.leaddetails.view.LeadDetailsActivity;
import com.bidfrail.provider.ui.chat.view.ChatActivity;
import com.bidfrail.provider.ui.modelsheetbidonlead.BidOnLeadListener;
import com.bidfrail.provider.ui.modelsheetbidonlead.BidOnLeadModalBottomSheet;
import com.bidfrail.provider.ui.modelsheetleadotp.LeadOTPListener;
import com.bidfrail.provider.ui.modelsheetleadotp.LeadOTPModalBottomSheet;
import com.bidfrail.provider.ui.modelsheetsendbill.SendBillListener;
import com.bidfrail.provider.ui.modelsheetsendbill.SendBillModalBottomSheet;
import com.library.adapter.recyclerview.LayoutManagerUtils;
import com.library.adapter.recyclerview.listener.OnRecyclerViewItemChildClick;
import com.library.navigator.activity.ActivityNavigator;
import com.library.navigator.fragment.FragmentNavigator;
import com.library.utilities.CallUtils;
import java.util.ArrayList;
import java.util.Objects;
import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LeadsFragment extends BaseFragment<FragmentLeadsBinding, LeadsViewModel> {

    private LeadsAdapter leadsAdapter;
    private ArrayList<Lead> leadArrayList = new ArrayList<>();

    private boolean stop = true;
    private Handler handler;
    private Runnable runnable;

    @Inject
    SharedPreferencesHelper sharedPreferencesHelper;

    @Override
    protected String getFragmentClassName() {
        return LeadsFragment.class.getSimpleName();
    }

    @Override
    protected ActivityNavigator getActivityNavigator() {
        return new ActivityNavigator(getActivity());
    }

    @Override
    protected FragmentNavigator getFragmentNavigator() {
        return null;
    }

    @Override
    protected void doInOnCreate(Bundle savedInstanceState) {
    }

    @Override
    protected LeadsViewModel getViewModel() {
        return viewModelProvider(LeadsViewModel.class);
    }

    @Override
    protected FragmentLeadsBinding getViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentLeadsBinding.inflate(inflater, container, false);
    }

    @Override
    protected void setupToolbar() {
    }

    @Override
    protected void init() {
    }

    @Override
    protected void initView() {
        setupLeadsRecyclerView(getContext());
    }

    @Override
    protected void addTextChangedListener() {
    }

    @Override
    protected void setOnFocusChangeListener() {
    }

    @Override
    protected void setupObservers() {
        Observer<String> leadsErrorObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String error) {
                hideProgressBar();
                mViewBinding.errorTextView.setVisibility(View.VISIBLE);
                mViewBinding.leadsRecyclerView.setVisibility(View.GONE);

                if (error == null) {
                    mViewBinding.errorTextView.setText(getString(R.string.something_went_wrong_please_try_again));
                } else {
                    mViewBinding.errorTextView.setText(error);
                }
            }
        };
        mViewModel.leadsError().observe(this, leadsErrorObserver);

        final Observer<ArrayList<Lead>> leadsSuccessObserver = new Observer<ArrayList<Lead>>() {
            @Override
            public void onChanged(ArrayList<Lead> leads) {
                hideProgressBar();
                mViewBinding.errorTextView.setVisibility(View.GONE);
                mViewBinding.leadsRecyclerView.setVisibility(View.VISIBLE);

                leadArrayList = leads;
                leadsAdapter.replaceArrayList(leadArrayList);
            }
        };
        mViewModel.leadsSuccess().observe(this, leadsSuccessObserver);

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

                showProgressBar();
                mViewBinding.leadsRecyclerView.setVisibility(View.GONE);
                mViewModel.leads(sharedPreferencesHelper.getProviderId());
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
                showShortToast(successMessage);

                showProgressBar();
                mViewBinding.leadsRecyclerView.setVisibility(View.GONE);
                mViewModel.leads(sharedPreferencesHelper.getProviderId());
            }
        };
        mViewModel.acceptOrRejectLeadSuccess().observe(this, acceptOrRejectLeadSuccessObserver);

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

                showProgressBar();
                mViewBinding.leadsRecyclerView.setVisibility(View.GONE);
                mViewModel.leads(sharedPreferencesHelper.getProviderId());
            }
        };
        mViewModel.setProviderStatusSuccess().observe(this, setProviderStatusSuccessObserver);


        Observer<String> raiseBillErrorObserver = new Observer<String>() {
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
        mViewModel.raiseBillError().observe(this, raiseBillErrorObserver);

        final Observer<String> raiseBillSuccessObserver = new Observer<String>() {
            @Override
            public void onChanged(String successMessage) {
                hideProgressDialog();
               // showShortToast(successMessage);

                showProgressBar();
                mViewBinding.leadsRecyclerView.setVisibility(View.GONE);
                mViewModel.leads(sharedPreferencesHelper.getProviderId());
            }
        };
        mViewModel.raiseBillSuccess().observe(this, raiseBillSuccessObserver);
    }

    @Override
    protected void setupListeners() {
        leadsAdapter.setOnRecyclerViewItemChildClick(new OnRecyclerViewItemChildClick<Lead>() {
            @Override
            public void OnItemChildClick(View viewChild, Lead lead, int position) {
                switch (viewChild.getId()) {
                    case R.id.leadRowItem:
                        Bundle leadDetails = new Bundle();
                        leadDetails.putString(AppConstants.Screen.Extras.EXTRA_ORDER_ID, String.valueOf(lead.getId()));
                        activityNavigator
                                .setBundle(leadDetails)
                                .startActivity(LeadDetailsActivity.class);
                        break;
                    case R.id.messageMaterialButton:
                        Bundle chatBundle = new Bundle();
                        chatBundle.putString(AppConstants.Screen.Extras.EXTRA_ORDER_ID, String.valueOf(lead.getId()));
                        activityNavigator
                                .setBundle(chatBundle)
                                .startActivity(ChatActivity.class);
                        break;
                    case R.id.callMaterialButton:
                        startActivity(CallUtils.dial(lead.getUser().getPhoneNumber()));
                        break;
                    case R.id.getDirectionMaterialButton:
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?q=loc:" + lead.getLatitude() + "," + lead.getLongitude()));
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        String mapsPackageName = "com.google.android.apps.maps";
                        if (isPackageExisted(getContext(), mapsPackageName)) {
                            i.setClassName(mapsPackageName, "com.google.android.maps.MapsActivity");
                            i.setPackage(mapsPackageName);
                        }
                        startActivity(i);
                        break;
                    case R.id.bidFroThisJobOrAcceptMaterialButton:
                        if (Objects.equals(lead.getOrderType(), "get_bids")) {
                            BidOnLeadModalBottomSheet bidOnLeadModalBottomSheet = new BidOnLeadModalBottomSheet(lead, new BidOnLeadListener() {
                                @Override
                                public void sendRates(int bidAmount, String message) {
                                    showProgressDialog();
                                    mViewModel.bidOnLead(sharedPreferencesHelper.getProviderId(), lead.getId(), bidAmount, message, lead.getUser().getFcmToken());
                                }
                            });
                            bidOnLeadModalBottomSheet.show(getChildFragmentManager(), bidOnLeadModalBottomSheet.getTag());
                        } else if (Objects.equals(lead.getOrderType(), "book_now")) {
                            showProgressDialog();
                            mViewModel.acceptOrRejectLead(lead.getId(), sharedPreferencesHelper.getProviderId(), "accept", "provider", sharedPreferencesHelper.getProviderId(), lead.getUser().getFcmToken());
                        }
                        break;
                    case R.id.rejectMaterialButton:
                        showProgressDialog();
                        mViewModel.acceptOrRejectLead(lead.getId(), sharedPreferencesHelper.getProviderId(), "reject", "provider", sharedPreferencesHelper.getProviderId(), lead.getUser().getFcmToken());
                        break;
                    case R.id.startAndRaiseBillMaterialButton:
                        if (Objects.equals(lead.getUserStatus(), "confirmed")) {
                            showProgressDialog();
                            mViewModel.setProviderStatus(lead.getId(), sharedPreferencesHelper.getProviderId(), "start", lead.getUser().getFcmToken());
                        } else if (Objects.equals(lead.getUserStatus(), "started")) {
                            SendBillModalBottomSheet sendBillModalBottomSheet = new SendBillModalBottomSheet(lead, new SendBillListener() {
                                @Override
                                public void sendRates(int billAmount, String description) {
                                    LeadOTPModalBottomSheet leadOTPModalBottomSheet = new LeadOTPModalBottomSheet(lead, new LeadOTPListener() {
                                        @Override
                                        public void submitOTP(String otp) {
                                            showProgressDialog();
                                            mViewModel.raiseBill(lead.getId(), billAmount, description, lead.getUser().getFcmToken());
                                        }
                                    });
                                    leadOTPModalBottomSheet.show(getChildFragmentManager(), leadOTPModalBottomSheet.getTag());
                                }
                            });
                            sendBillModalBottomSheet.show(getChildFragmentManager(), sendBillModalBottomSheet.getTag());
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void showProgressBar() {
        if (mViewBinding.progressBar.getVisibility() == View.GONE) {
            mViewBinding.progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgressBar() {
        if (mViewBinding.progressBar.getVisibility() == View.VISIBLE) {
            mViewBinding.progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showProgressDialog() {
        if (mViewBinding.progressDialog.pleaseWaitProgressBar.getVisibility() == View.GONE) {
            mViewBinding.progressDialog.pleaseWaitProgressBar.setVisibility(View.VISIBLE);
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    @Override
    public void hideProgressDialog() {
        if (mViewBinding.progressDialog.pleaseWaitProgressBar.getVisibility() == View.VISIBLE) {
            mViewBinding.progressDialog.pleaseWaitProgressBar.setVisibility(View.GONE);
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        stop = true;
        call(AppConstants.Delay.API_CALL);
    }

    @Override
    public void onPause() {
        super.onPause();
        stopHandler();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /***********************************************************************************************
     *********************************************Helper********************************************
     **********************************************************************************************/
    private void setupLeadsRecyclerView(Context context) {
        mViewBinding.leadsRecyclerView.setLayoutManager(LayoutManagerUtils.getLinearLayoutManagerVertical(context));
        leadsAdapter = new LeadsAdapter(context, sharedPreferencesHelper);
        leadsAdapter.addArrayList(leadArrayList);
        mViewBinding.leadsRecyclerView.setAdapter(leadsAdapter);
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

    public void call(long delayMillis) {
        showProgressBar();
        mViewBinding.leadsRecyclerView.setVisibility(View.GONE);
        mViewModel.leads(sharedPreferencesHelper.getProviderId());

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (stop) {
                    mViewModel.leads(sharedPreferencesHelper.getProviderId());
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