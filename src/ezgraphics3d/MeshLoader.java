package ezgraphics3d;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * MeshLoader Class
 * Loads meshes/models from files 
 * Right now only supports OBJ files
 * @author Ryan Theriot
 *
 */

public class MeshLoader {

	/**
	 * Loads and OBJ file into a mesh
	 * @param objFileLocation Location of the OBJ file
	 * @return A Mesh of the OBJ file
	 */
	public static Mesh loadOBJFile(String objFileLocation) {
		
		Mesh loadedMesh = new Mesh();
		Triangle newTriangle;
		
		InputStream is = MeshLoader.class.getClassLoader().getResourceAsStream(objFileLocation);
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader reader = new BufferedReader(isr);
		String line;

		ArrayList<Vector> vectors = new ArrayList<Vector>();
		ArrayList<Triangle> triangles = new ArrayList<Triangle>();

		try {
			while ((line = reader.readLine()) != null) {
				String[] tokenize = line.split(" ");

				if (tokenize[0].equals("v")) {

					float x = Float.parseFloat(tokenize[1]);
					float y = Float.parseFloat(tokenize[2]);
					float z = Float.parseFloat(tokenize[3]);
					
					vectors.add(new Vector(x, y, z));
				}

				else if (tokenize[0].equals("f")) {

					String[] trigToken1 = tokenize[1].split("/");
					String[] trigToken2 = tokenize[2].split("/");
					String[] trigToken3 = tokenize[3].split("/");
					
					newTriangle = new Triangle (vectors.get(Integer.parseInt(trigToken1[0]) - 1),
							                    vectors.get(Integer.parseInt(trigToken2[0]) - 1),
							                    vectors.get(Integer.parseInt(trigToken3[0]) - 1));			
					
					triangles.add(newTriangle);
					
				}
			}
		} catch (NumberFormatException e) {
			System.out.println("Exception in MeshLoader: Text file in incorrect format. " + e);

		} catch (IOException e) {
			System.out.println("Exception in MeshLoader: Text file can not be read. " + e);
		}
		
		for (int i = 0; i < triangles.size(); i++) {
			loadedMesh.addTriangle(triangles.get(i));
		}

		try {
			is.close();
			isr.close();
			reader.close();
		} catch (IOException e) {
			System.out.println("Exception in MeshLoader: Failed to close streamers and buffers. " + e);
		}

		return loadedMesh;

	}

}
