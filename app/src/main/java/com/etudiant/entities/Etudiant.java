package com.etudiant.entities;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Etudiant implements Serializable {
    private Integer id;
    private String nom, prenom;
    private LocalDate dateNaissance;
    private String ville;
    private Bitmap photo;
    private Filiere filiere;

    public Etudiant() {
    }

    public Etudiant(Integer id, String nom, String prenom, LocalDate dateNaissance, String ville, Bitmap photo, Filiere filiere) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.ville = ville;
        this.photo = photo;
        this.filiere = filiere;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public Filiere getFiliere() {
        return filiere;
    }

    public void setFiliere(Filiere filiere) {
        this.filiere = filiere;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Etudiant etudiant = (Etudiant) o;
        return Objects.equals(id, etudiant.id) && Objects.equals(nom, etudiant.nom) && Objects.equals(prenom, etudiant.prenom) && Objects.equals(dateNaissance, etudiant.dateNaissance) && Objects.equals(ville, etudiant.ville) && Objects.equals(photo, etudiant.photo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, prenom, dateNaissance, ville, photo);
    }

    @Override
    public String toString() {
        return "Etudiant{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", dateNaissance=" + dateNaissance +
                ", ville='" + ville + '\'' +
                ", photo=" + photo +
                '}';
    }
}
