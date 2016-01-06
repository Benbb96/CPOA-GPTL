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
public class Joueur extends Personne {

    public static Map<Integer, Joueur> listeJoueurs = new HashMap<>();
    
    private int idJoueur;
    private static int lastId; //le dernier id de joueur ajouté
    private int classement = 0; //par défaut classé 0 si ce n'est pas encore défini
    
    /**
     * Constructeur de Joueur avec id donnée
     * @param idJoueur l'id du joueur
     * @param nom Le nom du Joueur
     * @param prenom Le prénom du joueur
     * @param nationalite La nationalité du joueur
     */
    public Joueur(int idJoueur, String nom, String prenom, String nationalite) {
        super(nom, prenom, nationalite);
        this.idJoueur = idJoueur;
        lastId = idJoueur;
    }
    
    /**
     * Constructeur de Joueur avec génération automatique de l'id
     * @param nom Le nom du Joueur
     * @param prenom Le prénom du joueur
     * @param nationalite La nationalité du joueur
     */
    public Joueur(String nom, String prenom, String nationalite) {
        super(nom, prenom, nationalite);
        this.idJoueur = ++lastId;
        lastId = idJoueur;
    }
    
    public int getIdJoueur() {return idJoueur;}
    public int getClassement() {return classement;}
    public static Map<Integer, Joueur> getListeJoueurs() {return listeJoueurs;}
    public void setIdJoueur(int idJoueur) {this.idJoueur = idJoueur;}
    public void setClassement(int classement) {this.classement = classement;}
    
    public String s() {
        return getPrenom()+" "+getNom();
    }
    
    @Override
    public String toString() {
        return "Joueur n°"+this.getIdJoueur()+" : "+this.getPrenom()+" "+this.getNom()+" - "+this.getNationalite()+" - "+this.getClassement();
    }
    
    /**
     * Remet à jour la map contenant tous les joueurs indexés par leur numéro de joueur
     * @param connexion Une connexion à la base
     */
    public static void updateListJoueurs(Connection connexion) {
        try {
            ResultSet rset = ConfigConnexion.executeRequete(connexion,"select idjoueur, nom, prenom, nationalite from JOUEUR order by idjoueur");
            while (rset.next()) {
                listeJoueurs.put(rset.getInt(1), new Joueur(rset.getInt(1),rset.getString(2),rset.getString(3),rset.getString(4)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Permet d'ajouter un joueur à la base de donnée
     * @param connexion Une connexion à la base
     * @throws java.sql.SQLIntegrityConstraintViolationException
     */
    public void ajouterJoueur(Connection connexion) throws SQLIntegrityConstraintViolationException {
        String requete = "Insert into Joueur (idjoueur, nom, prenom, nationalite, classement) Values (?,?,?,?,?)";
        try {
            PreparedStatement prepared = connexion.prepareStatement(requete);
            prepared.setInt(1,this.getIdJoueur());
            prepared.setString(2,this.getNom());
            prepared.setString(3,this.getPrenom());
            prepared.setString(4,this.getNationalite());
            prepared.setInt(5,this.getClassement());
            int result = prepared.executeUpdate();
            System.out.println(result + " ligne(s) insérée(s)");
            updateListJoueurs(connexion);
        } catch (SQLIntegrityConstraintViolationException ex) {
            throw new SQLIntegrityConstraintViolationException();//On gère cette exception sur l'ihm
        } catch (SQLSyntaxErrorException ex) {
            Logger.getLogger(MatchSimple.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MatchSimple.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
