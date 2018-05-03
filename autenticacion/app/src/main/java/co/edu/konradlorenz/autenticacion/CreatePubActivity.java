package co.edu.konradlorenz.autenticacion;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class CreatePubActivity extends AppCompatActivity {


    private EditText Title;
    private EditText Description;
    private Spinner Category;
    private Button CreatePButton;
    private String category;
    static CreateAccountActivity instance;
    private FirebaseAuth mAuth;

    private Button CancelPButton;
    private FirebaseAuth.AuthStateListener mAuthListener;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pub);
        Spinner spinner = (Spinner) findViewById(R.id.categories_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        mAuth = FirebaseAuth.getInstance();
        Title = (EditText) findViewById(R.id.txt_title);
        Description = (EditText) findViewById(R.id.txt_description);
        Category = (Spinner) findViewById(R.id.categories_spinner);
        category = Category.getSelectedItem().toString();
        CreatePButton = (Button) findViewById(R.id.btn_createPub);
        CancelPButton = (Button) findViewById(R.id.btn_cancelPub);
        CancelPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreatePubActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        CreatePButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPub();
            }
        });


    }

    private void createPub() {

        final String title = Title.getText().toString().trim();
        final String description = Description.getText().toString().trim();

        if (!TextUtils.isEmpty(title)  && !TextUtils.isEmpty(description) ) {

            int o = GenerateId();
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("publications");
            DatabaseReference currentUserDB = mDatabase.child(String.valueOf(o));
            currentUserDB.child("category").setValue(category);
            currentUserDB.child("title").setValue(title);
            currentUserDB.child("description").setValue(description);
            currentUserDB.child("user").setValue(mAuth.getCurrentUser().getUid());
            Toast.makeText(CreatePubActivity.this, "created publication success", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CreatePubActivity.this, MainActivity.class);
            startActivity(intent);
                            } else
                                       Toast.makeText(CreatePubActivity.this, "failed to create publication", Toast.LENGTH_SHORT).show();

                        }


                        public int GenerateId(){
                            int randomId = 0;
                            Random rand = new Random();
                            for (int j=0; j<10; j++){
                                randomId = rand.nextInt();
                            }
                                return randomId;
                        }






}
