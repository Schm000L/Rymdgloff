package physics;

import java.util.ArrayList;

import main.Game;
import objects.Ball;
import objects.BlackHole;
import objects.Comet;
import objects.PhysObj;
import objects.Planet;

/**
 * 
 * @author Eric, Erik 
 * This is where all the calculations for the physics will be done.
 * Should take in a list of all the physical objects,
 * and make them affect each other
 */
public class Physics{
	//Exponent of G to compensate for lower mass on planets (add about 10^13) and distances (remove about 10^-3)
	//Is originaly 10^-11
	static double G = (double) (6.7*Math.pow(10, -1)); //Gravitational constant 
	static ArrayList<PhysObj>objects;
	public static double deltaTimez;
	private static int response=0;
	/**
	 * Does physics
	 * @param objects An array of PhysObj:s
	 * @param deltaTime The time since the last update 
	 */
	public static int doPhysics(ArrayList<PhysObj> obj,double deltaTime){
		objects= obj;
		deltaTimez =deltaTime;
		calculateForce();
		updatePosition(deltaTime);
		//return objects;
		return response;
	}
	/**
	 * Calculates net-forces for all objects in an array of "PhysObj"s using the evaluateForceX/Y methods.
	 * @param objects An array of PhysObj:s
	 */
	public static void calculateForce(){
		for (PhysObj mObj : objects){
			if(mObj.isMovable()){
				double netF=0;
				double netFX=0;
				double netFY=0;
				for (PhysObj fObj : objects){
					if(fObj!=mObj){
						netF=evaluateForce(mObj,fObj);
						double distX = fObj.getPosX()-mObj.getPosX();
						double distY = fObj.getPosY()-mObj.getPosY();
						double angle = (double) Math.atan(Math.abs(distY)/Math.abs(distX));
						double tempFX=(double) (netF * Math.cos(angle));
						double tempFY=(double) (netF * Math.sin(angle));
						
						if(distX<0)
							tempFX=-tempFX;
						if(distY<0)
							tempFY=-tempFY;
						netFX+=tempFX;
						netFY+=tempFY;
					}
				}
				mObj.setForceX(netFX);
				mObj.setForceY(netFY);
			}
		}
	}
	/**
	 * Evaluates the force one objects exerts on another object
	 * @param mObj The main objects
	 * @param fObj The object exerting force.
	 * @return The net-force
	 */
	public static double evaluateForce(PhysObj mObj,PhysObj fObj){
		double dist = (double) (Math.pow(fObj.getPosY()-mObj.getPosY(),2)+Math.pow(fObj.getPosX()-mObj.getPosX(),2));
		dist=(double) Math.sqrt(dist);
		double force = (double) ((G*mObj.getMass()*fObj.getMass())/Math.pow(dist,2));
		return force;
	}
	/**
	 * Updates the position for all the objects in an array using leapfrog
	 * @param objects The array of objects
	 * @param deltaTime The time since the last update
	 */
	public static void updatePosition(double deltaTime){
		//TODO the checked list isn't functioning as intended
		//resulting in collisions for comets not getting registered.
		//See if this can be solved.
		for (int i = 0; i < objects.size(); i++) {
		
			if(objects.get(i).isMovable()){
				
				//for x 
				double accX =objects.get(i).getForceX()/objects.get(i).getMass();
				double newVelX = objects.get(i).getVelX() + accX*deltaTime;
				objects.get(i).setVelX(newVelX);
				double newPosX = objects.get(i).getPosX() + objects.get(i).getVelX()*deltaTime;
				objects.get(i).setPosX(newPosX);
				
				//for y
				double accY = objects.get(i).getForceY()/objects.get(i).getMass();
				double newVelY = objects.get(i).getVelY()+ accY*deltaTime;
				objects.get(i).setVelY(newVelY);
				double newPosY = objects.get(i).getPosY() + objects.get(i).getVelY()*deltaTime;
				objects.get(i).setPosY(newPosY);
				
				// Detect collisions 
				for (int j = i+1; j < objects.size(); j++){
						if(hasCollided(i, j))
							handleCollision(i, j);
				}
			}
		}
	}
	/**
	 * Checks if two given PhysObj:s have collided, asuming the objects are circles
	 * @param o1 The first object
	 * @param o2 The second object
	 * @return
	 */
	public static boolean hasCollided(int o1, int o2) {
		double x1 = objects.get(o1).getPosX(); 
		double y1 = objects.get(o1).getPosY(); 
		double r1 = objects.get(o1).getRadius();
		double x2 = objects.get(o2).getPosX(); 
		double y2 = objects.get(o2).getPosY(); 
		double r2 = objects.get(o2).getRadius();
		
		if( Math.pow(x2-x1,2) + Math.pow(y1-y2,2) <= Math.pow(r1/2+r2/2,2)){
			return true;
		}
		else{
			return false; 
		}
				
	}
	/**
	 * Handles collision between two PhysObj:s
	 * @param o1 The first object
	 * @param o2 The first object
	 */
	public static void handleCollision(int o1, int o2) {
		//TODO make game end get triggered by the ball being immovable
		if(objects.get(o1) instanceof BlackHole && objects.get(o2) instanceof Ball ||
				objects.get(o2) instanceof BlackHole && objects.get(o1) instanceof Ball)
			response = 1;
		
		else if(!objects.get(o1).isMovable() || !objects.get(o2).isMovable()){
			objects.get(o1).setMovable(false);
			objects.get(o2).setMovable(false);
			if(objects.get(o2) instanceof Ball || objects.get(o1) instanceof Ball){
				response =-1;
			}
			
		}
		//Always elastic collision 
		else {
			double v_ix1 = objects.get(o1).getVelX(); 
			double v_iy1 = objects.get(o1).getVelY(); 
			double m1 = objects.get(o1).getMass(); 
			
			double v_ix2 = objects.get(o2).getVelX(); 
			double v_iy2 = objects.get(o2).getVelY(); 
			double m2 = objects.get(o2).getMass(); 
			
			//Calculate resulting speeds 
			
			double v_fx1 = ((m1-m2)/(m1+m2)) * v_ix1 + ((2*m2) / (m1+m2)) * v_ix2 ;   
			double v_fy1 = ((m1-m2)/(m1+m2)) * v_iy1 + ((2*m2) / (m1+m2)) * v_iy2 ;   
			double v_fx2 = ((m2-m1)/(m1+m2)) * v_ix2 + ((2*m1) / (m1+m2)) * v_ix1 ;   
			double v_fy2 = ((m2-m1)/(m1+m2)) * v_iy2 + ((2*m1) / (m1+m2)) * v_iy1 ;  
			
			
			 // Junk??
			//double angle1 = Math.atan(v_iy1/v_ix1);
			//double v1 =Math.pow(v_ix1, 2) + Math.pow(v_iy1, 2);
			//v1=Math.sqrt(v1);
			
			//double angle2 = Math.atan(v_iy2/v_ix1);
			//double v2 =Math.pow(v_ix2, 2) + Math.pow(v_iy2, 2);
			//v2=Math.sqrt(v1);
			
			//double angleColl = Math.atan((v_iy2-v_iy1)/(v_ix2-v_ix1));
			/*
			double v_fx1 = (((v1*Math.cos(angle1-angleColl)*(m1-m2) 
					+2*m2*v2*Math.cos(angle2-angleColl))*Math.cos(angleColl))/(m1+m2)) 
					+v1*Math.sin(angle1-angleColl)*Math.cos(angleColl+(Math.PI/2));
			
			double v_fy1 = (((v1*Math.cos(angle1-angleColl)*(m1-m2) 
					+2*m2*v2*Math.cos(angle2-angleColl))*Math.sin(angleColl))/(m1+m2)) 
					+v1*Math.sin(angle1-angleColl)*Math.sin(angleColl+(Math.PI/2));
			
			double v_fx2 =(((v2*Math.cos(angle2-angleColl)*(m2-m1) 
					+2*m1*v1*Math.cos(angle1-angleColl))*Math.cos(angleColl))/(m2+m1)) 
					+v2*Math.sin(angle2-angleColl)*Math.cos(angleColl+(Math.PI/2));
			
			double v_fy2 = (((v2*Math.cos(angle1-angleColl)*(m2-m1) 
					+2*m1*v1*Math.cos(angle1-angleColl))*Math.sin(angleColl))/(m1+m2)) 
					+v2*Math.sin(angle2-angleColl)*Math.sin(angleColl+(Math.PI/2));
			*/
			//Set objects speeds to calculated ones 
			objects.get(o1).setVelX((double)v_fx1); 
			objects.get(o1).setVelY((double)v_fy1); 
			objects.get(o2).setVelX((double)v_fx2); 
			objects.get(o2).setVelY((double)v_fy2); 
			
			objects.get(o1).setPosX(objects.get(o1).getPosX()+v_fx1*deltaTimez);
			objects.get(o2).setPosX(objects.get(o2).getPosX()+v_fx2*deltaTimez);
			objects.get(o1).setPosY(objects.get(o1).getPosY()+v_fy1*deltaTimez);
			objects.get(o2).setPosY(objects.get(o2).getPosY()+v_fy2*deltaTimez);
		}
	}

}
