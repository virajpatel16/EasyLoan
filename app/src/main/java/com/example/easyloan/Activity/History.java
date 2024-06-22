package com.example.easyloan.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.easyloan.Roomdatabase.AppDatabse;
import com.example.easyloan.R;
import com.example.easyloan.Roomdatabase.User;
import com.example.easyloan.Roomdatabase.UserDao;
import com.example.easyloan.adepter.LoanAdapter;
import com.example.easyloan.model.LoanItem;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LoanAdapter adapter;



    MaterialTextView txt_back_emi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        txt_back_emi=findViewById(R.id.txt_back_emi);
        // Set up RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Retrieve EMI history list
        recyclerView = findViewById(R.id.recyclerView);


        txt_back_emi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getroomdata();





    }

    private void getroomdata() {

        AppDatabse db = Room.databaseBuilder(getApplicationContext(),AppDatabse.class,"room_db")
                .allowMainThreadQueries().build();
        UserDao userDao =db.userDao();

        List<User> users= userDao.getalldata();
        adapter=new LoanAdapter(this, (ArrayList<User>) users);
        recyclerView.setAdapter(adapter);
    }


}
