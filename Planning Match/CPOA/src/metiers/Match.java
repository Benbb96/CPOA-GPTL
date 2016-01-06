/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metiers;

import connexion.ConfigConnexion;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ben
 */
public abstract class Match {
    
    private static int lastId = getLastId();
    
    private int idMatch;
    private String date;
    private int heure; //0 = 8h ; 1 = 11h ; 2 = 15h ; 3 = 18h ; 4 = 21h
    
    public Match (String date, int heure) {
        idMatch = ++lastId;
        this.date = date;
        this.heure = heure;
    }
    
    public Match(int idMatch, String date, int heure) {
        this.idMatch = idMatch;
        this.date = date;
        this.heure = heure;
    }
    
    public static int getLastId() {
        int maxId = -1; //pour gérer l'erreur
        try {
            Connection conn = ConfigConnexion.getConnection("connexion.properties");
            ResultSet rset = ConfigConnexion.executeRequete(conn,
                                                                "SELECT MAX(idmatch)\n" +
                                                                "FROM\n" +
                                                                "(\n" +
                                                                "    SELECT idmatch\n" +
                                                                "    FROM match_simple\n" +
                                                                "    UNION ALL\n" +
                                                                "    SELECT idmatch\n" +
                                                                "    FROM match_double\n" +
                                                                ")");
            rset.next();
            maxId = rset.getInt(1);
        } catch  (IOException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MatchSimple.class.getName()).log(Level.SEVERE, null, ex);
        }
        return maxId;
    }
    
    public int getIdMatch() {return idMatch;}
    public String getDate() {return date;}
    public int getHeure() {return heure;}
    public void setIdMatch(int idMatch) {this.idMatch = idMatch;}
    public void setDate(String date) {this.date = date;}
    public void setHeure(int heure) {this.heure = heure;}
}
