package ru.georgiy.wordtrainer;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
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
public class CardActivity extends Activity {

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_activity);

        prevBtn = (Button)findViewById(R.id.prevBtn);
        checkBtn = (Button)findViewById(R.id.checkBtn);
        nextBtn = (Button)findViewById(R.id.nextBtn);
        textView = (TextView)findViewById(R.id.txtView);
        answer = (EditText) findViewById(R.id.inputSolution);  //ввод ответа
        cardview = (CardView) findViewById(R.id.cv);

        test = (TextView)findViewById(R.id.test);



        try {
            SQLiteOpenHelper cardDatabaseHelper = new CardDatabaseHelper(this);
            db = cardDatabaseHelper.getReadableDatabase();
            cursor = db.query("WORDS", new String[]{"ENG", "RUS"}, null, null, null, null, null);
            if(cursor.moveToFirst()){
                String engtext = cursor.getString(0);
                String rustext = cursor.getString(1);
                textView.setText(engtext);
                //test.setText(rustext);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast toast = Toast.makeText(this, "db exception", Toast.LENGTH_SHORT);
            toast.show();
        }

        checkBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                check();
//                try {
//                    Scanner scanner = new Scanner();
//                    String s = scanner.nextLine();
//                    //test.setText(s);
//                }catch (Exception e){
//                    test.setText("exception");
//                }
                // textView.setText("нажата кнопка проверить");
            }
        });

//        answer.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                String input = answer.getText().toString();
//                test.setText(input);
//                return false;
//            }
//        });
    }

    public void check(){

        String rustext = cursor.getString(1);
//        InputStreamReader in = new InputStreamReader(System.in);
//        BufferedReader buf = new BufferedReader(in);
       // try {
//        Scanner scanner = new Scanner(System.in);
//        String s = scanner.nextLine();
//        test.setText(s);
        //test.setText(rustext);

        //String s = buf.readLine();
           // test.setText(s);

        String s = answer.getText().toString();
        //test.setText(s);
            if (s.equals(rustext)) {
                    textView.setText("Верно");
                } else {
                    textView.setText("Не верно");
                }

        }


    @Override
    public  void onDestroy(){
        super.onDestroy();
        cursor.close();
        db.close();
    }
}
