package com.etudiant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.etudiant.dao.EtudiantDAO;
import com.etudiant.dao.FiliereDAO;
import com.etudiant.entities.Etudiant;
import com.etudiant.entities.Filiere;

import java.io.IOException;
import java.time.LocalDate;

public class EtudiantFormActivity extends AppCompatActivity {
    private EditText txtId, txtNom, txtPrenom, txtDateNaissance, txtVille;
    private ImageView imageView;
    private Bitmap photo;
    private Button upload, submit;
    private Spinner spinner;
    private final FiliereDAO filiereDAO = new FiliereDAO(this);
    private final EtudiantDAO etudiantDAO = new EtudiantDAO(this);
    private ArrayAdapter<Filiere> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etudiant_form);
        initViews();

        upload.setOnClickListener(v -> {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent ,1);
        });

        submit.setOnClickListener(v ->{
            String nom = txtNom.getText().toString().trim(),
                    prenom = txtPrenom.getText().toString().trim(),
                    ville = txtVille.getText().toString().trim();
            Filiere filiere = (Filiere) spinner.getSelectedItem();
            try{
                LocalDate dateNaissance = LocalDate.parse(txtDateNaissance.getText().toString());
                Etudiant etudiant = new Etudiant(null,nom,prenom,dateNaissance,ville, photo, filiere);
                etudiantDAO.save(etudiant);
                getIntent().setClass(this, EtudiantActivity.class);
                finish();
                startActivity(getIntent());
                showToast("Etudiant enregistré avec succés", Toast.LENGTH_SHORT);
            }catch(Exception exception){
                showToast(exception.getMessage(), Toast.LENGTH_SHORT);
            }
        });
    }

    private void showToast(String message, int length) {
        Toast.makeText(this, message, length).show();
    }

    private void initViews() {
        txtId = findViewById(R.id.txtId);
        txtId.setEnabled(false);
        txtNom = findViewById(R.id.txtNom);
        txtPrenom = findViewById(R.id.txtPrenom);
        txtDateNaissance = findViewById(R.id.txtDate);
        txtVille = findViewById(R.id.txtVille);
        spinner = findViewById(R.id.filiereSpn);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, filiereDAO.findAll());
        spinner.setAdapter(adapter);
        upload = findViewById(R.id.upload);
        imageView = findViewById(R.id.photo);
        submit = findViewById(R.id.submit);
        submit.setEnabled(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1)
            if (resultCode == RESULT_OK) {
                try {
                    submit.setEnabled(true);
                    photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                    imageView.setImageBitmap(photo);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }
}