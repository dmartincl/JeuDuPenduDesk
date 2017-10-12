package jeupendu;

import java.awt.Container;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;





public class MainWindow {

	private JFrame mainWindow;
	private Pendu pendu=new Pendu();
	private Gestionaire gestionaire;	
	private Queue<JLabel> champs;
	private JLabel champ;	
	private JPanel panelCW;
	private char[] alphabet = "abcdefghijklmnopqrstuvwxyzéàçîôûâêèé".toCharArray();
	private List<JButton> keyboard=new ArrayList<JButton>();
	private JPanel panelSouth;
	private JPanel panelNC;
	private JLabel lblPoints;
	private JLabel lblWelcome;
	JCheckBoxMenuItem ajouterChronomtre;
	private Container mainwindowPanel;
	private String nom;
	CountDown chronometre;
	private int timeLeft=60;
	/**
	 * 	
	 *Vérifie la lettre saisie à partir du clavier dans l écran puis augmente le {@code score} de 1 si la lettre se trouve dans le mot.
	 *Rajoute un membre {@code pendu.addMembre()} au dessin si la lettre ne se trouve pas dans le mot.
	 *Réinitialise la partie {@code clearPlate()} s il n en reste plus de membres à ajouter.
	 *Déactive les buttons contenant le lettres déjà saisies. 
	 */
	class ClickKeyboardListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {	

			JButton button=(JButton) e.getSource();	
			button.setEnabled(false);

			newLettre(button.getText());	
			if(!gestionaire.lettreValide(button.getText())){					
				if(!pendu.addMembre()){						
					JOptionPane.showMessageDialog(mainWindow, "Le mot était: "+gestionaire.getMot(), "Tu as perdu!", 0);
					clearPlate();	
				}
			}
			else{
				gestionaire.addPoint();
				lblPoints.setText(gestionaire.score());	
				if(gestionaire.gagnant()){
					clearPlate();	
					JOptionPane.showMessageDialog(mainWindow, "Tu as gagné");
				}
			}			
		}		
	}
	/**
	 * Réinitialise la partie puis garde le score si le nom d utilisateur choisi est le même que le nom actuel.	 
	 */
	class DebuterListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {

			demanderNom();
			if (!lblWelcome.getText().equals("Welcome "+gestionaire.getNom())){
				gestionaire.clearScore();
				lblPoints.setText(gestionaire.score());
			}
			lblWelcome.setText("Welcome "+gestionaire.getNom());	
			clearPlate();
		}

	}
	/**	
	 *Active o déactive la minuterie tout dépendant de l état du {@code JCheckBoxMenuItem}.
	 */
	class SetChronometre implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			JCheckBoxMenuItem menu=(JCheckBoxMenuItem) arg0.getSource();
			if(menu.isSelected()){					
				chronometre=new CountDown(timeLeft);
				chronometre.timer.addPropertyChangeListener(timeOver);
				panelNC.add(chronometre.timer);
			}
			else{
				timeLeft=chronometre.arret();
				panelNC.remove(chronometre.timer);
				panelNC.revalidate();
			}				
		}
	}
	/**
	 * Réinitialise la partie si la minuterie ets rendu à 0.	 
	 */
	class TimeOver implements PropertyChangeListener {

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			JLabel timer=(JLabel) evt.getSource();			
			if(timer.getText().equals("0")){
				JOptionPane.showMessageDialog(mainWindow, "Le mot était: "+gestionaire.getMot(), "Tu as perdu!", 0);
				clearPlate();				
			}

		}
	}

	class ListensToButton extends KeyAdapter{
		@Override
		public void keyTyped(KeyEvent e) {			
			if(e.getID() == KeyEvent.KEY_TYPED)
			{				        	
				for(JButton button:keyboard){				        		
					if(button.getText().equals(String.valueOf(e.getKeyChar()))){
						button.doClick(50);
						button.setEnabled(false);									
					}
				}
			}			
		}			
	}

	class QuitterW extends WindowAdapter{		
		@Override
		public void windowClosing(WindowEvent arg0) {
			quitter();
		}
	}

	class QuitterM implements ActionListener{	
		@Override
		public void actionPerformed(ActionEvent e) {
			quitter();			
		}
	}

	private QuitterM quitterM=new QuitterM();
	private QuitterW quitterW=new QuitterW();
	private ListensToButton listensToButton=new ListensToButton();
	private TimeOver timeOver= new TimeOver();
	private SetChronometre setChronometre=new SetChronometre();
	private ClickKeyboardListener cK=new ClickKeyboardListener();
	private DebuterListener debuterListener=new DebuterListener();



	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {					
					MainWindow window = new MainWindow();
					window.mainWindow.setVisible(true);					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Invite l utilisateur à saisir un nom puis le valide. 
	 */

	public void quitter(){
		int n = JOptionPane.showConfirmDialog(
				mainWindow, "Voulez-vous quitter?","Quitter",JOptionPane.YES_NO_OPTION);
		if (n == JOptionPane.YES_OPTION) {
			System.exit(0);
		} else if (n == JOptionPane.NO_OPTION) {
			return;
		}
	}
	public void demanderNom(){
		nom=JOptionPane.showInputDialog(mainWindow, "Tapez votre nom");	

		while(!gestionaire.setNom(nom)){			
			JOptionPane.showMessageDialog(mainWindow, "Le nom doit commencer par au moins une lettre et ne doit pas contenir de caractères spéciaux");		
			nom=JOptionPane.showInputDialog(mainWindow, "Tapez votre nom");
		}
	}
	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();	

	}


	/**
	 * Clean the house
	 */
	public void clearPlate(){
		newLettre("none");
		setKeyboard();
		drawPendu();	
		if(ajouterChronomtre.isSelected())	{
			clearChronometre();
		}
	}

	public void clearChronometre(){
		chronometre.timer.setText("X");
		chronometre.arret();
		panelNC.remove(chronometre.timer);
		panelNC.revalidate();
		ajouterChronomtre.setSelected(false);
		timeLeft=60;	
	}
	/**
	 * Vide le paneau contenant le mot à déviner ainsi que les régles du jeu
	 * puis le rempli à nouveau avec le mot à déviner et les régles du jeu.
	 * @param lettre Lettre saisie
	 */
	public void newLettre(String lettre){
		champs=gestionaire.motChoisi(lettre);						
		panelCW.removeAll();
		panelCW.revalidate();	
		panelCW.repaint();
		while(!champs.isEmpty()){
			champ=champs.poll();							
			panelCW.add(champ, "cell 0 1");			
		}			
		JLabel lblRegles = new JLabel("<html><h2>Les régles du pendu</h2>Trouvez le plus rapidement possible le mot de "+gestionaire.getMot().length()+" lettres<html>");
		lblRegles.setFont(new Font("Rockwell", Font.BOLD, 11));
		panelCW.add(lblRegles, "cell 0 0");		
	}

	public void setKeyboard(){		
		panelSouth.removeAll();
		panelSouth.revalidate();
		panelSouth.repaint();
		keyboard.removeAll(keyboard);
		for(char c: alphabet){
			keyboard.add(new JButton(String.valueOf(c)));					
		}		
		for(JButton button:keyboard){			
			button.addActionListener(cK);
			button.addKeyListener(listensToButton);
			panelSouth.add(button);
		}		
	}

	public void drawPendu(){
		Rectangle r=mainWindow.getBounds();

		pendu.removeTout();		
		pendu.setBounds(0, 0, r.width, r.height);		
		mainwindowPanel.add(pendu);			
		pendu.draw(r.width-120,(int)r.height/3);			
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		gestionaire=new Gestionaire();

		mainWindow = new JFrame();
		mainWindow.setResizable(false);
		mainWindow.setTitle("Jeu du pendu");
		mainWindow.setBounds(100, 100, 641, 461);
		mainWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);			
		mainWindow.getContentPane().setLayout(null);
		mainwindowPanel = mainWindow.getContentPane();	

		demanderNom();		

		panelNC = new JPanel();
		panelNC.setBounds(272, 0, 108, 45);
		mainWindow.getContentPane().add(panelNC);






		//creation de jpanels
		JPanel panelNW = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelNW.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panelNW.setBounds(0, 0, 275, 45);
		mainWindow.getContentPane().add(panelNW);

		lblWelcome = new JLabel("Welcome "+gestionaire.getNom());
		lblWelcome.setFont(new Font("Rockwell", Font.BOLD, 20));
		panelNW.add(lblWelcome);

		JPanel panelNE = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panelNE.getLayout();
		flowLayout_1.setAlignment(FlowLayout.RIGHT);
		panelNE.setBounds(379, 0, 244, 45);
		mainWindow.getContentPane().add(panelNE);

		lblPoints = new JLabel("Points: 0");
		lblPoints.setFont(new Font("Rockwell", Font.BOLD, 20));
		panelNE.add(lblPoints);

		panelCW = new JPanel();
		panelCW.setBounds(0, 44, 468, 247);
		mainWindow.getContentPane().add(panelCW);
		panelCW.setLayout(new MigLayout("", "[grow,center]", "[grow,top][grow,top]"));				

		newLettre("none");//Mot a deviner	in panelCW	


		//creation du keyboard	
		panelSouth = new JPanel();		
		panelSouth.setBounds(0, 289, 623, 116);		
		mainwindowPanel.add(panelSouth);
		setKeyboard();				





		//listeners


		mainWindow.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {					
				drawPendu();								
			}
		});


		mainWindow.addWindowListener(quitterW);



		/*Custom dispatcher
				class KeyDispatcher implements KeyEventDispatcher {
				    public boolean dispatchKeyEvent(KeyEvent e) {
				        if(e.getID() == KeyEvent.KEY_TYPED && )
				        {				        	
				        	for(JButton button:keyboard){				        		
								if(button.getText().equals(String.valueOf(e.getKeyChar()))){
									button.doClick(50);
									button.setEnabled(false);									
								}
							}
				        }

				        //Allow the event to be redispatched
				        return false;
				    }
				}
		//Hijack the keyboard manager
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher( new KeyDispatcher() );*/



		//Menus

		JMenuBar menuBar = new JMenuBar();
		mainWindow.setJMenuBar(menuBar);

		JMenu mnFichier = new JMenu("Fichier");
		menuBar.add(mnFichier);

		JMenuItem DbuterLeJeu = new JMenuItem("D\u00E9buter le jeu");
		DbuterLeJeu.addActionListener(debuterListener);
		mnFichier.add(DbuterLeJeu);

		ajouterChronomtre = new JCheckBoxMenuItem("Ajouter chronom\u00E8tre");
		ajouterChronomtre.addActionListener(setChronometre);
		mnFichier.add(ajouterChronomtre);

		JMenuItem Quitter = new JMenuItem("Quitter");
		Quitter.addActionListener(quitterM);
		mnFichier.add(Quitter);

		JMenuItem mntmProposDe = new JMenuItem("\u00C0 propos de");
		mntmProposDe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(mainWindow, "Jeu du Pendu", "À propos de", 1);
			}
		});
		mnFichier.add(mntmProposDe);	

	}
}
