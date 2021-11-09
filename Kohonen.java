import java.util.ArrayList;

public class Kohonen {

	public static boolean PYPLOT = true;
	static int DIMENSION;
	static int ITERATIONS;
	static int INPUTS;
	static int TEST_LINES, TRAIN_LINES;
	static double RATE;
	static String dst_file;
	static String src_file;
	static String train_file;
	static String test_file;
	/*******************************************************/
	static ArrayList<double[]> train_inputs = new ArrayList<>();
	static ArrayList<Character> train_outputs = new ArrayList<>();
	/*******************************************************/
	static ArrayList<double[]> test_inputs = new ArrayList<>();
	static ArrayList<Character> test_outputs = new ArrayList<>();

	/*******************************************************/
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

	public static void printArguments() {
		System.out.println("Kohonen Map: " + DIMENSION + "x" + DIMENSION);
		System.out.println("Iterations: " + ITERATIONS);

	}

	public static void createClusterFile(Map m) {
		ArrayList<String> cluster = new ArrayList<>();
		for (Node[] ar : m.matrix)
			for (Node n : ar)
				cluster.add(new String(n.getLabel() + " " + n.getX() + " " + n.getY()));

		Tools.writeFile(dst_file, cluster);
		Tools.runPython("map.py", dst_file);
		String dump = "data_cluster.txt";
		Tools.deleteFile(dump);
		cluster = new ArrayList<>();
		for (int i = m.matrix.length - 1; i >= 0; i--) {
			int j;
			for (j = 0; j < m.matrix[i].length; j++)
				Tools.appendToFile(dump, new String(m.matrix[i][j].getLabel() + ""), false);

			Tools.appendToFile(dump, new String(""), true);
		}
		// System.out.println("OUT " + cluster.size() + " " + dst_file + "|");

	}

	public static void main(String[] args) {
		String filename = "parameters.txt";
		if (args.length >= 1)
			filename = args[0];
		ArrayList<String> list = Tools.getParameters(filename);
		handleParameters(list);

		Map m = new Map(DIMENSION, INPUTS, ITERATIONS, RATE);

		for (int epochs = 0; epochs < ITERATIONS; epochs++) {

			System.out.println("Epoch: " + epochs);
			/*******************************************************/
			for (int inLine = 0; inLine < TRAIN_LINES; inLine++) {
				m.enterNewInput(train_inputs.get(inLine));
				Node winner = m.findWinner();
				m.updateWeights(winner, train_outputs.get(inLine));
				m.addTrainError(winner.getDistance());

			}
			m.updateSigma(epochs);
			m.updateRate(epochs);
			for (int inLine = 0; inLine < TEST_LINES; inLine++) {
				m.enterNewInput(test_inputs.get(inLine));
				Node winner = m.findWinner();
				m.addTestError(winner.getDistance());

			}

			Tools.appendToFile("results.txt",
					new String(epochs + " " + m.getTrainningError(TRAIN_LINES) + " " + m.getTestingError(TEST_LINES)),
					true);
			m.resetErrors();
		}
		labelData(m);

		/// if (PYPLOT)
		Tools.runPython("error_plot.py", "results.txt");

		createClusterFile(m);

	}

}
