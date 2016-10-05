package ru.georgiy.wordtrainer;

import android.hardware.camera2.CaptureRequest;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class YandexTranslation {
    static YandexTranslationService yandexTranslationService;
    private static final OkHttpClient okHttpClient = new OkHttpClient();
//    private static final String API_BASE_URL = "https://translate.yandex.net";
    private static final Gson gson = new GsonBuilder().create();

    static {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(YandexTranslationService.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
//                .client(okHttpClient)
                .build();
        yandexTranslationService = retrofit.create(YandexTranslationService.class);
    }

    public void translateText(String key, String text, String lang, Callback<TranslateData> callback){
        Call<TranslateData> call = yandexTranslationService.getText(key, text, lang);
        call.enqueue(callback);
    }
}
