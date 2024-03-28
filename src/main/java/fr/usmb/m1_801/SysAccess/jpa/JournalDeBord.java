package fr.usmb.m1_801.SysAccess.jpa;

import java.util.Date;

import jakarta.persistence.*;

@Entity
public class JournalDeBord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date heureEntree;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date heureSortie;
    
    @ManyToOne
    private Utilisateur utilisateur;
    
    @ManyToOne
    private Batiment batiment;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getHeureEntree() {
		return heureEntree;
	}

	public void setHeureEntree(Date heureEntree) {
		this.heureEntree = heureEntree;
	}

	public Date getHeureSortie() {
		return heureSortie;
	}

	public void setHeureSortie(Date heureSortie) {
		this.heureSortie = heureSortie;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public Batiment getBatiment() {
		return batiment;
	}

	public void setBatiment(Batiment batiment) {
		this.batiment = batiment;
	}

}
