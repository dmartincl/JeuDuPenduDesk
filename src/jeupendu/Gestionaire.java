package jeupendu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Pattern;

import javax.swing.JLabel;
/**
 * Établit des liens entre la GUI et la classe Joueur.
 * Génère le mot à découvrir ainsi qu une liste de Jlabels pour chaque lettre
 * @author Dainel Martin 
 */
public class Gestionaire {

	private List<String> listeMots;	
	private Joueur joueur;	
	private String mot;
	private List<String>lettresValides;
	private int compteur;


	public Gestionaire(){
		listeMots=new ArrayList<String>();
		listeMots.add("quotidiennement");
		listeMots.add("rapidement");
		listeMots.add("sélectionnés ");
		listeMots.add("dictionnaire");
		listeMots.add("quotidiennement");
		listeMots.add("chronomètre");
		listeMots.add("principalement");
		listeMots.add("portugais");
		listeMots.add("slovène");
		listeMots.add("graphiques");
		lettresValides=new ArrayList<String>();		
		joueur=new Joueur();
	}
	/**
	 * 
	 * @return Un mot au hazard
	 */
	public String choisirMot(){
		Collections.shuffle(listeMots);		
		return listeMots.get(0);
	}

	public String getMot(){
		return this.mot;
	}
	/**
	 * 
	 * @param lettreSaisie Lettre Saisie par l utilisateur.
	 * @return Vrai si la lettre saisie se trouve dans le Mot.
	 */
	public boolean lettreValide(String lettreSaisie){
		char[] motChoisi=mot.toCharArray();
		boolean valide=false;
		for(char c:motChoisi){
			String lettre=String.valueOf(c);				
			if(lettreSaisie.equals(lettre)){
				valide=true;;
			}			
		}
		return valide;			
	}
	/**
	 * Génère une liste de Jlabels pour chaque lettre.
	 * Incrémente de 1 {@code compteur} chaque fois que l utilisateur choisi une lettre contenu dans le mot.
	 * @param lettreSaisie Lettre Saisie par l utilisateur.Si égal none, choisit un nouveau mot.
	 * @return Liste(Queue) de Jlabels.
	 */
	public Queue<JLabel> motChoisi(String lettreSaisie){

		if(lettreSaisie=="none"){
			mot=choisirMot();
			lettresValides.removeAll(lettresValides);			
		}		

		Queue<JLabel> champs=new LinkedList<>();		
		char[] motChoisi=mot.toCharArray();
		JLabel temp;
		Dimension tailleChampMotChoisi=new Dimension(20, 20);		

		for(char c:motChoisi){
			String lettre=String.valueOf(c);	

			if(lettreSaisie.equals(lettre)){
				champs.add(temp=new JLabel(lettre));
				lettresValides.add(lettreSaisie);	
				temp.setForeground(Color.BLACK);				
				compteur++;				
			}
			else if(lettresValides.indexOf(lettre)!=-1){
				champs.add(temp=new JLabel(lettre));
				temp.setForeground(Color.BLUE);

			}
			else{
				champs.add(temp=new JLabel("_"));
				temp.setForeground(Color.RED);
			}
			temp.setFont(new Font("Rockwell", Font.BOLD, 20));
			temp.setPreferredSize(tailleChampMotChoisi);			
			//temp.setColumns(2);
		}
		return champs;		
	}

	public String getNom(){
		return joueur.getNom();
	}

	public boolean setNom(String nom){		
		boolean valide = Pattern.matches("(?mi)^[a-z]+[a-z1-9]*", nom.trim());
		if(valide){
			joueur.setNom(nom.trim());
		}		
		return valide;
	}

	public void addPoint(){
		joueur.plusOnePoint();
	}

	public String score(){
		return String.format("Points: %d", joueur.getPoints());
	}

	public void clearScore(){
		joueur.clearPoints();
	}
	/**
	 * 
	 * @return Vrai si le compteur est égal a la taille du Mot.
	 */
	public boolean gagnant(){	
		if(compteur==mot.length()){
			compteur=0;
			return true;
		}
		else{return false;}		
	}


}
