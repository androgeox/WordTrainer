package ru.georgiy.wordtrainer;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddCardActivity extends Activity implements View.OnClickListener {

    public static final String RETURN_KEY = "RETURN_KEY";
    private static final String KEY = "key=trnsl.1.1.20160520T122236Z.ec4efb77f10fb48b.8de363a33aee3405e8527b4087402671c2471c6b";
    private static final String LANG = "en-ru";
    private static final String API_BASE_URL = "https://translate.yandex.net";
    private static final OkHttpClient okHttpClient = new OkHttpClient();
    private YandexTranslationService yandexTranslationService;
    private EditText engText;
    private EditText rustext;
    private Button addBtn;
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcard_layout);

        engText = (EditText) findViewById(R.id.editText);
        rustext = (EditText) findViewById(R.id.editText2);
        addBtn = (Button) findViewById(R.id.addBtn);

        String text = engText.getText().toString();

        Gson gson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
        yandexTranslationService = retrofit.create(YandexTranslationService.class);

        Call<TranslateData> call = yandexTranslationService.getText(KEY, text, LANG);
        call.enqueue(new Callback<TranslateData>() {
            @Override
            public void onResponse(Call<TranslateData> call, Response<TranslateData> response) {
                if (response.isSuccessful()) {
                    response.body();
                } else {
                    int statusCode = response.code();
                    ResponseBody errorBody = response.errorBody();
                }
            }

            @Override
            public void onFailure(Call<TranslateData> call, Throwable t) {

            }
        });
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
                try {
//                    rustext.setText(translateText(engText.getText().toString()));
                    TranslateData translateData = new TranslateData();
                    rustext.setText(translateData.getTranslatedText());
                    System.out.println(rustext);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast toast = Toast.makeText(this, "Translate exception", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
        }
    }

//    @Override
//    public void finish() {
//        Intent intent = new Intent();
//        intent.putExtra(RETURN_KEY, (Parcelable) cursor);
//        setResult(RESULT_OK, intent);
//        super.finish();
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //cursor.close();
        db.close();
    }

    @Override
    public String toString() {
        return "AddCardActivity{" +
                "yandexTranslationService=" + yandexTranslationService +
                '}';
    }

}
