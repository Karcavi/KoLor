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
import android.webkit.URLUtil;
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
import com.squareup.picasso.Picasso;


public class ProfileFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Log.i("JIACP","Ingreso al fragmento");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        Button btnAddP = (Button) view.findViewById(R.id.sign_out_buttonF);
        btnAddP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                //finish();
            }
        });
        Button btnEditU = (Button) view.findViewById(R.id.edit_user);
        btnEditU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileFragment.this.getActivity(), UpdateUser.class);
                startActivity(intent);
                //finish();
            }
        });
        TextView name = view.findViewById(R.id.txtName);
        TextView lastname = view.findViewById(R.id.txtLastName);
        TextView phonenumber = view.findViewById(R.id.txtPhoneNumber);
        TextView email = view.findViewById(R.id.txtEmail);
        ImageView imageProfile = (ImageView) view.findViewById(R.id.imageView);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("users");


        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        //String uid = sharedPref.getString("current_user", "");
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String m = currentUser.getUid();


        /*
         * Cambiar
         * */
       // DatabaseReference elemento = myRef.child(uid);
       // elemento.child("name").setValue("algo");

        /*
         * Para leer
         * */
        myRef.child(m).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserEntity value = dataSnapshot.getValue(UserEntity.class);
                name.setText(value.getName() );
                lastname.setText(value.getLastname() );
                phonenumber.setText(value.getPhonenumber() );
                email.setText(value.getEmail() );

                String imageUrl = String.valueOf(dataSnapshot.child("image").getValue());
                if (URLUtil.isValidUrl(imageUrl))
                    Picasso.with(getActivity()).load(Uri.parse(imageUrl)).into(imageProfile);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("JIACP","Ingresó al Fragment de profile");
            }
        });

        //Log.i("JIACP","Todo OK");

        return view;

    }




}
