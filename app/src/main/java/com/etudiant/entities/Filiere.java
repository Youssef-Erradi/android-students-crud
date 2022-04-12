package com.etudiant.entities;

import java.io.Serializable;
import java.util.Objects;

public class Filiere implements Serializable {
    private Integer id;
    private String intitule;

    public Filiere() {
    }

    public Filiere(Integer id, String intitule) {
        this.id = id;
        this.intitule = intitule;
    }

    public Integer getId() {
        return id;
    }

    public Filiere setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getIntitule() {
        return intitule;
    }

    public Filiere setIntitule(String intitule) {
        this.intitule = intitule;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Filiere filiere = (Filiere) o;
        return Objects.equals(id, filiere.id) && Objects.equals(intitule, filiere.intitule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, intitule);
    }

    @Override
    public String toString() {
        return id+" : "+intitule;
    }
}
