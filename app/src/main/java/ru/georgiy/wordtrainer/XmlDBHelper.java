package ru.georgiy.wordtrainer;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class XmlDBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "XMLWORDS";
    public static final int DB_VERSION = 1;
    SQLiteDatabase database;
    Context appContext;

    public XmlDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

//    public SQLiteDatabase fillDataBase() {
//
//        try {
//            SQLiteOpenHelper xmlDB = new XmlDBHelper(appContext);
//            database = xmlDB.getWritableDatabase();
//            XmlPullParser parser = getResources().getXml(R.xml.academic);
//
//            while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
//                if (parser.getEventType() == XmlPullParser.START_TAG && parser.getName().equals("word")) {
//                    ContentValues xmlValues = new ContentValues();
//                    xmlValues.put(parser.getAttributeValue(0), parser.getAttributeValue(1));
//                    database.insert(DB_NAME, null, xmlValues);
//                }
//            }
//        } catch (XmlPullParserException e) {
//            e.printStackTrace();
//        }
//        return database;
//    }

//    public Resources getResources() {
//        return getResources();
//    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE XMLWORDS (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + "ENG TEXT," + "RUS TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
