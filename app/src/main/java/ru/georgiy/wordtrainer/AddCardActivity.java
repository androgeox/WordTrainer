package ru.georgiy.wordtrainer;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.logging.Logger;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCardActivity extends Activity implements View.OnClickListener, Callback<TranslateData>{
    public static final Logger logger = Logger.getLogger("AddCardActivity");
    public static final String RETURN_KEY = "RETURN_KEY";
    private static final String KEY = "trnsl.1.1.20160520T122236Z.ec4efb77f10fb48b.8de363a33aee3405e8527b4087402671c2471c6b";
    private static final String LANG = "en-ru";

    private static final OkHttpClient okHttpClient = new OkHttpClient();
    private YandexTranslationService yandexTranslationService;
    private EditText engText;
    private EditText rustext;
    private Button addBtn;
    private Button translateBtn;
    private SQLiteDatabase db;
    private Cursor cursor;
    //    String text;
    TranslateData translateData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcard_layout);

        engText = (EditText) findViewById(R.id.editText);
        rustext = (EditText) findViewById(R.id.editText2);
        addBtn = (Button) findViewById(R.id.addBtn);
        translateBtn = (Button) findViewById(R.id.translateBtn);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addBtn:
                try {
                    SQLiteOpenHelper cardDatabaseHelper = new CardDatabaseHelper(this);
                    db = cardDatabaseHelper.getWritableDatabase();
                    String eng = engText.getText().toString();
                    String rus = rustext.getText().toString();
                    ContentValues new_word = new ContentValues();
                    new_word.put("ENG", eng);
                    new_word.put("RUS", rus);
                    db.insert("WORDS", null, new_word);
                    db.update("WORDS", new_word, "ENG =? AND RUS=?", new String[]{eng, rus});
                    //cursor = db.query("WORDS", new String[]{"ENG", "RUS"}, null, null, null, null, null);
                    db.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast toast = Toast.makeText(this, "addWord exception", Toast.LENGTH_SHORT);
                    toast.show();
                }
                finish();
                break;
            case R.id.translateBtn:
                String text = engText.getText().toString();
                Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
                toast.show();
                new YandexTranslation().translateText(KEY, text,LANG, this);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }

    @Override
    public void onResponse(Call<TranslateData> call, Response<TranslateData> response) {
        if (response.isSuccessful()) {
            TranslateData data = response.body();
            int code = response.body().getResponseCode();
            rustext.setText(data.getTranslatedText());
            logger.info(data.getTranslatedText() + " " + code);
        }
    }

    @Override
    public void onFailure(Call<TranslateData> call, Throwable t) {
        t.printStackTrace();
//        logger.info("Failure");
//        System.out.println("response fail");
    }
}
