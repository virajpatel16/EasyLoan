package com.example.easyloan.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.easyloan.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textview.MaterialTextView;

import java.text.DecimalFormat;

public class SipcalculateFragment extends Fragment {

    private EditText edtRegularMonthlyDeposit, edtRateOfInterest, edtTenureYears, edtTenureMonths;
    private TextView txtInvestmentAmount, txtTotalInterest, txtSipMaturityAmount;
    private ExtendedFloatingActionButton btnReset, btnCalculate;
    private CardView cardSipDetails;
    MaterialTextView txt_back_emi;
    private DecimalFormat decimalFormat;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sipcalculate, container, false);


        // Initialize views
        edtRegularMonthlyDeposit = view.findViewById(R.id.edt_regular_monthly_depostie);
        edtRateOfInterest = view.findViewById(R.id.edt_rate_of_interest);
        edtTenureYears = view.findViewById(R.id.edt_tenure_for_years);
        edtTenureMonths = view.findViewById(R.id.edt_tenure_for_month);

        txtInvestmentAmount = view.findViewById(R.id.txt_invetment_amount);
        txtTotalInterest = view.findViewById(R.id.txt_total_interest);
        txtSipMaturityAmount = view.findViewById(R.id.txt_sip_maturity_amount);

        btnReset = view.findViewById(R.id.btn_emi_reset);
        btnCalculate = view.findViewById(R.id.btn_emi_calculate);

        cardSipDetails = view.findViewById(R.id.card_sip_details);
        txt_back_emi=view.findViewById(R.id.txt_back_emi);
        decimalFormat = new DecimalFormat("#,##,##0.00");

        txt_back_emi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });
        // Reset button click listener
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFields();
            }
        });
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateSip();
            }
        });
   return  view;
    }
    private void resetFields() {
        edtRegularMonthlyDeposit.setText("");
        edtRateOfInterest.setText("");
        edtTenureYears.setText("");
        edtTenureMonths.setText("");

        txtInvestmentAmount.setText("");
        txtTotalInterest.setText("");
        txtSipMaturityAmount.setText("");

        cardSipDetails.setVisibility(View.GONE);
    }

    private void calculateSip() {
        String strRegularMonthlyDeposit = edtRegularMonthlyDeposit.getText().toString().trim();
        String strRateOfInterest = edtRateOfInterest.getText().toString().trim();
        String strTenureYears = edtTenureYears.getText().toString().trim();
        String strTenureMonths = edtTenureMonths.getText().toString().trim();

        if (!strRegularMonthlyDeposit.isEmpty() && !strRateOfInterest.isEmpty()
                && !strTenureYears.isEmpty() && !strTenureMonths.isEmpty()) {

            double regularMonthlyDeposit = Double.parseDouble(strRegularMonthlyDeposit);
            double annualRateOfInterest = Double.parseDouble(strRateOfInterest);
            int tenureYears = Integer.parseInt(strTenureYears);
            int tenureMonths = Integer.parseInt(strTenureMonths);

            // Calculate investment amount
            double investmentAmount = calculateInvestmentAmount(regularMonthlyDeposit, tenureYears, tenureMonths);
            // Calculate SIP maturity amount
            double sipMaturityAmount = calculateSipMaturityAmount(regularMonthlyDeposit, annualRateOfInterest, tenureYears, tenureMonths);
            // Calculate total interest earned
            double totalInterest = sipMaturityAmount - investmentAmount;

            // Update UI with formatted values
            txtInvestmentAmount.setText(getString(R.string.currency_format, decimalFormat.format(investmentAmount)));
            txtTotalInterest.setText(getString(R.string.currency_format, decimalFormat.format(totalInterest)));
            txtSipMaturityAmount.setText(getString(R.string.currency_format, decimalFormat.format(sipMaturityAmount)));

            // Show the SIP details card
            cardSipDetails.setVisibility(View.VISIBLE);
        } else {
            // If any of the fields are empty, hide the SIP details card
            cardSipDetails.setVisibility(View.GONE);
        }
    }

    private double calculateInvestmentAmount(double regularMonthlyDeposit, int tenureYears, int tenureMonths) {
        int totalMonths = tenureYears * 12 + tenureMonths;
        return regularMonthlyDeposit * totalMonths;
    }

    private double calculateSipMaturityAmount(double regularMonthlyDeposit, double annualRateOfInterest, int tenureYears, int tenureMonths) {
        int totalMonths = tenureYears * 12 + tenureMonths;
        double monthlyRateOfInterest = annualRateOfInterest / 12 / 100;
        double sipMaturityAmount = 0.0;

        for (int i = 0; i < totalMonths; i++) {
            sipMaturityAmount += regularMonthlyDeposit * Math.pow(1 + monthlyRateOfInterest, totalMonths - i);
        }

        return sipMaturityAmount;
    }
}