package ezgraphics3d;

import java.awt.Color;
import java.util.Comparator;

/**
 * Triangle Class. 
 * 3 Vectors makes up a Triangle.
 * @author Ryan Theriot
 */

public class Triangle {

	private Vector v1, v2, v3;
	private Vector surfaceNormal;
	private float distanceToCamera;
	private Color color;

	/**
	 * Constructor Class
	 * @param v1 - Vector 1
	 * @param v2 - Vector 2
	 * @param v3 - Vector 3
	 */
	public Triangle(Vector v1, Vector v2, Vector v3) {
		this.v1 = new Vector(v1.getX(), v1.getY(), v1.getZ());
		this.v2 = new Vector(v2.getX(), v2.getY(), v2.getZ());
		this.v3 = new Vector(v3.getX(), v3.getY(), v3.getZ());
		surfaceNormal = new Vector(0,0,0);
	}

	/**
	 * Returns this triangles vectors in an array
	 * @return The vectors in this triangle in an array
	 */
	public Vector[] getVectors() {
		Vector[] vertexArray = { v1, v2, v3 };
		return vertexArray;
	}
	
	public Vector getSurfaceNormal () {

		Vector U = new Vector (v2.getX() - v1.getX(), v2.getY() - v1.getY(), v2.getZ() - v1.getZ());
		Vector V = new Vector (v3.getX() - v1.getX(), v3.getY() - v1.getY(), v3.getZ() - v1.getZ());
		
		this.surfaceNormal.setX((U.getY() * V.getZ()) - ( U.getZ() * V.getY()));
		this.surfaceNormal.setY((U.getZ() * V.getX()) - ( U.getX() * V.getZ()));   
		this.surfaceNormal.setZ((U.getX() * V.getY()) - ( U.getY() * V.getX()));   

		return this.surfaceNormal;
		
	}
	
	/**
	 * Rotates the triangle
	 * Do use this class directly, should use the scale method on the Mesh this triangle is apart of 
	 * @param matrix The Rotation Matrix
	 * @param meshCX The X-Coordinate for the center of the mesh this Triangle is apart of
	 * @param meshCY The Y-Coordinate for the center of the mesh this Triangle is apart of
	 * @param meshCZ The Z-Coordinate for the center of the mesh this Triangle is apart of
	 */
	public void rotate(float[][] matrix, float meshCX, float meshCY, float meshCZ) {
		v1.rotate(matrix, meshCX, meshCY, meshCZ);
		v2.rotate(matrix, meshCX, meshCY, meshCZ);
		v3.rotate(matrix, meshCX, meshCY, meshCZ);
	}
	
	/**
	 * Scales the triangle
	 * Do use this class directly, should use the scale method on the Mesh this triangle is apart of 
	 * @param matrix The Scale Matrix
	 * @param meshCX The X-Coordinate for the center of the mesh this Triangle is apart of
	 * @param meshCY The Y-Coordinate for the center of the mesh this Triangle is apart of
	 * @param meshCZ The Z-Coordinate for the center of the mesh this Triangle is apart of
	 */
	public void scale(float[][] matrix, float meshCX, float meshCY, float meshCZ) {
		v1.scale(matrix, meshCX, meshCY, meshCZ);
		v2.scale(matrix, meshCX, meshCY, meshCZ);
		v3.scale(matrix, meshCX, meshCY, meshCZ);
	}

	/**
	 * Translates the triangle
	 * Do use this class directly, should use the scale method on the Mesh this triangle is apart of 
	 * @param matrix The Translation Matrix
	 */
	public void translate(float[][] matrix) {
		v1.translate(matrix);
		v2.translate(matrix);
		v3.translate(matrix);
	}

	/**
	 *  TEMPORARY Method, this is not the correct way to calculate the distance from a point to a triangle
	 *  BUT it does work for the time being
	 *  @param camera The Camera to obtain it's location
	 */
	public void setDistanceToCamera(Camera camera) {
		
		float xAvg = (v1.getX() + v2.getX() + v3.getX() ) / 3 ;
		float yAvg = (v1.getY() + v2.getY() + v3.getY() ) / 3 ;
		float zAvg = (v1.getZ() + v2.getZ() + v3.getZ() ) / 3 ;
		
		float cX = camera.getCameraLocation().getX();
		float cY = camera.getCameraLocation().getY();
		float cZ = camera.getCameraLocation().getZ();
		
		this.distanceToCamera = (float) Math.sqrt(Math.pow((xAvg - cX), 2) +  Math.pow((yAvg - cY), 2) + Math.pow((zAvg - cZ), 2) );
		
	}
	
	public float getDistanceToCamera () {
		return distanceToCamera;
	}
	
	public Vector getV1() {
		return v1;
	}

	public void setV1(Vector v1) {
		this.v1 = v1;
	}

	public Vector getV2() {
		return v2;
	}

	public void setV2(Vector v2) {
		this.v2 = v2;
	}

	public Vector getV3() {
		return v3;
	}

	public void setV3(Vector v3) {
		this.v3 = v3;
	}
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	/**
	 * Comparator for calculating which triangles are closer to camera
	 */
	public static class TriangleDistanceToCameraComparator implements Comparator<Triangle> {

		@Override
		/**
		 * Compare method for the comparator
		 * @param t1 The first triangle
		 * @param t2 The second triangle
		 * @return Returns -1 t1 is closer to camera. 
		 * Return 1 if t2 is closer to camera. 
		 * Returns 0 if they are the same distance.
		 */
		public int compare(Triangle t1, Triangle t2) {
			if (t1.getDistanceToCamera() < t2.getDistanceToCamera()) return -1;
			else if (t1.getDistanceToCamera() > t2.getDistanceToCamera()) return 1;
			return 0;
		}

	}


}
