package modules;


import lombok.*;

public class Personne {

    private int id;
    private String nom;
    private String prenom;
    private String mail;
    private String mdp;
    private String type;


    public Personne() {
    }

    public Personne(int id, String nom, String prenom, String mail, String mdp, String type) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.mdp = mdp;
        this.type = type;
    }

    public Personne(String nom, String prenom, String mail, String mdp, String type) {
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.mdp = mdp;
        this.type = type;
    }

    public Personne(String mail, String mdp) {
        this.mail = mail;
        this.mdp = mdp;
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return "Personne{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", mail='" + mail + '\'' +
                ", mdp='" + mdp + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
