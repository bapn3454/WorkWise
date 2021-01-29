package modules;

public class Privilege {
    private int id;
    private boolean lire;
    private boolean ecrire;
    private boolean commenter;
    private boolean noter;

    public Privilege() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isLire() {
        return lire;
    }

    public void setLire(boolean lire) {
        this.lire = lire;
    }

    public boolean isEcrire() {
        return ecrire;
    }

    public void setEcrire(boolean ecrire) {
        this.ecrire = ecrire;
    }

    public boolean isCommenter() {
        return commenter;
    }

    public void setCommenter(boolean commenter) {
        this.commenter = commenter;
    }

    public boolean isNoter() {
        return noter;
    }

    public void setNoter(boolean noter) {
        this.noter = noter;
    }
}
