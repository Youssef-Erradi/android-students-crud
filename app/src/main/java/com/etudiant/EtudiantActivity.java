package com.etudiant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.etudiant.dao.EtudiantDAO;
import com.etudiant.dao.FiliereDAO;
import com.etudiant.entities.Etudiant;
import com.etudiant.entities.Filiere;

public class EtudiantActivity extends AppCompatActivity {
    private Button btnAjouter, btnFiliere;
    private ListView listView;
    private ArrayAdapter<Etudiant> adapter;
    private final EtudiantDAO etudiantDAO = new EtudiantDAO(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etudiant);
        initViews();

        btnAjouter.setOnClickListener(v -> {
            getIntent().setClass(this, EtudiantFormActivity.class);
            startActivity(getIntent());
        });
        btnFiliere.setOnClickListener(v -> {
            getIntent().setClass(this, MainActivity.class);
            finish();
            startActivity(getIntent());
        });
    }

    private void initViews() {
        btnAjouter = findViewById(R.id.btnAjouter);
        btnFiliere = findViewById(R.id.btnFiliere);
        listView = findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, etudiantDAO.findAll());
        listView.setAdapter(adapter);
    }
}