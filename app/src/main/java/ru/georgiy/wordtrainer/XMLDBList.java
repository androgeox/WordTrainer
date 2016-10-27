package ru.georgiy.wordtrainer;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by Georgiy on 05.10.2016.
 */
public class XMLDBList extends Activity {
    ListView listView;
    SQLiteDatabase database;
    Cursor listCursor;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dblist_activity);

        try {
            listView = (ListView) findViewById(R.id.listView);
            SQLiteOpenHelper xmlDBHelper = new XmlDBHelper(this);
            try {
//                SQLiteOpenHelper xmlDB = new XmlDBHelper(appContext);
                database = xmlDBHelper.getWritableDatabase();
                XmlPullParser parser = getResources().getXml(R.xml.academic);

                while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                    if (parser.getEventType() == XmlPullParser.START_TAG && parser.getName().equals("word")) {
                        ContentValues xmlValues = new ContentValues();
                        xmlValues.put("ENG",parser.getText());
                        database.insert(XmlDBHelper.DB_NAME, null, xmlValues);
                        StringBuilder sb = new StringBuilder();
                        sb.append(parser.getText());
                        System.out.println(sb);
                    }
                    parser.next();
                }
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            database = xmlDBHelper.getReadableDatabase();
            listCursor = database.query("XMLWORDS", new String[]{"_id", "ENG"}, null, null, null, null, null);

            SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(XMLDBList.this,
                    android.R.layout.simple_list_item_1,
                    listCursor,
                    new String[]{"ENG"},
                    new int[]{android.R.id.text1}, 0);
            listView.setAdapter(listAdapter);
        } catch (IOException e) {
            e.printStackTrace();
            Toast toast = Toast.makeText(this, "something problem", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        listCursor.close();
        database.close();
    }
}
