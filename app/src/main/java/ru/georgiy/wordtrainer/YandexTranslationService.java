package ru.georgiy.wordtrainer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface YandexTranslationService {

    @POST("/api/v1.5/tr.json/translate")
    Call<TranslateData> getText(@Query("key") String key, @Query("text") String text, @Query("lang") String lang);

}
