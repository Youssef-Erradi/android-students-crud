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

import com.etudiant.dao.FiliereDAO;
import com.etudiant.entities.Filiere;

import java.io.IOException;

public class EtudiantFormActivity extends AppCompatActivity {
    private EditText txtId, txtNom, txtPrenom, txtDateNaissance, txtVille;
    private ImageView imageView;
    private Bitmap photo;
    private Button upload;
    private Spinner spinner;
    private final FiliereDAO filiereDAO = new FiliereDAO(this);
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
    }

    private void initViews() {
        txtId = findViewById(R.id.txtId);
        txtNom = findViewById(R.id.txtNom);
        txtPrenom = findViewById(R.id.txtPrenom);
        txtDateNaissance = findViewById(R.id.txtDate);
        txtVille = findViewById(R.id.txtVille);
        spinner = findViewById(R.id.filiereSpn);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, filiereDAO.findAll());
        spinner.setAdapter(adapter);
        upload = findViewById(R.id.upload);
        imageView = findViewById(R.id.photo);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1)
            if (resultCode == RESULT_OK) {
                Bitmap photo = null;
                try {
                    photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                    imageView.setImageBitmap(photo);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }
}