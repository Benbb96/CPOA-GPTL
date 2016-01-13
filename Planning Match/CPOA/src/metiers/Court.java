/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metiers;

import connexion.ConfigConnexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ben
 */
public class Court {
    
    private int idCourt = 0;
    private String type; //tournoi ou entrainement
    
    public static Map<Integer,Court> listeCourts = new HashMap();
    
    public Court(String type) {
        idCourt = idCourt++;
        this.type = type;
    }
    
    public Court(int idCourt, String type) {
        this.idCourt = idCourt;
        this.type = type;
    }
    
    public int getIdCourt() {return idCourt;}
    public String getType() {return type;}
    public void setIdCourt(int idCourt) {this.idCourt = idCourt;}
    public void setType(String type) {this.type = type;}
    
    @Override
    public String toString() {
        return "court °"+idCourt+" ("+type+")";
    }
    
    public static void updateListeCourts(Connection connexion) {
        System.out.println("Remise à jour de la liste des Joueurs");
        try {
            ResultSet rset = ConfigConnexion.executeRequete(connexion,"select idcourt, type from COURT order by idcourt");
            while (rset.next()) {
                listeCourts.put(rset.getInt(1), new Court(rset.getInt(1),rset.getString(2)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
