package ua.kpi.fpm.oop.activity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
      super(context, "UserDataBase", null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
      
      // создаем таблицу с полями
      db.execSQL("create table users ("
          + "user_id integer primary key autoincrement," 
    		  + "user_name text,"
          + "user_book text," 
    		  + "user_paragraph int," 
          + "user_word int" + ");");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	
    }
  }
