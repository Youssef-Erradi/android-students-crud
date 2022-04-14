package com.etudiant.dao;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.etudiant.entities.Etudiant;
import com.etudiant.entities.Filiere;

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
            Bitmap photo = BitmapFactory.decodeByteArray(cursor.getBlob(5), 0, cursor.getBlob(5).length);
            LocalDate date = LocalDate.parse(cursor.getString(3));
            Filiere filiere = filiereDAO.getById(cursor.getInt(6));
            etudiants.add(
                    new Etudiant(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                            date, cursor.getString(4), photo, filiere)
            );
        }
        return etudiants;
    }

    @Override
    public Etudiant getById(Integer id) {
        return null;
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public long save(Etudiant etudiant) {
        return 0;
    }

    @Override
    public void update(Etudiant etudiant, Integer id) {

    }
}
