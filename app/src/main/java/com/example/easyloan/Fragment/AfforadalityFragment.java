package com.example.easyloan.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.easyloan.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textview.MaterialTextView;

public class AfforadalityFragment extends Fragment {

    private EditText edtAffordableAmount, edtRateOfInterest, edtTenureForLoan;
    private TextView txtMaximumLoan, txtTotalAmount;
    private ExtendedFloatingActionButton btnCalculate, btnReset, btnSms;
    LinearLayout result_layout;
    MaterialTextView txt_back_emi;
    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_afforadality, container, false);

        // Initialize UI components
        edtAffordableAmount = view.findViewById(R.id.edt_affordable_amount);
        result_layout=view.findViewById(R.id.result_layout_afforadality);
        result_layout=view.findViewById(R.id.result_layout_afforadality1);
        edtRateOfInterest = view.findViewById(R.id.edt_rate_of_interest);
        edtTenureForLoan = view.findViewById(R.id.edt_tenure_for_loan);
        txtMaximumLoan = view.findViewById(R.id.txt_maximum_loan);
        txtTotalAmount = view.findViewById(R.id.txt_total_amount);
        btnCalculate = view.findViewById(R.id.btn_emi_calculate);
        btnReset = view.findViewById(R.id.btn_emi_reset);
        btnSms = view.findViewById(R.id.btn_sms);
        txt_back_emi=view.findViewById(R.id.txt_back_emi);

        // Set button click listeners
        btnCalculate.setOnClickListener(v -> calculateLoanAffordability());
        btnReset.setOnClickListener(v -> resetFields());
        btnSms.setOnClickListener(v -> shareLoanDetailsViaSMS());
        result_layout.setVisibility(View.INVISIBLE);

        txt_back_emi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });

        return view;
    }



    private void calculateLoanAffordability() {
        String amountStr = edtAffordableAmount.getText().toString().trim();
        String interestStr = edtRateOfInterest.getText().toString().trim();
        String tenureStr = edtTenureForLoan.getText().toString().trim();
        result_layout.setVisibility(View.VISIBLE);

        if (TextUtils.isEmpty(amountStr) || TextUtils.isEmpty(interestStr) || TextUtils.isEmpty(tenureStr)) {
            Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double amount = Double.parseDouble(amountStr);
            double annualInterestRate = Double.parseDouble(interestStr);
            int tenureYears = Integer.parseInt(tenureStr);

            // Basic input validation
            if (amount <= 0 || annualInterestRate <= 0 || tenureYears <= 0) {
                Toast.makeText(getContext(), "Please enter positive values", Toast.LENGTH_SHORT).show();
                return;
            }

            // Calculate monthly interest rate
            double monthlyInterestRate = annualInterestRate / 100 / 12;

            // Calculate tenure in months
            int tenureMonths = tenureYears * 12;

            // Calculate EMI using the formula: E = P * r * (1 + r)^n / ((1 + r)^n - 1)
            double emi = (amount * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, tenureMonths)) /
                    (Math.pow(1 + monthlyInterestRate, tenureMonths) - 1);

            // Calculate total amount payable
            double totalAmount = emi * tenureMonths;

            // Display results
            txtMaximumLoan.setText(String.format("%.2f", amount));
            txtTotalAmount.setText(String.format("%.2f", totalAmount));

        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Invalid input format", Toast.LENGTH_SHORT).show();
        }
    }



    private void resetFields() {
        edtAffordableAmount.setText("");
        edtRateOfInterest.setText("");
        edtTenureForLoan.setText("");
        txtMaximumLoan.setText("");
        txtTotalAmount.setText("");
        result_layout.setVisibility(View.INVISIBLE);

    }

    private void shareLoanDetailsViaSMS() {
        // Retrieve loan calculation details
        String AffordableAmount = edtAffordableAmount.getText().toString().trim();
        String RateOfInterest = edtRateOfInterest.getText().toString().trim();
        String TenureForLoan = edtTenureForLoan.getText().toString().trim();
        String MaximumLoan = txtMaximumLoan.getText().toString().trim();
        String TotalAmount = txtTotalAmount.getText().toString().trim();

        // Prepare the message to share via SMS
        String message = "Loan Details:\n" +
                "Monthly Earning: $" + AffordableAmount + "\n" +
                "Existing EMI: $" + RateOfInterest + "\n" +
                "Loan Interest Rate: " + TenureForLoan + "%\n" +
                "Loan Tenure: " + MaximumLoan + " years\n" +
                "Eligible Loan Amount: $" + TotalAmount;

        // Create an intent to share via SMS
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_TEXT, message);

        // Check if there is an app available to handle this intent
        if (sendIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivity(sendIntent);
        } else {
            // Handle case where no app is available to handle SMS sharing
            // Optional: show a message to the user
        }
    }
}
