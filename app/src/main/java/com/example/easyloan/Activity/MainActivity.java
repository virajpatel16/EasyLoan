package com.example.easyloan.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.easyloan.R;

public class MainActivity extends AppCompatActivity {
    CardView emi, sip, gst, interest, fd, rd, ppf, nearatm, nearbank, affordality, check, bankinfo, compound, compareloan, convertcurrency, cashcounter, amountword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emi = findViewById(R.id.emi);
        sip = findViewById(R.id.sip);
        gst = findViewById(R.id.gst);
        fd=findViewById(R.id.fd);
        interest = findViewById(R.id.interest);
        rd = findViewById(R.id.rd);
        ppf = findViewById(R.id.ppf);
        nearatm = findViewById(R.id.nearatm);
        nearbank = findViewById(R.id.nearbank);
        affordality = findViewById(R.id.affordality);
        check = findViewById(R.id.check);
        bankinfo = findViewById(R.id.bankinfo);
        compound = findViewById(R.id.compound);
        compareloan = findViewById(R.id.compareloan);
        convertcurrency = findViewById(R.id.convertcurrency);
        cashcounter = findViewById(R.id.cashcounter);
        amountword = findViewById(R.id.amountword);


        emi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 101);
                startActivity(intent);
            }
        });
        sip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 102);
                startActivity(intent);
            }
        });
        gst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 103);
                startActivity(intent);
            }
        });
        interest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 104);
                startActivity(intent);
            }
        });
        fd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 105);
                startActivity(intent);
            }
        });
        rd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 106);
                startActivity(intent);
            }
        });
        ppf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 107);
                startActivity(intent);
            }
        });
        nearatm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMaps("Atm");
            }
        });

        nearbank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMaps("bank");

            }
        });
        affordality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 108);
                startActivity(intent);
            }
        });
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 109);
                startActivity(intent);
            }
        });
        bankinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 110);
                startActivity(intent);
            }
        });
        compound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 111);
                startActivity(intent);
            }
        });
        compareloan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 112);
                startActivity(intent);
            }
        });
        convertcurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 113);
                startActivity(intent);
            }
        });
        cashcounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 114);
                startActivity(intent);
            }
        });
        amountword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 115);
                startActivity(intent);
            }
        });
    }
    private void openMaps(String query) {
        // Open Google Maps with the appropriate query
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + query);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {

        final AlertDialog.Builder alert=new AlertDialog.Builder(this);
        View view=getLayoutInflater().inflate(R.layout.exit_dialog,null);
        alert.setView(view);
        final AlertDialog dialog=alert.create();
        dialog.setCancelable(false);
        view.findViewById(R.id.BTN_YES).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finishAffinity();
                // finishAndRemoveTask();
            }
        });
        view.findViewById(R.id.BTN_NO).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.show();

    }
}