package com.karcavi.kolor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by karcavi on 21/3/2018.
 */

public class Search extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        Button botonCancelar = (Button) findViewById(R.id.button12);
        botonCancelar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent (Search.this, MainMenu.class);
                startActivity(intent);

            }

        });


    }
}
