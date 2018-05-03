package co.edu.konradlorenz.autenticacion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

public class AdDetail extends Fragment {

    private String n;

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.addetail_activity, container, false);

        /*Button btnAddP = (Button) view.findViewById(R.id.sign_out_buttonF);
        btnAddP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                //finish();
            }
        });*/

        /*Button btnEditU = (Button) view.findViewById(R.id.edit_user);
        btnEditU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UpdateUser.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                //finish();
            }
        });*/

        TextView user = view.findViewById(R.id.createdBy);
        TextView userEmail = view.findViewById(R.id.userEmail);
        TextView title = view.findViewById(R.id.adName);
        TextView description = view.findViewById(R.id.descriptionText);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("publications");


        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        //String uid = sharedPref.getString("current_user", "");
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        n = currentUser.getUid();


        /*Cambiar*/
        // DatabaseReference elemento = myRef.child(uid);
        // elemento.child("name").setValue("algo");

        /* Para leer*/
        myRef.child(n).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                PublicationEntity value = dataSnapshot.getValue(PublicationEntity.class);
                user.setText(value.getUser());
                userEmail.setText(value.getEmail());
                title.setText(value.getTitle());
                description.setText(value.getDescription());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;


    }
}
