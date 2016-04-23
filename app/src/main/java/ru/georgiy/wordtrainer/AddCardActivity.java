package ru.georgiy.wordtrainer;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Georgiy on 17.04.2016.
 */
public class AddCardActivity extends Activity implements View.OnClickListener{

    EditText engtext;
    EditText rustext;
    Button addBtn;
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcard_layout);

        engtext = (EditText)findViewById(R.id.editText);
        rustext = (EditText)findViewById(R.id.editText2);
        addBtn = (Button)findViewById(R.id.addBtn);



        //cursor = db.query("WORDS", new String[]{"ENG", "RUS"}, null, null, null, null, null);


    }

    @Override
    public void finish() {
        super.finish();
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.addBtn:
                try {
                    SQLiteOpenHelper cardDatabaseHelper=new CardDatabaseHelper(this);
                    db = cardDatabaseHelper.getWritableDatabase();
                    String eng = engtext.getText().toString();
                    String rus = rustext.getText().toString();

                    ContentValues new_word = new ContentValues();
                    new_word.put("ENG", eng);
                    new_word.put("RUS", rus);
                    db.insert("WORDS", null, new_word);
                    db.update("WORDS", new_word, "ENG =? AND RUS=?",new String[]{eng, rus});
                    db.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast toast = Toast.makeText(this, "add word exception", Toast.LENGTH_SHORT);
                    toast.show();
                }

//                Intent intent = new Intent(this,CardActivity.class);
//                startActivity(intent);


                finish();
                break;

        }
    }
    @Override
    public  void onDestroy(){
        super.onDestroy();
        cursor.close();
        db.close();
    }
}
