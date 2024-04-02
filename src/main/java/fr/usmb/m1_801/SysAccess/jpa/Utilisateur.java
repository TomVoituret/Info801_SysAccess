package fr.usmb.m1_801.SysAccess.jpa;

import jakarta.persistence.*;

/**
 * Entity implementation class for Entity: Utilisateur
 *
 */
@Entity
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nom;
    private String prenom;
    private String type; // Enseignant, PersonnelAdministratif, Etudiant, etc.
    
    public Utilisateur(Long id, String nom, String prenom, String type) {
    	this.nom = nom;
    	this.id = id;
    	this.prenom = prenom;
    	this.type = type;
    }
    
    
	public Utilisateur() {
		// TODO Auto-generated constructor stub
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
    
    // Getters and setters
}

