package com.bidfrail.provider.ui.modelsheetbidonlead;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bidfrail.provider.R;
import com.bidfrail.provider.model.Lead;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.library.utilities.string.StringUtils;

public class BidOnLeadModalBottomSheet extends BottomSheetDialogFragment {

    private final Lead lead;
    private final BidOnLeadListener sendBillListener;

    public BidOnLeadModalBottomSheet(Lead lead, BidOnLeadListener listener) {
        this.lead = lead;
        this.sendBillListener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bid_on_lead_modal_bottom_sheet, container);
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

    private TextInputLayout bidAmountTextInputLayout, messageTextInputLayout;
    private TextInputEditText bidAmountTextInputEditText, messageTextInputEditText;
    private MaterialButton sendBidMaterialButton;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bidAmountTextInputLayout = view.findViewById(R.id.bidAmountTextInputLayout);
        bidAmountTextInputEditText = view.findViewById(R.id.bidAmountTextInputEditText);
        messageTextInputLayout = view.findViewById(R.id.messageTextInputLayout);
        messageTextInputEditText = view.findViewById(R.id.messageTextInputEditText);
        sendBidMaterialButton = view.findViewById(R.id.sendBidMaterialButton);

        sendBidMaterialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                String bidAmount = bidAmountTextInputEditText.getText().toString().trim();
                String message = messageTextInputEditText.getText().toString().trim();
                if (sendBidButtonValidation(bidAmount))
                {
                    sendBillListener.sendRates(Integer.parseInt(bidAmount), message);
                }
            }
        });
    }
    /***********************************************************************************************
     *******************************************Validations*****************************************
     **********************************************************************************************/
    private boolean sendBidButtonValidation(String bidAmount) {
        boolean isValid = true;
        if (StringUtils.isEmpty(bidAmount)) {
            bidAmountTextInputLayout.setError("Please enter bid amount");
            isValid = false;
        } else {
            bidAmountTextInputLayout.setError(null);
            bidAmountTextInputLayout.setErrorEnabled(false);
        }
        return isValid;
    }
}