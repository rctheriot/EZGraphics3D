package ezgraphics3d;
import java.awt.Color;
import java.util.ArrayList;

/**
 * Mesh Class.
 * Stores a collection of triangles that make up the mesh.
 * @author Ryan Theriot
 */
public class Mesh {
	
	private ArrayList<Triangle> mesh;
	private float centerX, centerY, centerZ;
	private Color meshColor = Color.white;
	
	/**
	 * Constructor Method
	 */
	public Mesh () {
		mesh = new ArrayList<Triangle>();
	}
	
	/**
	 * Adds a triangle to the Mesh
	 * @param triangle The triangle to be added
	 */
	public void addTriangle(Triangle triangle) {
		mesh.add(triangle);
	}

	/**
	 * Rotates the mesh
	 * @param xDeg The X Degree of rotation
	 * @param yDeg The Y Degree of rotation
	 * @param zDeg The Z Degree of rotation
	 */
	public void rotate(float xDeg, float yDeg, float zDeg) {
		
		float[][] xMatrix = Matrix.rotateXMatrix((float)Math.toRadians(xDeg));
		float[][] yMatrix = Matrix.rotateYMatrix((float)Math.toRadians(yDeg));
		float[][] zMatrix = Matrix.rotateZMatrix((float)Math.toRadians(zDeg));
		
		for (int i = 0; i < this.getNumTriangles(); i++) {
			this.getTriangle(i).rotate(xMatrix, centerX, centerY, centerZ);		
			this.getTriangle(i).rotate(yMatrix, centerX, centerY, centerZ);	
			this.getTriangle(i).rotate(zMatrix, centerX, centerY, centerZ);	
		}
	}

	/**
	 * Scales the Mesh
	 * @param x The X scaling
	 * @param y The Y scaling
	 * @param z The Z scaling
	 */
	public void scale (float x, float y, float z) {
		float[][] matrix = Matrix.scaleMatrix(x, y, z);
		
		for (int i = 0; i < this.getNumTriangles(); i++) {
			this.getTriangle(i).scale(matrix, centerX, centerY, centerZ);				
		}
	}

	/**
	 * Translates the Mesh
	 * @param x The X translation
	 * @param y The Y translation
	 * @param z The Z translation
	 */
	public void translate (float x, float y, float z) {
		float [][] matrix = Matrix.translateMatrix(x, y, z);
		
		for (int i = 0; i < this.getNumTriangles(); i++) {
			this.getTriangle(i).translate(matrix);				
		}
		
		calculateCenter();
	}

	/**
	 * Returns the triangles that compose this mesh in an ArrayList
	 * @return An ArrayList of triangles in this Mesh
	 */
	public ArrayList<Triangle> getMesh() {
		return mesh;
	}
	
	/**
	 * Gets the triangle in the Mesh at the specified index
	 * @param index Index of the triangle in the mesh
	 * @return The Triangle at index
	 */
	public Triangle getTriangle(int index) {
		return mesh.get(index);
	}
	
	/**
	 * Get the number of triangles in this mesh
	 * @return The number of triangles in this mesh
	 */
	public int getNumTriangles() {
		return mesh.size();
	}
	
	/**
	 * Getter for mesh's color
	 * @return The mesh's color
	 */
	public Color getColor() {
		return meshColor;
	}

	/**
	 * Setter for mesh's color
	 * @param meshColor Color to set the mesh to
	 */
	public void setColor(Color meshColor) {
		this.meshColor = meshColor;
	}
	
	/**
	 * Sets the center of the mesh to the specified coordinates
	 * @param x - X coordinate
	 * @param y - Y coordinate
	 * @param z - Z coordinate
	 */
	public void setCenter(float x, float y, float z) {
		this.centerX = x; 
		this.centerY = y;
		this.centerZ = z;
	}

	/**
	 * Calculates and sets the center of the mesh
	 */
	private void calculateCenter() {
		
		int numVertices = 3 * this.getNumTriangles();
		float xCoordTotal = 0;
		float yCoordTotal = 0;
		float zCoordTotal = 0;
		
		for (int i = 0; i < this.getNumTriangles(); i++) {
			
			xCoordTotal += this.getTriangle(i).getV1().getX() +
						   this.getTriangle(i).getV2().getX() +
						   this.getTriangle(i).getV3().getX();
			
			yCoordTotal += this.getTriangle(i).getV1().getY() +
						   this.getTriangle(i).getV2().getY() +
						   this.getTriangle(i).getV3().getY();
			
			zCoordTotal += this.getTriangle(i).getV1().getZ() +
						   this.getTriangle(i).getV2().getZ() +
						   this.getTriangle(i).getV3().getZ();
						
		}
		
		this.centerX = xCoordTotal / numVertices;
		this.centerY = yCoordTotal / numVertices;
		this.centerZ = zCoordTotal / numVertices;
		
	}

}
