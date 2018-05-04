package co.edu.konradlorenz.autenticacion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;



public class HomeFragment extends Fragment {

    // Firebase var

   // private DatabaseReference rootRef1;
    // Android Layout
    private ListView listView;

    //Array List

    private ArrayAdapter<String> adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    View rootview = inflater.inflate(R.layout.fragment_home,container, false);

    listView = (ListView) rootview.findViewById(R.id.listViewH);
    ArrayList<PublicationEntity> arrayL = new ArrayList<>();

   // adapter = new ArrayAdapter(getActivity(),
    //android.R.layout.activity_list_item, android.R.id.text1);



    //Firebase
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("publications");
        //rootRef1 = FirebaseDatabase.getInstance().getReference("users");
        //read adds
        rootRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                PublicationEntity resultado = dataSnapshot.getValue(PublicationEntity.class);
                UserEntity resultado1 = dataSnapshot.getValue(UserEntity.class);
                String title = resultado.getTitle();
                arrayL.add(new PublicationEntity(title));
                NotesAdapter adap = new NotesAdapter(getActivity(), R.layout.notes_item,arrayL);
                listView.setAdapter(adap);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.i("JIACP","Ingresó al Fragment de profile");
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.i("JIACP","Ingresó al Fragment de profile");
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.i("JIACP","Ingresó al Fragment de profile");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG:", "Failed to read value.", error.toException());
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Query mQuery = rootRef.orderByValue().equalTo((String)
                       // listView.getItemAtPosition(position));
                Intent intent =new Intent(getActivity(),PubDetails.class);
                startActivity(intent);
            }
        });



        return rootview;
    }
}