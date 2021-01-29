package Client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ShowMessageClient {

    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void firstConnexionClient() {
        System.out.println("=======Redaction Memoire=======");
        System.out.println("||      1-Connexion          ||");
        System.out.println("||      2-Inscription        ||");
        System.out.println("===============================");
        System.out.println("NB : repondre par 1 ou 2");
    }

    public void returnString() {
       System.out.println("   1-Modifier votre memoire     ");
       System.out.println("   2-Liste des membre(groupe)   ");
       System.out.println("   3-Afficher la note/Commentaire du jury/encadrant   ");
       System.out.println("   4-Deconnexion   ");
       System.out.println("NB : repondez par 1 ou 2 ou 3 ou 4");
    }


    public void menuEncadrant() {
        System.out.println(" 1-Créer un projet    ");
        System.out.println(" 2-Lire Memoire       ");
        System.out.println(" 3-Deconnexion   ");
        System.out.println("NB : repondez par 1 ou 2 ou 3");
    }

    public static boolean isNullOrEmpty(String str) {
        if(str.equalsIgnoreCase("null"))
            return true;
        return false;
    }
    public static void notation(String nomP, String note, String appre, String comment) {

        if(isNullOrEmpty(nomP) && isNullOrEmpty(appre) && isNullOrEmpty(comment)) {
            System.out.println("Pas de note disponible");
        }else {
            System.out.println("Nom projet : " + nomP);
            System.out.println("Note : " + note);
            System.out.println("Appreciation : " + appre);
            System.out.println("Commentaire encdrant : " + comment);
        }

       /* System.out.println("Nom projet : " + strings[1]);
        if(strings[2] == "0.0") strings[2] = "Pas noter encore";
        System.out.println("Note : " + strings[2]);
        if(isNullOrEmpty(strings[9])) strings[9] = "Aucune appreciation";
        System.out.println("Appreciation : " + strings[9]);
        if(strings[4].equalsIgnoreCase(null)) strings[4] = "Aucun commentaire";
        System.out.println("Commentaire encdrant : " + strings[4]);*/
    }

    public  static  boolean verify_read(String rp) {
        if(rp.equalsIgnoreCase("1") || rp.equalsIgnoreCase("2"))
            return true;
        return false;
    }

    public  static  boolean verify_read_bis1(String rp) {
        if(rp.equalsIgnoreCase("1")
                || rp.equalsIgnoreCase("2")
                || rp.equalsIgnoreCase("3"))
            return true;

        return false;
    }

    public  static  boolean verify_read_bis2(String rp) {
        if(rp.equalsIgnoreCase("1")
                || rp.equalsIgnoreCase("2")
                || rp.equalsIgnoreCase("3")
                || rp.equalsIgnoreCase("4"))
            return true;

        return false;
    }

    public  static  boolean projet_vide(String rp) {
        if(rp.equalsIgnoreCase("1"))
            return true;

        return false;
    }

    protected static String now() {
        return FORMATTER.format(new Date());
    }


    public static void serverMessage( String message) {
        System.out.format("[%s][Server] %s\n", now(), message);
    }

    public static void ClientMessage( String message) {
        System.out.format("[%s][ERROR] %s\n", now(), message);
    }

}
