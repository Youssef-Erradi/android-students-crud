package com.etudiant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.etudiant.entities.Etudiant;

import java.util.List;

public class EtudiantAdapter extends ArrayAdapter<Etudiant> {

    private Context context;
    private List<Etudiant> etudiants;

    public EtudiantAdapter(Context context, List<Etudiant> list) {
        super(context, R.layout.row);
        etudiants = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return etudiants.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row, parent, false);

            holder = new ViewHolder();
            holder.nom = (TextView) convertView.findViewById(R.id.nomComplet);
            holder.ville = (TextView) convertView.findViewById(R.id.ville);
            holder.age = (TextView) convertView.findViewById(R.id.dateNaissance);
            holder.filiere = (TextView) convertView.findViewById(R.id.filiere);
            holder.image = (ImageView) convertView.findViewById(R.id.img);

            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        Etudiant e = etudiants.get(position);
        holder.nom.setText(e.getNom()+" "+e.getPrenom());
        holder.ville.setText(e.getVille());
        holder.age.setText(e.getDateNaissance().toString());
        holder.filiere.setText(e.getFiliere().toString());
        holder.image.setImageBitmap(e.getPhoto());

        return convertView;
    }

    class ViewHolder {
        TextView nom, ville, age, filiere;
        ImageView image;
    }
}
