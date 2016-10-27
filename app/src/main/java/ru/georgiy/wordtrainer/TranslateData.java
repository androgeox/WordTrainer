package ru.georgiy.wordtrainer;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TranslateData {

    @SerializedName("lang")
    private String mLang;
    @SerializedName("code")
    private int responseCode;
    @SerializedName("text")
    private Object translatedText;

//    public TranslateData(String lang, int responseCode, String translatedText) {
//        this.mLang = lang;
//        this.responseCode = responseCode;
//        this.translatedText = translatedText;
//    }

    public TranslateData() {
    }

    public String getmLang() {
        return mLang;
    }

    public void setmLang(String mLang) {
        this.mLang = mLang;
    }

    public String getLang() {
        return mLang;

    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public Object getTranslatedText() {
        return translatedText;
    }

    public void setTranslatedText(String translatedText) {
        this.translatedText = translatedText;
    }

    @Override
    public String toString() {
        return "TranslateData{" +
                "mLang='" + mLang + '\'' +
                ", responseCode=" + responseCode +
                ", translatedText='" + translatedText + '\'' +
                '}';
    }
}


