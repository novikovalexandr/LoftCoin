package com.alexandr4.loftcoin.data.db;

import android.content.Context;

import com.alexandr4.loftcoin.data.db.room.AppDatabase;
import com.alexandr4.loftcoin.data.db.room.DatabaseImplRoom;

import androidx.room.Room;

public class DatabaseInitializer {

    public Database init(Context context) {
        AppDatabase appDatabase = Room.databaseBuilder(context, AppDatabase.class, "loftcoin.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        return new DatabaseImplRoom(appDatabase);
    }
}
