package co.edu.konradlorenz.autenticacion;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.math.BigInteger;
import java.security.SecureRandom;

public class UpdateUser extends AppCompatActivity {

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    private int CAMERA_REQUEST_CODE = 0;
    private ProgressDialog progressDialog;
    private StorageReference mStorage;
    private DatabaseReference mDatabase;
    private ImageView imageProfile;
    private String m;
    private Button btnSaveInfo;
    private EditText Name;
    private EditText LastName;
    private AutoCompleteTextView Email;
    private EditText numberPhone;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);
        Email = (AutoCompleteTextView) findViewById(R.id.txt_Email);
        Name = (EditText) findViewById(R.id.txt_Name);
        LastName = (EditText) findViewById(R.id.txt_LastName);
        numberPhone = (EditText) findViewById(R.id.txt_PhoneNumber);
        btnSaveInfo = (Button) findViewById(R.id.save_changes);



        SharedPreferences sharedPref = UpdateUser.this.getPreferences(Context.MODE_PRIVATE);
        //String uid = sharedPref.getString("current_user", "");
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        m = currentUser.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("users");
        myRef.child(m).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserEntity value = dataSnapshot.getValue(UserEntity.class);
                Name.setText(value.getName() );
                LastName.setText(value.getLastname() );
                numberPhone.setText(value.getPhonenumber() );
                Email.setText(value.getEmail() );

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("JIACP","Ingresó al Fragment de profile");
            }
        });

         btnSaveInfo.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                editUser();
                 }
         }

    );


        imageProfile = (ImageView) findViewById(R.id.imageV);
        imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(Intent.createChooser(intent, "Select a picture for your profile"), CAMERA_REQUEST_CODE);
                }
            }
        });

        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                   // Log.i("JIACP","Ingreso al fragmento");
                    mStorage = FirebaseStorage.getInstance().getReference();
                    mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
                    mDatabase.child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            String imageUrl = String.valueOf(dataSnapshot.child("image").getValue());
                            if (URLUtil.isValidUrl(imageUrl))
                                Picasso.with(UpdateUser.this).load(Uri.parse(imageUrl)).into(imageProfile);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } else {
                    startActivity(new Intent(UpdateUser.this, MainActivity.class));
                    finish();
                }
            }
        };
    }

    private void editUser() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("users");
        DatabaseReference elemento = myRef.child(m);
        // elemento.child("name").setValue("algo");
        final String name = Name.getText().toString().trim();
        final String lastname = LastName.getText().toString().trim();
        final String email = Email.getText().toString().trim();
        final String numberphone = numberPhone.getText().toString().trim();
        elemento.child("name").setValue(name);
        elemento.child("lastname").setValue(lastname);
        elemento.child("email").setValue(email);
        elemento.child("phonenumber").setValue(numberphone);
        Toast.makeText(UpdateUser.this, "Successfully updated user", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(UpdateUser.this, MainActivity.class);
        startActivity(intent);
    }

    public String getRandomString() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UCrop.RESULT_ERROR) {
            Toast.makeText(this, "uCrop error", Toast.LENGTH_SHORT).show();
            return;
        }
        if (requestCode == UCrop.REQUEST_CROP) {
            final Uri imgUri = UCrop.getOutput(data);
            Toast.makeText(this, imgUri.getPath(), Toast.LENGTH_SHORT).show();
            uploadImage(imgUri);
            return;
        }

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {

            final Uri sourceUri = data.getData();
            if (sourceUri == null) {
                progressDialog.dismiss();
                return;
            } else {
                File tempCropped = new File(getCacheDir(), "tempImgCropped.png");
                Uri destinationUri = Uri.fromFile(tempCropped);
                UCrop.of(sourceUri, destinationUri)
                        //.withAspectRatio(3, 2)
                        //.withMaxResultSize(MAX_WIDTH, MAX_HEIGHT)
                        .start(this);
            }
        }
    }

    public void uploadImage(final Uri fileUri) {
        if (mAuth.getCurrentUser() == null)
            return;

        if (mStorage == null)
            mStorage = FirebaseStorage.getInstance().getReference();
        if (mDatabase == null)
            mDatabase = FirebaseDatabase.getInstance().getReference().child("users");

        final StorageReference filepath = mStorage.child("Photos").child(getRandomString());/*uri.getLastPathSegment()*/
        final DatabaseReference currentUserDB = mDatabase.child(mAuth.getCurrentUser().getUid());

        progressDialog.setMessage("Uploading image...");
        progressDialog.show();

        currentUserDB.child("image").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String image = dataSnapshot.getValue().toString();

                if (!image.equals("default") && !image.isEmpty()) {
                    Task<Void> task = FirebaseStorage.getInstance().getReferenceFromUrl(image).delete();
                    task.addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                                Toast.makeText(UpdateUser.this, "Deleted image succesfully", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(UpdateUser.this, "Deleted image failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                currentUserDB.child("image").removeEventListener(this);

                filepath.putFile(fileUri).addOnSuccessListener(UpdateUser.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        Uri downloadUri = taskSnapshot.getDownloadUrl();
                        Toast.makeText(UpdateUser.this, "Finished", Toast.LENGTH_SHORT).show();
                        Picasso.with(UpdateUser.this).load(fileUri).fit().centerCrop().into(imageProfile);
                        DatabaseReference currentUserDB = mDatabase.child(mAuth.getCurrentUser().getUid());
                        currentUserDB.child("image").setValue(downloadUri.toString());
                    }
                }).addOnFailureListener(UpdateUser.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(UpdateUser.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
