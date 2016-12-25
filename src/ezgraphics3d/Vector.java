package ezgraphics3d;

/**
 * Vector Class.
 * @author Ryan Theriot
 *
 */
public class Vector {

	private float x, y, z, w;

	/**
	 * Constructor Class
	 * @param x - The X Coordinate
	 * @param y - The Y Coordinate
	 * @param z - The Z Coordinate
	 */
	public Vector(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = 1;
	}

	/**
	 * Returns the vector's coordinates in a 4x1 Array
	 * Allows for easy multiplication in the Matrx.matrixMultiply() method
	 * @return The vector coordinates in a 4x1 array
	 */
	public float[][] getVectorMatrix() {
		float[][] vertexArray = { { x }, { y }, { z }, { w } };

		return vertexArray;
	}

	/**
	 * Rotates the vector.
	 * The vector is first 'translated' to around the origin before the rotation is applied
	 * Should not use. Rotate the mesh this vertex is apart of instead
	 * @param matrix The rotation matrix 
	 * @param meshCenterX The X center of the mesh this vector is apart of
	 * @param meshCenterY The Y center of the mesh this vector is apart of
	 * @param meshCenterZ The Z center of the mesh this vector is apart of
	 */
	public void rotate(float[][] matrix, float meshCenterX, float meshCenterY, float meshCenterZ) {
	
		float xt = this.x - meshCenterX;
		float yt = this.y - meshCenterY;
		float zt = this.z - meshCenterZ;
	
		float[][] centerdVertex = { { xt }, { yt }, { zt }, { this.w } };
		float[][] newValues = Matrix.matrixMultiply(matrix, centerdVertex);
	
		this.x = newValues[0][0] + meshCenterX;
		this.y = newValues[1][0] + meshCenterY;
		this.z = newValues[2][0] + meshCenterZ;
		this.w = newValues[3][0];
	}

	/**
	 * Scales the vector.
	 * The vector is first 'translated' to around the origin before the scale is applied
	 * Should not use. Scale the mesh this vertex is apart of instead
	 * @param matrix The rotation matrix 
	 * @param meshCenterX The X center of the mesh this vector is apart of
	 * @param meshCenterY The Y center of the mesh this vector is apart of
	 * @param meshCenterZ The Z center of the mesh this vector is apart of
	 */
	public void scale(float[][] matrix, float meshCenterX, float meshCenterY, float meshCenterZ) {
	
		float xt = this.x - meshCenterX;
		float yt = this.y - meshCenterY;
		float zt = this.z - meshCenterZ;
	
		float[][] centerdVertex = { { xt }, { yt }, { zt }, { this.w } };
		float[][] newValues = Matrix.matrixMultiply(matrix, centerdVertex);
	
		this.x = newValues[0][0] + meshCenterX;
		this.y = newValues[1][0] + meshCenterY;
		this.z = newValues[2][0] + meshCenterZ;
		this.w = newValues[3][0];
	
	}
	
	/**
	 * Translate the vector.
	 * @param matrix The translation matrix to apply
	 */
	public void translate(float[][] matrix) {
		float[][] newValues = Matrix.matrixMultiply(matrix, this.getVectorMatrix());
		this.x = newValues[0][0];
		this.y = newValues[1][0];
		this.z = newValues[2][0];
		this.w = newValues[3][0];
	}

	public Vector getNormal() {
		float magnitude = (float) Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2) + Math.pow(this.z, 2));
		Vector normal = new Vector (this.x / magnitude, this.y / magnitude, this.z / magnitude);
		
		return normal;
	}
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public float getW() {
		return w;
	}

	public void setW(float w) {
		this.w = w;
	}

}
