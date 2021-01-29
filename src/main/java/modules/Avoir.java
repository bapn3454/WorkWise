package modules;

public class Avoir {
    private int idPersonne;
    private int codePriv;

    public Avoir() {
    }

    public Avoir(int idPersonne, int codePriv) {
        this.idPersonne = idPersonne;
        this.codePriv = codePriv;
    }

    public int getIdPersonne() {
        return idPersonne;
    }

    public void setIdPersonne(int idPersonne) {
        this.idPersonne = idPersonne;
    }

    public int getCodePriv() {
        return codePriv;
    }

    public void setCodePriv(int codePriv) {
        this.codePriv = codePriv;
    }
}

