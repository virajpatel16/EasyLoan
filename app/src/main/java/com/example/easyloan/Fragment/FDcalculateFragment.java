package com.example.easyloan.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.easyloan.R;
import com.google.android.material.textview.MaterialTextView;

public class FDcalculateFragment extends Fragment {
    private EditText edtPrincipalAmount, edtRateOfInterest, edtYears, edtMonths;
    private TextView txtInvestmentAmount, txtTotalInterest, txtMaturityAmount;
    Button btn_fd_reset,btn_fd_calculate;
    MaterialTextView txt_back_emi;
    CardView resultfd;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_f_dcalculate, container, false);

        btn_fd_calculate=view.findViewById(R.id.btn_fd_calculate);
        btn_fd_reset=view.findViewById(R.id.btn_fd_reset);
        txt_back_emi=view.findViewById(R.id.txt_back_emi);

        edtPrincipalAmount = view.findViewById(R.id.edt_fd_principal_amount);
        edtRateOfInterest = view.findViewById(R.id.edt_fd_rate_of_interest);
        edtYears = view.findViewById(R.id.edt_time_for_years);
        edtMonths = view.findViewById(R.id.edt_time_for_month);

        txtInvestmentAmount = view.findViewById(R.id.txt_fd_investment_amount);
        txtTotalInterest = view.findViewById(R.id.txt_total_fd_interest);
        txtMaturityAmount = view.findViewById(R.id.txt_fd_maturity_amount);
        resultfd=view.findViewById(R.id.resultfd);

        resultfd.setVisibility(View.INVISIBLE);

        btn_fd_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFields();
            }
        });
        btn_fd_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultfd.setVisibility(View.VISIBLE);
                calculateFD();
            }
        });

        txt_back_emi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });
        addTextChangeListeners();
    return view;
    }
    private void resetFields() {
        edtPrincipalAmount.setText("");
        edtRateOfInterest.setText("");
        edtYears.setText("");
        edtMonths.setText("");
        resultfd.setVisibility(View.INVISIBLE);
        txtInvestmentAmount.setText("");
        txtTotalInterest.setText("");
        txtMaturityAmount.setText("");
    }  private void addTextChangeListeners() {
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
    private void calculateFD() {
        try {
            double principal = Double.parseDouble(edtPrincipalAmount.getText().toString());
            double rate = Double.parseDouble(edtRateOfInterest.getText().toString());
            double years = Double.parseDouble(edtYears.getText().toString());
            double months = Double.parseDouble(edtMonths.getText().toString());

            // Calculate logic (example: simple interest calculation)
            double totalInvestment = principal;
            double totalInterest = (principal * rate * years) / 100;
            double maturityAmount = principal + totalInterest;

            // Set values to TextViews
            txtInvestmentAmount.setText(String.format("%.2f", totalInvestment));
            txtTotalInterest.setText(String.format("%.2f", totalInterest));
            txtMaturityAmount.setText(String.format("%.2f", maturityAmount));

        } catch (NumberFormatException e) {
            e.printStackTrace();
            // Handle error in parsing input
        }
    }
}