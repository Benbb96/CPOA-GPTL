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
    
    private static final String[] LISTENAT = new String[100];
    
    public Personne() {}
    
    public Personne(String nom, String prenom, String nationalite) {
        this.nom = nom;
        this.prenom = prenom;
        this.nationalite = nationalite;
        if (!NatContains(nationalite)) addNationalite(nationalite);
    }
    
    public String getNom() {return nom;}
    public String getPrenom() {return prenom;}
    public String getNationalite() {return nationalite;}
    public void setNom(String nom) {this.nom = nom;}
    public void setPrenom(String prenom) {this.prenom = prenom;}
    public void setNationalite(String nationalite) {this.nationalite = nationalite;}

    public static String[] getLISTENAT() {
        return LISTENAT;
    }
    
    public static void addNationalite(String nat) {
        int i = 0;
        for (String n : LISTENAT) {
            if (n == null) {
                LISTENAT[i] = nat;
                break;
            }
            i++;
        }
    }
    
    public static boolean NatContains(String nat) {
        for (String n : LISTENAT) {
            if (n != null && n.equals(nat)) return true; 
        }
        return false;
    }
    
}
