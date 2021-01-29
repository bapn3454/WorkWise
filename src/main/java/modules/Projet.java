package modules;

import java.io.File;

public class Projet {
    private int id;
    private String nom;
    private String contenu;

    public Projet() {
    }


    public Projet(String nom, String contenu) {
        this.nom = nom;
        this.contenu = contenu;
    }

    public Projet(String nom) {
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    @Override
    public String toString() {
        return "Projet{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", contenu='" + contenu + '\'' +
                '}';
    }
}
