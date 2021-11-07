import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.tools.Tool;

import graph.SimpleGraph;
import main.FunctionalityExampleMain;

public class Kohonen {

	public static boolean GNUPLOT = false;
	public static boolean JAVA = true;
	public static boolean PYPLOT = true;
	static int DIMENSION;
	static int ITERATIONS;
	static int INPUTS;
	static double RATE;
	static String dst_file;
	static String src_file;
	static String train_file;
	static String test_file;

	/**
	 * This function selects the parameters given by the input file.
	 * 
	 * @param list ArrayList(String) all the parameters
	 */
	public static void handleParameters(ArrayList<String> list) {
		DIMENSION = Integer.parseInt(list.get(0));
		ITERATIONS = Integer.parseInt(list.get(1));
		RATE = Double.parseDouble(list.get(2));
		INPUTS = Integer.parseInt(list.get(3));
		src_file = new String(list.get(4));
		dst_file = new String(list.get(5));
		train_file = new String(list.get(6));
		test_file = new String(list.get(7));

	}

	public static void printArguments() {
		System.out.println(DIMENSION + "x" + DIMENSION + " Kohonen Map");
		System.out.println("Iterations: " + ITERATIONS);

	}

	public static void createClusterFile(Map m) {
		ArrayList<String> cluster = new ArrayList<>();
		for (Node[] ar : m.matrix)
			for (Node n : ar)
				if (n.getLetter() != ' ')
					cluster.add(new String(n.getLetter() + " " + n.getX() + " " + n.getY()));

		// System.out.println("OUT " + cluster.size() + " " + dst_file + "|");
		Tools.writeFile(dst_file, cluster);
		Tools.runPython("map.py", dst_file);
	}

	public static void main(String[] args) {
		String filename = "parameters.txt";
		if (args.length >= 1)
			filename = args[0];
		ArrayList<String> list = Tools.getParameters(filename);
		handleParameters(list);
		printArguments();
		Tools.createTrainAndTestSets(src_file);
		/*******************************************************/
		ArrayList<double[]> train_inputs = new ArrayList<>();
		ArrayList<Character> train_outputs = new ArrayList<>();
		/*******************************************************/
		ArrayList<double[]> test_inputs = new ArrayList<>();
		ArrayList<Character> test_outputs = new ArrayList<>();
		/*******************************************************/
		int TRAIN_LINES = Tools.findLines(train_file);
		for (int i = 0; i < TRAIN_LINES; i++)
			train_inputs.add(new double[INPUTS]);
		Tools.fillData(train_file, train_inputs, train_outputs, INPUTS);
		/*******************************************************/
		int TEST_LINES = Tools.findLines(test_file);
		for (int i = 0; i < TEST_LINES; i++)
			test_inputs.add(new double[INPUTS]);
		Tools.fillData(test_file, test_inputs, test_outputs, INPUTS);
		/*******************************************************/
		Map m = new Map(DIMENSION, INPUTS, ITERATIONS, RATE);
		ArrayList<String> errors = new ArrayList<>();
		for (int epochs = 0; epochs < ITERATIONS; epochs++) {

			System.out.println("Epoch: " + epochs);
			/*******************************************************/
			double train_error = 0;
			for (int inLine = 0; inLine < TRAIN_LINES; inLine++) {
				m.enterNewInput(train_inputs.get(inLine));
				Node winner = m.findWinner();
				winner.setLetter(train_outputs.get(inLine));
				m.updateWeights(winner, train_outputs.get(inLine));

				train_error += winner.errorCalcu(train_outputs.get(inLine));
				// System.out.println(train_error);

			}
			// System.out.println(train_error);
			m.updateSigma(epochs);
			m.updateRate(epochs);

			double test_error = 0;
			for (int inLine = 0; inLine < TEST_LINES; inLine++) {
				m.enterNewInput(test_inputs.get(inLine));
				Node winner = m.findWinner();
				test_error += winner.errorCalcu(test_outputs.get(inLine));

			}
			errors.add(new String(epochs + " " + train_error / TRAIN_LINES + " " + test_error / TEST_LINES));

		}

		Tools.writeFile("results.txt", errors);
		Tools.runPython("error_plot.py", "results.txt");

		if (GNUPLOT)
			createGNUPlot(m);

		if (PYPLOT)
			createClusterFile(m);

		if (JAVA)
			createJavaPlot(m);

		System.out.println("done");

	}

	private static void createGNUPlot(Map m) {
		String[] files = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
				"S", "T", "U", "V", "W", "X", "Y", "Z" };
		for (int i = 0; i < 26; i++) {
			File myObj = new File(files[i]);
			try {
				myObj.delete();
				myObj.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (Node[] ar : m.matrix)
			for (Node n : ar)
				if (n.getLetter() != ' ')
					Tools.appendToFile(new String(n.getLetter() + ""), new String(n.getX() + " " + n.getY()));

	}

	private static void createJavaPlot(Map m) {
		SimpleGraph graph = new SimpleGraph(DIMENSION * 1.2, DIMENSION * 1.2);
		graph.display();

		for (Node[] ar : m.matrix)
			for (Node n : ar)
				if (n.getLetter() != ' ')
					FunctionalityExampleMain.plotPoint(graph, n.getLetter(), n.getX(), n.getY());
		graph.repaint();

	}
}
