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

//    @GET("/api/v1.5/tr.json/translate/{key}&{text}&{lang}")
//    Observable<Object> getText(@Path("key") String key, @Path("text") String text, @Path("lang") String lang);

//    @GET("/api/v1.5/tr.json/translate/{key}&{text}&{lang}")
//    Call<ArrayList<String>> getText(@Path("key") String key, @Path("text") String text, @Path("lang") String lang);

    @GET("/api/v1.5/tr.json/translate/{key}&{text}&{lang}")
    Call<TranslateData> getText(@Path("key") String key, @Path("text") String text, @Path("lang") String lang);

}
