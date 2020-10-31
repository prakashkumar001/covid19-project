package com.virus.covid19.database;


import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.virus.covid19.database.dao.OrderHistoryDao;
import com.virus.covid19.database.dao.ProductDao;
import com.virus.covid19.database.dao.ShopsDao;
import com.virus.covid19.database.dao.UserDao;
import com.virus.covid19.database.entities.OrderHistory;
import com.virus.covid19.database.entities.Product;
import com.virus.covid19.database.entities.Shops;
import com.virus.covid19.database.entities.User;


@Database(entities = {User.class, Shops.class, Product.class, OrderHistory.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "covid";
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }

    public abstract UserDao userDao();
    public abstract ShopsDao shopsDao();
    public abstract ProductDao productDao();
    public abstract OrderHistoryDao orderHistoryDao();

}