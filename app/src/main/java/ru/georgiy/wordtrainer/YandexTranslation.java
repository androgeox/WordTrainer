package ru.georgiy.wordtrainer;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class YandexTranslation {

    private static final String API_BASE_URL = "https://translate.yandex.net";
    private final String KEY = "key=trnsl.1.1.20160520T122236Z.ec4efb77f10fb48b.8de363a33aee3405e8527b4087402671c2471c6b";
    private YandexTranslationService yandexTranslationService;
    private OkHttpClient client;

    public YandexTranslation() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        yandexTranslationService = retrofit.create(YandexTranslationService.class);
    }

    public void translateText(String textForTranslation) {
        Call<TranslateData> call;
        call = yandexTranslationService.getText(KEY, textForTranslation, TranslateData.getLang());
        call.enqueue(new Callback<TranslateData>() {
            @Override
            public void onResponse(Call<TranslateData> call, Response<TranslateData> response) {
                TranslateData data = response.body();
            }

            @Override
            public void onFailure(Call<TranslateData> call, Throwable t) {

            }
        });
    }
}
