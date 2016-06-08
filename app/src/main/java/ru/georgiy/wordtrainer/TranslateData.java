package ru.georgiy.wordtrainer;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TranslateData {


    @SerializedName("code")
    private int responcseCode;

    @SerializedName("text")
    private ArrayList<String> translatedText = new ArrayList<>();

    @SerializedName("lang")
    private static String lang;

    public static String getLang() {
        return lang;
    }

    public TranslateData(){
    }
    public ArrayList<String> getTranslatedText() {
        return translatedText;
    }
}


