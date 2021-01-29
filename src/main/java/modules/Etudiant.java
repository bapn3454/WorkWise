package modules;

public class Etudiant extends Personne {

    private int idEtu;
    public Etudiant(String nom, String prenom, String mail, String mdp, String type) {
        super(nom, prenom, mail, mdp, type);
    }

    public Etudiant() {
    }

    public Etudiant(String mail, String mdp) {
        super(mail, mdp);
    }

    public int getIdEtu() {
        return idEtu;
    }

    public void setIdEtu(int idEtu) {
        this.idEtu = idEtu;
    }
}
