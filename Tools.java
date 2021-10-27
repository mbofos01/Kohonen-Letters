import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Tools {
	/**
	 * This function generates random decimal numbers between minus one and one
	 * excluding zero.
	 * 
	 * @return double (0,1]
	 */
	public static double generateRandomWeights() {
		int max = 1, min = 0;
		double ran = 0.0;
		do {
			ran = Math.random() * (max - min) + min;
		} while (ran == 0.0);

		return ran;

	}

	/**
	 * This function creates a text file given an arraylist of strings.
	 * 
	 * @param name String the name of the new file
	 * @param in   Arraylist of string file lines
	 */
	public static void feedFile(String name, ArrayList<String> in) {

		try {
			FileWriter myWriter = new FileWriter(name);
			for (String a : in) {
				myWriter.write(a);
				myWriter.write("\n");
			}
			myWriter.close();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	public static double Euclidean(Node winner, Node random_node) {
		double x_d = winner.getX() - random_node.getX();
		double y_d = winner.getY() - random_node.getY();
		return Math.pow(x_d, 2) + Math.pow(y_d, 2);
	}
}
