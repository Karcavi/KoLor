package co.edu.konradlorenz.autenticacion;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {

    private EditText Title;
    private EditText Desrcription;

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
        return view;
    }

}
