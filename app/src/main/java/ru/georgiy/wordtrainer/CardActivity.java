package ru.georgiy.wordtrainer;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.UserDictionary;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Created by Georgiy on 16.04.2016.
 */
public class CardActivity extends Activity implements View.OnClickListener{

    Button prevBtn;
    Button checkBtn;
    Button nextBtn;
    TextView textView;
    TextView test;
    EditText answer;
    CardView cardview;
    private Cursor cursor;
    private SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_activity);

        prevBtn=(Button)findViewById(R.id.prevBtn);
        checkBtn=(Button)findViewById(R.id.checkBtn);
        nextBtn=(Button)findViewById(R.id.nextBtn);
        textView=(TextView)findViewById(R.id.txtView);
        answer=(EditText)findViewById(R.id.inputSolution);  //ввод ответа
        cardview=(CardView)findViewById(R.id.cv);
        test=(TextView)findViewById(R.id.test);


        try{
            SQLiteOpenHelper cardDatabaseHelper=new CardDatabaseHelper(this);
            db = cardDatabaseHelper.getReadableDatabase();
            cursor=db.query("WORDS",new String[]{"ENG","RUS"},null,null,null,null,null);
                if(cursor.moveToFirst()){
                    String engtext=cursor.getString(0);
                    //String rustext=cursor.getString(1);
                    textView.setText(engtext);
                    //test.setText(rustext);
                }
        }catch(Exception e){
            e.printStackTrace();
            Toast toast=Toast.makeText(this,"db exception",Toast.LENGTH_SHORT);
            toast.show();
        }

    }


    private void previous() {
        if(!cursor.isFirst()){
            cursor.moveToPrevious();
            String engtext=cursor.getString(0);
            textView.setText(engtext);
            cardview.setCardBackgroundColor(Color.rgb(0,150,200));
        }
    }

    private void next() {
        if(!cursor.isLast()){
            cursor.moveToNext();
            String engtext=cursor.getString(0);
            textView.setText(engtext);
            cardview.setCardBackgroundColor(Color.rgb(0,150,200));
        }
    }

    public void check(){
        String rustext = cursor.getString(1);
        String s = answer.getText().toString();
        //test.setText(s);
            if (s.equals(rustext)) {
                    textView.setText("Верно");
                    cardview.setCardBackgroundColor(Color.GREEN);
                    cardview.animate();
                } else {
                    textView.setText("Не верно");
                    cardview.setCardBackgroundColor(Color.RED);
                }
        }

    @Override
    public  void onDestroy(){
        super.onDestroy();
        cursor.close();
        db.close();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case (R.id.checkBtn):
                check();
                //textView.setText("check pressed");
                break;
            case (R.id.nextBtn):
                next();
                answer.setText("");
                //textView.setText("next pressed");
                break;
            case (R.id.prevBtn):
                previous();
                answer.setText("");
                //textView.setText("prev pressed");
                break;
            case R.id.fab:
                Intent intent = new Intent(this, AddCardActivity.class);
                startActivity(intent);
                break;
            case R.id.fab_delete:
                deleteWord();
                break;
            case R.id.fab_list:
                Intent intent_list = new Intent(this,DataBaseList.class);
                startActivity(intent_list);
                break;
            default:
                Toast toast = Toast.makeText(this, "not press any button", Toast.LENGTH_SHORT);
                toast.show();
            }
    }

    private void deleteWord() {
        try {
            String current = cursor.getString(cursor.getColumnIndex("ENG"));
            SQLiteOpenHelper cardDatabaseHelper = new CardDatabaseHelper(this);
            db = cardDatabaseHelper.getWritableDatabase();
            db.delete("WORDS", "ENG = ?", new String[]{current});
//            ContentValues removed = new ContentValues();
//            removed.remove("ENG");
//            db.update("WORDS", removed, null, null);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            Toast toast = Toast.makeText(this, "exception db", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
