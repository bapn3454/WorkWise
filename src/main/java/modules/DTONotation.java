package modules;

public class DTONotation {

    private int idProjet;
    private int idEncadrant;
    private String commentaire;

    private int jury;
    private float note;
    private String appreciation;
    private String NomProjet;

    public DTONotation() {
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

    public int getJury() {
        return jury;
    }

    public void setJury(int jury) {
        this.jury = jury;
    }

    public float getNote() {
        return note;
    }

    public void setNote(float note) {
        this.note = note;
    }

    public String getAppreciation() {
        return appreciation;
    }

    public void setAppreciation(String appreciation) {
        this.appreciation = appreciation;
    }

    public String getNomProjet() {
        return NomProjet;
    }

    public void setNomProjet(String nomProjet) {
        NomProjet = nomProjet;
    }
}
