/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metiers;

/**
 *
 * @author Ben
 */
public abstract class Personne {
    
    private String nom;
    private String prenom;
    private String nationalite;
    
    public Personne(String nom, String prenom, String nationalite) {
        this.nom = nom;
        this.prenom = prenom;
        this.nationalite = nationalite;
    }
    
    public String getNom() {return nom;}
    public String getPrenom() {return prenom;}
    public String getNationalite() {return nationalite;}
    public void setNom(String nom) {this.nom = nom;}
    public void setPrenom(String prenom) {this.prenom = prenom;}
    public void setNationalite(String nationalite) {this.nationalite = nationalite;}
}
