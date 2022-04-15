package com.etudiant;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.etudiant.dao.FiliereDAO;
import com.etudiant.entities.Etudiant;
import com.etudiant.entities.Filiere;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private Button btnAjouter, btnEtudiant;
    private ListView listView;
    private ArrayAdapter<Filiere> adapter;
    private final FiliereDAO filiereDAO = new FiliereDAO(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        btnAjouter.setOnClickListener(v -> formDialog(null));
        listView.setOnItemLongClickListener((adapterView, view, pos, id) -> {
            Filiere filiere = adapter.getItem(pos);
            confirmDialog(filiere);
            return true;
        });
        listView.setOnItemClickListener((adapterView, view, pos, id) -> {
            Filiere filiere = adapter.getItem(pos);
            formDialog(filiere);
        });
        btnEtudiant.setOnClickListener(v->{
            getIntent().setClass(this, EtudiantActivity.class);
            startActivity(getIntent());
        });
    }

    private void initViews() {
        btnAjouter = findViewById(R.id.btnAjouter);
        btnEtudiant = findViewById(R.id.btnEtudiant);
        listView = findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, filiereDAO.findAll());
        listView.setAdapter(adapter);
    }

    private void formDialog(Filiere f) {
        EditText txtIntitule = new EditText(this);
        txtIntitule.setHint("Entrer l'intitulé de la Filière *");
        if (f != null)
            txtIntitule.setText(f.getIntitule());
        new AlertDialog.Builder(this)
                .setTitle("Formulaire")
                .setView(txtIntitule)
                .setNeutralButton("Annuler", (dialog, which) -> dialog.cancel())
                .setPositiveButton("Valider", (dialog, which) -> {
                    String intitule = txtIntitule.getText().toString().trim();
                    if (intitule.isEmpty()) {
                        showToast("Remplissez le champ intitulé d'abord", Toast.LENGTH_SHORT);
                        return;
                    }
                    if( f == null ){
                        Filiere filiere = new Filiere(null, intitule);
                        int id = (int) filiereDAO.save(filiere);
                        filiere.setId(id);
                        adapter.add(filiere);
                    } else {
                        f.setIntitule(intitule);
                        filiereDAO.update(f, f.getId());
                    }
                    showToast("filière enregistrée avec succés", Toast.LENGTH_LONG);
                }).show();
    }

    private void confirmDialog(Filiere filiere) {
        new AlertDialog.Builder(this)
                .setTitle("Formulaire de suppression")
                .setMessage("Voulez-vous supprimer cette filiere :\n" + filiere.toString())
                .setNeutralButton("Annuler", (dialog, which) -> dialog.cancel())
                .setPositiveButton("Supprimer", (dialog, which) -> {
                    filiereDAO.deleteById(filiere.getId());
                    adapter.remove(filiere);
                    showToast("La filiere a été supprimée avec succés", Toast.LENGTH_LONG);
                }).show();
    }

    private void showToast(String message, int length) {
        Toast.makeText(this, message, length).show();
    }
}