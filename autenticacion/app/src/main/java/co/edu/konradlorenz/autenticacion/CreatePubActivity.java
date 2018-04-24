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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreatePubActivity extends AppCompatActivity {


    private EditText Title;
    private EditText Description;
    private Button CreatePButton;
    static CreateAccountActivity instance;
    private FirebaseAuth mAuth;
    private ProgressDialog mProgress;
    private Button CancelPButton;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pub);

        mAuth = FirebaseAuth.getInstance();
        Title = (EditText) findViewById(R.id.etxt_name);
        Description = (EditText) findViewById(R.id.etxt_lastname);
        CreatePButton = (Button) findViewById(R.id.btn_createPub);
        CancelPButton = (Button) findViewById(R.id.btn_cancelPub);
        CancelPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreatePubActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        mProgress = new ProgressDialog(this);
        CreatePButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPub();
            }
        });
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    Intent intent = new Intent(CreatePubActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

    }

    private void createPub() {
        final String title = Title.getText().toString().trim();
        final String description = Description.getText().toString().trim();

        if (!TextUtils.isEmpty(title)  && !TextUtils.isEmpty(description) ) {
            mProgress.setMessage("creating, please wait...");
            mProgress.show();

                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("publications");
                                DatabaseReference currentUserDB = mDatabase.child(mAuth.getCurrentUser().getUid());
                                currentUserDB.child("title").setValue(title);
                                currentUserDB.child("description").setValue(description);
                            } else
                                Toast.makeText(CreatePubActivity.this, "failed to registering user", Toast.LENGTH_SHORT).show();

                        }







}
