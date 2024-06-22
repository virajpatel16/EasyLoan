package com.example.easyloan.adepter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.easyloan.R;
import com.example.easyloan.Activity.inDetails_Bank_Services;
import com.example.easyloan.model.Bank;

import java.util.List;

public class BankAdapter extends RecyclerView.Adapter<BankAdapter.ViewHolder> {
    private final Context context;
    private final List<Bank> bankList;

    public BankAdapter(Context context, List<Bank> bankList) {
        this.context = context;
        this.bankList = bankList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_bank, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bank bank = bankList.get(position);
        holder.bankName.setText(bank.getName());
        holder.bankLogo.setImageResource(bank.getLogo());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, inDetails_Bank_Services.class);
                intent.putExtra("bankName", bank.getName());
                intent.putExtra("bankLogo", bank.getLogo());
                intent.putExtra("balanceNumber", bank.getBalanceNumber());
                intent.putExtra("statementNumber", bank.getStatementNumber());
                intent.putExtra("careNumber", bank.getCareNumber());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bankList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView bankLogo;
        TextView bankName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bankLogo = itemView.findViewById(R.id.bank_logo);
            bankName = itemView.findViewById(R.id.bank_name);
        }
    }
}
