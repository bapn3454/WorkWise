package services;

import jdbc.JDBCDaoImpl;
import jdbc.Connexion;
import modules.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OtherService {

    PreparedStatement ps = null;

    public int insertProjet(Projet p) {
        Connexion connexion = new Connexion();
        Connection con = null;
        int id = 0;
        try {
            con =  connexion.connect();
            if ( con == null ) {
                System.out.println( "Error getting the connection. Please check if the DB server is running" );
                return id;
            }
            con.setAutoCommit(false);
            ps = con.prepareStatement(JDBCDaoImpl.INSERT_SQL_PROJET, Statement.RETURN_GENERATED_KEYS);
            ps.setString( 1, p.getNom());
            //ps.execute();
            System.out.println(Projet.class + " => " + ps.toString() );
            int rowsAffected = ps.executeUpdate();
            if(rowsAffected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()){
                    if (rs.next()) {
                        id = rs.getInt(1);
                    }
                }catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            con.commit();
        } catch ( SQLException e ) {
            try {
                if( con != null ) {
                    con.rollback();
                }
            } catch ( SQLException e1 ) {
                System.out.println(e1.getMessage());
            }
        } finally {
            try {
                if(p.getId() == 0) p = null;
                connexion.closePrepaerdStatement( ps );
                connexion.closeConnection( con );
            } catch ( SQLException e ) {
                System.out.println(e.getMessage());
            }
        }
        return id;

    }

    public int insertNote(Noter noter) {
        Connexion connexion = new Connexion();
        Connection con = null;
        int rowsAffected = 0;
        try {
            con =  connexion.connect();
            if ( con == null ) {
                System.out.println( "Error getting the connection. Please check if the DB server is running" );
                return rowsAffected;
            }
            con.setAutoCommit( false );
            ps = con.prepareStatement(JDBCDaoImpl.INSERT_SQL_NOTER);
            ps.setInt( 1, noter.getIdProjet());
            ps.setInt( 2, noter.getJury());
            ps.setFloat(3, noter.getNote());
            ps.setString(4, noter.getAppreciation());

            rowsAffected = ps.executeUpdate();
            System.out.println( "Projet enregistrer => " + ps.toString() );
            con.commit();

        } catch ( SQLException e ) {
            try {
                if( con != null ) {
                    con.rollback();
                }
            } catch ( SQLException e1 ) {
                System.out.println(e1.getMessage());
            }
        } finally {
            try {
                connexion.closePrepaerdStatement( ps );
                connexion.closeConnection( con );
            } catch ( SQLException e ) {
                System.out.println(e.getMessage());
            }
        }
        return rowsAffected;
    }


    public void insertPrivillege(Avoir avoir) {
        Connexion connexion = new Connexion();
        Connection con = null;
        try {
            con =  connexion.connect();
            if ( con == null ) {
                System.out.println( "Error getting the connection. Please check if the DB server is running" );
                return;
            }
            con.setAutoCommit( false );
            ps = con.prepareStatement(JDBCDaoImpl.INSERT_SQL_AVOIR);
            ps.setInt( 1, avoir.getIdPersonne());
            ps.setInt( 2, avoir.getCodePriv() );

            ps.execute();
            System.out.println( "Avoir => " + ps.toString() );
            con.commit();

        } catch ( SQLException e ) {
            try {
                if( con != null ) {
                    con.rollback();
                }
            } catch ( SQLException e1 ) {
                System.out.println(e1.getMessage());
            }
        } finally {
            try {
                connexion.closePrepaerdStatement( ps );
                connexion.closeConnection( con );
            } catch ( SQLException e ) {
                System.out.println(e.getMessage());
            }
        }

    }


    public List<Projet> all_project() {

        Connexion connexion = new Connexion();
        Connection con = null;
        List<Projet> projets = new ArrayList<Projet>();

        ResultSet rs =  null;
        try {
            con =  connexion.connect();
            if ( con == null )
            {
                System.out.println("Error getting the connection. Please check if the DB server is running" );
                return projets;
            }
            ps = con.prepareStatement(JDBCDaoImpl.SELECT_ALL_PROJECT);
            rs = ps.executeQuery();
            while ( rs.next() ) {
                Projet proj = new Projet();
                proj.setId(rs.getInt( "ID" ));
                proj.setNom( rs.getString( "NOM" ) );
                proj.setContenu( rs.getString( "CONTENU" ) );
                projets.add(proj) ;
            }
        } catch ( SQLException e ) {
            System.out.println(e.getMessage());

        } finally {
            try {
                connexion.closePrepaerdStatement( ps );
                connexion.closeConnection( con );
            } catch ( SQLException e ) {
                System.out.println(e.getMessage());
            }
        }
        return projets;
    }


    public int insertTravailSur(TravailleSur t) {
        Connexion connexion = new Connexion();
        Connection con = null;
        int rep = 0;
        try {
            con =  connexion.connect();
            if ( con == null ) {
                System.out.println( "Error getting the connection. Please check if the DB server is running" );
                return rep;
            }
            con.setAutoCommit(false);
            ps = con.prepareStatement(JDBCDaoImpl.INSERT_SQL_TRAVAIL_SUR, Statement.RETURN_GENERATED_KEYS);
            ps.setInt( 1, t.getIdProjet());
            ps.setInt( 2, t.getIdEtudiant());
            ps.execute();
            System.out.println(TravailleSur.class + " => " + ps.toString() );
            con.commit();
            int rowsAffected = ps.executeUpdate();
            if(rowsAffected > 0) {
                // then we get the ID of the affected row(s)
                try (ResultSet rs = ps.getGeneratedKeys()){
                    if (rs.next()) {
                        rep = rs.getInt(1);
                    }
                }catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch ( SQLException e ) {
            try {
                if( con != null ) {
                    con.rollback();
                }
            } catch ( SQLException e1 ) {
                System.out.println(e1.getMessage());
            }
        } finally {
            try {
                connexion.closePrepaerdStatement( ps );
                connexion.closeConnection( con );
            } catch ( SQLException e ) {
                System.out.println(e.getMessage());
            }
        }

        return rep;
    }


    /**
     *
     * @param idProjet : identifiant du projet
     * @param idEtudiant : identifiant de l'etudiant
     * @return
     */
    public DTOEtudiantTravailSurProjet return_file(int idProjet, int idEtudiant) {

        DTOEtudiantTravailSurProjet dto = new DTOEtudiantTravailSurProjet();

        Connexion connexion = new Connexion();
        Connection con = null;
        ResultSet rs =  null;
        try {
            con =  connexion.connect();
            if ( con == null ) {
                System.out.println("Error getting the connection. Please check if the DB server is running" );
                return dto;
            }
            ps = con.prepareStatement(JDBCDaoImpl.SELECT_ETUDIANT_TRAVAIL_SUR_PROJET);
            ps.setInt( 1, idProjet);
            ps.setInt( 2, idEtudiant);
            rs = ps.executeQuery();
            System.out.println( "Select etudiant travailSur projet => " + ps.toString());
            while ( rs.next()) {
                dto.setIdEtudiant(rs.getInt("IDETUDIANT"));
                dto.setIdProjet(rs.getInt( "IDPROJET" ));
                dto.setContenu( rs.getString( "CONTENU" ));
                dto.setNomProjet( rs.getString( "NOM" ));
            }
        } catch ( SQLException e ) {
            System.out.println(e.getMessage());

        } finally {
            try {
                if(dto.getIdEtudiant() == 0 && dto.getIdProjet() == 0) dto = null;
                connexion.closePrepaerdStatement( ps );
                connexion.closeConnection( con );
            } catch ( SQLException e ) {
                System.out.println(e.getMessage());
            }
        }
        return dto;
    }


    /**
     *
     * @param id
     * @return
     */
    public TravailleSur select_travailSur(int id) {

        Connexion connexion = new Connexion();
        Connection con = null;
        TravailleSur travailleSur = new TravailleSur();
        ResultSet rs =  null;
        try {
            con =  connexion.connect();
            if ( con == null ) {
                System.out.println("Error getting the connection. Please check if the DB server is running" );
                return travailleSur;
            }
            ps = con.prepareStatement(JDBCDaoImpl.SELECT_TRAVAIL_SUR);
            ps.setInt( 1, id);
            System.out.println( "Select TravailSur=> " + ps.toString());
            rs = ps.executeQuery();
            while(rs.next()) {
                travailleSur.setIdProjet(rs.getInt( "IDPROJET"));
                travailleSur.setIdEtudiant(rs.getInt( "IDETUDIANT"));
            }
        } catch ( SQLException e ) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(travailleSur.getIdProjet() == 0 && travailleSur.getIdEtudiant() == 0) travailleSur = null;
                connexion.closePrepaerdStatement(ps);
                connexion.closeConnection(con);
            } catch ( SQLException e ) {
                System.out.println(e.getMessage());
            }
        }
        return travailleSur;
    }


    /**
     *
     * @param id : identifiant projet
     * @param contenu : contenu de la memoire du projet
     * @return
     */
    public int update_contenu_projet (int id, String contenu) {

        Connexion connexion = new Connexion();
        Connection con = null;
        int retour = 0;
        try {
            con =  connexion.connect();
            if ( con == null ) {
                System.out.println("Error getting the connection. Please check if the DB server is running" );
                return retour;
            }
            con.setAutoCommit(false);
            ps = con.prepareStatement(JDBCDaoImpl.UPDATE_CONTENU_MEMOIRE, Statement.RETURN_GENERATED_KEYS);
            ps.setString( 1, contenu);
            ps.setInt( 2, id);
            int rowsAffected = ps.executeUpdate();
            System.out.println( "Select TravailSur=> " + ps.toString());
            if(rowsAffected > 0) {
                retour = 1;
            }
            con.commit();
        } catch ( SQLException e ) {
            System.out.println(e.getMessage());
        } finally {
            try {
                connexion.closePrepaerdStatement(ps);
                connexion.closeConnection(con);
            } catch ( SQLException e ) {
                System.out.println(e.getMessage());
            }
        }
        return retour;
    }

    /**
     *
     * @param idProjet
     * @return
     */
    public List<Etudiant> listOfStudentsWhoHaveTheSmeProject(int idProjet) {
        List<Etudiant> etudiant = new ArrayList<Etudiant>();
        Connexion connexion = new Connexion();
        Connection con = null;
        ResultSet rs =  null;
        try {
            con =  connexion.connect();
            if ( con == null ) {
                System.out.println("Error getting the connection. Please check if the DB server is running" );
                etudiant = null;
                return etudiant;
            }
            ps = con.prepareStatement(JDBCDaoImpl.SELECT_LIST_ETUDIANT_TRAVAIL_SUR_PROJET);
            ps.setInt( 1, idProjet);
            System.out.println( "Select TravailSur=> " + ps.toString());
            rs = ps.executeQuery();
            while(rs.next()) {
                Etudiant etud = new Etudiant();
                etud.setNom(rs.getString( "NOM"));
                etud.setPrenom(rs.getString( "PRENOM"));
                etudiant.add(etud);
            }
        } catch ( SQLException e ) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(etudiant == null) etudiant = null;
                connexion.closePrepaerdStatement(ps);
                connexion.closeConnection(con);
            } catch ( SQLException e ) {
                System.out.println(e.getMessage());
            }
        }
        return  etudiant;
    }


    public int insertEncadrer(Encadre e) {
        Connexion connexion = new Connexion();
        Connection con = null;
        int id = 0;
        try {
            con =  connexion.connect();
            if ( con == null ) {
                System.out.println( "Error getting the connection. Please check if the DB server is running" );
                return id;
            }
            con.setAutoCommit(false);
            ps = con.prepareStatement(JDBCDaoImpl.INSERT_SQL_ENCADRER, Statement.RETURN_GENERATED_KEYS);
            ps.setInt( 1, e.getIdProjet());
            ps.setInt( 2, e.getIdEncadrant());
            //ps.execute();
            System.out.println("Encadrer" + " => " + ps.toString() );
            int rowsAffected = ps.executeUpdate();
            if(rowsAffected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()){
                    if (rs.next()) {
                        id = rs.getInt(1);
                    }
                }catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            con.commit();
        } catch ( SQLException e0 ) {
            try {
                if( con != null ) {
                    con.rollback();
                }
            } catch ( SQLException e1 ) {
                System.out.println(e1.getMessage());
            }
        } finally {
            try {
                connexion.closePrepaerdStatement( ps );
                connexion.closeConnection( con );
            } catch ( SQLException e2 ) {
                System.out.println(e2.getMessage());
            }
        }
        return id;

    }


    /**
     *
     * @param id
     * @return
     */
    public Projet select_travailSur_project(int id) {

        Connexion connexion = new Connexion();
        Connection con = null;
        Projet projet = new Projet();
        ResultSet rs =  null;
        try {
            con =  connexion.connect();
            if ( con == null ) {
                System.out.println("Error getting the connection. Please check if the DB server is running" );
                return projet;
            }
            ps = con.prepareStatement(JDBCDaoImpl.SELECT_TRAVAIL_SUR);
            ps.setInt( 1, id);
            System.out.println( "projet => " + ps.toString());
            rs = ps.executeQuery();
            while(rs.next()) {
                projet.setId(rs.getInt( "IDPROJET"));
                projet.setNom(rs.getString( "NOM"));
                projet.setContenu(rs.getString( "CONTENU"));
            }
        } catch ( SQLException e ) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(projet.getNom() == null ) projet = null;
                connexion.closePrepaerdStatement(ps);
                connexion.closeConnection(con);
            } catch ( SQLException e ) {
                System.out.println(e.getMessage());
            }
        }
        return projet;
    }


    public int update_commentaire_encadrer (int idProjet, int idEncadrant, String commentaire) {

        Connexion connexion = new Connexion();
        Connection con = null;
        int retour = 0;
        try {
            con =  connexion.connect();
            if ( con == null ) {
                System.out.println("Error getting the connection. Please check if the DB server is running" );
                return retour;
            }
            con.setAutoCommit(false);
            ps = con.prepareStatement(JDBCDaoImpl.UPDATE_COMMENTAIRE_ENCADRER, Statement.RETURN_GENERATED_KEYS);
            ps.setString( 1, commentaire);
            ps.setInt( 2, idProjet);
            ps.setInt( 3, idEncadrant);
            int rowsAffected = ps.executeUpdate();
            System.out.println( "Select TravailSur=> " + ps.toString());
            if(rowsAffected > 0) {
                retour = 1;
            }
            con.commit();
        } catch ( SQLException e ) {
            System.out.println(e.getMessage());
        } finally {
            try {
                connexion.closePrepaerdStatement(ps);
                connexion.closeConnection(con);
            } catch ( SQLException e ) {
                System.out.println(e.getMessage());
            }
        }
        return retour;
    }


    public Encadrant select_encadrant(int id) {

        Connexion connexion = new Connexion();
        Connection con = null;
        Encadrant encadrant = new Encadrant();
        ResultSet rs =  null;
        try {
            con =  connexion.connect();
            if ( con == null ) {
                System.out.println("Error getting the connection. Please check if the DB server is running" );
                return encadrant;
            }
            ps = con.prepareStatement(JDBCDaoImpl.SELECT_ENCADRANT);
            ps.setInt( 1, id);
            System.out.println( "encadrant => " + ps.toString());
            rs = ps.executeQuery();
            while(rs.next()) {
                encadrant.setId(rs.getInt( "ID"));
                encadrant.setNom(rs.getString( "NOM"));
                encadrant.setPrenom(rs.getString( "PRENOM"));
            }
        } catch ( SQLException e ) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(encadrant.getNom() == null ) encadrant = null;
                connexion.closePrepaerdStatement(ps);
                connexion.closeConnection(con);
            } catch ( SQLException e ) {
                System.out.println(e.getMessage());
            }
        }
        return encadrant;
    }

    /**
     *
     * @param idEncadrant
     * @return
     */
    public List<Projet> select_encadrer_project(int idEncadrant) {

        Connexion connexion = new Connexion();
        Connection con = null;
        List<Projet> projets = new ArrayList<Projet>();
        ResultSet rs =  null;
        try {
            con =  connexion.connect();
            if ( con == null ) {
                System.out.println("Error getting the connection. Please check if the DB server is running" );
                return projets;
            }
            ps = con.prepareStatement(JDBCDaoImpl.SELECT_ENCADRANT_ENCADRE_PROJET);
            ps.setInt( 1, idEncadrant);
            System.out.println( "projet Encadrant => " + ps.toString());
            rs = ps.executeQuery();
            while(rs.next()) {
                Projet projet = new Projet();
                projet.setId(rs.getInt( "IDPROJET"));
                projet.setNom(rs.getString( "NOM"));
                projet.setContenu(rs.getString( "CONTENU"));
            }
        } catch ( SQLException e ) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(projets == null ) projets = null;
                connexion.closePrepaerdStatement(ps);
                connexion.closeConnection(con);
            } catch ( SQLException e ) {
                System.out.println(e.getMessage());
            }
        }
        return projets;
    }


    public List<DTOEncadrantProjet> return_encadrant_projet(int idEcadrant) {

        List<DTOEncadrantProjet> dto = new ArrayList<DTOEncadrantProjet>();

        Connexion connexion = new Connexion();
        Connection con = null;
        ResultSet rs =  null;
        try {
            con =  connexion.connect();
            if ( con == null ) {
                System.out.println("Error getting the connection. Please check if the DB server is running" );
                return dto;
            }
            ps = con.prepareStatement(JDBCDaoImpl.SELECT_ENCADRANT_ENCADRE_PROJET);
            ps.setInt( 1, idEcadrant);
            rs = ps.executeQuery();
            System.out.println( "Select etudiant travailSur projet => " + ps.toString());
            while ( rs.next()) {
                DTOEncadrantProjet list = new DTOEncadrantProjet();
                list.setIdEncadrant(rs.getInt("IDENCADRANT"));
                list.setIdProjet(rs.getInt( "IDPROJET" ));
                list.setContenu( rs.getString( "CONTENU" ));
                list.setNomProjet( rs.getString( "NOM" ));
                dto.add(list);
            }
        } catch ( SQLException e ) {
            System.out.println(e.getMessage());

        } finally {
            try {
                if(dto.isEmpty()) dto = null;
                connexion.closePrepaerdStatement( ps );
                connexion.closeConnection( con );
            } catch ( SQLException e ) {
                System.out.println(e.getMessage());
            }
        }
        return dto;
    }


    /**
     *
     * @param id
     * @return
     */
    public Projet select_project(int id) {

        Connexion connexion = new Connexion();
        Connection con = null;
        Projet projet = new Projet();
        ResultSet rs =  null;
        try {
            con =  connexion.connect();
            if ( con == null ) {
                System.out.println("Error getting the connection. Please check if the DB server is running" );
                return projet;
            }
            ps = con.prepareStatement(JDBCDaoImpl.SELECT_PROJECT);
            ps.setInt( 1, id);
            System.out.println( "projet => " + ps.toString());
            rs = ps.executeQuery();
            while(rs.next()) {
                projet.setId(rs.getInt( "ID"));
                projet.setNom(rs.getString( "NOM"));
                projet.setContenu(rs.getString( "CONTENU"));
            }
        } catch ( SQLException e ) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(projet.getNom() == null ) projet = null;
                connexion.closePrepaerdStatement(ps);
                connexion.closeConnection(con);
            } catch ( SQLException e ) {
                System.out.println(e.getMessage());
            }
        }
        return projet;
    }



    /**
     *
     * @param id
     * @return
     */
    public DTONotation select_notation(int id) {


        Connexion connexion = new Connexion();
        Connection con = null;
        DTONotation dto = new DTONotation();
        ResultSet rs =  null;
        try {
            con =  connexion.connect();
            if ( con == null ) {
                System.out.println("Error getting the connection. Please check if the DB server is running" );
                return dto;
            }
            ps = con.prepareStatement(JDBCDaoImpl.SELECT_NOTATION);
            ps.setInt( 1, id);
            System.out.println( "notation => " + ps.toString());
            rs = ps.executeQuery();
            while(rs.next()) {
                dto.setIdProjet(rs.getInt( "IDPROJET"));
                dto.setIdEncadrant(rs.getInt( "IDENCADRANT"));
                dto.setJury(rs.getInt( "IDJURY"));
                dto.setCommentaire(rs.getString( "COMMENTAIRE"));
                dto.setNote(rs.getFloat( "NOTE"));
                dto.setNomProjet(rs.getString("NOM"));
                dto.setAppreciation(rs.getString("APPRECIATION"));
            }
        } catch ( SQLException e ) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(dto == null ) dto = null;
                connexion.closePrepaerdStatement(ps);
                connexion.closeConnection(con);
            } catch ( SQLException e ) {
                System.out.println(e.getMessage());
            }
        }
        return dto;
    }



    public Etudiant select_etudiant(int id) {

        Connexion connexion = new Connexion();
        Connection con = null;
        Etudiant etudiant = new Etudiant();
        ResultSet rs =  null;
        try {
            con =  connexion.connect();
            if ( con == null ) {
                System.out.println("Error getting the connection. Please check if the DB server is running" );
                return etudiant;
            }
            ps = con.prepareStatement(JDBCDaoImpl.SELECT_ETUDIANT);
            ps.setInt( 1, id);
            System.out.println( "etudiant => " + ps.toString());
            rs = ps.executeQuery();
            while(rs.next()) {
                etudiant.setId(rs.getInt( "ID"));
                etudiant.setNom(rs.getString( "NOM"));
                etudiant.setPrenom(rs.getString( "PRENOM"));
            }
        } catch ( SQLException e ) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(etudiant.getNom() == null ) etudiant = null;
                connexion.closePrepaerdStatement(ps);
                connexion.closeConnection(con);
            } catch ( SQLException e ) {
                System.out.println(e.getMessage());
            }
        }
        return etudiant;
    }



}
