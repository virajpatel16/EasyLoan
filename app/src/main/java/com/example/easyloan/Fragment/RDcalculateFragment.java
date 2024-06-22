package com.example.easyloan.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyloan.R;
import com.google.android.material.textview.MaterialTextView;

public class RDcalculateFragment extends Fragment {

    MaterialTextView txt_back_emi;

    private EditText edtPrincipalAmount, edtRateOfInterest, edtYears, edtMonths;
    private TextView txtInvestmentAmount, txtTotalInterest, txtMaturityAmount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_r_dcalculate, container, false);
        txt_back_emi = view.findViewById(R.id.txt_back_emi);
        // Initialize views
        edtPrincipalAmount = view.findViewById(R.id.edt_rd_principal_amount);
        edtRateOfInterest = view.findViewById(R.id.edt_rd_rate_of_interest);
        edtYears = view.findViewById(R.id.edt_time_for_years);
        edtMonths = view.findViewById(R.id.edt_time_for_month);
        txtInvestmentAmount = view.findViewById(R.id.txt_rd_investment_amount);
        txtTotalInterest = view.findViewById(R.id.txt_total_rd_interest);
        txtMaturityAmount = view.findViewById(R.id.txt_rd_maturity_amount);

        // Optional: Add TextChangedListeners to calculate on-the-fly
        addTextChangeListeners();
        view.findViewById(R.id.btn_rd_calculate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateRD();
            }
        });

        // Reset button click listener
        view.findViewById(R.id.btn_rd_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFields();
            }
        });

        txt_back_emi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });
        // Method to add TextChangedListeners for real-time calculation (optional)


    return view;
    }
    private void calculateRD() {
        try {
            double principal = Double.parseDouble(edtPrincipalAmount.getText().toString());
            double rate = Double.parseDouble(edtRateOfInterest.getText().toString());
            double years = Double.parseDouble(edtYears.getText().toString());
            double months = Double.parseDouble(edtMonths.getText().toString());

            // Calculate logic (compound interest calculation for RD)
            double n = 4; // Compounded quarterly
            double totalMonths = years * 12 + months;
            double maturityAmount = 0;
            for (int i = 0; i < totalMonths; i++) {
                maturityAmount += principal * Math.pow(1 + (rate / (n * 100)), n * ((totalMonths - i) / 12.0));
            }

            // Set values to TextViews
            txtInvestmentAmount.setText(String.format("%.2f", principal * totalMonths));
            txtTotalInterest.setText(String.format("%.2f", maturityAmount - (principal * totalMonths)));
            txtMaturityAmount.setText(String.format("%.2f", maturityAmount));

        } catch (NumberFormatException e) {
            e.printStackTrace();
            // Handle error in parsing input
            Toast.makeText(requireActivity(), "Please enter valid numbers", Toast.LENGTH_SHORT).show();
        }
    }



    // Method to reset all input fields
    private void resetFields() {
        edtPrincipalAmount.setText("");
        edtRateOfInterest.setText("");
        edtYears.setText("");
        edtMonths.setText("");
        txtInvestmentAmount.setText("");
        txtTotalInterest.setText("");
        txtMaturityAmount.setText("");
    }
    // Method to add TextChangedListeners for real-time calculation (optional)
    private void addTextChangeListeners() {
        // Example for Principal Amount
        edtPrincipalAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                // Calculate something or validate input
            }
        });

        // Similar listeners can be added for other input fields
    }
}