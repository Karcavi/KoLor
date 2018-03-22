package com.karcavi.kolor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by karcavi on 21/3/2018.
 */

public class CreateAdd extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createadd);

        Button buttonIngresar = (Button) findViewById(R.id.button5);
        buttonIngresar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent (CreateAdd.this, MyAdds.class);
                startActivity(intent);

            }



        });


        Button buttonCancelar = (Button) findViewById(R.id.button4);
        buttonIngresar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent (CreateAdd.this, MainMenu.class);
                startActivity(intent);

            }



        });




    }
}
