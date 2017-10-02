package gfx;
import javax.imageio.ImageIO;
import javax.swing.*;
import objects.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.FileSystems;
import java.util.*;
import java.awt.event.MouseEvent;


/*
 * TODO 
 * Add text objects
 * 
 * 
 */
/*
 * Added: 
 * 
 * Aura (Disco woo!)
 * Trail
 */

	


public class GFX extends JPanel
{
	private ArrayList<PhysObj> list = new ArrayList<PhysObj>();
	private ArrayList<Ball> trail = new ArrayList<Ball>();
	private int count;
	private Color aura;
	private BufferedImage lava;
	private BufferedImage cube;
	private BufferedImage background;
	private BufferedImage planetOrange;
	private BufferedImage planetBlue;
	private BufferedImage planetGas;
	private BufferedImage planetPurple;
	private BufferedImage ball;
	private BufferedImage comet;
	private BufferedImage warning;
	
	/**
	 * Constructor, load all needed imagefiles for later use.
	 */
	public GFX() 
	{
		aura = new Color(0,255,0,50);
		count = 0;
		try
		{
		lava = ImageIO.read(GFX.class.getResource("/images/lava-planet.gif"));
		cube = ImageIO.read(GFX.class.getResource("/images/kub.gif"));
		planetBlue = ImageIO.read(GFX.class.getResource("/images/planetBlue.png"));
		planetPurple = ImageIO.read(GFX.class.getResource("/images/planetPurple.png"));
		planetGas = ImageIO.read(GFX.class.getResource("/images/planetGas.png"));
		background = ImageIO.read(GFX.class.getResource("/images/bak.png"));
		planetOrange = ImageIO.read(GFX.class.getResource("/images/planetOrange.png"));
		ball = ImageIO.read(GFX.class.getResource("/images/GolfBall.png"));
		comet = ImageIO.read(GFX.class.getResource("/images/mete.png"));
		warning = ImageIO.read(GFX.class.getResource("/images/Exclamation.png"));
		
		}
		catch(Exception e)
		{
			System.out.println("File Not found");
		}
	}
	/**
	 * Main function repaint() calls for, sorts through all objects and prints them in correct way.
	 */
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		drawBackground(g2);
		drawText(50,50,50,"Rymdgolf", aura, g2);
		drawArrow(g2); 
		if(!list.isEmpty())
		{
			for(PhysObj p : list)
			{
				if(p instanceof Planet)
				{
					drawPlanet(p.getPosX(), p.getPosY(), p.getRadius(), p.getImgURI(), g2);
				}
				else if(p instanceof Ball)
				{
					drawBall(p.getPosX(), p.getPosY(), p.getRadius(), p.getImgURI(), g2);
				}
				else if(p instanceof Comet)
				{
					drawComet(p.getPosX(), p.getPosY(), p.getRadius(),g2);
				}
				else if(p instanceof BlackHole)
				{
					drawBlackHole(p.getPosX(), p.getPosY(), p.getRadius(),g2);
				}
			}
		}
	}
	/**
	 * Updates the current list of PhysObj and calls the repaint() fnc
	 * @param list
	 */
	public void update(ArrayList<PhysObj> list)
	{
		this.list = list;
		repaint();
	}
	/**
	 * Calls function depending on what texture a object is supposed to be drawn with
	 * @param xPos
	 * @param yPos
	 * @param radius
	 * @param color
	 * @param g2
	 */
	private void drawPlanet(double xPos, double yPos, double radius, String color, Graphics2D g2)
	{
		switch(color)
		{
		case "red": 
			{
				drawCircle(xPos, yPos, radius, Color.RED, g2);
				break;
			}
		case "black":
			{	
				drawCircle(xPos, yPos, radius, Color.BLACK, g2);
				break;
			}
		case "blue":
			{
				drawCircle(xPos, yPos, radius, Color.BLUE, g2);
				break;
			}
		case "cyan": 
			{
				drawCircle(xPos, yPos, radius, Color.CYAN, g2);
				break;
			}
		case "lava":
			{
				drawImage(xPos, yPos, radius, lava, g2 );
				break;
			}
		case "cube":
			{
				drawImage(xPos, yPos, radius,cube, g2);
				break;
			}
		case "planetOrange":
			{
				drawImage(xPos, yPos, radius,planetOrange, g2);
				break;
			}
		case "planetBlue":
			{
				drawImage(xPos, yPos, radius,planetBlue, g2);
				break;
			}
		case "planetGas":
			{
			drawImage(xPos, yPos, radius,planetGas, g2);
			break;
			}
		case "planetPurple":
			{
			drawImage(xPos, yPos, radius,planetPurple, g2);
			break;
			}
		case "comet":
		{
			drawCircle(xPos, yPos, radius, Color.RED, g2);
			break;
		}
		
		}
	}
	/**
	 * Draws the main ball
	 * @param xPos
	 * @param yPos
	 * @param radius
	 * @param color
	 * @param g2
	 */
	private void drawBall(double xPos, double yPos, double radius, String color, Graphics2D g2)
	{
		drawImage(xPos, yPos, radius, ball, g2);
		Random r = new Random();
		int colour = r.nextInt(5);
		//For disco mode
		if(count == 4)
		{
			switch(colour)
			{
			case 0:
				{
					aura = new Color(0,255,0,50);
					break;
				}
			case 1:
				{
					aura = new Color(255,0,0,50);
					break;
				}
			case 2:
				{
					aura = new Color(0,0,255,50);
					break;
				}
			case 3:
				{
					aura = new Color(223,230,39,50);
					break;
				}
			case 4:
				{
					aura = new Color(66,194,155,50);
					break;
				}
			case 5:
				{
					aura = new Color(172,66,194,50);
					break;
				}
			}
		}
		g2.setColor(aura);
		g2.fillOval((int)(xPos-radius), (int)(yPos-radius), (int)radius*2, (int)radius*2);
		
		//trail
		for(Ball b : trail)
		{
			drawImage(b.getPosX(), b.getPosY(), b.getRadius(), ball, g2);
		}
		if(count == 5)
		{
			trail.add(new Ball(1, xPos-10, yPos+10, true, "ball"));
			count = 0;
		}
		if(trail.size() > 7)
		{
			trail.remove(1);
		}
		if(xPos > 1280 && yPos > 720)
			drawImage(1260, 680, 20, warning, g2);
		else if(xPos < 0 && yPos < 0)
			drawImage(0, 0, 20, warning, g2);
		else if(xPos < 0 && yPos > 720)
			drawImage(10, 680, 20, warning, g2);
		else if(xPos > 1280 && yPos < 0)
			drawImage(1260, 10, 20, warning, g2);
		else if(xPos > 1280)
			drawImage(1260, yPos, 20, warning, g2);
		else if(yPos > 720)
			drawImage(xPos, 680, 20, warning, g2);
		else if(yPos < 0)
			drawImage(xPos, 20, 20, warning, g2);
		else if(xPos < 0)
			drawImage(10, yPos, 20, warning, g2);
		count++;
	}
	/**
	 * Draws a image
	 * @param xPos
	 * @param yPos
	 * @param radius
	 * @param image
	 * @param g2
	 */
	private void drawImage(double xPos, double yPos, double radius, BufferedImage image, Graphics2D g2)
	{
		if(image == null)
		{
			drawCircle(xPos-radius/2, yPos-radius/2, radius, Color.MAGENTA, g2);
		}
		else
		{
			g2.drawImage(image,(int)(xPos-radius/2), (int)(yPos-radius/2), (int) radius, (int) radius, null);
		}
	}
	/**
	 * Draws the background
	 * @param g2
	 */
	private void drawBackground(Graphics2D g2)
	{
		g2.drawImage(background, 0, 0, 1280, 720, null);
	}
	/**
	 * Draws a singlecoloured circle
	 * @param xPos
	 * @param yPos
	 * @param radius
	 * @param color
	 * @param g2
	 */
	private void drawCircle(double xPos, double yPos, double radius, Color color, Graphics2D g2)
	{
		
		g2.setColor(color);
		g2.fillOval((int)(xPos-radius/2), (int)(yPos-radius/2), (int)radius, (int)radius);
	}
	/**
	 * Draws a comet.
	 * @param xPos
	 * @param yPos
	 * @param radius
	 * @param g2
	 */
	private void drawComet(double xPos, double yPos, double radius, Graphics2D g2)
	{
		g2.drawImage(comet,(int)(xPos-radius/2), (int)(yPos-radius/2), (int) radius, (int) radius, null);
	}
	private void drawText(double xPos, double yPos,int size, String text,Color color,Graphics2D g2)
	{
		g2.setFont(new Font("TimesRoman", Font.PLAIN, size));
		g2.setColor(color);
		g2.drawString(text, (int)xPos,(int) yPos);
	}
	private void drawBlackHole(double xPos, double yPos, double radius, Graphics2D g2)
	{
		drawCircle(xPos, yPos, radius+10, Color.WHITE, g2);
		drawCircle(xPos, yPos, radius, Color.BLACK, g2);		
	}
	private void drawArrow(Graphics2D g2){
		int xEnd = (int) MouseInfo.getPointerInfo().getLocation().getX();
		int yEnd = (int) MouseInfo.getPointerInfo().getLocation().getY();
		double x2 = xEnd * 0.8 ;
		double y2 = yEnd * 0.9 ;
		double x3 = xEnd * 0.9 ;
		double y3 = yEnd * 0.8 ;
		
		
		g2.drawPolygon(new int[]{xEnd, (int) (x2), (int)(x3)}, new int[]{yEnd,(int)(y2),(int)(y3)}, 3);
		
	}
}

