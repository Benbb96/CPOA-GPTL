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
public class MatchDouble extends Match {
    
    public static Map<Integer,MatchDouble> listeMatchDouble = new HashMap();
    
    private Joueur a1; // Equipe A - Joueur 1
    private Joueur a2; // Equipe A - Joueur 2
    private Joueur b1; // Equipe B - Joueur 1
    private Joueur b2; // Equipe B - Joueur 2

    public MatchDouble(String date, int heure, String tour, Court court, Joueur a1, Joueur a2, Joueur b1, Joueur b2) {
        super(date, heure, tour, court);
        this.a1 = a1;
        this.a2 = a2;
        this.b1 = b1;
        this.b2 = b2;
    }
    
    public MatchDouble(int idMatchDouble, String date, int heure, String tour, Court court, Joueur a1, Joueur a2, Joueur b1, Joueur b2) {
        super(idMatchDouble, date, heure, tour, court);
        this.a1 = a1;
        this.a2 = a2;
        this.b1 = b1;
        this.b2 = b2;
    }
    
    public Joueur getA1() {return a1;}
    public Joueur getA2() {return a2;}
    public Joueur getB1() {return b1;}
    public Joueur getB2() {return b2;}
    public void setA1(Joueur a1) {this.a1 = a1;}
    public void setA2(Joueur a2) {this.a2 = a2;}
    public void setB1(Joueur b1) {this.b1 = b1;}
    public void setB2(Joueur b2) {this.b2 = b2;}
    
    @Override
    public String toString() {
        return "Match Double ("+getTour()+") : "+a1.getPrenom()+" "+a1.getNom()+" et "+a2.getPrenom()+" "+a2.getNom()+"\n\tVS\n"+b1.getPrenom()+" "+b1.getNom()+" et "+b2.getPrenom()+" "+b2.getNom()+"\n"+getDate()+", à "+getRealTime();
    }
    
    public String affiche() {
        return "Match Double n°"+getIdMatch()+" - "+getTour()+" ("+getDate()+" à "+getRealTime()+")\nSur le "+getCourt()+".\n\n"+a1.prenomNom()+" et "+a2.prenomNom()+"\n      Contre\n"+b1.prenomNom()+" et "+b2.prenomNom()+"\n\nVoulez-vous modifier ce match ?";
    }
    
    /**
     * Permet d'ajouter un match double à la base
     * @param conn Une connexion à la base
     * @throws java.sql.SQLIntegrityConstraintViolationException
     */
    public void ajouterMatchDouble(Connection conn) throws SQLIntegrityConstraintViolationException {
        try {
            String requete = "Insert into Match_Double(idMatch, idja1, idja2, idjb1, idjb2, date_match, heure_match, tour_match, idCourt) Values (?,?,?,?,?,?,?,?,?)";
            PreparedStatement prepared = conn.prepareStatement(requete);
            prepared.setInt(1,this.getIdMatch());
            prepared.setInt(2,this.getA1().getIdJoueur());
            prepared.setInt(3,this.getA2().getIdJoueur());
            prepared.setInt(4,this.getB1().getIdJoueur());
            prepared.setInt(5,this.getB2().getIdJoueur());
            prepared.setString(6,this.getDate());
            prepared.setInt(7,this.getHeure());
            prepared.setString(8, this.getTour());
            prepared.setInt(9,this.getCourt().getIdCourt());
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
     * Remet à jour la map contenant tous les matchs doubles indexés par les id
     * @param conn Une connexion à la base
     */
    public static void updateListMatchDouble(Connection conn) {
        System.out.println("Remise à jour de la liste des matchs en double.");
        listeMatchDouble.clear();
        try {
            ResultSet rset = ConfigConnexion.executeRequete(conn, "select idmatch, date_match, heure_match, tour_match, idja1, idja2, idjb1, idjb2, idcourt from MATCH_DOUBLE order by heure_match");
            while (rset.next()) {
                Joueur j1 = Joueur.listeJoueurs.get(rset.getInt(5));
                Joueur j2 = Joueur.listeJoueurs.get(rset.getInt(6));
                Joueur j3 = Joueur.listeJoueurs.get(rset.getInt(7));
                Joueur j4 = Joueur.listeJoueurs.get(rset.getInt(8));
                Court c = Court.listeCourts.get(rset.getInt(9));
                listeMatchDouble.put(rset.getInt(1), new MatchDouble(rset.getInt(1),rset.getString(2),rset.getInt(3),rset.getString(4),c,j1,j2,j3,j4));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
