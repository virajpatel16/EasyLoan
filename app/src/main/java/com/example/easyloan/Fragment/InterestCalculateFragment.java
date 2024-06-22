package com.example.easyloan.Fragment;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.easyloan.R;
import com.google.android.material.textview.MaterialTextView;

import java.text.DecimalFormat;


public class InterestCalculateFragment extends Fragment {


    private EditText edtPrincipalAmount, edtRateOfInterest, edtYears, edtMonths;
    private TextView txtSimpleInterest, txtPrincipalAmount, txtTotalAmount;
    private DecimalFormat decimalFormat;
    MaterialTextView txt_back_emi;
    Button btn_interest_calculate,btn_interest_reset;
    CardView resultduration;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_interest_calculate, container, false);

        edtPrincipalAmount = view.findViewById(R.id.edt_interest_principal_amount);
        edtRateOfInterest = view.findViewById(R.id.edt_interest_rate_of_interest);
        edtYears = view.findViewById(R.id.edt_time_for_years);
        edtMonths = view.findViewById(R.id.edt_time_for_month);
        txtSimpleInterest = view.findViewById(R.id.txt_simple_interest);
        txtPrincipalAmount = view.findViewById(R.id.txt_principal_amount);
        txtTotalAmount = view.findViewById(R.id.txt_total_amount);
        btn_interest_calculate=view.findViewById(R.id.btn_interest_calculate);
        btn_interest_reset=view.findViewById(R.id.btn_interest_reset);
        resultduration=view.findViewById(R.id.resultduration);
        // Initialize DecimalFormat for currency formatting
        txt_back_emi=view.findViewById(R.id.txt_back_emi);
        decimalFormat = new DecimalFormat("#,##0.00");
        resultduration.setVisibility(View.INVISIBLE);
        txt_back_emi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();

            }
        });
        btn_interest_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateSimpleInterest();
                resultduration.setVisibility(View.VISIBLE);
            }
        });

        btn_interest_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtSimpleInterest.setText("");
                txtPrincipalAmount.setText("");
                txtTotalAmount.setText("");

                resultduration.setVisibility(View.INVISIBLE);
            }
        });

    return  view;
    }
    private void calculateSimpleInterest() {
        String strPrincipalAmount = edtPrincipalAmount.getText().toString().trim();
        String strRateOfInterest = edtRateOfInterest.getText().toString().trim();
        String strYears = edtYears.getText().toString().trim();
        String strMonths = edtMonths.getText().toString().trim();

        if (!strPrincipalAmount.isEmpty() && !strRateOfInterest.isEmpty()
                && !strYears.isEmpty() && !strMonths.isEmpty()) {

            double principalAmount = Double.parseDouble(strPrincipalAmount);
            double rateOfInterest = Double.parseDouble(strRateOfInterest);
            double years = Double.parseDouble(strYears);
            double months = Double.parseDouble(strMonths);

            double totalYears = years + (months / 12.0);
            double simpleInterest = (principalAmount * rateOfInterest * totalYears) / 100.0;
            double totalAmount = principalAmount + simpleInterest;

            txtSimpleInterest.setText(getString(R.string.simple_interest_result, decimalFormat.format(simpleInterest)));
            txtPrincipalAmount.setText(getString(R.string.principal_amount_result, decimalFormat.format(principalAmount)));
            txtTotalAmount.setText(getString(R.string.total_amount_result, decimalFormat.format(totalAmount)));
        } else {
            // Clear result if any field is empty
            txtSimpleInterest.setText("");
            txtPrincipalAmount.setText("");
            txtTotalAmount.setText("");
        }
    }
}