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
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

public class YandexTranslation {

    private static final String API_BASE_URL = "https://translate.yandex.net";
    private static final String KEY = "key=trnsl.1.1.20160520T122236Z.ec4efb77f10fb48b.8de363a33aee3405e8527b4087402671c2471c6b";
    private static final String LANG = "en-ru";
    private YandexTranslationService yandexTranslationService;
    private static rx.Observable<String> observableRetrofit;
    private static final OkHttpClient okHttpClient  = new OkHttpClient();

    public YandexTranslation() {

        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        Gson gson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(rxAdapter)
                .client(okHttpClient)
                .build();
        yandexTranslationService = retrofit.create(YandexTranslationService.class);

//        observableRetrofit = yandexTranslationService.getText(KEY, , LANG);
    }

    public void translateText(String text) {

        Call<TranslateData> call = yandexTranslationService.getText(KEY, text, LANG);
        call.enqueue(new Callback<TranslateData>() {
            @Override
            public void onResponse(Call<TranslateData> call, Response<TranslateData> response) {
                if(response.isSuccessful()){
                    TranslateData translateData = response.body();
                    AddCardActivity cardActivity = new AddCardActivity();
                    cardActivity.rustext.setText(translateData.toString());
                }else{
                    int statusCode = response.code();
                    ResponseBody errorBody = response.errorBody();
                }
//                System.out.println(data);
            }

            @Override
            public void onFailure(Call<TranslateData> call, Throwable t) {

            }
        });
    }
}
