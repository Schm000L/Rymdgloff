package gfx;

import javax.swing.*;

import objects.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;

public class FejkMain {

    public static final int width = 1280;
    public static final int height = 720;
	public static void main(String[] args) 
	{   
		ArrayList list = new ArrayList();
		
		PhysObj phys = new Planet(100, 50, 50, false, "red", 20);
		list.add(phys);
		
		GFX gfx = new GFX();
		gfx.update(list);
		JFrame frame = new JFrame("Fösnter");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(width, height);
		frame.add(gfx);

		
		frame.setVisible(true);
		
	}

}
