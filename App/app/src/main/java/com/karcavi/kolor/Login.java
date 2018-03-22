package com.karcavi.kolor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Created by karcavi on 21/3/2018.
 */

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Button buttonIngresar = (Button) findViewById(R.id.button9);
        buttonIngresar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent (Login.this, MainMenu.class);
                startActivity(intent);

            }



        });

        Button buttonCancelar = (Button) findViewById(R.id.button8);
        buttonCancelar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent (Login.this, MainActivity.class);
                startActivity(intent);

            }



        });

    }
}

