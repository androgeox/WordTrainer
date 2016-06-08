package ru.georgiy.wordtrainer;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CardActivity extends Activity implements View.OnClickListener {

    public static final int REQUEST_CODE = 1;
    private FloatingActionButton prevBtn;
    private FloatingActionButton checkBtn;
    private FloatingActionButton nextBtn;
    private TextView textView;
    private TextView test;
    private EditText answer;
    private CardView cardview;
    private Cursor cursor;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_activity);

        prevBtn = (FloatingActionButton) findViewById(R.id.prevBtn);
        checkBtn = (FloatingActionButton) findViewById(R.id.checkBtn);
        nextBtn = (FloatingActionButton) findViewById(R.id.nextBtn);
        textView = (TextView) findViewById(R.id.txtView);
        answer = (EditText) findViewById(R.id.inputSolution);  //ввод ответа
        cardview = (CardView) findViewById(R.id.cv);
//      test=(TextView)findViewById(R.id.test);     //служит для тестового вывода в простом случае

        /* Чтение базы и установка значения в CardView */
        try {
            SQLiteOpenHelper cardDatabaseHelper = new CardDatabaseHelper(this);
            db = cardDatabaseHelper.getReadableDatabase();
            cursor = db.query("WORDS", new String[]{"ENG", "RUS"}, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                String engtext = cursor.getString(0);
                //String rustext=cursor.getString(1);
                textView.setText(engtext);
                //test.setText(rustext);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast toast = Toast.makeText(this, "db exception", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void previousValueFromCursor() {
        if (!cursor.isFirst()) {
            cursor.moveToPrevious();
            String engtext = cursor.getString(0);
            textView.setText(engtext);
            cardview.setCardBackgroundColor(Color.parseColor("#FF87D5F4"));
        }
    }

    private void nextValueFromCursor() {
        if (!cursor.isLast()) {
            cursor.moveToNext();
            String engtext = cursor.getString(0);
            textView.setText(engtext);
            cardview.setCardBackgroundColor(Color.parseColor("#FF87D5F4"));
        }
    }

    /* Сравнение введенного значения и значения в курсоре */
    public void check() {
        String rusText = cursor.getString(1);
        String s = answer.getText().toString();
        //test.setText(s);
        if (s.equals(rusText)) {
            textView.setText("Верно");
            cardview.setCardBackgroundColor(Color.GREEN);
            cardview.animate();
        } else {
            textView.setText("Не верно");
            cardview.setCardBackgroundColor(Color.RED);
        }
    }

    /* Удаление слова из БД */
    private void deleteWord() {
        try {
            String current = cursor.getString(cursor.getColumnIndex("ENG"));
            SQLiteOpenHelper cardDatabaseHelper = new CardDatabaseHelper(this);
            db = cardDatabaseHelper.getWritableDatabase();
            db.delete("WORDS", "ENG = ?", new String[]{current});
            cursor = db.query("WORDS", new String[]{"ENG", "RUS"}, null, null, null, null, null);
            nextValueFromCursor();
//            ContentValues removed = new ContentValues();
//            removed.remove("ENG");
//            db.update("WORDS", removed, null, null);
//            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            Toast toast = Toast.makeText(this, "exception db", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
//        super.onActivityResult(requestCode, resultCode, intent);
//        switch (requestCode) {
//            case REQUEST_CODE:
//                if (resultCode != RESULT_OK) {
//                    return;
//                }
//                if (intent.hasExtra(AddCardActivity.RETURN_KEY)) {
//                    Cursor newCursor = intent.getExtras().getParcelable(AddCardActivity.RETURN_KEY);
//                    cursor = newCursor;
//                }
//                break;
//        }
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.checkBtn):
                check();
                //textView.setText("check pressed");
                break;
            case (R.id.nextBtn):
                nextValueFromCursor();
                answer.setText("");
                //textView.setText("nextValueFromCursor pressed");
                break;
            case (R.id.prevBtn):
                previousValueFromCursor();
                answer.setText("");
                //textView.setText("prev pressed");
                break;
            case R.id.fab_add:
                Intent intent = new Intent(this, AddCardActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.fab_delete:
                deleteWord();
                break;
            case R.id.fab_list:
                Intent intent_list = new Intent(this, DataBaseList.class);
                startActivity(intent_list);
                break;
            default:
                Toast toast = Toast.makeText(this, "not press any button", Toast.LENGTH_SHORT);
                toast.show();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        try {
            cursor = db.query("WORDS", new String[]{"ENG", "RUS"}, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                String engtext = cursor.getString(0);
                //String rustext=cursor.getString(1);
                textView.setText(engtext);
                //test.setText(rustext);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast toast = Toast.makeText(this, "smth bad", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }
}
