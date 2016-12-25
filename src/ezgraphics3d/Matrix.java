package ezgraphics3d;

/**
 * Matrix Class.
 * @author Ryan Theriot
 */
public class Matrix {

	/**
	 * Translation Matrix
	 * @param x The amount to translate on the x-axis
	 * @param y The amount to translate on the y axis
	 * @param z The amount to translate on the z axis
	 * @return The translation Matrix as float[][] array
	 */
	public static float[][] translateMatrix(float x, float y, float z) {

		float[][] matrix = { { 1, 0, 0, x }, 
				             { 0, 1, 0, y }, 
				             { 0, 0, 1, z }, 
				             { 0, 0, 0, 1 } };
		return matrix;
	}

	/**
	 * Scale Matrix
	 * @param x The amount to scale on the x-axis
	 * @param y The amount to scale on the y-axis
	 * @param z The amount to scale on the z-axis
	 * @return The scale matrix as a float[][] array
	 */
	public static float[][] scaleMatrix(float x, float y, float z) {

		float[][] matrix = { { x, 0, 0, 0 }, 
				             { 0, y, 0, 0 }, 
				             { 0, 0, z, 0 }, 
				             { 0, 0, 0, 1 } };
		return matrix;
	}

	/**
	 * Rotation X Matrix
	 * @param degree The amount to rotate in degrees about the x-axis
	 * @return The rotation matrix as a float[][] array
	 */
	public static float[][] rotateXMatrix(float degree) {

		float rad = (float) Math.toRadians(degree);
		float cosD = (float) Math.cos(rad);
		float sinD = (float) Math.sin(rad);

		float[][] matrix = { { 1, 0, 0, 0 }, 
				             { 0, cosD, -sinD, 0 }, 
				             { 0, sinD, cosD, 0 }, 
				             { 0, 0, 0, 1 } };
		return matrix;
	}

	/**
	 * Rotation Y Matrix
	 * @param degree The amount to rotate in degrees about the y-axis
	 * @return The rotation matrix as a float[][] array
	 */
	public static float[][] rotateYMatrix(float degree) {

		float rad = (float) Math.toRadians(degree);
		float cosD = (float) Math.cos(rad);
		float sinD = (float) Math.sin(rad);

		float[][] matrix = { { cosD, 0, sinD, 0 }, 
				             { 0, 1, 0, 0 }, 
				             { -sinD, 0, cosD, 0 }, 
				             { 0, 0, 0, 1 } };

		return matrix;
	}

	/**
	 * Rotation Z Matrix
	 * @param degree The amount to rotate in degrees about the z-axis
	 * @return The rotation matrix as a float[][] array
	 */
	public static float[][] rotateZMatrix(float degree) {

		float rad = (float) Math.toRadians(degree);
		float cosD = (float) Math.cos(rad);
		float sinD = (float) Math.sin(rad);

		float[][] matrix = { { cosD, -sinD, 0, 0 }, 
				             { sinD, cosD, 0, 0 }, 
				             { 0, 0, 1, 0 }, 
				             { 0, 0, 0, 1 } };

		return matrix;
	}

	/**
	 * Multiply two matrices
	 * @param matrix1 Matrix A
	 * @param matrix2 Matrix B
	 * @return The multiplication of A and B ( A*B )
	 */
	public static float[][] matrixMultiply(float[][] matrix1, float[][] matrix2) {

		int rowsM1 = matrix1.length;
		int colM1 = matrix1[0].length;
		int colM2 = matrix2[0].length;

		float[][] returnMatrix = new float[rowsM1][colM2];

		for (int i = 0; i < rowsM1; i++) {
			for (int j = 0; j < colM2; j++) {
				for (int k = 0; k < colM1; k++) {
					returnMatrix[i][j] = returnMatrix[i][j] + matrix1[i][k] * matrix2[k][j];
				}
			}
		}

		return returnMatrix;
	}
	
	/**
	 * Prints a matrix
	 * @param matrix The matrix to print
	 */
	public static void printMatrix (float[][] matrix) {
		System.out.println();
		for (int i = 0; i < matrix.length; i++) { 
			for (int j = 0; j < matrix[0].length; j++){
				System.out.print((matrix[i][j] + ", "));
			}
			System.out.println();
		}
		System.out.print("------------------------");
	}

}
