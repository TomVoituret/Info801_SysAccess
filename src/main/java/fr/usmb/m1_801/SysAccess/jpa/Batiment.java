package fr.usmb.m1_801.SysAccess.jpa;

import java.util.Map;

import jakarta.persistence.*;

@Entity
public class Batiment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nom;
    
    // D'autres informations sur le bâtiment
    
    @ElementCollection
    @CollectionTable(name = "Autorisations_Acces")
    @MapKeyColumn(name = "type_utilisateur")
    @Column(name = "autorisation")
    private Map<String, Boolean> autorisationsAcces; // Autorisations d'accès par type d'utilisateur

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

	public Map<String, Boolean> getAutorisationsAcces() {
		return autorisationsAcces;
	}

	public void setAutorisationsAcces(Map<String, Boolean> autorisationsAcces) {
		this.autorisationsAcces = autorisationsAcces;
	}
    
}
