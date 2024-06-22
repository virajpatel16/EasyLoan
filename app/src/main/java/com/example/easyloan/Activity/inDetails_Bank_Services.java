package com.example.easyloan.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.easyloan.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

public class inDetails_Bank_Services extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_details_bank_services);

        MaterialTextView bank_name=findViewById(R.id.bank_name);
        ShapeableImageView bankLogo = findViewById(R.id.bank_logo);
        AppCompatTextView checkBalanceNumber = findViewById(R.id.check_balance_number);
        AppCompatTextView getMiniStatementNumber = findViewById(R.id.get_mini_statement_number);
        AppCompatTextView customerCareNumber = findViewById(R.id.customer_care_number);

        bank_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        if (intent != null) {
            String name = intent.getStringExtra("bankName");
            int logo = intent.getIntExtra("bankLogo", 0);
            String balanceNumber = intent.getStringExtra("balanceNumber");
            String statementNumber = intent.getStringExtra("statementNumber");
            String careNumber = intent.getStringExtra("careNumber");

            bank_name.setText(name);
            bankLogo.setImageResource(logo);
            checkBalanceNumber.setText(balanceNumber);
            getMiniStatementNumber.setText(statementNumber);
            customerCareNumber.setText(careNumber);
        }
    }

}
