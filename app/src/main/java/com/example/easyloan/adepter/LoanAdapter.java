package com.example.easyloan.adepter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.easyloan.Roomdatabase.AppDatabse;
import com.example.easyloan.R;
import com.example.easyloan.Roomdatabase.User;
import com.example.easyloan.Roomdatabase.UserDao;

import java.util.ArrayList;

public class LoanAdapter extends RecyclerView.Adapter<LoanAdapter.ViewHolder> {

    private Context context;
    private ArrayList<User> LoanItemArrayList;
    private UserDao userDao;

    public LoanAdapter(Context context, ArrayList<User> loanItemArrayList  ) {
        this.context = context;
        LoanItemArrayList = loanItemArrayList;
        this.userDao = userDao;
    }

    public void setLoanItemArrayList(ArrayList<User> LoanItemArrayList) {
        this.LoanItemArrayList = (ArrayList<User>) LoanItemArrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loan, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.id.setText(String.valueOf(this.LoanItemArrayList.get(position).getUid()));
        holder.principalamount.setText(this.LoanItemArrayList.get(position).getPrincipalAmount());
        holder.rateofinterest.setText(this.LoanItemArrayList.get(position).getRateOfInterest());
        holder.tenureofyears.setText(this.LoanItemArrayList.get(position).getTenureYears());
        holder.tenureofmonth.setText(this.LoanItemArrayList.get(position).getTenureMonths());
        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDatabse db = Room.databaseBuilder(holder.id.getContext(), AppDatabse.class, "room_db")
                        .allowMainThreadQueries()
                        .build();
                UserDao userDao = db.userDao();
                userDao.deletedata(LoanItemArrayList.get(position).getUid());
                LoanItemArrayList.remove(position);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return LoanItemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        EditText principalamount;
        EditText rateofinterest;
        EditText tenureofyears;
        EditText tenureofmonth;
        ImageView Delete;
        TextView id;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Delete = itemView.findViewById(R.id.delete);
            principalamount = itemView.findViewById(R.id.tv_principal_amount);
            rateofinterest = itemView.findViewById(R.id.tv_rate_of_interest);
            tenureofyears = itemView.findViewById(R.id.tv_tenure_years);
            tenureofmonth = itemView.findViewById(R.id.tv_tenure_months);
            id = itemView.findViewById(R.id.id);
        }
    }
}
