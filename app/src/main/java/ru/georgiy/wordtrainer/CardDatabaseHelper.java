package ru.georgiy.wordtrainer;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Georgiy on 17.04.2016.
 */
public class CardDatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "WORDS";
    public static final int DB_VERSION = 1;

    public CardDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db, 0, DB_VERSION);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion<1){
            db.execSQL("CREATE TABLE WORDS (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + "ENG TEXT," + "RUS TEXT);");
            insertWord(db, "hello", "привет");
            insertWord(db, "house", "дом");
            insertWord(db, "girl", "девушка");
        }
        if(oldVersion<2){
            db.execSQL("ALTER TABLE WORDS ADD COLUMN FAVORITE NUMERIC;");
        }

    }

    private static void insertWord(SQLiteDatabase db, String eng, String rus) {
        ContentValues wordvalues = new ContentValues();
        wordvalues.put("ENG", eng);
        wordvalues.put("RUS", rus);
        db.insert("WORDS", null, wordvalues);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);
    }
}
