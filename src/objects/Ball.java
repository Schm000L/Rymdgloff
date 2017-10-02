package objects;



public class Ball extends PhysObj{
	/**
	 * A ball used to play spacegolf, has a fixed and constant radius
	 * 
	 * @param mass Value of the objects mass
	 * @param posX Distance from game origo to center of object in x.
	 * @param posY Distance from game origo to center of object in y.
	 * @param movable Whether the object should be movable.
	 * @param imgURI URI to image representing the object.
	 */
	public Ball(double mass, double posX, double posY, boolean movable, String imgURI) {
		super(mass, posX+10, posY-10, movable, imgURI, 10);
		
	}

}
