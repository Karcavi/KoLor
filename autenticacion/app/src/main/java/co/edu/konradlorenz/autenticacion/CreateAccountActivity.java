package co.edu.konradlorenz.autenticacion;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText Name;
    private EditText LastName;
    private AutoCompleteTextView Email;
    private EditText Password;
    private EditText numberPhone;
    private Button mRegisterButton;
    static  CreateAccountActivity instance;
    private FirebaseAuth mAuth;
    private ProgressDialog mProgress;
    private TextView TextLogin;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        instance = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mAuth = FirebaseAuth.getInstance();
        Email = (AutoCompleteTextView) findViewById(R.id.etxt_email);
        Name = (EditText) findViewById(R.id.etxt_name);
        LastName = (EditText) findViewById(R.id.etxt_lastname);
        Password = (EditText) findViewById(R.id.etxt_password);
        numberPhone = (EditText) findViewById(R.id.etxt_numphone);
        mRegisterButton = (Button) findViewById(R.id.btn_register);
        TextLogin = (TextView) findViewById(R.id.hereToLogin);
        TextLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        mProgress = new ProgressDialog(this);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegister();
            }
        });
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

    }

    private void startRegister() {
        final String name = Name.getText().toString().trim();
        final String lastname = LastName.getText().toString().trim();
        final String email = Email.getText().toString().trim();
        final String password = Password.getText().toString().trim();
        final String numberphone = numberPhone.getText().toString().trim();

        if (!TextUtils.isEmpty(name)  && !TextUtils.isEmpty(lastname)  && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(numberphone)) {
            mProgress.setMessage("Registering, please wait...");
            mProgress.show();
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            mProgress.dismiss();
                            if (task.isSuccessful()) {
                                mAuth.signInWithEmailAndPassword(email, password);
                                //Toast.makeText(ActivityRegister.this, user_id, Toast.LENGTH_SHORT).show();

                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
                                DatabaseReference currentUserDB = mDatabase.child(mAuth.getCurrentUser().getUid());
                                currentUserDB.child("name").setValue(name);
                                currentUserDB.child("lastname").setValue(lastname);
                                currentUserDB.child("email").setValue(email);
                                currentUserDB.child("phonenumber").setValue(numberphone);
                                currentUserDB.child("image").setValue("default");
                            } else
                                Toast.makeText(CreateAccountActivity.this, "failed to registering user", Toast.LENGTH_SHORT).show();

                        }
                    });
        }

    }

}

