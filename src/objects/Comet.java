package objects;



public class Comet extends PhysObj{
	/**
	 * A comet that is movable and flies around.
	 * @param mass Value of the objects mass
	 * @param posX Distance from game origo to center of object in x.
	 * @param posY Distance from game origo to center of object in y.
	 * @param movable Whether the object should be movable.
	 * @param imgURI URI to image representing the object.
	 * @param radius The radius if the object
	 */
	public Comet(double mass, double posX, double posY, boolean movable, String imgURI, double radius) {
		super(mass, posX, posY, movable, imgURI,radius);
	}

}
