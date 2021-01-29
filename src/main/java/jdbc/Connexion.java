package jdbc;

import java.sql.*;
import java.util.logging.Logger;

public class Connexion {

    private final static Logger logger = Logger.getLogger(Connection.class.getName());


    public Connection connect() throws SQLException {

        return DriverManager.getConnection(
                    "jdbc:postgresql://127.0.0.1:5432/projet_l3",
                    "postgres", "dabola");
    }

    public static void closeConnection( Connection con ) throws SQLException
    {
        if ( con != null ) {
            con.close();
        }
    }

    public static void closePrepaerdStatement( PreparedStatement stmt ) throws SQLException
    {
        if ( stmt != null ) {
            stmt.close();
        }
    }

    public static void closeResultSet( ResultSet rs ) throws SQLException
    {
        if ( rs != null ) {
            rs.close();
        }
    }

}
