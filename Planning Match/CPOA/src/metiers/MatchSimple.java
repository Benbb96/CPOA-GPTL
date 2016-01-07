/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metiers;

import connexion.ConfigConnexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLSyntaxErrorException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ben
 */
public class MatchSimple extends Match {
    
    public static Map<Integer,MatchSimple> listeMatchSimple= new HashMap();
    
    private Joueur j1;
    private Joueur j2;
    private int vainqueur; // =1 si le j1 a gagné, 2 si c'est j2

    public MatchSimple(String date, int heure, String tour, Joueur j1, Joueur j2) {
        super(date, heure, tour);
        this.j1 = j1;
        this.j2 = j2;
    }
    
    public MatchSimple(int idMatchSimple, String date, int heure, String tour, Joueur j1, Joueur j2) {
        super(idMatchSimple, date, heure, tour);
        this.j1 = j1;
        this.j2 = j2;
    }
    
    public MatchSimple(String date, int heure, String tour, int j1, int j2) {
        super(date, heure, tour);
        this.j1 = Joueur.listeJoueurs.get(j1);
        this.j2 = Joueur.listeJoueurs.get(j2);
    }
    
    public MatchSimple(int idMatch, String date, int heure, String tour, int j1, int j2) {
        super(idMatch, date, heure, tour);
        this.j1 = Joueur.listeJoueurs.get(j1);
        this.j2 = Joueur.listeJoueurs.get(j2);
    }
    
    public Joueur getJ1() {return j1;}
    public Joueur getJ2() {return j2;}
    public int getVainqueur() {return vainqueur;}
    public void setJ1(Joueur j1) {this.j1 = j1;}
    public void setJ2(Joueur j2) {this.j2 = j2;}
    public void setVainqueur(int vainqueur) {this.vainqueur = vainqueur;}
    
    @Override
    public String toString() {
        return "Match Simple ("+getTour()+") : "+j1.prenomNom()+" VS "+j2.prenomNom()+" - "+getDate()+", à "+getRealTime();
    }
    
    public String affiche() {
        return "Match Simple - "+getTour()+" ("+getDate()+" à "+getRealTime()+")\n\n"+j1.prenomNom()+" Contre "+j2.prenomNom()+"\n\nVoulez-vous modifier ce match ?";
    }
    
    /**
     * Obtenir un match en fonction de son id
     * @param idMatch
     * @param conn Une connexion à la base
     * @return le match correspond à l'id passé en paramètre
     */
    public static MatchSimple getMatch(int idMatch, Connection conn) {
        MatchSimple match = null;
        try {
            ResultSet rset = ConfigConnexion.executeRequete(conn,"select date_match, heure_match, tour_match, idj1, idj2 from Match_Simple where idMatch="+idMatch);
            if(rset.next()) {
                match = new MatchSimple(idMatch, rset.getString(1), rset.getInt(2), rset.getString(3), rset.getInt(4), rset.getInt(5));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MatchSimple.class.getName()).log(Level.SEVERE, null, ex);
        }
        return match;
    }
    
    public static MatchSimple getMatch(String date, int heure, Connection conn) {
        MatchSimple match = null;
        try {
            ResultSet rset = ConfigConnexion.executeRequete(conn, "select id_match, idj1, idj2, tour_match from Match_Simple where date_match="+date+" and heure_match="+heure);
            match = new MatchSimple(rset.getInt(1), date, heure, rset.getString(4), rset.getInt(2), rset.getInt(3));
        } catch (SQLException ex) {
            Logger.getLogger(MatchSimple.class.getName()).log(Level.SEVERE, null, ex);
        }
        return match;
    }
    
    public void ajouterMatchSimple(Connection conn) throws SQLIntegrityConstraintViolationException {
        try {
            String requete = "Insert into Match_Simple(idMatch, idj1, idj2, date_match, heure_match, tour_match) Values (?,?,?,?,?,?)";
            PreparedStatement prepared = conn.prepareStatement(requete);
            prepared.setInt(1,this.getIdMatch());
            prepared.setInt(2,this.getJ1().getIdJoueur());
            prepared.setInt(3,this.getJ2().getIdJoueur());
            prepared.setString(4,this.getDate());
            prepared.setInt(5,this.getHeure());
            prepared.setString(6,this.getTour());
            int result = prepared.executeUpdate();
            System.out.println(result + " ligne(s) insérée(s)");
        } catch (SQLIntegrityConstraintViolationException ex) {
            throw new SQLIntegrityConstraintViolationException();
        } catch (SQLSyntaxErrorException ex) {
            Logger.getLogger(MatchSimple.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MatchSimple.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Remet à jour la map contenant tous les matchs simples indexés par les id
     * @param conn Une connexion à la base
     */
    public static void updateListMatchSimple(Connection conn) {
        System.out.println("Remise à jour de la liste des matchs en simple.");
        listeMatchSimple.clear();
        try {
            ResultSet rset = ConfigConnexion.executeRequete(conn,"select idmatch, date_match, heure_match, tour_match, idj1, idj2 from MATCH_SIMPLE order by heure_match");
            while (rset.next()) {
                listeMatchSimple.put(rset.getInt(1), new MatchSimple(rset.getInt(1),rset.getString(2),rset.getInt(3),rset.getString(4), rset.getInt(5),rset.getInt(6)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
