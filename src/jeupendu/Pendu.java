package jeupendu;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JComponent;

/**
 *Dessine l echafaud et l homme pendu
 */
public class Pendu  extends JComponent  {

	private static final long serialVersionUID = 1L;
	private ArrayList<Shape> shapeList = new ArrayList<Shape>();	
	private Queue<Shape> membres = new LinkedList<Shape>();	
	private int x;
	private int y;
	private LineShape body;
	private LineShape leftLeg;
	private LineShape rightLeg;
	private LineShape leftArm;
	private LineShape rightArm;
	private CircleShape head;	

	public  Pendu() {	

	}

	/**
	 * Dessine l échafaud et crée une liste(Queue) contenant les {@code membres} à ajouter plus tard.
	 * @param x1 Point supérior gauche de l image dans X axis
	 * @param y1 Point supérior gauche de l image dans Y axis
	 */
	public void draw(int x1,int y1){
		this.x=x1;
		this.y=y1;

		head=new CircleShape(x-20, y-40, 40, 20);		
		body=new     LineShape(x, y,    x,    y+60);
		leftLeg=new  LineShape(x, y+60, x-20, y+80);
		rightLeg=new LineShape(x, y+60, x+20, y+80);
		leftArm=new  LineShape(x, y+20, x-20, y+20);
		rightArm=new LineShape(x, y+20, x+20, y+20);

		membres.removeAll(membres);
		membres.add(head);//head
		membres.add(body);
		membres.add(leftLeg);//left leg
		membres.add(rightLeg);//right leg
		membres.add(leftArm);//left arm
		membres.add(rightArm);//right arm


		shapeList.add(new LineShape(x-40, y+100, x+80, y+100));
		shapeList.add(new LineShape(x+80, y-100, x+80, y+100));
		shapeList.add(new LineShape(x,    y-100, x+80, y-100));
		shapeList.add(new LineShape(x,    y-100, x   , y-40));
		repaint();
	}
	/**
	 * Rajoute un {@code membre} à la liste de dessins puis le retire la liste {@code membres}
	 * @return {@code membres.isEmpty()?false:true}
	 */
	public boolean addMembre(){		
		shapeList.add(membres.peek());
		membres.poll();
		repaint();		
		return membres.isEmpty()?false:true;				
	}

	public void removePendu(){
		shapeList.remove(head);
		shapeList.remove(body);
		shapeList.remove(leftLeg);
		shapeList.remove(rightLeg);
		shapeList.remove(leftArm);
		shapeList.remove(rightArm);
		repaint();
	}

	public void removeTout(){
		shapeList.removeAll(shapeList);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Shape s : shapeList) {
			s.draw(g);
		}
	}
}
