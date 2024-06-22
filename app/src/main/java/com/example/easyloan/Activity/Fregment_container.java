package com.example.easyloan.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.easyloan.Fragment.AfforadalityFragment;
import com.example.easyloan.Fragment.AmounttowordFragment;
import com.example.easyloan.Fragment.BankInformatioFragment;
import com.example.easyloan.Fragment.CashCounterFragment;
import com.example.easyloan.Fragment.CheckEligibilityFragment;
import com.example.easyloan.Fragment.CompareloanFragment;
import com.example.easyloan.Fragment.CompondinterestFragment;
import com.example.easyloan.Fragment.ConvertCurrencyFragment;
import com.example.easyloan.Fragment.EMICalculateFragment;
import com.example.easyloan.Fragment.FDcalculateFragment;
import com.example.easyloan.Fragment.GstCalculateFragment;
import com.example.easyloan.Fragment.InterestCalculateFragment;
import com.example.easyloan.Fragment.PpfcalculateFragment;
import com.example.easyloan.Fragment.RDcalculateFragment;
import com.example.easyloan.Fragment.SipcalculateFragment;
import com.example.easyloan.R;

public class Fregment_container extends AppCompatActivity {
    int check = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fregment_container);

        if (getIntent().getExtras() != null) {
            check = getIntent().getIntExtra("dailycheck", 1);
            switch (check) {
                case 101:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fregment, new EMICalculateFragment()).commit();
                    break;
                case 102:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fregment, new SipcalculateFragment()).commit();
                    break;
                case 103:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fregment, new GstCalculateFragment()).commit();
                    break;
                case 104:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fregment, new InterestCalculateFragment()).commit();
                    break;
                case 105:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fregment, new FDcalculateFragment()).commit();
                    break;
                case 106:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fregment, new RDcalculateFragment()).commit();
                    break;
                case 107:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fregment, new PpfcalculateFragment()).commit();
                    break;
                case 108:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fregment, new AfforadalityFragment()).commit();
                    break;
                case 109:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fregment, new CheckEligibilityFragment()).commit();
                    break;
                case 110:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fregment, new BankInformatioFragment()).commit();
                    break;
                case 111:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fregment, new CompondinterestFragment()).commit();
                    break;
                case 112:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fregment, new CompareloanFragment()).commit();
                    break;
                case 113:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fregment, new ConvertCurrencyFragment()).commit();
                    break;
                case 114:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fregment, new CashCounterFragment()).commit();
                    break;
                case 115:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fregment, new AmounttowordFragment()).commit();
                    break;


            }
        }
    }
}