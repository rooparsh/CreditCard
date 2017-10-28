package com.darkknight.darkcard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.darkknight.darkcard.widget.CardForm;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CardForm cardForm = (CardForm) findViewById(R.id.card_form);
    }
}
