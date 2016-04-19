package ru.georgiy.wordtrainer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Scanner;

/**
 * Created by Georgiy on 17.04.2016.
 */
public class AddCardActivity extends Activity{

    EditText engtext;
    EditText rustext;
    Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcard_layout);

        engtext = (EditText)findViewById(R.id.editText);
        rustext = (EditText)findViewById(R.id.editText2);
        addBtn = (Button)findViewById(R.id.button);

        Scanner scanner = new Scanner(System.in);
        String addword = scanner.nextLine();
        engtext.getText();
    }

    public void onClick(View view) {
    }
}
