package com.bidfrail.provider.ui.modelsheetsendbill;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
import java.util.Objects;

public class SendBillModalBottomSheet extends BottomSheetDialogFragment {

    private final Lead lead;
    private final SendBillListener sendBillListener;

    public SendBillModalBottomSheet(Lead lead, SendBillListener listener) {
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
        return inflater.inflate(R.layout.fragment_send_bill_modal_bottom_sheet, container);
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


    private TextView totalMRPValueTextView;
    private TextView couponDiscountHeadingTextView;
    private TextView couponDiscountValueTextView;
    private TextView convenienceFeeValueTextView;
    private TextView taxChargesValueTextView;
    private TextView billTotalValueTextView;

    private TextInputLayout giveFinalRateTextInputLayout, descriptionTextInputLayout;
    private TextInputEditText giveFinalRateTextInputEditText, descriptionTextInputEditText;
    private MaterialButton sendRatesMaterialButton;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        totalMRPValueTextView = view.findViewById(R.id.totalMRPValueTextView);
        totalMRPValueTextView.setText("\u20B9 "+lead.getBookingBill().getTotalAmount());

        couponDiscountHeadingTextView = view.findViewById(R.id.couponDiscountHeadingTextView);
        couponDiscountValueTextView = view.findViewById(R.id.couponDiscountValueTextView);

        if (StringUtils.isBlank(String.valueOf(lead.getBookingBill().getCouponAmount()))) {
            couponDiscountValueTextView.setText("\u20B9 "+0);
        } else {
            couponDiscountValueTextView.setText("\u20B9 "+lead.getBookingBill().getCouponAmount());
        }

        if (Objects.equals(lead.getOrderType(), "get_bids")) {
            couponDiscountHeadingTextView.setVisibility(View.GONE);
            couponDiscountValueTextView.setVisibility(View.GONE);
        } else if (Objects.equals(lead.getOrderType(), "book_now")) {
            couponDiscountHeadingTextView.setVisibility(View.VISIBLE);
            couponDiscountValueTextView.setVisibility(View.VISIBLE);
        }

        convenienceFeeValueTextView = view.findViewById(R.id.convenienceFeeValueTextView);
        convenienceFeeValueTextView.setText("\u20B9 "+lead.getSubCategory().getConvenienceFee());

        taxChargesValueTextView = view.findViewById(R.id.taxChargesValueTextView);
        taxChargesValueTextView.setText("\u20B9 "+lead.getSubCategory().getTaxAndCharges());

        billTotalValueTextView = view.findViewById(R.id.billTotalValueTextView);
        billTotalValueTextView.setText("\u20B9 "+lead.getBookingBill().getPayAmount());

        giveFinalRateTextInputLayout = view.findViewById(R.id.giveFinalRateTextInputLayout);
        giveFinalRateTextInputEditText = view.findViewById(R.id.giveFinalRateTextInputEditText);
        descriptionTextInputLayout = view.findViewById(R.id.descriptionTextInputLayout);
        descriptionTextInputEditText = view.findViewById(R.id.descriptionTextInputEditText);

        sendRatesMaterialButton = view.findViewById(R.id.sendRatesMaterialButton);

        sendRatesMaterialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String billAmount = giveFinalRateTextInputEditText.getText().toString().trim();
                String description = descriptionTextInputEditText.getText().toString().trim();

                if (StringUtils.isBlank(billAmount)) {
                    billAmount =  "0";
                }

                if (StringUtils.isBlank(description)) {
                    description =  "null";
                }

                dismiss();
                sendBillListener.sendRates(Integer.parseInt(billAmount), description);
            }
        });
    }
}