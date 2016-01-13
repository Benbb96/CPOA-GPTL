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

    public MatchSimple(String date, int heure, String tour, Court court, Joueur j1, Joueur j2) {
        super(date, heure, tour, court);
        this.j1 = j1;
        this.j2 = j2;
    }
    
    public MatchSimple(int idMatchSimple, String date, int heure, String tour, Court court, Joueur j1, Joueur j2) {
        super(idMatchSimple, date, heure, tour, court);
        this.j1 = j1;
        this.j2 = j2;
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
        return "Match Simple - "+getTour()+" ("+getDate()+" à "+getRealTime()+")\nSur le "+getCourt()+".\n\n"+j1.prenomNom()+" Contre "+j2.prenomNom()+"\n\nVoulez-vous modifier ce match ?";
    }
    
    public void ajouterMatchSimple(Connection conn) throws SQLIntegrityConstraintViolationException {
        try {
            String requete = "Insert into Match_Simple(idMatch, idj1, idj2, date_match, heure_match, tour_match, idcourt) Values (?,?,?,?,?,?,?)";
            PreparedStatement prepared = conn.prepareStatement(requete);
            prepared.setInt(1,this.getIdMatch());
            prepared.setInt(2,this.getJ1().getIdJoueur());
            prepared.setInt(3,this.getJ2().getIdJoueur());
            prepared.setString(4,this.getDate());
            prepared.setInt(5,this.getHeure());
            prepared.setString(6,this.getTour());
            prepared.setInt(7,this.getCourt().getIdCourt());
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
            ResultSet rset = ConfigConnexion.executeRequete(conn,"select idmatch, date_match, heure_match, tour_match, idj1, idj2, idCourt from MATCH_SIMPLE ");
            while (rset.next()) {
                Joueur j1 = Joueur.listeJoueurs.get(rset.getInt(5));
                Joueur j2 = Joueur.listeJoueurs.get(rset.getInt(6));
                Court c = Court.listeCourts.get(rset.getInt(7));
                listeMatchSimple.put(rset.getInt(1), new MatchSimple(rset.getInt(1),rset.getString(2),rset.getInt(3),rset.getString(4),c,j1,j2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
