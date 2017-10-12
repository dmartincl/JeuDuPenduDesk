package jeupendu;

import java.awt.Color;
import java.awt.Font;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
/** 
 * Crée un minuterie dans une nouvelle thread
 * @author dainel martin 
 * @param t nombre de secondes
 */
public class CountDown extends Thread  {
	protected JLabel timer;
	protected Thread th;
	private Integer timeLeft;
	Queue<Integer> queue;

	public CountDown(){

	}	

	/**
	 * @param t nombre de secondes 
	 */
	public CountDown(int t){
		timer=new JLabel();		
		timer.setHorizontalAlignment(SwingConstants.CENTER);
		timer.setFont(new Font("Rockwell", Font.BOLD, 20));	
		timeLeft=new Integer(t);
		queue = new LinkedList<Integer>();
		start();
	}	


	public void run() { 
		Work(timeLeft);  //some code that executes the functionality of the thread 
	} 

	/**
	 * Crée un Queue contenant n secondes avant de les retirer avec 1 seconde de intervale
	 * @param secsleft nombre de secondes
	 */
	public void Work(int secsleft) { 


		for (int i = secsleft; i >= 0; i--){
			queue.add(i);
		}

		while (!queue.isEmpty()) {
			timeLeft=queue.remove();
			if(timeLeft<10){
				timer.setForeground(Color.RED);
				timer.setFont(new Font("Rockwell", Font.BOLD, 30));
			}
			timer.setText(timeLeft.toString()); 	   		
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				queue.removeAll(queue);	   			
			}      
		}	   	
	}
	/**
	 * Vide la Queue causant ainsi arrêt de la boucle et la fin du thread 
	 * @return nombre de secondes restantes lors d l arrêt.
	 */
	public int arret(){
		queue.removeAll(queue);
		return timeLeft;				
	}



}
