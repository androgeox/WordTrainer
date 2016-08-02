//package ru.georgiy.wordtrainer;
//
//import android.app.Activity;
//import android.content.ContentValues;
//import android.content.Context;
//import android.content.pm.PackageManager;
//import android.content.res.Resources;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.os.Bundle;
//
//import org.xmlpull.v1.XmlPullParser;
//import org.xmlpull.v1.XmlPullParserException;
//
//public class XmlReader {
//    SQLiteDatabase database;
//    Cursor xmlCursor;
//
//    public void fillDataBase() {
//        try {
//            SQLiteOpenHelper xmlDB = new SQLiteOpenHelper() {
//                @Override
//                public void onCreate(SQLiteDatabase db) {
//                    db.execSQL("CREATE TABLE XMLWORDS (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + "ENG TEXT," + "RUS TEXT);");
//                }
//
//                @Override
//                public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//
//                }
//            };
//
//            XmlPullParser parser = getResources().getXml(R.xml.test);
//
//            while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
//                if (parser.getEventType() == XmlPullParser.START_TAG && parser.getName().equals("words")) {
//                    ContentValues xmlValues = new ContentValues();
//                    xmlValues.put(parser.getAttributeValue(0), parser.getAttributeValue(1));
//                    database.insert("XMLWORDS", null, xmlValues);
//                }
//            }
//        } catch (XmlPullParserException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public Resources getResources() {
//        return getResources();
//    }
//}
