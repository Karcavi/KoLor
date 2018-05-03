package co.edu.konradlorenz.autenticacion;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {

    private EditText Title;
    private EditText Desrcription;
    // Firebase var
    private DatabaseReference rootRef;
    // private DatabaseReference rootRef1;
    // Android Layout
    private ListView listView;
    private FirebaseAuth mAuth;
    //Array List
    private ArrayList<PublicationEntity> arrayList = new ArrayList<PublicationEntity>();

    private ArrayAdapter<String> adapter;
    private String title;
    private String category;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dashboard,container,false);

        Button btnAddP = (Button) view.findViewById(R.id.addPButton);
        btnAddP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DashboardFragment.this.getActivity(), CreatePubActivity.class);
                startActivity(intent);
            }
        });

        Title = (EditText) view.findViewById(R.id.etxt_name);
        Desrcription = (EditText) view.findViewById(R.id.etxt_lastname);
       // adapter = new ArrayAdapter(getActivity(),
          //      android.R.layout.activity_list_item, android.R.id.text1);

        listView = (ListView) view.findViewById(R.id.listViewD);
        ArrayList<PublicationEntity> arrayL = new ArrayList<>();

        //Firebase
        rootRef = FirebaseDatabase.getInstance().getReference("publications");
        //rootRef1 = FirebaseDatabase.getInstance().getReference("users");
        //read adds
        rootRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                PublicationEntity resultado = dataSnapshot.getValue(PublicationEntity.class);
                UserEntity resultado1 = dataSnapshot.getValue(UserEntity.class);
                title = resultado.getTitle();
                //category = resultado.getCategory();
                arrayL.add(new PublicationEntity(title));
                NotesAdapter adap = new NotesAdapter(getActivity(), R.layout.notes_item,arrayL);
                listView.setAdapter(adap);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG:", "Failed to read value.", error.toException());
            }
        });
        return view;
    }

}
