package com.example.easyloan.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyloan.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

public class CompareloanFragment extends Fragment {

    private TextInputEditText edtPrincipalFirst, edtPrincipalSecond, edtRateFirst, edtRateSecond, edtTermFirst, edtTermSecond, edtMonthlyEMIFirst, edtMonthlyEMISecond, edtInterestFirst, edtInterestSecond, edtPaymentFirst, edtPaymentSecond;
    private TextView edtEMIDiffer, edtInterestDiffer, edtPaymentDiffer, tvYears, tvMonths;
    private int monthOrYear = 1; // Default to years
    private Button btnReset, btnCalculate;
    CardView result_compareloan;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_compareloan, container, false);

        edtPrincipalFirst = view.findViewById(R.id.edt_principal_first);
        edtPrincipalSecond = view.findViewById(R.id.edt_principal_second);
        edtRateFirst = view.findViewById(R.id.edt_rate_first);
        edtRateSecond = view.findViewById(R.id.edt_rate_second);
        edtTermFirst = view.findViewById(R.id.edt_term_first);
        edtTermSecond = view.findViewById(R.id.edt_term_second);
        tvYears = view.findViewById(R.id.tv_years);
        tvMonths = view.findViewById(R.id.tv_months);
        btnReset = view.findViewById(R.id.btn_business_reset);
        btnCalculate = view.findViewById(R.id.btn_business_calculate);
        edtMonthlyEMIFirst = view.findViewById(R.id.edt_monthlyEMI_first);
        edtMonthlyEMISecond = view.findViewById(R.id.edt_monthlyEMI_second);
        edtInterestFirst = view.findViewById(R.id.edt_interest_first);
        edtInterestSecond = view.findViewById(R.id.edt_interest_second);
        edtPaymentFirst = view.findViewById(R.id.edt_payment_first);
        edtPaymentSecond = view.findViewById(R.id.edt_payment_second);
        edtEMIDiffer = view.findViewById(R.id.edt_emi_differ);
        edtInterestDiffer = view.findViewById(R.id.edt_interest_differ);
        result_compareloan=view.findViewById(R.id.result_compareloan);
        edtPaymentDiffer = view.findViewById(R.id.edt_payment_differ);
        MaterialToolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              requireActivity().finish();
            }
        });
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate(v);
                result_compareloan.setVisibility(View.VISIBLE);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset(v);
            }
        });
        result_compareloan.setVisibility(View.INVISIBLE);

        tvMonths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    setColorsOfSelectedTextView(tvYears, tvMonths);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Set monthOrYear to 2 indicating months
                monthOrYear = 2;
            }
        });

        tvYears.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    setColorsOfSelectedTextView(tvMonths, tvYears);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Set monthOrYear to 1 indicating years
                monthOrYear = 1;
            }
        });

    return view;
    }
    private void reset(View view) {
        // Clear the text of all TextInputEditText fields
        edtPrincipalFirst.setText("");
        edtPrincipalSecond.setText("");
        edtRateFirst.setText("");
        edtRateSecond.setText("");
        edtTermFirst.setText("");
        edtTermSecond.setText("");
        edtMonthlyEMIFirst.setText("");
        edtMonthlyEMISecond.setText("");
        edtInterestFirst.setText("");
        edtInterestSecond.setText("");
        result_compareloan.setVisibility(View.INVISIBLE);
        edtPaymentFirst.setText("");
        edtPaymentSecond.setText("");
        edtEMIDiffer.setText("Difference :");
        edtInterestDiffer.setText("Difference :");
        edtPaymentDiffer.setText("Difference :");
        Toast.makeText(getContext(), "Value is 0", Toast.LENGTH_SHORT).show();
    }
    private void calculate(View view) {

        // Hide the soft keyboard
        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        try {
            // Parse input values
            double principalFirst = Double.parseDouble(edtPrincipalFirst.getText().toString());
            double principalSecond = Double.parseDouble(edtPrincipalSecond.getText().toString());
            double rateFirst = Double.parseDouble(edtRateFirst.getText().toString());
            double rateSecond = Double.parseDouble(edtRateSecond.getText().toString());
            double termFirst = Double.parseDouble(edtTermFirst.getText().toString());
            double termSecond = Double.parseDouble(edtTermSecond.getText().toString());

            // Calculate term in months
            double termMonthsFirst = termFirst * (monthOrYear == 1 ? 12 : 1);
            double termMonthsSecond = termSecond * (monthOrYear == 1 ? 12 : 1);

            // Calculate monthly payment for both loans
            double monthlyPaymentFirst = calculateEmi(principalFirst, rateFirst, termMonthsFirst);
            double monthlyPaymentSecond = calculateEmi(principalSecond, rateSecond, termMonthsSecond);

            // Display the calculated monthly payments in the respective EditTexts
            edtMonthlyEMIFirst.setText(String.format("%.2f", monthlyPaymentFirst));
            edtMonthlyEMISecond.setText(String.format("%.2f", monthlyPaymentSecond));

            // Calculate total interest for both loans
            double totalInterestFirst = (monthlyPaymentFirst * termMonthsFirst) - principalFirst;
            double totalInterestSecond = (monthlyPaymentSecond * termMonthsSecond) - principalSecond;

            // Display the calculated total interest in the respective EditTexts
            edtInterestFirst.setText(String.format("%.2f", totalInterestFirst));
            edtInterestSecond.setText(String.format("%.2f", totalInterestSecond));

            // Calculate total payment for both loans
            double totalPaymentFirst = principalFirst + totalInterestFirst;
            double totalPaymentSecond = principalSecond + totalInterestSecond;

            // Display the calculated total payments in the respective EditTexts
            edtPaymentFirst.setText(String.format("%.2f", totalPaymentFirst));
            edtPaymentSecond.setText(String.format("%.2f", totalPaymentSecond));

            // Calculate and display the difference for each parameter
            double emiDifference = Math.abs(monthlyPaymentFirst - monthlyPaymentSecond);
            double interestDifference = Math.abs(totalInterestFirst - totalInterestSecond);
            double paymentDifference = Math.abs(totalPaymentFirst - totalPaymentSecond);

            edtEMIDiffer.setText("Loan 1 " + (monthlyPaymentFirst < monthlyPaymentSecond ? "lower" : "higher") + " by " + String.format("%.2f", emiDifference));
            edtInterestDiffer.setText("Loan 1 " + (totalInterestFirst < totalInterestSecond ? "lower" : "higher") + " by " + String.format("%.2f", interestDifference));
            edtPaymentDiffer.setText("Loan 1 " + (totalPaymentFirst < totalPaymentSecond ? "lower" : "higher") + " by " + String.format("%.2f", paymentDifference));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private double calculateEmi(double principal, double rate, double termMonths) {
        double monthlyRate = rate / (12 * 100); // Monthly interest rate
        return (principal * monthlyRate * Math.pow(1 + monthlyRate, termMonths)) / (Math.pow(1 + monthlyRate, termMonths) - 1);
    }

    private void setColorsOfSelectedTextView(TextView textView, TextView textView2) {
        textView2.setBackgroundColor(requireContext().getResources().getColor(R.color.white));
        textView2.setTextColor(requireContext().getResources().getColor(R.color.black));
        textView.setBackground(requireContext().getResources().getDrawable(R.drawable.gradient));
        textView.setTextColor(requireContext().getResources().getColor(R.color.white));
    }
}