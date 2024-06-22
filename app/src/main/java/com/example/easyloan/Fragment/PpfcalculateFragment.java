package com.example.easyloan.Fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.easyloan.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class PpfcalculateFragment extends Fragment {

    private EditText edtInvestmentAmount, edtRateOfInterest, edtDuration;
    private Button btnCalculate, btnReset;
    private CardView ppfLayout;
    private PieChart totalInterestChart;
    MaterialTextView txt_back_emi;
    private TextView txtTotalInvestment, txtTotalInterestValue, txtMaturityAmount;

    private PieChart pieChart;
    private float investmentAmount, rateOfInterest, duration;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ppfcalculate, container, false);
        edtInvestmentAmount = view.findViewById(R.id.edt_investment_amount);
        edtRateOfInterest = view.findViewById(R.id.edt_rate_of_interest);
        edtDuration = view.findViewById(R.id.duration);
        btnCalculate = view.findViewById(R.id.btn_emi_calculate);
        btnReset = view.findViewById(R.id.btn_emi_reset);
        txt_back_emi=view.findViewById(R.id.txt_back_emi);
        ppfLayout = view.findViewById(R.id.ppf_layout);
        totalInterestChart = view.findViewById(R.id.total_interest_chart);
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculatePPF();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
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
        return view;
    }

    private void calculatePPF() {
        if (validateInput()) {


            double investment = Double.parseDouble(edtInvestmentAmount.getText().toString());
            double interestRate = Double.parseDouble(edtRateOfInterest.getText().toString())/100;
            int period = Integer.parseInt(edtDuration.getText().toString());
            // Retrieve input values from EditText fields
            double totalInvestment = investment * period;
            double maturityAmount = calculateMaturityAmount((float) investment, (float) interestRate, period);
            double totalInterest = maturityAmount - totalInvestment;




            // Display the results in TextViews
            ppfLayout.setVisibility(View.VISIBLE);

            TextView txtTotalInvestment = getView().findViewById(R.id.txt_total_investment);
            TextView txtTotalInterestValue = getView().findViewById(R.id.txt_total_interest_value);
            TextView txtMaturityAmount = getView().findViewById(R.id.txt_maturity_amount);

            txtTotalInvestment.setText(String.format("%.2f", totalInvestment));
            txtTotalInterestValue.setText(String.format("%.2f", totalInterest));
            txtMaturityAmount.setText(String.format("%.2f", maturityAmount));

            // Assuming you have a method to update a pie chart with the values
            updatePieChart((float) totalInterest, (float) totalInvestment);
        } else {
            // Show a toast message if input validation fails
            Toast.makeText(requireActivity(), "Please enter valid input", Toast.LENGTH_SHORT).show();
        }
    }
    private void resetFields() {
        edtInvestmentAmount.setText("");
        edtRateOfInterest.setText("");
        edtDuration.setText("");

        ppfLayout.setVisibility(View.GONE);
        totalInterestChart.setVisibility(View.GONE);


    }
    private boolean validateInput() {
        return !edtInvestmentAmount.getText().toString().isEmpty() &&
                !edtRateOfInterest.getText().toString().isEmpty() &&
                !edtDuration.getText().toString().isEmpty();
    }
    private void updatePieChart(float totalInterest, float totalInvestment) {
        totalInterestChart.setVisibility(View.VISIBLE);

        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(totalInterest, "Interest"));
        entries.add(new PieEntry(totalInvestment, "totalInvestment"));

        PieDataSet dataSet = new PieDataSet(entries, "Interest Distribution");
        dataSet.setColors(new int[]{R.color.selectedTextColor, R.color.app_bg_color}, getActivity());

        PieData data = new PieData(dataSet);
        totalInterestChart.setData(data);

        Description description = new Description();
        description.setText("Interest Distribution");
        totalInterestChart.setDescription(description);
        totalInterestChart.invalidate(); // refresh chart

    }
    private double calculateMaturityAmount(float investmentAmount, float rateOfInterest, float duration) {
        // Convert rate of interest from percentage to decimal

        // Calculate maturity amount using the formula
        double maturityAmount = investmentAmount * ((Math.pow(1 + rateOfInterest, duration) - 1) / rateOfInterest) * (1 + rateOfInterest);

        return maturityAmount;
    }



}