package helpers;

import jdbc.Connexion;
import jdbc.JDBCDaoImpl;
import modules.*;
import services.OtherService;
import services.PersonneGeneric;

import java.sql.*;
import java.util.List;

import static helpers.MessageServer.verifiy_email;

public class Controller {

    OtherService otherService = new OtherService();


    public int personne(String nom, String prenom, String mail, String mdp, String type) {
        int id  = 0;

        Connection con = null;
        PreparedStatement ps = null;
        Connexion connexion = new Connexion();
        try {
            con =  connexion.connect();
            if ( con == null ) {
                System.out.println( "Error getting the connection. Please check if the DB server is running" );
                return id;
            }
            con.setAutoCommit( false );
            ps = con.prepareStatement(JDBCDaoImpl.INSERT_SQL_PERSONNE, Statement.RETURN_GENERATED_KEYS);
            ps.setString( 1, nom);
            ps.setString( 2, prenom);
            ps.setString( 3, mail);
            ps.setString( 4, mdp);
            ps.setString( 5, type);

            int rowsAffected = ps.executeUpdate();
            if(rowsAffected > 0) {
                // then we get the ID of the affected row(s)
                try (ResultSet rs = ps.getGeneratedKeys()){
                    if (rs.next()) {
                        id = rs.getInt(1);
                        System.out.println("KEY Person " + id);
                    }
                }catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            System.out.println("Personne => " + ps.toString() );
            con.commit();


        } catch ( SQLException e ) {
            try {
                if ( con != null ) {
                    con.rollback();
                }
            }
            catch ( SQLException e1 ) {
                System.out.println(e1.getMessage());
            }
        } finally {
            try {
                connexion.closePrepaerdStatement(ps);
                connexion.closeConnection(con);
            } catch ( SQLException e ) {
                System.out.println(e.getMessage());
            }
        }
        return id;

    }


    public StringBuilder etudiant(String[] strings) {
        Etudiant etudiant = null;
        PersonneGeneric<Etudiant> personEtudiant = new PersonneGeneric<Etudiant>();
        StringBuilder stringBuilder = new StringBuilder();
        switch (strings[1]) {
            case "inscription":
                if(strings[4].isEmpty()
                        || !verifiy_email(strings[4])
                        || strings[5].isEmpty()
                        || strings[2].isEmpty()
                        || strings[3].isEmpty()
                ) {
                    stringBuilder.append("Inscription#ERRORINSCRIPTION#1#VALEURSINCORRECT");
                }
                else {
                    //int id = this.personne(strings[2], strings[3], strings[4], strings[5],"Etudiant");
                    etudiant = new Etudiant(strings[2], strings[3], strings[4], strings[5], "Etudiant");
                    int rep = personEtudiant.inscription(etudiant, JDBCDaoImpl.INSERT_SQL_ETUDIANT);
                    if (rep > 0) {
                        Avoir avoir = new Avoir(rep, 3);
                        otherService.insertPrivillege(avoir);
                        stringBuilder.append("DONEINSCRIPTION#1");
                    } else {
                        stringBuilder.append("Inscription#ERRORINSCRIPTION#1");
                    }
                }

                break;

            case "connexion":
                String mail = strings[2];
                String mdp = strings[3];
                if(mail == null || mail.isEmpty() || !verifiy_email(mail) ) {
                    ///erreur email
                    stringBuilder.append("Connect#ERROR_CONNEXION#1#ADRESSEMPTYORINVALID");
                }
                else if(mdp == null || mdp.isEmpty() ) {
                    stringBuilder.append("Connect#ERROR_CONNEXION#1#MOTDEPASSVIDE");
                }
                else {
                    etudiant = new Etudiant(mail, mdp);
                    Etudiant etud = personEtudiant.verifify_connexion(etudiant, JDBCDaoImpl.VERIFY__USERNAME_CONNEXION_ETUDIANT);
                    if (etud != null && etud.getNom() != null && etud.getPrenom() != null) {

                        TravailleSur travailleSur = otherService.select_travailSur(etud.getId());
                        if (travailleSur != null) {
                            stringBuilder.append("redigeMemoireOrAfficheNote#");
                            stringBuilder.append(etud.getId()); // identifiant etudiant
                            stringBuilder.append("#");
                            stringBuilder.append(etud.getNom()); //Nom etudiant
                            stringBuilder.append("#");
                            stringBuilder.append(etud.getPrenom()); // prenom etudiant
                            stringBuilder.append("#");
                            stringBuilder.append(travailleSur.getIdProjet()); // idenftifiant projet
                        } else {
                            List<Projet> projet = otherService.all_project();
                            if (projet.isEmpty()) {
                                stringBuilder.append("tableProjetEmpty#");
                            } else {
                                String allProjet;
                                stringBuilder.append("allProject#");
                                stringBuilder.append("1#"); //case etudiant
                                stringBuilder.append(etud.getId()); // id etudiant
                                stringBuilder.append("#");
                                for (Projet proj : projet) {
                                    allProjet = proj.getId() + "-" + proj.getNom();
                                    stringBuilder.append(allProjet);
                                    stringBuilder.append(":");
                                }
                            }
                        }
                    } else {
                        stringBuilder.append("Connect#ERROR_CONNEXION#1");
                    }
                }
                break;

            case "all_project":
                otherService.all_project();
                break;

            case "choixProjet" :
                TravailleSur travailleSur = new TravailleSur(Integer.parseInt(strings[2]),
                                                    Integer.parseInt(strings[3]));
                Etudiant etu = otherService.select_etudiant(Integer.parseInt(strings[3]));
                int trv = otherService.insertTravailSur(travailleSur);
                if(trv > 0) {
                    stringBuilder.append("choixProjetSave");
                    stringBuilder.append("#");
                    stringBuilder.append(strings[2]);
                    stringBuilder.append("#");
                    stringBuilder.append(strings[3]);
                    if(etu != null) {
                        stringBuilder.append("#");
                        stringBuilder.append(etu.getPrenom());
                        stringBuilder.append("#");
                        stringBuilder.append(etu.getNom());
                    }
                }
                break;
            case "redigeMemoireOrAfficheNoteOrListMembre":
                switch (strings[2]) {
                    case "1" :
                        DTOEtudiantTravailSurProjet dto = otherService.return_file(Integer.parseInt(strings[3]),
                                Integer.parseInt(strings[4]));
                        if(dto != null) {
                            stringBuilder.append("contenuMemoire");
                            stringBuilder.append("#");
                            stringBuilder.append(strings[3]); //Id projet
                            stringBuilder.append("#");
                            stringBuilder.append(strings[4]);// Id etudiant
                            stringBuilder.append("#");
                            stringBuilder.append(dto.getContenu());// Conetnu Memoire
                            stringBuilder.append("#");
                            stringBuilder.append(strings[5]);// prenom etudiant
                            stringBuilder.append("#");
                            stringBuilder.append(strings[6]);// nom etudiant
                        }
                        else {
                            stringBuilder.append("ErrorEnvoiMemoire#");
                        }
                        break;
                    case "2" :
                        List<Etudiant> etudi = otherService.listOfStudentsWhoHaveTheSmeProject(Integer.parseInt(strings[3]));
                        if(etudi != null) {
                            String list;
                            stringBuilder.append("listEtudiantProjet#");
                            stringBuilder.append(strings[3]);
                            stringBuilder.append("#");
                            stringBuilder.append(strings[4]);
                            stringBuilder.append("#");
                            stringBuilder.append(strings[5]);
                            stringBuilder.append("#");
                            stringBuilder.append(strings[6]);
                            stringBuilder.append("#");
                            for (Etudiant e : etudi) {
                                list = e.getNom() + " " + e.getPrenom();
                                stringBuilder.append(list);
                                stringBuilder.append(":");
                            }
                        }else {
                            stringBuilder.append("listEtudiantProjetNul#");
                        }
                        break;
                    case"3" :
                        DTONotation note = otherService.select_notation(Integer.parseInt(strings[3]));
                        if(note != null) {
                            stringBuilder.append("notation#");
                            stringBuilder.append(note.getNomProjet()); //nom projet
                            stringBuilder.append("#");
                            stringBuilder.append(note.getNote());// note projett
                            stringBuilder.append("#");
                            stringBuilder.append(note.getAppreciation());// appreciation
                            stringBuilder.append("#");
                            stringBuilder.append(note.getCommentaire());// commentaire encdrant
                            stringBuilder.append("#");
                            stringBuilder.append(strings[3]); //Id projet
                            stringBuilder.append("#");
                            stringBuilder.append(strings[4]);// Id etudiant
                            stringBuilder.append("#");
                            stringBuilder.append(strings[5]);// prenom etudiant
                            stringBuilder.append("#");
                            stringBuilder.append(strings[6]);// nom etudiant
                        }
                        break;
                }
                break;
            case "SavecontenuMemoire" :
                int save = otherService.update_contenu_projet(Integer.parseInt(strings[3]),strings[2]);
                if(save == 1 ) {
                    stringBuilder.append("ContenuMemoireSave#");
                    stringBuilder.append(strings[3]); //Id projet
                    stringBuilder.append("#");
                    stringBuilder.append(strings[4]);// Id etudiant
                    stringBuilder.append("#");
                    stringBuilder.append(strings[5]);// prenom etudiant
                    stringBuilder.append("#");
                    stringBuilder.append(strings[6]);// nom etudianr
                }
                else {
                    stringBuilder.append("ContenuMemoireNoSave#");
                }
                break;
            default:
                stringBuilder.append("FORMATINCORRECT#");
                break;
        }

        return stringBuilder;

    }

    public StringBuilder encarant(String[] strings) {

        PersonneGeneric<Encadrant> personEncadrant = new PersonneGeneric();
        Encadrant encadrant = null;
        StringBuilder stringBuilder = new StringBuilder();

        switch (strings[1]) {
            case "inscription":
                if(strings[4].isEmpty()
                        || !verifiy_email(strings[4])
                        || strings[5].isEmpty()
                        || strings[2].isEmpty()
                        || strings[3].isEmpty()
                ) {
                    stringBuilder.append("Inscription#ERRORINSCRIPTION#2#VALEURSINCORRECT");
                }else {
                    //int id = this.personne(strings[2], strings[3], strings[4], strings[5], "Encadrant");
                    encadrant = new Encadrant(strings[2], strings[3], strings[4], strings[5], "Encadrant");
                    int rep = personEncadrant.inscription(encadrant, JDBCDaoImpl.INSERT_SQL_ENCADRANT);
                    if (rep > 0) {
                        Avoir avoir = new Avoir(rep, 1);
                        otherService.insertPrivillege(avoir);
                        stringBuilder.append("DONEINSCRIPTION#2");
                    } else {
                        stringBuilder.append("Inscription#ERRORINSCRIPTION#2");
                    }
                }
                break;

            case "connexion":
                String mail = strings[2];
                String mdp = strings[3];
                if(mail == null || mail.isEmpty() || !verifiy_email(mail) ) {
                    //erreur adress mail
                    stringBuilder.append("Connect#ERROR_CONNEXION#2#ADRESSEMPTYORINVALID");
                }
                else if(mdp == null || mdp.isEmpty() ) {
                    //erreur mot de pass
                    stringBuilder.append("Connect#ERROR_CONNEXION#2#MOTDEPASSVIDE");
                }
                else {
                    encadrant = new Encadrant(mail, mdp);
                    Encadrant encad = personEncadrant.verifify_connexion(encadrant, JDBCDaoImpl.VERIFY__USERNAME_CONNEXION_ENCADRANT);
                    if (encad != null && encad.getNom() != null && encad.getPrenom() != null) {
                        stringBuilder.append("ConnexionEncadDone#");
                        stringBuilder.append(encad.getId());
                        stringBuilder.append("#");
                        stringBuilder.append(encad.getPrenom());
                        stringBuilder.append("#");
                        stringBuilder.append(encad.getNom());
                    } else {
                        stringBuilder.append("Connect#ERROR_CONNEXION#2");
                    }
                }
                break;
            case "create_projet" :
                Projet pr = new Projet(strings[2]);
                int id = otherService.insertProjet(pr);
                if(id > 0) {
                    Encadre encadre = new Encadre(id,Integer.parseInt(strings[3]));
                    otherService.insertEncadrer(encadre);
                    stringBuilder.append("ConnexionEncadDone#");
                    stringBuilder.append(strings[3]); // id encadrant
                    stringBuilder.append("#");
                    stringBuilder.append(strings[4]); // prenom encadrant
                    stringBuilder.append("#");
                    stringBuilder.append(strings[5]); //nom encadrant
                }
                else {
                    stringBuilder.append("errorCreationProjet#2");
                }
                break;
            case "all_project":
                List<DTOEncadrantProjet> allP = otherService.return_encadrant_projet(Integer.parseInt(strings[2]));
                if (allP.isEmpty()) {
                    stringBuilder.append("tableProjetEmpty#");
                } else {
                    String allProjet;
                    stringBuilder.append("allProject#");
                    stringBuilder.append("2#"); // case encadrant
                    stringBuilder.append(strings[2]); // id encadrant
                    stringBuilder.append("#");
                    for (DTOEncadrantProjet proj : allP) {
                        allProjet = proj.getIdProjet() + "-" + proj.getNomProjet();
                        stringBuilder.append(allProjet);
                        stringBuilder.append(":");
                    }
                }
                break;
            case "choixProjet" :
                Projet projet1 = otherService.select_project(Integer.parseInt(strings[2]));
                if(projet1 != null) {
                    stringBuilder.append("contenuProjet#");
                    stringBuilder.append("2#"); // case encadrant
                    stringBuilder.append(strings[3]); // id encadrant
                    stringBuilder.append("#");
                    stringBuilder.append(projet1.getId());// id projet
                    stringBuilder.append("#");
                    stringBuilder.append(projet1.getNom()); // nom projet
                    stringBuilder.append("#");
                    if(projet1.getContenu().equalsIgnoreCase("")) {
                        stringBuilder.append("null");
                    }else {
                        stringBuilder.append(projet1.getContenu()); // contenu projet
                    }
                }
                break;
            case "SavecommentaireProjet" :
                int en = otherService.update_commentaire_encadrer(Integer.parseInt(strings[4]),
                        Integer.parseInt(strings[3]), strings[2]);
                if(en > 0) {
                    Encadrant enc = otherService.select_encadrant(Integer.parseInt(strings[3]));
                    stringBuilder.append("ConnexionEncadDone#");
                    stringBuilder.append(enc.getId());
                    stringBuilder.append("#");
                    stringBuilder.append(enc.getNom());
                    stringBuilder.append("#");
                    stringBuilder.append(enc.getPrenom());
                }
                break;
                default:
                    stringBuilder.append("FORMATINCORRECT#");
                    break;
        }
        return stringBuilder;

    }

    public StringBuilder jury(String[] strings) {

        PersonneGeneric<Jury> personJury = new PersonneGeneric();
        Jury jury = null;
        StringBuilder stringBuilder = new StringBuilder();

        switch (strings[1]) {
            case "inscription":
                if(strings[4].isEmpty()
                        || !verifiy_email(strings[4])
                        || strings[5].isEmpty()
                        || strings[2].isEmpty()
                        || strings[3].isEmpty()
                ) {
                    stringBuilder.append("Inscription#ERRORINSCRIPTION#1#VALEURSINCORRECT");
                }else {
                    //int id = this.personne(nom, prenom, mail, mdp, type);
                    jury = new Jury(strings[2], strings[3], strings[4], strings[5], "Jury");
                    int rep = personJury.inscription(jury, JDBCDaoImpl.INSERT_SQL_JURY);
                    if (rep > 0) {
                        Avoir avoir = new Avoir(rep, 2);
                        otherService.insertPrivillege(avoir);
                        stringBuilder.append("DONEINSCRIPTION#3");
                    } else {
                        stringBuilder.append("Inscription#ERRORINSCRIPTION#3");
                    }
                }
                break;

            case "connexion":
                String mail = strings[2];
                String mdp = strings[3];
                if(mail == null || mail.isEmpty() || !verifiy_email(mail) ) {
                    stringBuilder.append("Connect#ERROR_CONNEXION#3#ADRESSEMPTYORINVALID");
                }
                else if(mdp == null || mdp.isEmpty() ) {
                    stringBuilder.append("Connect#ERROR_CONNEXION#3#MOTDEPASSVIDE");
                }
                else {
                    jury = new Jury(mail, mdp);
                    Jury jur = personJury.verifify_connexion(jury, JDBCDaoImpl.VERIFY__USERNAME_CONNEXION_JURY);
                    if (jur != null && jur.getNom() != null && jur.getPrenom() != null) {
                        List<Projet> projet = otherService.all_project();
                        if (projet.isEmpty()) {
                            stringBuilder.append("tableProjetEmpty#");
                        } else {
                            String allProjet;
                            stringBuilder.append("allProject#");
                            stringBuilder.append("3#"); //Case Jury
                            stringBuilder.append(jur.getId()); //id jury
                            stringBuilder.append("#");
                            for (Projet proj : projet) {
                                allProjet = proj.getId() + "-" + proj.getNom();
                                stringBuilder.append(allProjet);
                                stringBuilder.append(":");
                            }
                        }
                    } else {
                        stringBuilder.append("Connect#ERROR_CONNEXION#3");
                    }
                }
                break;
            case "choixProjet" :
                Projet projet1 = otherService.select_project(Integer.parseInt(strings[2]));
                if(projet1 != null) {
                    stringBuilder.append("contenuProjet#");
                    stringBuilder.append("3#"); // case encadrant
                    stringBuilder.append(strings[3]); // id jury
                    stringBuilder.append("#");
                    stringBuilder.append(projet1.getId());// id projet
                    stringBuilder.append("#");
                    stringBuilder.append(projet1.getNom()); // nom projet
                    stringBuilder.append("#");
                    stringBuilder.append(projet1.getContenu()); // contenu projet
                }
                break;
            case "saveNotationJury" :
                Noter noter = new Noter(Integer.parseInt(strings[5]),
                        Integer.parseInt(strings[4]),
                        Float.parseFloat(strings[2]),
                        strings[3]);
                int note = otherService.insertNote(noter);
                if(note > 0) {
                    List<Projet> projet = otherService.all_project();
                    if (projet.isEmpty()) {
                        stringBuilder.append("tableProjetEmpty#");
                    } else {
                        String allProjet;
                        stringBuilder.append("allProject#");
                        stringBuilder.append("3#"); //Case Jury
                        stringBuilder.append(strings[4]); //id jury
                        stringBuilder.append("#");
                        for (Projet proj : projet) {
                            allProjet = proj.getId() + "-" + proj.getNom();
                            stringBuilder.append(allProjet);
                            stringBuilder.append(":");
                        }
                    }
                }
                break;
            default:
                stringBuilder.append("FORMATINCORRECT#");
                break;
        }
        return stringBuilder;

    }
}
