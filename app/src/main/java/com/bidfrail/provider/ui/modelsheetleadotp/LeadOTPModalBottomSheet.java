package com.bidfrail.provider.ui.modelsheetleadotp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bidfrail.provider.R;
import com.bidfrail.provider.model.Lead;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.library.pinview.PinView;
import com.library.utilities.string.StringUtils;

public class LeadOTPModalBottomSheet extends BottomSheetDialogFragment {

    private final Lead lead;
    private final LeadOTPListener leadOTPListener;

    public LeadOTPModalBottomSheet(Lead lead, LeadOTPListener listener) {
        this.lead = lead;
        this.leadOTPListener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lead_otp_modal_bottom_sheet, container);
    }

    @Override
    public void onStart() {
        super.onStart();
        BottomSheetDialog dialog = (BottomSheetDialog) getDialog();
        assert dialog != null;

        dialog.getBehavior().setSkipCollapsed(true);
        dialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);

        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return false;   // if true then on BackPressed not work, if false on BackPressed work
                }
                return false;
            }
        });
    }

    private PinView pinView;
    private MaterialButton submitMaterialButton;

    private String inputOTP;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pinView = view.findViewById(R.id.pinView);
        submitMaterialButton = view.findViewById(R.id.submitMaterialButton);

        pinView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inputOTP = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        submitMaterialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (submitButtonValidation(inputOTP)) {
                    dismiss();
                    leadOTPListener.submitOTP(inputOTP);
                }
            }
        });
    }

    private boolean submitButtonValidation(String inputOTP) {
        if (StringUtils.isEmpty(inputOTP)) {
            Toast toast = Toast.makeText(getContext(), getString(R.string.register_step_two_error_otp_one), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        } else if (!inputOTP.equals(lead.getOtp())) {
            Toast toast = Toast.makeText(getContext(), getString(R.string.register_step_two_error_otp_two), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }
        return true;
    }
}