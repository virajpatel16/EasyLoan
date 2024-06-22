package com.example.easyloan.Roomdatabase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM emi")
    List<User> getalldata();
    @Insert
    void insert(User users);

    @Query("DELETE FROM emi WHERE uid =:id")
    void deletedata(int id);


}
