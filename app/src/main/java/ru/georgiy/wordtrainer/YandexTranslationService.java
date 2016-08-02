package ru.georgiy.wordtrainer;

import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface YandexTranslationService {

    @GET("/api/v1.5/tr.json/translate")
    Call<TranslateData> getText(@Query("key") String key, @Query("text") String text, @Query("lang") String lang);

}
