package main;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import gfx.GFX;

/*
 * TODO Make main great again
 * Create menu
 * Create new game function
 * Add StatTracking
 * 
 * */
public class Main {
	private static boolean won = false;
	private static int wins = 0, games = 0;
	private static boolean running = false;
	private static Game g;
	private static JFrame frame;
	
	private static GFX gfx;
	private static JButton startButton;
	
	private static int windowWidth = 1280;
	private static int windowHeight = 720;
	
	public static void main(String[] args) {
		frame = new JFrame();
		gfx = new GFX();
		
		createButton();
		createFrame();
		while(true){
			if(running){
				won = g.run();
				games++;
				
				if(won)
					wins++;
			} else {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		//runGame();
		
		/*
		won = g.run();
		games++;
		
		if(won)
			wins++;
		*/
		//showMenu();
	}
	
	public static void runGame() {
		g = new Game(frame);
	}
	
	public static void createFrame() {
		frame.setResizable(false);
		frame.setSize(windowWidth, windowHeight);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Rymdgloff");
		
		frame.setVisible(true);
		frame.add(startButton);
	}
	public static void createButton(){
		System.out.println("Det funkar!!!!!!!!!!!!!!!!!!!!");

		try {
			BufferedImage img = ImageIO.read(GFX.class.getResource("/images/bakGolf2.png"));
			img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
			Icon menu = new ImageIcon(img.getScaledInstance(1280, 720, java.awt.Image.SCALE_SMOOTH));
			
			startButton = new JButton(menu);
			startButton.setOpaque(true);
			
			startButton.addActionListener(new ActionListener(){
	
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("Det funkar");
					startButton.setVisible(false);
					
					
					runGame();
					running = true;
				}});
	
			startButton.setVisible(true);
			frame.add(startButton);
		
		} catch (IOException e1) {
			System.out.println("Something is wrooong!!");
		}
	}
	
}
