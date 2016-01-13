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
    
    private static int lastId = getLastId(true);
    
    private int idMatch;
    private String date;
    private int heure; //0 = 8h ; 1 = 11h ; 2 = 15h ; 3 = 18h ; 4 = 21h
    private String tour; //Qualif, 1/4, 1/2 ou finale ?
    private Court court; //Le court associé au match
    
    public Match (String date, int heure, String tour, Court court) {
        idMatch = ++lastId;
        this.date = date;
        this.heure = heure;
        this.tour = tour;
        this.court = court;
    }
    
    public Match(int idMatch, String date, int heure, String tour, Court court) {
        this.idMatch = idMatch;
        this.date = date;
        this.heure = heure;
        this.tour = tour;
        this.court = court;
    }
    
    public static int getLastId(boolean update) {System.out.println("Obtenir le dernier id des matchs :");
        int maxId = -1; //pour gérer l'erreur
        if (update) {
            try (Connection conn = ConfigConnexion.getConnection("connexion.properties")) {
                ResultSet rset = ConfigConnexion.executeRequete(conn,"SELECT MAX(idmatch) FROM (SELECT idmatch FROM match_simple UNION ALL SELECT idmatch FROM match_double)");
                rset.next();
                maxId = rset.getInt(1);
            } catch  (IOException | ClassNotFoundException | SQLException ex) {
                Logger.getLogger(MatchSimple.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Dernier id : "+maxId);
        }
        return maxId;
    }
    
    public static void updateLastId() {
        try (Connection conn = ConfigConnexion.getConnection("connexion.properties")) {
            ResultSet rset = ConfigConnexion.executeRequete(conn,"SELECT MAX(idmatch) FROM (SELECT idmatch FROM match_simple UNION ALL SELECT idmatch FROM match_double)");
            rset.next();
            lastId = rset.getInt(1);
        } catch  (IOException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MatchSimple.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getRealTime() {
        switch (heure) {
            case 0 : return "8h";
            case 1 : return "11h";
            case 2 : return "15h";
            case 3 : return "18h";
            case 4 : return "21h";
            default : return "Erreur Horaire !";
        }
    }
    
    public static String getRealTime(int heure) {
        switch (heure) {
            case 0 : return "8h";
            case 1 : return "11h";
            case 2 : return "15h";
            case 3 : return "18h";
            case 4 : return "21h";
            default : return "Erreur Horaire !";
        }
    }
    
    public int getIdMatch() {return idMatch;}
    public String getDate() {return date;}
    public int getHeure() {return heure;}
    public String getTour() {return tour;}
    public Court getCourt() {return court;}
    public void setIdMatch(int idMatch) {this.idMatch = idMatch;}
    public void setDate(String date) {this.date = date;}
    public void setHeure(int heure) {this.heure = heure;}
    public void setTour(String tour) {this.tour = tour;}
    public void setCourt(Court court) {this.court = court;}
    
}
