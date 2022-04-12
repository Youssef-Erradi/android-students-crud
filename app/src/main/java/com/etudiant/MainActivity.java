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
    private Button btnAjouter;
    private ListView listView;
    private ArrayAdapter<Filiere> adapter;
    private final FiliereDAO filiereDAO = new FiliereDAO(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        btnAjouter.setOnClickListener(v -> ajouterFiliereDialog());
    }

    private void initViews() {
        btnAjouter = findViewById(R.id.btnAjouter);
        listView = findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, filiereDAO.findAll());
        listView.setAdapter(adapter);
    }

    private void ajouterFiliereDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        EditText txtIntitule = new EditText(this);
        txtIntitule.setHint("Entrer l'intitulé de la Filière *");
        builder.setTitle("Formulaire d'ajout ");
        builder.setView(txtIntitule);
        builder.setNeutralButton("Annuler", (dialog, which) -> dialog.cancel());
        builder.setPositiveButton("Ajouter", (dialog, which) -> {
            String intitule = txtIntitule.getText().toString().trim();
            if(intitule.isEmpty()){
                Toast.makeText(this, "Remplissez le champ", Toast.LENGTH_SHORT).show();
                return;
            }
            Filiere filiere = new Filiere(null, intitule);
            int id = (int) filiereDAO.save(filiere);
            filiere.setId(id);
            adapter.add(filiere);
            Toast.makeText(this, "filière ajoutée avec succés", Toast.LENGTH_LONG).show();
        });
        builder.show();
    }
}