package co.edu.konradlorenz.autenticacion;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import co.edu.konradlorenz.autenticacion.PublicationEntity;
import co.edu.konradlorenz.autenticacion.R;

/**
 * Created by ingenieria on 22/02/18.
 */

public class NotesAdapter extends ArrayAdapter<PublicationEntity> {

    public NotesAdapter(@NonNull Context context, int resource, @NonNull List<PublicationEntity> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View current_view = convertView;

        if (current_view==null){
            LayoutInflater vi = LayoutInflater.from(getContext());
            current_view = vi.inflate(R.layout.notes_item,null);
        }

        PublicationEntity current_note = getItem(position);

        ImageView image = (ImageView) current_view.findViewById(R.id.list_notes_imagen);
        TextView texto1 = (TextView) current_view.findViewById(R.id.list_notes_text1);
        TextView texto2 = (TextView) current_view.findViewById(R.id.list_notes_text2);

        texto1.setText(current_note.getTitle());
        texto2.setText(current_note.getCategory());

       // if (current_note.getKind() == 1){
          // image.setImageDrawable(getContext().getResources().getDrawable(android.R.drawable.ic_lock_idle_alarm));

       // }


        return current_view;
    }


}
