package cl.inacap.wikisimpsons.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

import cl.inacap.wikisimpsons.R;
import cl.inacap.wikisimpsons.dto.Consejo;

public class ConsejoAdapter extends ArrayAdapter<Consejo> {
    private List<Consejo> consejos;
    private Activity activity;
    public ConsejoAdapter(@NonNull Activity context, int resource, @NonNull List<Consejo> objects) {
        super(context, resource, objects);
        this.consejos = objects;
        this.activity = context;
    }


    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater inflater = this.activity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_consejos, null, true);
        TextView nombreTxt = rowView.findViewById(R.id.nombre_txt);
        TextView consejoTxt = rowView.findViewById(R.id.consejo_txt);
        ImageView imagePer = rowView.findViewById(R.id.image_per);
        nombreTxt.setText(consejos.get(position).getCharacter());
        consejoTxt.setText(consejos.get(position).getQuote());
        Picasso.get().load(this.consejos.get(position).getImage())
                .resize(360, 640)
                .centerCrop()
                .into(imagePer);
        return rowView;
    }

}
