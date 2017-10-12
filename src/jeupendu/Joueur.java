package jeupendu;

import java.util.HashSet;
import java.util.Set;

public class Joueur {
	
	private String nom;
	private int points;
	private Set<String>listMembres;
	
	public Joueur(){		
		listMembres=new HashSet<>();
	}
	
	
	public String getNom(){
		return this.nom;
	}
	
	public int getPoints(){
		return this.points;
	}
	
	public void plusOnePoint(){
		++this.points;
	}
	
	public void clearPoints(){
		this.points=0;
	}
	
	public Set<String> getList(){
		return this.listMembres;
	}
	
	public void setNom(String nom){
		this.nom=nom;
	}

}
