package ezgraphics3d;

/**
 * Camera Class.
 * @author Ryan Theriot
 */
public class Camera {

	private Vector cameraLocation;
	private float zNear;
	private float zFar;
	private int FOV;
	private float pitch;
	private float yaw;
	
	private float speed = 0.3f;
	private float turnSpeed = 3f;
	
	/**
	 * Camera Constructor
	 * @param x - X Coordinate location of camera
	 * @param y - Y Coordinate location of camera
	 * @param z - Z Coordinate location of camera
	 * @param yaw - Starting yaw of camera
	 * @param pitch - Starting pitch of camera
	 * @param zNear Z Near Plane
	 * @param zFar Z Far Plane
	 * @param FOV Field of View of camera
	 */
	public Camera (float x, float y, float z, float yaw, float pitch, float zNear, float zFar, int FOV) {
		
		this.cameraLocation = new Vector(x,y,z);
		this.pitch = pitch;
		this.yaw = yaw;
		this.zNear = zNear;
		this.zFar = zFar;
		this.FOV = FOV;
		
	}
	
	/**
	 * Returns the location of the camera as a Vector
	 * @return The location of the camera as a Vector
	 */
	public Vector getCameraLocation() {
		
		float xCoord = -cameraLocation.getX();
		float yCoord = -cameraLocation.getY();
		float zCoord = -cameraLocation.getZ();
		
		Vector cLoc = new Vector(xCoord, yCoord, zCoord);
		
		return cLoc;
	}
	
	/**
	 * Returns the pitch of the camera
	 * @return The camera's pitch
	 */
	public float getPitch() {
		return pitch;	
	}

	/**
	 * Return the yaw of the camera
	 * @return The camera's yaw
	 */
	public float getYaw() {
		return yaw;
	}
	
	/**
	 * Controls:
	 * WASD Key controls players movement
	 * Arrow Keys controls players pitch and yaw
	 * Q Lowers Camera
	 * E Raises Camera
	 */
	public void controlsFPS() {
		if (EZInteraction.wasKeyPressed("VK_LEFT")) {
			yaw += turnSpeed;
		}
		if (EZInteraction.wasKeyPressed("VK_RIGHT")) {
			yaw += -turnSpeed;
		}
		if (EZInteraction.wasKeyPressed("VK_UP")) {
			pitch += turnSpeed;
		}
		if (EZInteraction.wasKeyPressed("VK_DOWN")) {
			pitch += -turnSpeed;
		}
		if (EZInteraction.wasKeyPressed("w")) {
			float x = (float) Math.sin(Math.toRadians(yaw)) * speed;
			float z = (float) Math.cos(Math.toRadians(yaw)) * speed;
			cameraLocation.translate(Matrix.translateMatrix(-x, 0, -z));
		}
		if (EZInteraction.wasKeyPressed("s")) {
			float x = (float) Math.sin(Math.toRadians(yaw)) * speed;
			float z = (float) Math.cos(Math.toRadians(yaw)) * speed;
			cameraLocation.translate(Matrix.translateMatrix(x, 0, z));
		}
		if (EZInteraction.wasKeyPressed("a")) {
			float x = (float) Math.sin(Math.toRadians(yaw) + Math.PI/2) * speed;
			float z = (float) Math.cos(Math.toRadians(yaw) + Math.PI/2) * speed;
			cameraLocation.translate(Matrix.translateMatrix(-x, 0, -z));
		}
		if (EZInteraction.wasKeyPressed("d")) {
			float x = (float) Math.sin(Math.toRadians(yaw) + Math.PI/2) * speed;
			float z = (float) Math.cos(Math.toRadians(yaw) + Math.PI/2) * speed;
			cameraLocation.translate(Matrix.translateMatrix(x, 0, z));
		}
		if (EZInteraction.wasKeyPressed("e")) {
			cameraLocation.translate(Matrix.translateMatrix(0, speed, 0));
		}
		if (EZInteraction.wasKeyPressed("q")) {
			cameraLocation.translate(Matrix.translateMatrix(0, -speed, 0));
		}
		
		//Axis and WireFrame Toggle. Temporary Location
		if (EZInteraction.wasKeyPressed('t')) Renderer.toggleAxis();
		if (EZInteraction.wasKeyPressed('y')) Renderer.toggleWireFrame();
	}
	
	/**
	 * Sets the yaw of the camera
	 * @param yaw The desired yaw in degrees
	 */
	public void setYaw(float yaw) {
		this.yaw = yaw;
	}
	
	/**
	 * Sets the pitch of the camera
	 * @param pitch The desired pitch in degrees
	 */
	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	/**
	 * Getter for Z Near Plane
	 * @return zNear
	 */
	public float getZNear() {
		return zNear;
	}

	/**
	 * Getter for the Z Far plane
	 * @return zFar
	 */
	public float getZFar() {
		return zFar;
	}

	/**
	 * Getter for Field of View
	 * @return Camera's Field of view
	 */
	public int getFOV() {
		return FOV;
	}
	

}
