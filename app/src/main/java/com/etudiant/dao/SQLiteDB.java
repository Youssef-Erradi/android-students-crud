package com.etudiant.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "etudiants.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_ETUDIANTS = "Etudiants";
    public static final String COL_ETUDIANTS_ID = "Id";
    public static final String COL_ETUDIANTS_NOM = "Nom";
    public static final String COL_ETUDIANTS_PRENOM = "Prenom";
    public static final String COL_ETUDIANTS_VILLE = "Ville";
    public static final String COL_ETUDIANTS_PHOTO = "Photo";
    public static final String COL_ETUDIANTS_FILIERE = "Id_Filiere";

    public static final String TABLE_FILIERES = "Filieres";
    public static final String COL_FILIERES_ID = "Id";
    public static final String COL_FILIERES_INTITULE = "intitule";

    private static final String SQL_DELETE_TABLE_ETUDIANTS = "DROP TABLE IF EXISTS " + TABLE_ETUDIANTS;
    private static final String SQL_CREATE_TABLE_ETUDIANTS = "CREATE TABLE " +
            TABLE_ETUDIANTS + " ("
            + COL_ETUDIANTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_ETUDIANTS_NOM + " TEXT NOT NULL, "
            + COL_ETUDIANTS_PRENOM + " TEXT NOT NULL, "
            + COL_ETUDIANTS_PHOTO + " BLOB NOT NULL, "
            + COL_ETUDIANTS_VILLE + " TEXT NOT NULL,"
            + COL_ETUDIANTS_FILIERE + "INTEGER NOT NULL,"
            + "FOREIGN KEY ("+COL_ETUDIANTS_FILIERE+") REFERENCES "
            + TABLE_FILIERES+" ( "+COL_FILIERES_ID+" ) ON DELETE CASCADE);";

    private static final String SQL_DELETE_TABLE_FILIERES = "DROP TABLE IF EXISTS " + TABLE_FILIERES;
    private static final String SQL_CREATE_TABLE_FILIERES = "CREATE TABLE " +
            TABLE_ETUDIANTS + " ("
            + COL_FILIERES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_FILIERES_INTITULE + " TEXT NOT NULL );";

    public SQLiteDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_FILIERES);
        db.execSQL(SQL_CREATE_TABLE_ETUDIANTS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DELETE_TABLE_FILIERES);
        db.execSQL(SQL_DELETE_TABLE_ETUDIANTS);
        onCreate(db);
    }
}
