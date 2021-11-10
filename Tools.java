import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Tools {
	static boolean python = true;

	/**
	 * This function generates random decimal numbers between minus one and one
	 * excluding zero.
	 * 
	 * @return double (0,1]
	 */
	public static double generateRandomWeights() {
		int max = 1, min = 0;
		double ran = 0.0;
		ran = Math.random() * (max - min) + min;

		return ran;

	}

	/**
	 * This function reads a parameter file and fills an arraylist with the data.
	 * 
	 * @param filename parameters file
	 * @return ArrayList(String) containing the file data
	 */
	public static ArrayList<String> getParameters(String filename) {
		ArrayList<String> list = new ArrayList<>();
		try {
			File myObj = new File(filename);
			Scanner myReader = new Scanner(myObj);
			int cnt = 0;
			while (list.size() < findLines(filename) && myReader.hasNextLine()) {
				cnt++;
				String data = myReader.next();
				if (cnt == 2) {
					list.add(data);
					cnt = 0;
				}

				// System.out.println(data);

			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * This function counts the lines of a file.
	 * 
	 * @param filename file we count the lines
	 * @return # of lines
	 */
	public static int findLines(String filename) {
		int cnt = 0;
		try {
			File myObj = new File(filename);
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				cnt++;
				myReader.nextLine();

			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		return cnt;
	}

	/**
	 * This function reads a file and fills two arraylists (of double arrays) with
	 * the training/testing data.
	 * 
	 * @param filename training or testing data file
	 * @param inputs   ArrayList(double[])
	 * @param outputs  ArrayList(double[])
	 */
	public static void fillData(String filename, ArrayList<double[]> inputs, ArrayList<Character> outputs, int ARGS) {
		try {
			File myObj = new File(filename);
			Scanner myReader = new Scanner(myObj);
			int cnt = 0;
			while (myReader.hasNextLine()) {
				String line = myReader.nextLine();
				StringTokenizer tok = new StringTokenizer(line);
				String letter = tok.nextToken();
				// System.out.println(letter);
				outputs.add(letter.charAt(0));
				// System.out.println(letter.charAt(0));
				for (int i = 0; i < ARGS; i++) {
					// System.out.println(Double.parseDouble(tok.nextToken()));
					inputs.get(cnt)[i] = Double.parseDouble(tok.nextToken());
					// System.out.println(inputs.get(cnt)[i]);
				}
				cnt++;
				// System.exit(0);

			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

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

	/**
	 * This function is used to write a few lines to a file.
	 * 
	 * @param filename The name of the file in which we want to write
	 * @param list     The String lines we print to the file
	 */
	public static void writeFile(String filename, ArrayList<String> list) {

		try {
			FileWriter fw = new FileWriter(filename, false);
			for (String s : list) {
				fw.write(s + "\n");// appends the string to the file
			}
			fw.close();
		} catch (IOException ioe) {
			System.err.println("IOException: " + ioe.getMessage());
		}
	}

	public static void deleteFile(String filename) {
		File myObj = new File(filename);
		myObj.delete();

	}

	/**
	 * This function runs a Python script to generate a plot using a text file
	 * 
	 * NOTE: For this function to run successfully your machine ought to have python
	 * and matplot library installed
	 * 
	 * @param filename new name of the source data file
	 */
	public static void runPython(String script, String filename, boolean lvq) {
		int offset = -1;
		if (lvq)
			offset = 1;
		else
			offset = 0;
		if (python) {
			String command = "python3 " + script + " " + filename + " " + offset;
			try {
				Runtime.getRuntime().exec(command);
			} catch (IOException e) {
				System.out.println(
						"Python or pyplot aren't installed on this system.\nNo graphs will be autogenerated.\nCheck files for results");
				python = false;
			}
		}
	}

	/**
	 * This function counts all the appearances of each letter and returns all the
	 * lines in an arraylist.
	 * 
	 * @param alpha    Integer array that contains the appearances of each letter
	 * @param filename The name of the file we use
	 * @return Arraylist of strings - each line is a member of the arraylist
	 */
	public static ArrayList<String> countLetters(int[] alpha, String filename) {
		ArrayList<String> lets = new ArrayList<>();
		try {
			File myObj = new File(filename);
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				lets.add(myReader.nextLine());

			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		for (String a : lets)
			alpha[a.charAt(0) - 'A']++;

		return lets;
	}

	/**
	 * This function creates the training and testing set of the letter recognition
	 * problem. First we count all the letters used ( A 700 B 720 ...) and then we
	 * add 70% of each letter to the training set and the rest 30% to the testing
	 * set (After we shuffle our sets a hundred times in order to create a set we
	 * can use without it becoming biased).
	 * 
	 * @param args The name of the file which contains all of your (Normalized)
	 *             data.
	 */
	public static void createTrainAndTestSets(String args) {
		int[] alpha = new int[26];
		ArrayList<String> lets = countLetters(alpha, args);
		ArrayList<ArrayList<String>> letterlines = new ArrayList<>();
		for (int i = 0; i < 26; i++)
			letterlines.add(new ArrayList<String>());

		Collections.sort(lets);
		for (String a : lets)
			letterlines.get((int) (a.charAt(0) - 'A')).add(a);

		for (int i = 0; i < 26; i++) {
			Collections.shuffle(letterlines.get(i));
		}

		ArrayList<String> train = new ArrayList<>();
		ArrayList<String> test = new ArrayList<>();
		for (int i = 0; i < 26; i++) {
			int j;
			for (j = 0; j < letterlines.get(i).size() * 0.7; j++)
				train.add(letterlines.get(i).get(j));
			for (; j < letterlines.get(i).size(); j++)
				test.add(letterlines.get(i).get(j));
		}
		for (int i = 0; i < 100; i++) {
			Collections.shuffle(train);
			Collections.shuffle(test);
		}
		writeFile("train.txt", train);
		writeFile("test.txt", test);
	}

	public static void appendToFile(String filename, String line, boolean new_line) {

		FileWriter fos = null;
		try {
			fos = new FileWriter(filename, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if (new_line)
				fos.write(new String(line + "\n"));
			else
				fos.write(new String(line));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static double Euclidean(Node winner, Node random_node) {
		double x_d = winner.getX() - random_node.getX();
		double y_d = winner.getY() - random_node.getY();
		return Math.pow(x_d, 2) + Math.pow(y_d, 2);
	}

	/**
	 * This function calculates the error of an output.
	 * 
	 * (0.5*Ó(target - real)^2)
	 * 
	 * @param tpj ArrayList(Double) target outputs
	 * @param opj ArrayList(Double) real outputs
	 * @return double value - error
	 */
	public static double error(double[] tpj, double[] opj) {
		double sum = 0;
		for (int i = 0; i < tpj.length; i++)
			sum += (tpj[i] - opj[i]) * (tpj[i] - opj[i]);

		return 0.5 * sum;

	}

	/**
	 * This function creates an array than represents the output we expect from a
	 * neural network in order to categorize letters.
	 * 
	 * @param letter The character we want to recognize
	 * @return Double array (binary logic)
	 */
	public static double[] createExpectedOutputArray(char letter) {
		double[] dump = new double[26];
		for (int i = 0; i < 26; i++)
			dump[i] = 0;
		dump[(int) (letter - 'A')] = 1;
		return dump;
	}

}
