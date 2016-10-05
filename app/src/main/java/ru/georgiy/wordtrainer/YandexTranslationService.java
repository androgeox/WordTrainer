package ru.georgiy.wordtrainer;

import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface YandexTranslationService {
    String API_BASE_URL = "https://translate.yandex.net/";
    String TRANSLATE_URL = "api/v1.5/tr.json/translate";

    @GET(TRANSLATE_URL)
    Call<TranslateData> getText( @Query("key") String key, @Query("text") String text, @Query("lang") String lang);

}
