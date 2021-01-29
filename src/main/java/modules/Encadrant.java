package modules;

public class Encadrant extends Personne {
    public Encadrant() {
    }

    public Encadrant(String nom, String prenom, String mail, String mdp, String type) {
        super(nom, prenom, mail, mdp, type);
    }

    public Encadrant(String mail, String mdp) {
        super(mail, mdp);
    }
}
