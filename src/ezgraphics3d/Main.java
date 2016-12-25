package ezgraphics3d;

import java.awt.Color;
import java.util.ArrayList;

/**
 * EZGraphics3D
 * 
 * This is the basic setup procedure for EZGraphics3D
 * 1. Initialize a window with EZ. 
 * 2. Setup a new Camera object and Renderer object. 
 * 3. Load up desired OBJ/TEXT Files into new Mesh objects.
 * 4. Add all the newly created meshes to an ArrayList
 * 5. Then start the main rendering loop (Explained Below)
 * 
 * @author Ryan Theriot
 */
public class Main {

	/** Width of Window */
	public static int width = 1200;
	/** Height of Window */
	public static int height = 800;
	/** Window Aspect Ratio */
	public static int aspectRatio = width / height;
	/** Target Frame rate */
	public static int framerate = 30;
	/** Background color of window */
	public static Color backGroundColor = Color.darkGray;
	
	/**
	 * Main Method
	 */
	public static void main(String[] args) {

		/** Create the window with EZ. Change the static variables above instead of setting the parameters below */
		EZ.initialize(width, height);
		EZ.setBackgroundColor(backGroundColor);
		EZ.setFrameRate(framerate);
	
		/** Create the Camera and Renderer */
		Camera camera = new Camera (2, 0, 2, 45, 0, 0.1f, 20, 60);
		Renderer renderer = new Renderer();

		//Head Mesh
		Mesh head = MeshLoader.loadOBJFile("Resources/head.obj");
		head.setColor(Color.WHITE);
		head.setCenter(0, 0, 0);
		head.scale(1, 1, 1);
		head.rotate(0, 0, 0);
		head.translate(0, 0, 0);
		
		//Blockade Runner Mesh
		Mesh blockaderunner = MeshLoader.loadOBJFile("Resources/blockaderunner.obj");
		blockaderunner.setColor(Color.red);
		blockaderunner.setCenter(0, 0, 0);
		blockaderunner.scale(1, 1, 1);
		blockaderunner.rotate(0, 0, 0);
		blockaderunner.translate(0, 3, 0);
		

		//Add all the created meshes to an ArrayList
		//This will be used to pass all the meshes to the Renderer every frame
		ArrayList<Mesh> meshList = new ArrayList<Mesh>();
		meshList.add(head);
		meshList.add(blockaderunner);
		
		/**
		* Main Rendering Loop
		* 1. Move camera (Player Control)
		* 2. Scale, rotate, or translate any meshes 
		*   **ORDER MUST BE SCALE, ROTATE AND THEN TRANSLATE**
		* 3. The screen is cleared with EZ.removeAllEZElements();
		* 4. Use the renderer's render() method to draw all the meshes in the MeshList to the screen
		* 5. Refresh the screen with EZ
		*/
		while (true) {
			
			//Camera Control
			camera.controlsFPS();
			
	
			//Scale, Rotate, and Translate Meshes. **SRT** (Scale, Rotate, Translate)
			head.rotate(0, -40, 0);
			blockaderunner.rotate(0, 45, 0);

			//Clear the screen
			EZ.removeAllEZElements();

			//Render The Image and refresh the screen
			renderer.render(meshList, camera);
			EZ.refreshScreen();

		}
	}
	

}
