package jdbc;

public class JDBCDaoImpl {

    //INSERT
    public static final String INSERT_SQL_PERSONNE        = "INSERT INTO personne(nom, prenom, email, mdp, type) VALUES(?,?,?,?,?)";
    public static final String INSERT_SQL_ETUDIANT        = "INSERT INTO etudiant(nom, prenom, email, mdp, type) VALUES(?,?,?,?,?)";
    public static final String INSERT_SQL_ENCADRANT       = "INSERT INTO encadrant(nom, prenom, email, mdp, type) VALUES(?,?,?,?,?)";
    public static final String INSERT_SQL_JURY            = "INSERT INTO jury(nom, prenom, email, mdp, type) VALUES(?,?,?,?,?)";
    public static final String INSERT_SQL_PROJET          = "INSERT INTO projet(nom, contenu) VALUES(?, '')";
    public static final String INSERT_SQL_PRIVILLEGE      = "INSERT INTO privillege(lire, ecrire, commenter, noter) VALUES(?,?,?,?)";
    public static final String INSERT_SQL_ENCADRER        = "INSERT INTO encadrer(idProjet, idEncadrant, commentaire) VALUES(?,?, '')";
    public static final String INSERT_SQL_NOTER           = "INSERT INTO noter(idProjet, idJury, note, appreciation) VALUES(?,?,?,?)";
    public static final String INSERT_SQL_AVOIR           = "INSERT INTO avoir(idPersonne, codePriv) VALUES(?,?)";
    public static final String INSERT_SQL_TRAVAIL_SUR     = "INSERT INTO travailleSur(idProjet, idEtudiant) VALUES(?,?)";


    //VERIFY USERNAME CONNEXION
    public static final String VERIFY__USERNAME_CONNEXION_ETUDIANT = "SELECT * FROM etudiant WHERE email=? AND mdp=?";
    public static final String VERIFY__USERNAME_CONNEXION_ENCADRANT = "SELECT * FROM encadrant WHERE email=? AND mdp=?";
    public static final String VERIFY__USERNAME_CONNEXION_JURY = "SELECT * FROM jury WHERE email=? AND mdp=?";

    //SELECT
    public static final String SELECT_ALL_PROJECT = "SELECT * FROM projet";
    public static final String SELECT_FILE_PROJECT = "SELECT file FROM projet WHERE id=?";
    public final static String SELECT_TRAVAIL_SUR = "SELECT  * FROM travailleSur WHERE idEtudiant=?";
    public final static String SELECT_PROJECT = "SELECT * FROM projet WHERE id=?";
    public final static String SELECT_ETUDIANT_TRAVAIL_SUR_PROJET = "SELECT t.idEtudiant, t.idProjet, p.nom, p.contenu" +
            " FROM etudiant e " +
            "INNER JOIN travailleSur t ON t.idEtudiant = e.id " +
            "INNER JOIN  projet p ON  p.id = t.idProjet " +
            "WHERE  p.id = ? AND e.id = ?";

    public  final static String SELECT_LIST_ETUDIANT_TRAVAIL_SUR_PROJET = "SELECT  e.nom, e.prenom"
            + " FROM etudiant e " +
            "INNER JOIN travailleSur t ON t.idEtudiant = e.id " +
            "INNER JOIN  projet p ON  p.id = t.idProjet "
            + "where  p.id = ?";

    public final static String SELECT_ENCADRANT_ENCADRE_PROJET = "SELECT c.idEncadrant, c.idProjet, p.nom, p.contenu" +
            " FROM encadrant e " +
            "INNER JOIN encadrer c ON c.idEncadrant = e.id " +
            "INNER JOIN  projet p ON  p.id = c.idProjet " +
            "WHERE e.id = ?";
    public final static String SELECT_ENCADRANT = "SELECT * FROM encadrant WHERE id=?";
    public final static String SELECT_ETUDIANT = "SELECT * FROM etudiant WHERE id=?";

    public final static String SELECT_NOTATION = "SELECT * FROM  noter n " +
            "INNER JOIN  encadrer e ON  n.idProjet = e.idProjet " +
            "INNER JOIN  projet p ON  p.id = e.idProjet " +
            "WHERE  p.id = ?";


    //UPDATE
    public static final String UPDATE_CONTENU_MEMOIRE     = "UPDATE projet SET contenu=? WHERE id=?";
    public static final String UPDATE_COMMENTAIRE_ENCADRER     = "UPDATE  encadrer SET commentaire = ? WHERE idProjet=? AND idEncadrant = ?";

}
