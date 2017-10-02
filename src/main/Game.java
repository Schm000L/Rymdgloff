package main;

import objects.*;
import physics.*;

import java.util.ArrayList;

import java.awt.event.MouseEvent;
import java.awt.MouseInfo;
import java.awt.event.MouseAdapter;

import gfx.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * TODO Create comet...
 * Create goal
 * Create spwn comet function
 * Add javadocs comments
 * Rearrange methods and variables to logical order
 * Add power arrow and rescale start velocity and cap start velocity
 * */
public class Game extends JPanel {
	/**
	 * 
	 */
	
	//Why eclipse why?
	private static final long serialVersionUID = 1L;

	//Graphics
	private GFX gfx = new GFX(); 
	
	
	private boolean listenerActive = true;
	
	private ArrayList<PhysObj> objects = new ArrayList<PhysObj>();
	
	private static boolean gameRunning = false;
		
	//Time
	private static long lastUpdate;
	
	JPanel panel;
	JFrame frame;
	
	/*
	 * Create all the objects that you need.
	 */
	public Game(JFrame frame){
		this.frame = frame;
		createObjects();
	}
	
	
	
	
	/**
	 * Where the magic happens
	 */
	public boolean run(){
		//Create frame
		createFrame();
	    
	    //Timer
		long lastDrawn = System.currentTimeMillis(), diff, deltaT;
		
	    int state = 0;
	    
		//Main loop
	    while(state == 0) {
	    	diff = System.currentTimeMillis() - lastDrawn;
	    	
			if(gameRunning) { //Game is running
				deltaT = System.currentTimeMillis() - lastUpdate;
				lastUpdate = System.currentTimeMillis();
				state = Physics.doPhysics(objects, deltaT*0.0001);
			}	
			
			for (int i = 0; i < objects.size(); i++) {
				if(objects.get(i) instanceof Comet){
					if(!objects.get(i).isMovable()) 
						objects.remove(i); 
				}
			}
			
			if(diff > 17) {
				gfx.update(objects);
				lastDrawn = System.currentTimeMillis();
			}
		}
	    
	    if(state == -1) { //Lose
	    	System.out.println("GAME OVER MAZAFAKKKA!");
	    	return false;
	    } else if(state == 1) { //Win
	    	System.out.println("YOU WON!!!");
	    	return true;
	    } else {
	    	System.out.println("Sumting went wong");
	    }
	    	return false;
	    
	}
	
	//Creates starting objects1280
	public void createObjects() {
		//The ball (player)
		objects.add(new Ball(200, 0, 720, true, "iMage"));
		
		//Create satellite around planet 1
		objects.add(new Comet(200, 1000, -50, true, "red", 30));
		objects.add(new Comet(200, 800, -50, true, "red", 30));
		objects.add(new Comet(200, 1200, -50, true, "red", 30));
		objects.add(new Comet(200, 700, -50, true, "red", 30));
		objects.get(1).setVelY(20);
		objects.get(4).setVelY(20);
		objects.get(2).setVelY(20);
		objects.get(3).setVelY(20);
		objects.add(new Comet(200, 1000, 750, true, "red", 30));
		objects.add(new Comet(200, 300, 750, true, "red", 30));
		objects.add(new Comet(200, 400, 750, true, "red", 30));
		objects.add(new Comet(200, 600, 750, true, "red", 30));
		objects.get(5).setVelY(-200);
		objects.get(6).setVelY(-400);
		objects.get(7).setVelY(-200);
		objects.get(8).setVelY(-250);
		objects.add(new Comet(200, 1300, 200, true, "red", 30));
		objects.add(new Comet(200, 1300, 300, true, "red", 30));
		objects.get(5).setVelX(-200);
		objects.get(6).setVelX(-400);
		objects.get(5).setVelY(-500);
		objects.get(6).setVelY(-700);
		//Planets, Mass 10^9
		for (int i = 0; i < objects.size(); i++) {
			if(objects.get(i) instanceof Comet){
				if(!objects.get(i).isMovable()) 
					objects.remove(i); 
			}
		}
		
		objects.add(new Planet(100000000, 240, 240, false, "planetBlue", 150));
		objects.add(new Planet(100000000, 520, 650, false, "planetOrange", 150));
		objects.add(new Planet(100000000, 620, 250, false, "planetGas", 130));
		objects.add(new Planet(1000000, 970, 200, false, "planetPurple", 80));

		//Create goal
		objects.add(new BlackHole(2100000000, 1180, 600, false, "blackhole", 100));
	}
	
	
	public void createFrame() {
		/*frame = new JFrame();
		frame.setResizable(false);
		frame.setSize(windowWidth, windowHeight);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Rymdgloff");*/
				
		frame.addMouseListener(new MouseAdapter() { 
		    @Override
	          public void mousePressed(MouseEvent me) { 
		        int x=me.getX();
		        int y=me.getY();
		        System.out.println(x+","+y);//these co-ords are relative to the component
		    	
		    	if(listenerActive) {
	            	shootBall(x,y);
	            	lastUpdate = System.currentTimeMillis();
	            	gameRunning = true;
	            	listenerActive = false;
	            			for (int i = 0; i < objects.size(); i++) {
	            				if(objects.get(i) instanceof Comet){
	            					if(!objects.get(i).isMovable()) 
	            						objects.remove(i); 
	            				}
	            			}
	            }
	          } 
	    }); 
		
		frame.add(gfx);
		
	}
	
	public void shootBall(int velX, int velY) {
		objects.get(0).setVelX(velX);
		objects.get(0).setVelY(velY-720);
	}
	
/*	public void CometCollisionBH(){
		
	} */
}
