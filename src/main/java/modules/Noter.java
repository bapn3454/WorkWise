package modules;

public class Noter {
    private int idProjet;
    private int jury;
    private float note;
    private String appreciation;

    public Noter() {
    }

    public Noter(int idProjet, int jury, float note, String appreciation) {
        this.idProjet = idProjet;
        this.jury = jury;
        this.note = note;
        this.appreciation = appreciation;
    }

    public int getIdProjet() {
        return idProjet;
    }

    public void setIdProjet(int idProjet) {
        this.idProjet = idProjet;
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
}
