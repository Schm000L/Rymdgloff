package objects;


/**
 * 
 * @author eric
 * An object that holds all the variables needed for calculating physics
 */
public abstract class PhysObj{
	private double mass;
	private double posX;
	private double posY;
	private double velX=0;
	private double velY=0;
	private double forceX=0;
	private double forceY=0;
	private boolean movable;
	private String imgURI =null;
	private double radius;
	private int id;
	/**
	 * Object used to represent all the physical objects in the 
	 * game, and keep track of their kinetic values. 
	 * @param mass Value of the objects mass. 
	 * @param posX Distance from game origo to center of object in x. 
	 * @param posY Distance from game origo to center of object in y.
	 * @param movable Whether the object should be movable. 
	 * @param imgURI URI to image representing the object. 
	 * @param radius The radius of the object (all objects are represented by circles). 
	 */
	public PhysObj(double mass,double posX,double posY,boolean movable,String imgURI,double radius){
		this.imgURI=imgURI;
		this.mass=mass;
		this.posX=posX;
		this.posY=posY;
		this.movable=movable;
		this.radius=radius;
		this.id = (int)(Math.random()*1000000000);
	}
	
	public double getMass() {
		return mass;
	}
	public void setMass(double mass) {
		this.mass = mass;
	}
	public double getPosX() {
		return posX;
	}
	public void setPosX(double posX) {
		if(this.movable)
			this.posX = posX;
	}
	public double getPosY() {
		return posY;
	}
	public void setPosY(double posY) {
		if(this.movable)
			this.posY = posY;
	}
	public boolean isMovable() {
		return movable;
	}
	public void setMovable(boolean movable) {
		this.movable=movable;
	}
	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public void setVelX(double velX){
		
		this.velX=velX;
	}
	public double getVelX(){
		return this.velX;
	}
	public void setVelY(double velY){
		
		this.velY=velY;
	}
	public double getVelY(){
		return this.velY;
	}
	public double getForceX() {
		return forceX;
	}

	public void setForceX(double forceX) {
		this.forceX = forceX;
	}

	public double getForceY() {
		return forceY;
	}

	public void setForceY(double forceY) {
		this.forceY = forceY;
	}
	public String getImgURI() {
		return imgURI; 
	}
	public int getId(){
		return this.id;
	}
	@Override
	public boolean equals(Object object)
	{
	    boolean isEqual= false;

	    if (object != null && object instanceof PhysObj)
	    {
	        isEqual = (this.id == ((PhysObj) object).getId());
	    }
	    return isEqual;
	}
	
	@Override
	public int hashCode() {
	    return this.id;
	}

}
