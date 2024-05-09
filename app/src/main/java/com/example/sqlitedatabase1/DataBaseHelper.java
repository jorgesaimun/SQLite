package com.example.sqlitedatabase1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String CUSTOMER_TABLE = "CUSTOMER_TABLE";
    public static final String CUSTOMER_AGE = "CUSTOMER_AGE";
    public static final String CUSTOMER_NAME = "CUSTOMER_NAME";
    public static final String CUSTOMER_ACTIVE = "CUSTOMER_ACTIVE";
    public static final String ID = "ID";


    public DataBaseHelper(@Nullable Context context) {
        super(context, "customer.db", null, 2);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // This method is called for the first time when the app is opened to create the database table

        String createTableStatement = "CREATE TABLE  " + CUSTOMER_TABLE + "   (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, `" + CUSTOMER_NAME + "` VARCHAR(200), `" + CUSTOMER_AGE + "` INTEGER, `" + CUSTOMER_ACTIVE + "` BOOLEAN) ";

        db.execSQL(createTableStatement);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      //  db.execSQL("DROP TABLE IF EXISTS " + CUSTOMER_TABLE);
        onCreate(db);
    }


    public boolean addOne(CustomerModel customerModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(CUSTOMER_NAME, customerModel.getName());
        cv.put(CUSTOMER_AGE, customerModel.getAge());
        cv.put(CUSTOMER_ACTIVE, customerModel.isActive());

        long insert = db.insert(CUSTOMER_TABLE, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public List<CustomerModel> getEveryOne() {
        List<CustomerModel> list = new ArrayList<>();
        String queryString = " SELECT * FROM " + CUSTOMER_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                int customerId = cursor.getInt(0);
                String customerName = cursor.getString(1);
                int customerAge = cursor.getInt(2);
                boolean customerActive = cursor.getInt(3) == 1 ? true : false;
                CustomerModel customerModel = new CustomerModel(customerId, customerName, customerAge, customerActive);
                list.add(customerModel);
            } while (cursor.moveToNext());
        } else {

        }
        cursor.close();
        db.close();
        return list;
    }
}
