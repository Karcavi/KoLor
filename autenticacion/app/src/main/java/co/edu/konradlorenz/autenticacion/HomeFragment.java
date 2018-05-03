package co.edu.konradlorenz.autenticacion;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    // Firebase var
    private DatabaseReference rootRef;
   // private DatabaseReference rootRef1;
    // Android Layout
    private ListView listView;

    //Array List
    private ArrayList<PublicationEntity> arrayList = new ArrayList<PublicationEntity>();
    private ArrayAdapter<String> adapter;


    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    View rootview = inflater.inflate(R.layout.fragment_home,container, false);


    adapter = new ArrayAdapter(getActivity(),
    android.R.layout.activity_list_item, android.R.id.text1);

    listView = (ListView) rootview.findViewById(R.id.listViewH);
    listView.setAdapter(adapter);
    //Firebase
        rootRef = FirebaseDatabase.getInstance().getReference("publications");
        //rootRef1 = FirebaseDatabase.getInstance().getReference("users");
        //read adds
        rootRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                PublicationEntity resultado = dataSnapshot.getValue(PublicationEntity.class);
                UserEntity resultado1 = dataSnapshot.getValue(UserEntity.class);
                adapter.add(resultado.getTitle());
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



        return rootview;
    }
}