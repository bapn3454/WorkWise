package services;

import jdbc.Connexion;
import modules.Personne;

import java.sql.*;

public class PersonneGeneric<T extends Personne> {


    Connection con = null;
    PreparedStatement ps = null;
    Connexion connexion = new Connexion();

    public int inscription(T p, String query) {
        int id = 0;
        try {
            con =  connexion.connect();
            if ( con == null ) {
                System.out.println( "Error getting the connection. Please check if the DB server is running" );
                return id;
            }

            con.setAutoCommit(false);
            ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString( 1, p.getNom() );
            ps.setString( 2, p.getPrenom() );
            ps.setString( 3, p.getMail() );
            ps.setString( 4, p.getMdp());
            ps.setString( 5, p.getType());

            int rowsAffected = ps.executeUpdate();
            if(rowsAffected > 0) {
                // then we get the ID of the affected row(s)
                try (ResultSet rs = ps.getGeneratedKeys()){
                    if (rs.next()) {
                        id = rs.getInt(1);
                    }
                }catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            System.out.println(p.getClass() + " => " + ps.toString() );
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


    public T verifify_connexion(T p, String query) {

        ResultSet rs = null;
        try {
            con =  connexion.connect();
            if ( con == null ) {
                System.out.println( "Error getting the connection. Please check if the DB server is running" );
                p = null;
                return p;
            }
            //con.setAutoCommit( false );
            ps = con.prepareStatement(query);
            ps.setString( 1, p.getMail());
            ps.setString( 2, p.getMdp());
            rs = ps.executeQuery();
            System.out.println( "retrivePerson => " + ps.toString() );
            //con.commit();
            while(rs.next()){
                p.setId(rs.getInt( "ID" ));
                p.setNom( rs.getString( "NOM" ) );
                p.setPrenom( rs.getString( "PRENOM" ) );
                p.setMail( rs.getString( "EMAIL") );
            }

        } catch ( SQLException e ) {
            System.out.println(e.getMessage());

        } finally {
            try {
                connexion.closeResultSet( rs );
                connexion.closePrepaerdStatement( ps );
                connexion.closeConnection( con );
            } catch ( SQLException e ) {
                System.out.println(e.getMessage());
            }
        }
        return p;

    }
}
