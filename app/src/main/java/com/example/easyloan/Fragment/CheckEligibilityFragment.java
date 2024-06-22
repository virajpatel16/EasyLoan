package com.example.easyloan.Fragment;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.easyloan.R;
import com.google.android.material.textview.MaterialTextView;

import java.text.DecimalFormat;


public class CheckEligibilityFragment extends Fragment {

    MaterialTextView txt_back_emi;
    private EditText edtMonthlyEarning, edtExistingEMI, edtLoanInterestRate, edtLoanTenure;
    private Button btnCalculate,btnReset,btn_sms;
    private TextView tvResult;
    LinearLayout result_layout;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_check_eligibility, container, false);

        txt_back_emi=view.findViewById(R.id.txt_back_emi);
        edtMonthlyEarning = view.findViewById(R.id.edt_monthly_earning);
        edtExistingEMI =view. findViewById(R.id.edt_existing_emi_investment);
        edtLoanInterestRate =view. findViewById(R.id.edt_loan_interest_rate);
        edtLoanTenure = view.findViewById(R.id.edt_tenure_for_loan);
        btnCalculate =view. findViewById(R.id.btn_emi_calculate);
        btnReset =view. findViewById(R.id.btn_emi_reset);
        tvResult =view. findViewById(R.id.txt_result_loan_eligibility);
        result_layout=view.findViewById(R.id.result_layout);
        btn_sms=view.findViewById(R.id.btn_sms);
        txt_back_emi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateLoanEligibility();
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFields();
            }
        });

        result_layout.setVisibility(View.INVISIBLE);

        btn_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareLoanDetailsViaSMS();
            }
        });
    return view;
    }

    private void calculateLoanEligibility() {
        // Retrieve input values from EditText fields
        String monthlyEarningStr = edtMonthlyEarning.getText().toString().trim();
        String existingEMIStr = edtExistingEMI.getText().toString().trim();
        String loanInterestRateStr = edtLoanInterestRate.getText().toString().trim();
        String loanTenureStr = edtLoanTenure.getText().toString().trim();

        // Check if any input field is empty
        if (TextUtils.isEmpty(monthlyEarningStr) || TextUtils.isEmpty(existingEMIStr) ||
                TextUtils.isEmpty(loanInterestRateStr) || TextUtils.isEmpty(loanTenureStr)) {
            // Handle empty fields gracefully (optional: show error message)
            return;
        }

        try {
            // Convert input strings to respective numeric values
            double monthlyEarning = Double.parseDouble(monthlyEarningStr);
            double existingEMI = Double.parseDouble(existingEMIStr);
            double annualInterestRate = Double.parseDouble(loanInterestRateStr) / 100.0;
            int tenureYears = Integer.parseInt(loanTenureStr);

            // Calculate monthly interest rate
            double monthlyInterestRate = annualInterestRate / 12;

            // Calculate tenure in months
            int tenureMonths = tenureYears * 12;

            // Calculate EMI using formula: EMI = [P * r * (1 + r)^n] / [(1 + r)^n - 1]
            double emi = (monthlyEarning * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, tenureMonths))
                    / (Math.pow(1 + monthlyInterestRate, tenureMonths) - 1);

            // Calculate eligible loan amount based on monthly income and existing EMI
            double eligibleLoanAmount = (monthlyEarning - existingEMI) * emi;

            // Display result
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
            tvResult.setText(decimalFormat.format(eligibleLoanAmount));
            result_layout.setVisibility(View.VISIBLE);

        } catch (NumberFormatException e) {
            e.printStackTrace();
            // Handle parsing errors (optional: show error message)
        }
    }
    private void resetFields() {
        // Clear all EditText fields
        edtMonthlyEarning.setText("");
        edtExistingEMI.setText("");
        edtLoanInterestRate.setText("");
        edtLoanTenure.setText("");
        result_layout.setVisibility(View.INVISIBLE);
        tvResult.setText(""); // Clear result text
    }
    private void shareLoanDetailsViaSMS() {
        // Retrieve loan calculation details
        String monthlyEarningStr = edtMonthlyEarning.getText().toString().trim();
        String existingEMIStr = edtExistingEMI.getText().toString().trim();
        String loanInterestRateStr = edtLoanInterestRate.getText().toString().trim();
        String loanTenureStr = edtLoanTenure.getText().toString().trim();
        String resultStr = tvResult.getText().toString().trim();

        // Prepare the message to share via SMS
        String message = "Loan Details:\n" +
                "Monthly Earning: $" + monthlyEarningStr + "\n" +
                "Existing EMI: $" + existingEMIStr + "\n" +
                "Loan Interest Rate: " + loanInterestRateStr + "%\n" +
                "Loan Tenure: " + loanTenureStr + " years\n" +
                "Eligible Loan Amount: $" + resultStr;

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