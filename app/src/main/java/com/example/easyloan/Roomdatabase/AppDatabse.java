package com.example.easyloan.Roomdatabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class},version = 1)
public abstract class AppDatabse extends RoomDatabase {

    public abstract   UserDao userDao();
}
