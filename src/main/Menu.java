package main;

import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

//import javax.swing.JFrame;

import gfx.GFX;
import objects.PhysObj;

public class Menu extends JPanel {

	static JButton startButton;

	
	public static JButton createButton(){
		System.out.println("Det funkar!!!!!!!!!!!!!!!!!!!!");

		try {
			BufferedImage img = ImageIO.read(GFX.class.getResource("/images/bakGolf2.png"));
			img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
			Icon menu = new ImageIcon(img.getScaledInstance(1280, 720, java.awt.Image.SCALE_SMOOTH));
			
			startButton = new JButton(menu);
			startButton.setOpaque(true);
			//frame.add(startButton);
			
			startButton.addActionListener(new ActionListener(){
	
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("Det funkar");
					startButton.setVisible(false);
				}});
	
			startButton.setVisible(true);
			return startButton;
		
		} catch (IOException e1) {
			System.out.println("Something is wrooong!!");
		}
		return startButton;
	}
}
