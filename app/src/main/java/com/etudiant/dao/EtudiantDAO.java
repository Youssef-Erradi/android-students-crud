package com.etudiant.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.etudiant.entities.Etudiant;
import com.etudiant.entities.Filiere;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EtudiantDAO implements IDAO<Etudiant> {
    private final SQLiteDB sqlite;
    private final FiliereDAO filiereDAO;

    public EtudiantDAO(Context context) {
        this.sqlite = new SQLiteDB(context);
        this.filiereDAO = new FiliereDAO(context);
    }

    @Override
    public List<Etudiant> findAll() {
        List<Etudiant> etudiants = new ArrayList<>();
        String sql = "SELECT * FROM "+sqlite.TABLE_ETUDIANTS;
        Cursor cursor = sqlite.getReadableDatabase().rawQuery(sql, null);
        while (cursor.moveToNext()) {
            Bitmap photo = BitmapFactory.decodeByteArray(cursor.getBlob(4), 0, cursor.getBlob(4).length);
            LocalDate date = LocalDate.parse(cursor.getString(3));
            Filiere filiere = filiereDAO.getById(cursor.getInt(6));
            etudiants.add(
                    new Etudiant(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                            date, cursor.getString(5), photo, filiere)
            );
        }
        return etudiants;
    }

    @Override
    public Etudiant getById(Integer id) {
        Etudiant etudiant = new Etudiant();
        String sql = "SELECT * FROM "+sqlite.TABLE_ETUDIANTS + " WHERE id="+id;
        Cursor cursor = sqlite.getReadableDatabase().rawQuery(sql, null);
        if (cursor.moveToNext()) {
            Bitmap photo = BitmapFactory.decodeByteArray(cursor.getBlob(4), 0, cursor.getBlob(4).length);
            LocalDate date = LocalDate.parse(cursor.getString(3));
            Filiere filiere = filiereDAO.getById(cursor.getInt(6));
            etudiant = new Etudiant(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                            date, cursor.getString(5), photo, filiere);
        }
        return etudiant;
    }

    @Override
    public void deleteById(Integer id) {
        sqlite.getWritableDatabase().delete(sqlite.TABLE_ETUDIANTS,"id=?", new String[]{id+""});
    }

    @Override
    public long save(Etudiant etudiant) {
        long id = -1;
        ContentValues values = new ContentValues();
        values.put(sqlite.COL_ETUDIANTS_NOM, etudiant.getNom());
        values.put(sqlite.COL_ETUDIANTS_PRENOM, etudiant.getPrenom());
        values.put(sqlite.COL_ETUDIANTS_DATE, etudiant.getDateNaissance().toString());
        values.put(sqlite.COL_ETUDIANTS_VILLE, etudiant.getVille());
        values.put(sqlite.COL_ETUDIANTS_PHOTO, getBitmapAsByteArray(etudiant.getPhoto()));
        values.put(sqlite.COL_ETUDIANTS_FILIERE, etudiant.getFiliere().getId());
        try {
            id = sqlite.getWritableDatabase().insertOrThrow(sqlite.TABLE_ETUDIANTS, "", values);
        } catch (SQLException ex) {
            Log.e("Exception", ex.getMessage());
        }
        return id;
    }

    @Override
    public void update(Etudiant etudiant, Integer id) {
        ContentValues values = new ContentValues();
        values.put(sqlite.COL_ETUDIANTS_NOM, etudiant.getNom());
        values.put(sqlite.COL_ETUDIANTS_PRENOM, etudiant.getPrenom());
        values.put(sqlite.COL_ETUDIANTS_DATE, etudiant.getDateNaissance().toString());
        values.put(sqlite.COL_ETUDIANTS_VILLE, etudiant.getVille());
        values.put(sqlite.COL_ETUDIANTS_PHOTO, getBitmapAsByteArray(etudiant.getPhoto()));
        values.put(sqlite.COL_ETUDIANTS_FILIERE, etudiant.getFiliere().getId());
        sqlite.getWritableDatabase().update(sqlite.TABLE_ETUDIANTS, values, "id=?", new String[]{id+""});
    }

    private byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }
}
