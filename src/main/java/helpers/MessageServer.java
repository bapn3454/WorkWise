package helpers;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageServer {
    public static final String CONNEXION= "Connect#";
    public static final String INSCRIPTION= "Inscription#";
    public static final String INSCRIPTION_OR_CONNEXION= "InscriptionOrConnexion#";

    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static boolean verifiy_email(String email) {

        final Scanner in = new Scanner(System.in);

        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    protected static String now() {
        return FORMATTER.format(new Date());
    }

    public static void afficher( String message) {
        System.out.format("[%s][Server] %s\n", now(), message);
    }

    public static void clientMessage( String message) {
        if(message.equalsIgnoreCase("null" )){
            message = "Interruption/coupure connexion du client";
            System.out.format("[%s][Client] %s\n", now(), message);

        }else if(message.equalsIgnoreCase("ClientConnect")) {

        }else {
            String[] strings = message.split("#");
            String client = "Client";
            if(strings[0].equalsIgnoreCase("1")) {
                client = "Etudiant";
            }else if(strings[0].equalsIgnoreCase("2")) {
                client = "Encadrant";
            }
            else  if(strings[0].equalsIgnoreCase("3")){
                client = "Jury";
            }
            System.out.format("[%s][%s] %s\n", now(), client, message);
        }
    }




}
