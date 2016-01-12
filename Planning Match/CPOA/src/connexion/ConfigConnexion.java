package connexion;

import java.sql.*;
import java.io.*;
import java.util.*;
import java.net.URL;


/**
 * ConfigConnection.java
 * Initialise une connexion à une base
 * en lisant un fichier de propriétés
 * @version 2 (avec getResource)
 */
public class ConfigConnexion {
     /**
   * Obtenir une connexion à partir des infos qui sont
   * dans un fichier de propriétés.
   * Le fichier doit contenir les propriétés driver, url,
   * utilisateur, mdp (mot de passe).
   * @param nomFichierProp nom du fichier de propriétés. Si le nom
   * commence par "/", l'emplacement désigne un endroit relatif
   * au classpath, sinon il désigne un endroit relatif au
   * répertoire qui contient le fichier ConfigConnection.class.
   * @return une connexion à la base
     * @throws java.io.IOException
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
   */
  public static Connection getConnection(String nomFichierProp)
      throws IOException, ClassNotFoundException, SQLException {
    Properties props = new Properties();
    URL urlFichierProp = ConfigConnexion.class.getResource(nomFichierProp);
    try (BufferedInputStream bis = new BufferedInputStream(urlFichierProp.openStream())) {
        props.load(bis);
        String driver = props.getProperty("driver");
        String url = props.getProperty("url");
        String utilisateur = props.getProperty("utilisateur");
        String mdp = props.getProperty("mdp");
        Class.forName(driver);
        return DriverManager.getConnection(url, utilisateur, mdp);
    }
  }

  /**
   * Obtenir une connexion à partir des infos qui sont
   * dans un fichier de propriétés, du nom d'utilisateur
   * et du mot de passe passés en paramètre.
   * Le fichier doit contenir les propriétés driver, url.
   * Le nom et le mot de passe de l'utilisateur sont passés
   * en paramètre de la méthode.
   * @param nomFichierProp nom du fichier de propriétés. Si le nom
   * commence par "/", l'emplacement désigne un endroit relatif
   * au classpath, sinon il désigne un endroit relatif au
   * répertoire qui contient le fichier ConfigConnection.class.
   * @param utilisateur nom de l'utilisateur.
   * @param mdp mot de passe de l'utilisateur.
   * @return une connexion à la base.
     * @throws java.io.IOException
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
   */
    public static Connection getConnection(String nomFichierProp, String utilisateur, String mdp) throws IOException, ClassNotFoundException, SQLException {
        Properties props = new Properties();
        URL urlFichierProp = ConfigConnexion.class.getResource(nomFichierProp);
        try (BufferedInputStream bis = new BufferedInputStream(urlFichierProp.openStream())) {
            props.load(bis);
            String driver = props.getProperty("driver");
            String url = props.getProperty("url");
            Class.forName(driver);
            return DriverManager.getConnection(url, utilisateur, mdp);
        }
    }
    
    /**
     * Méthode permettant d'obtenir un objet Statement pour ensuite effectuer des requêtes dessus
     * @return un Statement
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public static Statement getStatement() throws IOException, ClassNotFoundException, SQLException {
        Connection conn = getConnection ("connexion.properties");
        conn.close();
        return conn.createStatement();
    }
    
    /**
     * Méthode pour exécuter une requête passée en paramètre
     * @param conn Une connexion à la base
     * @param requete String contenant la requête SQL
     * @return un ResultSet contenant le résultat de la requête
     */
    public static ResultSet executeRequete(Connection conn, String requete) {
        ResultSet rset = null;
        try {
            Class.forName ("oracle.jdbc.OracleDriver");
            Statement stmt = conn.createStatement();
            System.out.println("Reqête SQl envoyée : "+requete);
            rset = stmt.executeQuery (requete);
        }
        catch (ClassNotFoundException e) {
            System.err.println("La classe n'a pas été trouvée :"+e.getMessage());
        }
        catch (SQLException e) {
            System.out.println (" Capture d'une SQLException :");
            while (e != null) {
                System.out.print ("SQLSTATE: " + e.getSQLState ( ));
                System.out.print (" Message: " + e.getMessage ( ));
                System.out.println (" Code d’erreur vendeur: " + e.getErrorCode( ));
                e.printStackTrace(System.out);
                e = e.getNextException();
                System.out.println (" ");
            }
        }
        catch (Exception e) {
            System.err.println("Une exception a été soulevée : "+e.getMessage());
        }
        return rset;
    }
}
