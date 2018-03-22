package com.karcavi.kolor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by karcavi on 21/3/2018.
 */

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);

        Button crearAnuncio = (Button) findViewById(R.id.button);
        crearAnuncio.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent (MainMenu.this, CreateAdd.class);
                startActivity(intent);

            }



        });

        Button verMisAnuncios = (Button) findViewById(R.id.button2);
        verMisAnuncios.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent2 = new Intent (MainMenu.this, MyAdds.class);
                startActivity(intent2);

            }



        });


        Button SearchForAdds = (Button) findViewById(R.id.button3);
        SearchForAdds.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent3 = new Intent (MainMenu.this, Search.class);
                startActivity(intent3);

            }



        });


        Button Salir = (Button) findViewById(R.id.button11);
        Salir.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent2 = new Intent (MainMenu.this, MainActivity.class);
                startActivity(intent2);

            }



        });




    }


}
