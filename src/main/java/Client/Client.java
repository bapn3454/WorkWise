package Client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

import static Client.ShowMessageClient.*;

public class Client implements Runnable {

    private static final int MAX_TIMEOUT = 10000;	// milliseconds

    private static boolean isPortInUse(String host, int port) {
        try {
            (new Socket(host, port)).close();
            return false;
        }  catch (Exception e) {
            return true;
        }
    }

    @Override
    public void run()  {

        BufferedReader sin;
        PrintStream sout = null;
        BufferedReader stdin;
        Socket sk = null;

        ShowMessageClient showMessageClient = new ShowMessageClient();

        try {
            // getting localhost ip
            int port = 7000;
            String host = "localhost";
            while(isPortInUse("localhost", port) ){
                final Scanner in = new Scanner(System.in);
                // Enter the port to listen
                System.out.format("Le port %d n'est pas ouvert, veuillez saisir : \n", port);
                System.out.print("Host :");
                if(!in.hasNext()) {
                    break;
                }
                host = in.nextLine();
                System.out.print("Port : ");
                port = in.nextInt();
            }
            InetAddress ip = InetAddress.getByName(host);
            sk = new Socket(ip, port);
            //sk.setSoTimeout(MAX_TIMEOUT);
            System.out.format("[Client] Connecté au serveur %s:%d!\n", host, port);
            showMessageClient.firstConnexionClient();

            sin = new BufferedReader(new InputStreamReader(sk.getInputStream()));
            sout = new PrintStream(sk.getOutputStream());
            stdin = new BufferedReader(new InputStreamReader(System.in));
            String s;
            //sout.println("ClientConnect");

            String re;
            int lenStrings;

            System.out.println("Réponse : ");
            String rp = stdin.readLine();
            if(rp != null) {
                while (!verify_read(rp)) {
                    System.out.println("Vous devez répondre par 1 ou 2");
                    rp = stdin.readLine();
                }
                if (rp.equalsIgnoreCase("2")) {
                    rp = "inscription";
                } else {
                    rp = "connexion";
                }

                System.out.println("==========Vous etes============");
                System.out.println("||      1-Etudiant          ||");
                System.out.println("||      2-Encadrant         ||");
                System.out.println("||      3-Jury              ||");
                System.out.println("===============================");
                System.out.println("NB : repondre par 1 ou 2 ou 3");

                re = stdin.readLine();
                while (!verify_read_bis1(re)) {
                    System.out.println("Vous devez répondre par 1 ou 2 ou 3");
                    re = stdin.readLine();
                }
                if (rp.equalsIgnoreCase("connexion")) {
                    System.out.print("Email : ");
                    String em = stdin.readLine();
                    System.out.print("Mot de pass : ");
                    String mdp = stdin.readLine();
                    s = re + "#" + rp + "#" + em + "#" + mdp;
                } else {
                    System.out.println("========INSCRIPTION=====");
                    System.out.println("Nom : ");
                    String nom = stdin.readLine();
                    System.out.println("Prenom : ");
                    String prenom = stdin.readLine();
                    System.out.println("Email : ");
                    String mail = stdin.readLine();
                    System.out.println("Mot de pass : ");
                    String mdp = stdin.readLine();
                    s = re + "#" + rp + "#" + nom + "#" + prenom + "#" + mail + "#" + mdp;
                }

                sout.println(s);
            }

            while(true) {
                /*if(!hostAvailabilityCheck(sk.getInetAddress(),sk.getLocalPort())) {
                    ClientMessage("connexion du serveur");
                }*/
                s = sin.readLine();

                if (s != null) {
                    String[] strings = s.split("#");
                    System.out.println();
                    //System.out.println("Server : " + s);
                    serverMessage(s);
                    switch (strings[0]) {
                        case "Connect":
                            lenStrings = strings.length;
                            if (lenStrings > 1 && strings[1].equalsIgnoreCase("ERROR_CONNEXION")) {
                                if (lenStrings > 3 && strings[3].equalsIgnoreCase("ADRESSEMPTYORINVALID")) {
                                    System.out.println("Adresse mail vide ou invalide : ");
                                } else if (lenStrings > 3 && strings[3].equalsIgnoreCase("MOTDEPASSVIDE")) {
                                    System.out.println("Adresse vide ou invalide : ");
                                } else {
                                    System.out.println("Vos identifiants sont incorrects");
                                }
                                System.out.println("==========Vous etes============");
                                System.out.println("||      1-Etudiant          ||");
                                System.out.println("||      2-Encadrant         ||");
                                System.out.println("||      3-Jury              ||");
                                System.out.println("===============================");
                                System.out.println("NB : repondre par 1 ou 2 ou 3");
                                re = stdin.readLine();
                                if(re != null) {
                                    while (!verify_read_bis1(re)) {
                                        System.out.println("Vous devez répondre par 1 ou 2 ou 3");
                                        re = stdin.readLine();
                                    }
                                    System.out.print("Email : ");
                                    String em = stdin.readLine();
                                    System.out.print("Mot de pass : ");
                                    String mdp = stdin.readLine();
                                    s = re + "#" + "connexion" + "#" + em + "#" + mdp;
                                }
                            } else {
                                String s1, s2, s3;
                                System.out.println();
                                System.out.println("========CONNEXION=====");
                                System.out.println(" Vous etes :");
                                System.out.println("1-Etudiant");
                                System.out.println("2-Encadrant");
                                System.out.println("3-Jury");
                                System.out.println("Repondre par 1 ou 2 ou 3");
                                s1 = stdin.readLine();
                                if(s1 != null) {
                                    while (!verify_read_bis1(s1)) {
                                        System.out.println("Vous devez répondre par 1 ou 2 ou 3");
                                        s1 = stdin.readLine();
                                    }
                                    System.out.print("Email : ");
                                    s2 = stdin.readLine();
                                    System.out.print("Mot de pass : ");
                                    s3 = stdin.readLine();
                                    s = s1 + "#" + "connexion" + "#" + s2 + "#" + s3;
                                }
                            }
                            sout.println(s);
                            break;
                        case "Inscription":
                            String type, nom, prenom, mail, mdp;
                            System.out.println();
                            lenStrings = strings.length;
                            if (lenStrings > 1 && strings[1].equalsIgnoreCase("ERRORINSCRIPTION")) {
                                System.out.println("Votre inscription a echouée");
                                System.out.println("Verifier que aucun champs est vide ou adresse mail est valide");
                            }
                            System.out.println("========INSCRIPTION=====");
                            System.out.println(" Vous etes :");
                            System.out.println("1-Etudiant");
                            System.out.println("2-Encadrant");
                            System.out.println("3-Jury");
                            System.out.println("Repondre par 1 ou 2 ou 3");
                            type = stdin.readLine();
                            if(type != null) {
                                while (!verify_read_bis1(type)) {
                                    System.out.println("Vous devez répondre par 1 ou 2 ou 3");
                                    type = stdin.readLine();
                                }
                                System.out.print("Nom : ");
                                nom = stdin.readLine();
                                System.out.print("Prenom : ");
                                prenom = stdin.readLine();
                                System.out.print("Email : ");
                                mail = stdin.readLine();
                                System.out.print("Mot de pass : ");
                                mdp = stdin.readLine();
                                s = type + "#" + "inscription" + "#" + nom + "#" + prenom + "#" + mail + "#" + mdp;
                                sout.println(s);
                            }
                            break;
                        case "DONEINSCRIPTION":
                            System.out.println("Vous inscription est bien enregistrée");
                            System.out.println("Vous pouvez entre vos identifiant(Email/mot de pass) pour se connecter");
                            s = strings[1];
                            sout.println(s);
                            break;
                        case "allProject":
                            String[] proj = strings[3].split(":");
                            System.out.println("Choix du projet");
                            System.out.println("======Liste des projets====");
                            for (int i = 0; i < proj.length; i++) {
                                System.out.println(proj[i]);
                            }

                            if (strings[1].equalsIgnoreCase("3")) {
                                System.out.println();
                                System.out.println();
                                System.out.println("d-Tapez pour se deconnecter");
                                System.out.println();
                            }
                            System.out.println("Votre choix : ");
                            String idProjet = stdin.readLine();
                            if(idProjet != null) {
                                if (idProjet.equalsIgnoreCase("d")) {
                                    s = "Deconnexion";
                                } else {
                                    s = strings[1] + "#" + "choixProjet#" + idProjet + "#" + strings[2];
                                }
                                sout.println(s);
                            }
                            break;
                        case "tableProjetEmpty":
                            System.out.println("La liste des projets est vide, veuillez vous reconnecter plus tard");
                            System.out.println("Entrer 1 pour se deconnecter");
                            String projVide = stdin.readLine();
                            if(projVide != null) {
                                while (!projet_vide(projVide)) {
                                    projVide = stdin.readLine();
                                }
                                sout.println("Deconnexion");
                            }
                            break;
                        case "redigeMemoireOrAfficheNote":
                            System.out.print("=============Etudiant : ");
                            System.out.print(strings[2]);
                            System.out.print(" ");
                            System.out.print(strings[3]);
                            System.out.println("==============");
                            showMessageClient.returnString();
                            String reps = stdin.readLine();
                            if(reps != null) {
                                while (!verify_read_bis2(reps)) {
                                    System.out.println("Vous devez répondre par 1 ou 2 ou 3 ou 4");
                                    reps = stdin.readLine();
                                }
                                if (reps.equalsIgnoreCase("4")) {
                                    s = "Deconnexion";
                                } else {
                                    s = "1#" + "redigeMemoireOrAfficheNoteOrListMembre#" + reps + "#" + strings[4]
                                            + "#" + strings[1] + "#" + strings[3] + "#" + strings[2];
                                }
                                sout.println(s);
                            }
                            break;

                        case "choixProjetSave":
                            System.out.print("=============Etudiant : ");
                            System.out.print(strings[3]);
                            System.out.print(" ");
                            System.out.print(strings[4]);
                            System.out.println("==============");
                            System.out.println(" 1-Rediger votre memoire    ");
                            System.out.println(" 2-Liste des membre(groupe) ");
                            System.out.println(" 3-Afficher la note/Commentaire du jury/encadrant ");
                            System.out.println(" 4-Deconnexion   ");
                            System.out.println("NB : repondez par 1 ou 2 ou 3 ou 4");
                            String repss = stdin.readLine();
                            if(repss != null) {
                                while (!verify_read_bis2(repss)) {
                                    System.out.println("Vous devez répondre par 1 ou 2 ou 3 ou 4");
                                    repss = stdin.readLine();
                                }
                                if (repss.equalsIgnoreCase("4")) {
                                    s = "Deconnexion";
                                } else {
                                    s = "1#" + "redigeMemoireOrAfficheNoteOrListMembre#" + repss + "#" + strings[1] + "#" + strings[2] + "#" + strings[3] + "#" + strings[4];
                                }
                                sout.println(s);
                            }
                            break;

                        case "contenuMemoire":
                            System.out.println("========================Contenu Memoire===========================");
                            System.out.println(strings[3]);
                            String rdeps = stdin.readLine();
                            if(rdeps != null) {
                                s = "1#" + "SavecontenuMemoire#" + rdeps + "#" + strings[1] + "#" + strings[2]
                                        + "#" + strings[4] + "#" + strings[5];
                                sout.println(s);
                            }
                            break;
                        case "listEtudiantProjet":
                            System.out.println();
                            System.out.println();
                            String[] etu = strings[5].split(":");

                            System.out.println("======Liste des membres du projet====");
                            for (int i = 0; i < etu.length; i++) {
                                if (!etu[i].isEmpty()) {
                                    System.out.println(etu[i]);
                                }
                            }
                            System.out.println();
                            System.out.println();
                            //break;
                        case "ContenuMemoireSave":
                            System.out.print("=============Etudiant : ");
                            System.out.print(strings[3]);
                            System.out.print(" ");
                            System.out.print(strings[4]);
                            System.out.println("==============");
                            showMessageClient.returnString();
                            String ch = stdin.readLine();
                            if(ch != null) {
                                while (!verify_read_bis2(ch)) {
                                    System.out.println("Vous devez répondre par 1 ou 2 ou 3 ou 4");
                                    ch = stdin.readLine();
                                }
                                if (ch.equalsIgnoreCase("4")) {
                                    s = "Deconnexion";
                                } else {
                                    s = "1#" + "redigeMemoireOrAfficheNoteOrListMembre#" + ch + "#" + strings[1]
                                            + "#" + strings[2] + "#" + strings[3] + "#" + strings[4];
                                }
                                sout.println(s);
                            }
                            break;

                        case "notation":
                            System.out.println("=============NOTATION===============");
                            notation(strings[1], strings[2], strings[3], strings[4]);
                            System.out.println("=====================================");
                            System.out.println("Choisir 1 pour retourner au menu precedent ou 2 pour se deconnecter");
                            re = stdin.readLine();
                            if(re != null) {
                                while (!verify_read(re)) {
                                    System.out.println("Vous devez répondre par 1 ou 2");
                                    re = stdin.readLine();
                                }
                                if (re.equalsIgnoreCase("2")) {
                                    s = "Deconnexion";
                                } else if (re.equalsIgnoreCase("1")) {
                                    System.out.print("=============Etudiant : ");
                                    System.out.print(strings[7]);
                                    System.out.print(" ");
                                    System.out.print(strings[8]);
                                    System.out.println("==============");
                                    showMessageClient.returnString();
                                    String rps = stdin.readLine();
                                    if (rps.equalsIgnoreCase("4")) {
                                        s = "Deconnexion";
                                    } else {
                                        s = "1#" + "redigeMemoireOrAfficheNoteOrListMembre#" + rps + "#" + strings[5]
                                                + "#" + strings[6] + "#" + strings[7] + "#" + strings[8];
                                    }
                                }
                                sout.println(s);
                            }
                            break;


                        case "ConnexionEncadDone":
                            System.out.print("============= Encadrant : ");
                            System.out.print(strings[2]);
                            System.out.print(" ");
                            System.out.print(strings[3]);
                            System.out.println(" ==============");
                            showMessageClient.menuEncadrant();
                            String res = stdin.readLine();
                            if(res != null) {
                                while (!verify_read_bis1(res)) {
                                    System.out.println("Vous devez répondre par 1 ou 2 ou 3");
                                    res = stdin.readLine();
                                }
                                if (res.equalsIgnoreCase("3")) {
                                    s = "Deconnexion";
                                } else {
                                    if (res.equalsIgnoreCase("1")) {
                                        System.out.println("==========Creer un projet============");
                                        System.out.println("Nom projet : ");
                                        String nomP = stdin.readLine();
                                        s = "2#" + "create_projet#" + nomP + "#" + strings[1] + "#" + strings[2] + "#" + strings[3];
                                    } else if (res.equalsIgnoreCase("2")) {
                                        s = "2#" + "all_project#" + strings[1];
                                    }
                                }
                                sout.println(s);
                            }
                            break;
                        case "contenuProjet":
                            System.out.print("======================== Projet : ");
                            System.out.print(strings[4]);
                            System.out.println(" ===========================");
                            if (strings[5].equalsIgnoreCase(null)) {
                                System.out.println("Pas de contenu pour le moment");
                            } else {
                                System.out.println(strings[5]);
                            }
                            System.out.println("==========================================================");
                            if (strings[1].equalsIgnoreCase("2")) {
                                System.out.println("Vous pouvez laisser un commentaire : ");
                                String cm = stdin.readLine();
                                s = strings[1] + "#" + "SavecommentaireProjet#" + cm + "#" + strings[2] + "#" + strings[3];
                            }

                            if (strings[1].equalsIgnoreCase("3")) {
                                System.out.println("Vous pouvez noter : ");
                                System.out.println("Note : ");
                                String note = stdin.readLine();
                                System.out.print("Appreciation : ");
                                String appre = stdin.readLine();
                                s = strings[1] + "#" + "saveNotationJury#" + note + "#" + appre + "#" + strings[2] + "#" + strings[3];
                            }
                            sout.println(s);
                            break;
                        case "Aurevoir":
                            break;
                        default:
                            //sout.println("FORMATINCORRECT#");
                            break;
                    }
                    //if(s.equalsIgnoreCase("Aurevoir")) break;
                }
                else {
                    break;
                }
            }

            sk.close();
            sin.close();
            sout.close();
            stdin.close();
        } catch (SocketTimeoutException exception) {
            sout.println("[TimeOut]Reponse Server => Client");
        }catch (SocketException e) {
            //System.out.println("Coupure connexion server");
            //e.printStackTrace();
            ClientMessage("Interruption/coupure connexion du serveur");
        } catch (UnknownHostException e) {
            //e.printStackTrace();
        } catch (IOException e) {
           // e.printStackTrace();
        } finally {
            try {
                if (sk != null)
                    sk.close();
            } catch (IOException e) {
                //e.printStackTrace();
            }
        }
    }

}