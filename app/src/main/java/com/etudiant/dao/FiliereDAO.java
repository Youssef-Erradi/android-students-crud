package com.etudiant.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.etudiant.entities.Filiere;

import java.util.ArrayList;
import java.util.List;

public class FiliereDAO implements IDAO<Filiere> {
    private final SQLiteDB sqlite;

    public FiliereDAO(Context context) {
        this.sqlite = new SQLiteDB(context);
    }

    @Override
    public List<Filiere> findAll() {
        List<Filiere> filieres = new ArrayList<>();
        String sql = "SELECT * FROM "+sqlite.TABLE_FILIERES;
        Cursor cursor = sqlite.getReadableDatabase().rawQuery(sql, null);
        while (cursor.moveToNext())
            filieres.add(
                    new Filiere(cursor.getInt(0), cursor.getString(1))
            );
        return filieres;
    }

    @Override
    public Filiere getById(Integer id) {
        Filiere filiere = null;
        String sql = "SELECT "+sqlite.COL_FILIERES_INTITULE+" FROM "+sqlite.TABLE_FILIERES+" WHERE id="+id;
        Cursor cursor = sqlite.getReadableDatabase().rawQuery(sql, null);
        if(cursor.moveToNext())
            filiere = new Filiere(id, cursor.getString(0));
        return filiere;
    }

    @Override
    public void deleteById(Integer id) {
        sqlite.getWritableDatabase().delete(sqlite.TABLE_FILIERES,"id=?", new String[]{id+""});
    }

    @Override
    public long save(Filiere filiere) {
        long id = -1;
        ContentValues values = new ContentValues();
        values.put(sqlite.COL_FILIERES_ID, filiere.getId());
        values.put(sqlite.COL_FILIERES_INTITULE, filiere.getIntitule());
        try {
            id = sqlite.getWritableDatabase().insert(sqlite.TABLE_FILIERES, null, values);
        } catch (SQLiteException ex) {
            Log.e("SQLiteException", ex.getMessage());
        }
        return id;
    }

    @Override
    public void save(Filiere filiere, Integer id) {
        ContentValues values = new ContentValues();
        values.put(sqlite.COL_FILIERES_INTITULE, filiere.getIntitule());
        sqlite.getWritableDatabase().update(sqlite.TABLE_FILIERES, values, "id=?", new String[]{id+""});
    }
}
