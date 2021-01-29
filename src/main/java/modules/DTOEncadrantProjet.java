package modules;

public class DTOEncadrantProjet {

    private int idEncadrant;
    private String nomEncadrant;
    private String prenomEncadrant;
    private String mail;
    private String mdp;
    private String type;

    private int idProjet;
    private String nomProjet;
    private String contenu;
    private String commentaire;

    public DTOEncadrantProjet() {
    }


    public int getIdEncadrant() {
        return idEncadrant;
    }

    public void setIdEncadrant(int idEncadrant) {
        this.idEncadrant = idEncadrant;
    }

    public String getNomEncadrant() {
        return nomEncadrant;
    }

    public void setNomEncadrant(String nomEncadrant) {
        this.nomEncadrant = nomEncadrant;
    }

    public String getPrenomEncadrant() {
        return prenomEncadrant;
    }

    public void setPrenomEncadrant(String prenomEncadrant) {
        this.prenomEncadrant = prenomEncadrant;
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

    public int getIdProjet() {
        return idProjet;
    }

    public void setIdProjet(int idProjet) {
        this.idProjet = idProjet;
    }

    public String getNomProjet() {
        return nomProjet;
    }

    public void setNomProjet(String nomProjet) {
        this.nomProjet = nomProjet;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
}
