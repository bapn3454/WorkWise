package modules;

public class Jury extends Personne {
    public Jury(int id, String nom, String prenom, String mail, String mdp, String type) {
        super(id, nom, prenom, mail, mdp, type);
    }

    public Jury(String nom, String prenom, String mail, String mdp, String type) {
        super(nom, prenom, mail, mdp, type);
    }

    public Jury(String mail, String mdp) {
        super(mail, mdp);
    }
}
