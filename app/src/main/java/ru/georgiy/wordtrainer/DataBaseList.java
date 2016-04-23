package ru.georgiy.wordtrainer;

import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

/**
 * Created by Georgiy on 19.04.2016.
 */
public class DataBaseList extends Activity {
    ListView listView;
    SQLiteDatabase database;
    Cursor listCursor;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dblist_activity);

        try {
            listView = (ListView)findViewById(R.id.listView);
            //ListView listView = getListView();
            SQLiteOpenHelper cardDatabaseHelper = new CardDatabaseHelper(this);
            database = cardDatabaseHelper.getReadableDatabase();
            listCursor=database.query("WORDS", new String[]{"_id","ENG", "RUS"}, null, null, null, null, null);

            SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(DataBaseList.this,
                    android.R.layout.simple_list_item_2,
                    listCursor,
                    new String[]{"ENG","RUS"},
                    new int[]{android.R.id.text1, android.R.id.text2},0);
            listView.setAdapter(listAdapter);
        } catch (Exception e) {
            e.printStackTrace();
            Toast toast = Toast.makeText(this, "something problem", Toast.LENGTH_SHORT);
            toast.show();
        }


    }

    @Override
    public  void onDestroy(){
        super.onDestroy();
        listCursor.close();
        database.close();
    }
}
