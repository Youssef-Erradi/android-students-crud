package com.etudiant;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.etudiant.dao.EtudiantDAO;
import com.etudiant.dao.FiliereDAO;
import com.etudiant.entities.Etudiant;
import com.etudiant.entities.Filiere;

public class EtudiantActivity extends AppCompatActivity {
    private Button btnAjouter, btnFiliere;
    private ListView listView;
    private EtudiantAdapter adapter;
    private final EtudiantDAO etudiantDAO = new EtudiantDAO(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etudiant);
        initViews();
        btnAjouter.setOnClickListener(v -> {
            getIntent().setClass(this, EtudiantFormActivity.class);
            finish();
            startActivity(getIntent());
        });
        btnFiliere.setOnClickListener(v -> {
            getIntent().setClass(this, MainActivity.class);
            finish();
            startActivity(getIntent());
        });
        listView.setOnItemClickListener((adapterView, view, pos, id) -> {
            Etudiant etudiant = adapter.getItem(pos);
            Intent intent = new Intent(this, EtudiantFormActivity.class);
            intent.putExtra("etudiant", etudiant.getId());
            startActivity(intent);
        });
        listView.setOnItemLongClickListener((adapterView, view, pos, id) -> {
            Etudiant etudiant = adapter.getItem(pos);
            confirmDialog(etudiant);
            return true;
        });
    }

    private void initViews() {
        btnAjouter = findViewById(R.id.btnAjouter);
        btnFiliere = findViewById(R.id.btnFiliere);
        listView = findViewById(R.id.listView);
        adapter = new EtudiantAdapter(this, etudiantDAO.findAll());
        listView.setAdapter(adapter);
    }

    private void confirmDialog(Etudiant etudiant) {
        new AlertDialog.Builder(this)
                .setTitle("Formulaire de suppression")
                .setMessage("Voulez-vous supprimer ce etudiant :\n" + etudiant.toString())
                .setNeutralButton("Annuler", (dialog, which) -> dialog.cancel())
                .setPositiveButton("Supprimer", (dialog, which) -> {
                    etudiantDAO.deleteById(etudiant.getId());
                    adapter.remove(etudiant);
                    showToast("L'etudiant a été supprimé avec succés", Toast.LENGTH_LONG);
                }).show();
    }

    private void showToast(String message, int length) {
        Toast.makeText(this, message, length).show();
    }
}