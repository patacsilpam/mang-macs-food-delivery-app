package com.example.mangmacs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DbHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "cart";
    private static final String TBL_NAME = "tblcart";
    private static final int DB_Version = 1;
    private static final String PRODUCT_ID = "id";
    private static final String PRODUCT_NAME = "productName";
    private static final String PRODUCT_PRICE = "productPrice";
    public DbHandler(Context context){
        super(context,DB_NAME,null,DB_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = " CREATE TABLE " + TBL_NAME +
                " (" + PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PRODUCT_NAME + " TEXT, " +
                PRODUCT_PRICE + " TEXT)";
        sqLiteDatabase.execSQL(query);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TBL_NAME);
        onCreate(sqLiteDatabase);
    }
    public void addNewProducts(String productName, String price){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PRODUCT_NAME,productName);
        contentValues.put(PRODUCT_PRICE, price);
        sqLiteDatabase.insert(TBL_NAME,null,contentValues);
        sqLiteDatabase.close();
    }
    Cursor readProducts(){
        String query = " SELECT * FROM " + TBL_NAME;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = null;
        if(sqLiteDatabase != null){
             cursor = sqLiteDatabase.rawQuery(query,null);
        }
        return cursor;
    }
}
