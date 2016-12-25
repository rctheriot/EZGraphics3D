package ezgraphics3d;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Renderer Class.
 * @author Ryan Theriot
 */
public class Renderer {
	
	/** Boolean for drawing the axis lines */
	private static boolean drawAxis = true;
	/** Boolean for changing rendering to wire frame mode */
	private static boolean wireFrame = false;

	/**
	 * The Render Method
	 * @param meshList The list of meshes to be rendered
	 * @param camera The camera of the scene
	 */
	public void render(ArrayList<Mesh> meshList, Camera camera) {

		/** Draw Axis Lines */
		if (drawAxis) {

			Vector negXAxis = new Vector(-1, 0, 0);
			Vector posXAxis = new Vector(1, 0, 0);
			Vector negYAxis = new Vector(0, -1, 0);
			Vector posYAxis = new Vector(0, 1, 0);
			Vector negZAxis = new Vector(0, 0, -1);
			Vector posZAxis = new Vector(0, 0, 1);

			addAxis(negXAxis, posXAxis, camera, Color.red);
			addAxis(negYAxis, posYAxis, camera, Color.green);
			addAxis(negZAxis, posZAxis, camera, Color.blue);
		}

		/** Adding all the triangles in every mesh to an ArrayList */
		ArrayList<Triangle> triangles = new ArrayList<Triangle>();

		for (int i = 0; i < meshList.size(); i++) {
			Mesh currentMesh = meshList.get(i);
			Color meshColor = currentMesh.getColor();

			for (int j = 0; j < currentMesh.getNumTriangles(); j++) {
				currentMesh.getTriangle(j).setDistanceToCamera(camera);
				currentMesh.getTriangle(j).setColor(meshColor);
				triangles.add(currentMesh.getTriangle(j));
			}
		}

		/** Sorts triangles by distance from camera so closer triangles are drawn last */
		Comparator<Triangle> comp = new Triangle.TriangleDistanceToCameraComparator();
		triangles.sort(comp);

		/** The Matrices that will be used */
		float[][] viewMatrix = viewMatrix(camera);
		float[][] perspectiveMatrix = perspectiveMatrix(camera);
		float[][] viewport = viewportMatrix(camera);
		
		/**
		 * MAIN RENDERING LOOP
		 * Loops through every triangle in the triangle list
		 */
		for (int i = 0; i < triangles.size(); i++) {

			/** 1. VECTORS: Grab each vector from the triangle */
			float[][] v1 = triangles.get(i).getV1().getVectorMatrix();
			float[][] v2 = triangles.get(i).getV2().getVectorMatrix();
			float[][] v3 = triangles.get(i).getV3().getVectorMatrix();

			/**
			 * 2. VIEW MATRIX: Move the vector in relation to the camera position 
			 *    The camera does not move, the world moves around the camera
			 */
			v1 = Matrix.matrixMultiply(viewMatrix, v1);
			v2 = Matrix.matrixMultiply(viewMatrix, v2);
			v3 = Matrix.matrixMultiply(viewMatrix, v3);

			/**
			 * 3. PERSPECTIVE MATRIX: Applies a perspective view to the vector. 
			 *    Things near the camera appear larger. Also sets up the clipping area
			 */
			v1 = Matrix.matrixMultiply(perspectiveMatrix, v1);
			v2 = Matrix.matrixMultiply(perspectiveMatrix, v2);
			v3 = Matrix.matrixMultiply(perspectiveMatrix, v3);

			/** 4. NORMALIZE: The vector is normalized so we can decide if to clip it or not */
			v1 = normalize(v1);
			v2 = normalize(v2);
			v3 = normalize(v3);

			/**
			 *  5. CLIPPING: Check to see if the triangle should be clipped(not rendered). 
			 *    All the vertices need to be out of view for this to fail.
			 */
			
			//Vector cameraLook =  new Vector (viewMatrix[2][0], viewMatrix[2][1], viewMatrix[2][2]);
			//float cullValue = backfaceCull(triangles.get(i).getSurfaceNormal(), cameraLook);
			
			if ( 1 >= 0) {
				
				if (!clip(v1) || !clip(v2) || !clip(v3)  ) {

				/** 6. VIEWPORT MATRIX: Calculates the location ON THE SCREEN that the triangles are located */
				v1 = Matrix.matrixMultiply(viewport, v1);
				v2 = Matrix.matrixMultiply(viewport, v2);
				v3 = Matrix.matrixMultiply(viewport, v3);

				/**
				 * 7. SCREEN LOCATION : Grab the X,Y from the final calculated matrix. 
				 *    These correspond to a pixel location on the screen 
				 */
				int v1X = (int) v1[0][0];
				int v1Y = (int) v1[1][0];
				int v2X = (int) v2[0][0];
				int v2Y = (int) v2[1][0];
				int v3X = (int) v3[0][0];
				int v3Y = (int) v3[1][0];

			    
				/**Color of Triangle**/
				Color color = triangles.get(i).getColor();

				/** 8. Drawing Time */
				if (wireFrame) {
					EZ.addLine(v1X, v1Y, v2X, v2Y, color, 1);
					EZ.addLine(v2X, v2Y, v3X, v3Y, color, 1);
					EZ.addLine(v3X, v3Y, v1X, v1Y, color, 1);

				} else {
					
					//LightValue
					Vector light = new Vector(1, 1, 0);
					float lightValue = backfaceCull(triangles.get(i).getSurfaceNormal().getNormal(), light.getNormal());
					
					//Color Values
					float[] hsbvals = new float[3];
					Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsbvals);
					
					if (lightValue >= 0)
						color = Color.getHSBColor(hsbvals[0], hsbvals[1], lightValue); 
					else 
						color = Color.getHSBColor(hsbvals[0], hsbvals[1], 0.01f);
					
					//Add Polyon
					int[] xp = { v1X, v2X, v3X };
					int[] yp = { v1Y, v2Y, v3Y };
					EZ.addPolygon(xp, yp, color, true);

				}
			}
		}
		}
	}

	/**
	 * Normalize the x, y ,z coordinates with w
	 * @param vector The Vector to be normalized
	 * @return The normalized vectors in an array[][]
	 */
	private float[][] normalize(float[][] vector) {
		
		float xp = vector[0][0];
		float yp = vector[1][0];
		float zp = vector[2][0];
		float wp = vector[3][0];

		float[][] normalizedMatrix = { { xp / wp }, 
				                       { yp / wp }, 
				                       { zp / wp }, 
				                       { wp / wp } };

		return normalizedMatrix;
	}
	
	/**
	 * Perspective Matrix
	 * @param camera Camera to obtain the FOV, zNear, and zFar
	 * @return Calculated Perspective Matrix
	 */
	private float[][] perspectiveMatrix (Camera camera) {
		
		int FOV = camera.getFOV();
		float zNear = camera.getZNear();
		float zFar = camera.getZFar();
		
		float tanHalfFOV = (float) Math.tan(Math.toRadians(FOV / 2));

		float[][] matrix = { { 1 / tanHalfFOV, 0, 0, 0 }, 
				             { 0, 1 / tanHalfFOV, 0, 0 },
				             { 0, 0, -((zFar) / (zFar - zNear)), -( zFar * zNear) / (zFar - zNear) }, 
				             { 0, 0, -1.0f, 0 } };

		return matrix;
	}
	
	/**
	 * The View Matrix
	 * @return The View Matrix
	 */
	private float[][] viewMatrix(Camera camera)
	{
		Vector cLOC = camera.getCameraLocation();
		float pitch = camera.getPitch();
		float yaw = camera.getYaw();
		
		float cosPitch = (float) Math.cos(Math.toRadians(pitch));
	    float sinPitch = (float) Math.sin(Math.toRadians(pitch));
	    float cosYaw = (float) Math.cos(Math.toRadians(yaw));
	    float sinYaw = (float) Math.sin(Math.toRadians(yaw));
	 
	    float[] xaxis = {            cosYaw,         0,           -sinYaw };
	    float[] yaxis = { sinYaw * sinPitch,  cosPitch, cosYaw * sinPitch };
	    float[] zaxis = { sinYaw * cosPitch, -sinPitch, cosPitch * cosYaw };
	    
	    float dotX = (xaxis[0] * -cLOC.getX()) + (xaxis[1] * -cLOC.getY()) + (xaxis[2] * -cLOC.getZ()) ;
	    float dotY = (yaxis[0] * -cLOC.getX()) + (yaxis[1] * -cLOC.getY()) + (yaxis[2] * -cLOC.getZ()) ;
	    float dotZ = (zaxis[0] * -cLOC.getX()) + (zaxis[1] * -cLOC.getY()) + (zaxis[2] * -cLOC.getZ()) ;
	 
	    float [][] viewMatrix = new float[][] {{xaxis[0], xaxis[1], xaxis[2], -dotX},
                                               {yaxis[0], yaxis[1], yaxis[2], -dotY},
                                               {zaxis[0], zaxis[1], zaxis[2], -dotZ},
                                               {       0,        0,        0,      1}};
                                               
	    return viewMatrix;
	    
	}
	
	
	/**
	 * Viewport Matrix
	 * @param camera Camera to obtain zNear and zFar
	 * @return Calculated Viewport Matrix
	 */
	private float[][] viewportMatrix (Camera camera) {
		
		float zNear = camera.getZNear();
		float zFar = camera.getZFar();
		
		float[][] matrix = { { Main.width / 2, 0, 0, Main.width / 2 }, 
				             { 0, -Main.height / 2, 0, Main.height / 2 },
				             { 0, 0, zFar - zNear, zNear }, 
				             { 0, 0, 0, 1 } };

		return matrix;
	}
	
	private float backfaceCull(Vector sN, Vector cN) {
		
	    float dot = ((sN.getX() * cN.getX()) + (sN.getY() * cN.getY()) + (sN.getZ() * cN.getZ()));
	    
		return dot;
	}
	
	/**
	 * Clipping method to see if the normalized vector is within the perspective frustum
	 * @param vertex The vertex to check
	 * @return True if outside frustum. False if inside frustum
	 */
	private boolean clip(float[][] normalizedVertex) {
		
		float x = normalizedVertex[0][0];
		float y = normalizedVertex[1][0];
		float z = normalizedVertex[2][0];
		
		boolean clip = false;
		
		 if ((x <= -1 || x >= 1) || (y <= -1 || y >= 1) || (z <= -1 || z >= 1)) {
			 clip = true;
		 }
		 
		return clip;
	}

	/**
	 * Just a Temporary Method to draw the axis lines
	 */
	private void addAxis(Vector v1, Vector v2, Camera camera, Color color) {

		float[][] v1C = Matrix.matrixMultiply(viewMatrix(camera), v1.getVectorMatrix());
		v1C = Matrix.matrixMultiply(perspectiveMatrix(camera), v1C);
		v1C = normalize(v1C);

		float[][] v2C = Matrix.matrixMultiply(viewMatrix(camera), v2.getVectorMatrix());
		v2C = Matrix.matrixMultiply(perspectiveMatrix(camera), v2C);
		v2C = normalize(v2C);

		if (!clip(v1C) || !clip(v2C)) {
			
			float[][] viewM1 = Matrix.matrixMultiply(viewportMatrix(camera), v1C);
			int v1X = (int) viewM1[0][0];
			int v1Y = (int) viewM1[1][0];

			float[][] viewM2 = Matrix.matrixMultiply(viewportMatrix(camera), v2C);
			int v2X = (int) viewM2[0][0];
			int v2Y = (int) viewM2[1][0];

			EZ.addLine(v1X, v1Y, v1X, v1Y, color, 1);
			EZ.addLine(v2X, v2Y, v1X, v1Y, color, 1);
			
		}
	}
	
	/**
	 * Toggle Wire Frame Mode
	 */
	public static void toggleWireFrame() { 
		if (Renderer.wireFrame == true)  Renderer.wireFrame = false; 
		else  Renderer.wireFrame = true; 
	}
	
	/**
	 * Toggle the XYZ axis at the origin on and off
	 */
	public static void toggleAxis() { 
		if (Renderer.drawAxis == true)  Renderer.drawAxis = false; 
		else  Renderer.drawAxis = true; 
	}
	
}
