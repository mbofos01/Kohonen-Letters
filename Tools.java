import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

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

	/**
	 * This function counts all the appearances of each letter and returns all the
	 * lines in an arraylist.
	 * 
	 * @param alpha    Integer array that contains the appearances of each letter
	 * @param filename The name of the file we use
	 * @return Arraylist of strings - each line is a member of the arraylist
	 */
	public static ArrayList<String> countLetters(int[] alpha, String filename) {
		int cnt = 0;
		ArrayList<String> lets = new ArrayList<>();
		try {
			File myObj = new File(filename);
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				cnt++;
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

	public static double Euclidean(Node winner, Node random_node) {
		double x_d = winner.getX() - random_node.getX();
		double y_d = winner.getY() - random_node.getY();
		return Math.pow(x_d, 2) + Math.pow(y_d, 2);
	}
}
