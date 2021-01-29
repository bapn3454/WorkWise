package modules;

public class Encadre {
    private int idProjet;
    private int idEncadrant;
    private String commentaire;


    public Encadre() {
    }

    public Encadre(int idProjet, int idEncadrant, String commentaire) {
        this.idProjet = idProjet;
        this.idEncadrant = idEncadrant;
        this.commentaire = commentaire;
    }

    public Encadre(int idProjet, int idEncadrant) {
        this.idProjet = idProjet;
        this.idEncadrant = idEncadrant;
    }

    public int getIdProjet() {
        return idProjet;
    }

    public void setIdProjet(int idProjet) {
        this.idProjet = idProjet;
    }

    public int getIdEncadrant() {
        return idEncadrant;
    }

    public void setIdEncadrant(int idEncadrant) {
        this.idEncadrant = idEncadrant;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    @Override
    public String toString() {
        return "Encadre{" +
                "idProjet=" + idProjet +
                ", idEncadrant=" + idEncadrant +
                ", commentaire='" + commentaire + '\'' +
                '}';
    }
}
