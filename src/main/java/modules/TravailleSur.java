package modules;

public class TravailleSur {
    private int idProjet;
    private int idEtudiant;

    public TravailleSur() {
    }

    public int getIdProjet() {
        return idProjet;
    }

    public void setIdProjet(int idProjet) {
        this.idProjet = idProjet;
    }

    public int getIdEtudiant() {
        return idEtudiant;
    }

    public void setIdEtudiant(int idEtudiant) {
        this.idEtudiant = idEtudiant;
    }

    public TravailleSur(int idProjet, int idEtudiant) {
        this.idProjet = idProjet;
        this.idEtudiant = idEtudiant;
    }

    @Override
    public String toString() {
        return "TravailleSur{" +
                "idProjet=" + idProjet +
                ", idEtudiant=" + idEtudiant +
                '}';
    }
}
