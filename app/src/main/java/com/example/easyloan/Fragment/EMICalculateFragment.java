package com.example.easyloan.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.easyloan.Activity.History;
import com.example.easyloan.Roomdatabase.AppDatabse;
import com.example.easyloan.R;

import com.example.easyloan.Roomdatabase.User;
import com.example.easyloan.Roomdatabase.UserDao;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.textview.MaterialTextView;


import java.util.ArrayList;

public class EMICalculateFragment extends Fragment {

    private EditText edtPrincipalAmount, edtRateOfInterest, edtTenureYears, edtTenureMonths;
    private TextView txtEMIResult, txtTotalPayment;
    private PieChart pieChart;

    MaterialTextView txt_back_emi;


    ImageView history;

LinearLayout totalemi,totalpayment,piechart_liear;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_e_m_i_calculate, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize UI elements
        edtPrincipalAmount = view.findViewById(R.id.edt_affordable_amount);
        edtRateOfInterest = view.findViewById(R.id.edt_rate_of_interest);
        edtTenureYears = view.findViewById(R.id.edt_tenure_for_years);
        edtTenureMonths = view.findViewById(R.id.edt_tenure_for_month);
        txtEMIResult = view.findViewById(R.id.txt_emi_result);
        txtTotalPayment = view.findViewById(R.id.txt_total_payment);
        pieChart = view.findViewById(R.id.total_emi_chart);
        txt_back_emi=view.findViewById(R.id.txt_back_emi);
        totalemi=view.findViewById(R.id.totalemi);
        totalpayment=view.findViewById(R.id.totalpayment);
        piechart_liear=view.findViewById(R.id.piechart_liear);
        history=view.findViewById(R.id.history);
        // Setup button click listeners

        totalemi.setVisibility(View.INVISIBLE);
        totalpayment.setVisibility(View.INVISIBLE);
        piechart_liear.setVisibility(View.INVISIBLE);
        txt_back_emi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new bgthred().start();
                startActivity(new Intent(requireActivity().getApplicationContext(), History.class));
            }
        });
        view.findViewById(R.id.btn_emi_calculate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateEMI();


                totalemi.setVisibility(View.VISIBLE);
                totalpayment.setVisibility(View.VISIBLE);
                piechart_liear.setVisibility(View.VISIBLE);
            }
        });

        view.findViewById(R.id.btn_emi_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFields();
            }
        });



    }

    private void calculateEMI() {
        try {
            // Get input values
            double principal = Double.parseDouble(edtPrincipalAmount.getText().toString());
            double rateOfInterest = Double.parseDouble(edtRateOfInterest.getText().toString());
            double tenureYears = Double.parseDouble(edtTenureYears.getText().toString());
            double tenureMonths = Double.parseDouble(edtTenureMonths.getText().toString());

            // Calculate monthly interest rate
            double monthlyInterestRate = rateOfInterest / (12 * 100);

            // Calculate total number of payments
            double totalPayments = (tenureYears * 12) + tenureMonths;

            // Calculate EMI
            double emi = (principal * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, totalPayments)) /
                    (Math.pow(1 + monthlyInterestRate, totalPayments) - 1);

            // Display results
            txtEMIResult.setText(String.format("%.2f", emi));
            txtTotalPayment.setText(String.format("%.2f", emi * totalPayments));

            // Display pie chart
            displayPieChart(principal, emi, totalPayments);

        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Please enter valid numbers", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayPieChart(double principal, double emi, double totalPayments) {
        pieChart.setVisibility(View.VISIBLE);

        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry((float) principal, "Principal"));
        entries.add(new PieEntry((float) (emi * totalPayments - principal), "Interest"));

        PieDataSet dataSet = new PieDataSet(entries, "EMI Breakdown");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.WHITE);

        pieChart.setData(data);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawEntryLabels(false);
        pieChart.animateY(1000);
        pieChart.invalidate();

        Legend legend = pieChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setTextColor(Color.WHITE);
        legend.setTextSize(12f);
    }

    private void resetFields() {
        edtPrincipalAmount.setText("");
        edtRateOfInterest.setText("");

        totalemi.setVisibility(View.INVISIBLE);
        totalpayment.setVisibility(View.INVISIBLE);
        piechart_liear.setVisibility(View.INVISIBLE);
        edtTenureYears.setText("");
        edtTenureMonths.setText("");
        txtEMIResult.setText("");
        txtTotalPayment.setText("");
        pieChart.setVisibility(View.GONE);
    }

    class bgthred extends Thread {
        @Override
        public void run() {
            super.run();


            try {
                AppDatabse db = Room.databaseBuilder(requireActivity().getApplicationContext(), AppDatabse.class, "room_db")
                        .build();
                UserDao userDao = db.userDao();

                // Insert data
                userDao.insert(new User(
                        edtPrincipalAmount.getText().toString(),
                        edtRateOfInterest.getText().toString(),
                        edtTenureMonths.getText().toString(),
                        edtTenureYears.getText().toString()));

                // Clear UI input fields
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        edtPrincipalAmount.setText("");
                        edtRateOfInterest.setText("");
                        edtTenureYears.setText("");
                        edtTenureMonths.setText("");
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
