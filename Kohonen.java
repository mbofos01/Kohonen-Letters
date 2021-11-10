import java.util.ArrayList;

/**
 * This class is the driver class of our project.
 * 
 * @author Michail Panagiotis Bofos
 *
 */
public class Kohonen {
	private static boolean PYPLOT = true, LVQ = false;
	private static int DIMENSION;
	private static int ITERATIONS;
	private static int INPUTS;
	private static int TEST_LINES, TRAIN_LINES;
	private static double RATE;
	private static String dst_file;
	private static String src_file;
	private static String train_file;
	private static String test_file;
	private static ArrayList<double[]> train_inputs = new ArrayList<>();
	private static ArrayList<Character> train_outputs = new ArrayList<>();
	private static ArrayList<double[]> test_inputs = new ArrayList<>();
	private static ArrayList<Character> test_outputs = new ArrayList<>();

	/**
	 * This function labels each node based on the minimum distance of the testing
	 * set.
	 * 
	 * @param m The SOM we use
	 */
	private static void labelData(Map m) {
		System.out.println("Labeling . . .");
		for (Node[] ar : m.matrix)
			for (Node n : ar) {
				double min = Double.MAX_VALUE;
				int place = -1;
				for (int i = 0; i < TEST_LINES; i++) {
					double dist = 0;
					for (int a = 0; a < test_inputs.get(i).length; a++)
						dist += Math.pow(test_inputs.get(i)[a] - n.weights[a], 2);

					if (dist <= min) {
						min = dist;
						place = i;
					}

				}
				n.setLabel(test_outputs.get(place));
			}
	}

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
		PYPLOT = Boolean.parseBoolean(list.get(8));
		LVQ = Boolean.parseBoolean(list.get(9));

		Tools.createTrainAndTestSets(src_file);
		printArguments();
		TRAIN_LINES = Tools.findLines(train_file);
		for (int i = 0; i < TRAIN_LINES; i++)
			train_inputs.add(new double[INPUTS]);
		Tools.fillData(train_file, train_inputs, train_outputs, INPUTS);
		/*******************************************************/
		TEST_LINES = Tools.findLines(test_file);
		for (int i = 0; i < TEST_LINES; i++)
			test_inputs.add(new double[INPUTS]);
		Tools.fillData(test_file, test_inputs, test_outputs, INPUTS);
		/*******************************************************/
		Tools.deleteFile("results.txt");
		Tools.deleteFile("cluster.txt");
	}

	/**
	 * This function prints some of the arguments given.
	 */
	public static void printArguments() {
		System.out.println("Kohonen Map: " + DIMENSION + "x" + DIMENSION);
		System.out.println("Iterations: " + ITERATIONS);

	}

	/**
	 * This function creates the cluster visualization using pyplot if selected and
	 * a txt file with the clusters by default.
	 * 
	 * @param m The SOM we use
	 * @param b True if we create the LVQ cluster too
	 */
	public static void createClusterFile(Map m, boolean b) {
		ArrayList<String> cluster = new ArrayList<>();
		for (Node[] ar : m.matrix)
			for (Node n : ar)
				cluster.add(new String(n.getLabel() + " " + n.getX() + " " + n.getY()));

		String dump = new String();
		if (b) {
			Tools.writeFile(dst_file, cluster);
			if (PYPLOT)
				Tools.runPython("CreateCluster.py", dst_file, true);
			dump = "data_cluster.txt";
		} else {
			String lvq = "LVQ_";
			String newS = lvq.concat(dst_file);
			Tools.writeFile(newS, cluster);
			if (PYPLOT)
				Tools.runPython("CreateCluster.py", newS, false);
			dump = "LVQ_data_cluster.txt";
		}
		Tools.deleteFile(dump);
		cluster = new ArrayList<>();

		for (int j = m.matrix[0].length - 1; j >= 0; j--) {
			for (int i = 0; i < m.matrix.length; i++)
				Tools.appendToFile(dump, new String(m.matrix[i][j].getLabel() + ""), false);

			Tools.appendToFile(dump, new String(""), true);
		}

	}

	/**
	 * Main function of your project.
	 * 
	 * @param args The parameters file we use
	 */
	public static void main(String[] args) {
		String filename = "parameters.txt";
		if (args.length >= 1)
			filename = args[0];
		ArrayList<String> list = Tools.getParameters(filename);
		handleParameters(list);

		Map m = new Map(DIMENSION, INPUTS, ITERATIONS, RATE);
		m.printDetails();
		ArrayList<String> results = new ArrayList<>();
		for (int epochs = 0; epochs < ITERATIONS; epochs++) {

			System.out.println("Epoch: " + (epochs + 1));
			/*******************************************************/
			for (int inLine = 0; inLine < TRAIN_LINES; inLine++) {
				m.enterNewInput(train_inputs.get(inLine));
				Node winner = m.findWinner();
				m.updateWeights(winner);
				m.addTrainError(winner.getDistance());
			}
			m.updateSigma(epochs);
			m.updateRate(epochs);
			for (int inLine = 0; inLine < TEST_LINES; inLine++) {
				m.enterNewInput(test_inputs.get(inLine));
				Node winner = m.findWinner();
				m.addTestError(winner.getDistance());

			}
			System.out.println(
					new String(epochs + " " + m.getTrainningError(TRAIN_LINES) + " " + m.getTestingError(TEST_LINES)));
			results.add(
					new String(epochs + " " + m.getTrainningError(TRAIN_LINES) + " " + m.getTestingError(TEST_LINES)));
			m.resetErrors();
		}

		Tools.writeFile("results.txt", results);
		labelData(m);
		createClusterFile(m, true);

		if (LVQ) {
			System.out.println("LVQ enabled:");
			m.LVQ(train_inputs, train_outputs);
			labelData(m);
			createClusterFile(m, false);
		}
		if (PYPLOT)
			Tools.runPython("CreateErrorPlot.py", "results.txt", false);
		System.out.println("Process Ended");
	}

}
