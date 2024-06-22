package com.example.easyloan.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.easyloan.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.text.DecimalFormat;


public class GstCalculateFragment extends Fragment {

    private EditText edtInitialAmount, edtGstRate;
    private RadioGroup radioGroup;
    private RadioButton addGstRadioButton, subtractGstRadioButton;
    private TextView txtNetAmount, txtGstAmount, txtOriginalPrice;
    private CardView resultCard;
    ImageView gst_back;
    private final DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=  inflater.inflate(R.layout.fragment_gst_calculate, container, false);


        edtInitialAmount = view.findViewById(R.id.initial_amount_edit_text);
        edtGstRate = view.findViewById(R.id.gst_rate_edit_text);
        radioGroup = view.findViewById(R.id.radio_group);
        addGstRadioButton = view.findViewById(R.id.add_gst_radio_button);
        subtractGstRadioButton = view.findViewById(R.id.subtract_gst_radio_button);
        txtNetAmount = view.findViewById(R.id.net_amount_edit_text);
        txtGstAmount = view.findViewById(R.id.gst_amount_edit_text);
        txtOriginalPrice = view.findViewById(R.id.total_gst_involves);
        resultCard = view.findViewById(R.id.resultduration);
        gst_back=view.findViewById(R.id.gst_back);

        ExtendedFloatingActionButton btnCalculate = view.findViewById(R.id.btn_emi_calculate);
        ExtendedFloatingActionButton btnReset = view.findViewById(R.id.btn_emi_reset);

        gst_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });
        btnCalculate.setOnClickListener(v -> calculateGst());
        btnReset.setOnClickListener(v -> resetFields());
        return  view;
    }
    private void calculateGst() {
        String strInitialAmount = edtInitialAmount.getText().toString().trim();
        String strGstRate = edtGstRate.getText().toString().trim();

        if (!strInitialAmount.isEmpty() && !strGstRate.isEmpty()) {
            double initialAmount = Double.parseDouble(strInitialAmount);
            double gstRate = Double.parseDouble(strGstRate);
            double gstAmount, netAmount;

            if (addGstRadioButton.isChecked()) {
                // Add GST
                gstAmount = initialAmount * gstRate / 100;
                netAmount = initialAmount + gstAmount;
                txtOriginalPrice.setText(getString(R.string.original_price, decimalFormat.format(initialAmount)));
            } else if (subtractGstRadioButton.isChecked()) {
                // Subtract GST
                netAmount = initialAmount / (1 + (gstRate / 100));
                gstAmount = initialAmount - netAmount;
                txtOriginalPrice.setText(getString(R.string.original_price, decimalFormat.format(netAmount)));
            } else {
                // If no radio button is selected, show nothing
                return;
            }

            // Update UI with formatted values
            txtGstAmount.setText(getString(R.string.gst_amount, decimalFormat.format(gstAmount)));
            txtNetAmount.setText(getString(R.string.net_amount, decimalFormat.format(netAmount)));

            // Show the result card
            resultCard.setVisibility(View.VISIBLE);
        } else {
            // If any of the fields are empty, hide the result card
            resultCard.setVisibility(View.GONE);
        }
    }

    private void resetFields() {
        edtInitialAmount.setText("");
        edtGstRate.setText("");
        radioGroup.clearCheck();
        resultCard.setVisibility(View.GONE);
    }
}