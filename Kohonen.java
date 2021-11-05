import java.util.ArrayList;

public class Kohonen {
	public static void main(String[] args) {
		int DIMENSION = 800;
		int ITERATIONS = 5;
		int INPUTS = 16;
		double RATE = 0.5;
		String train_file = "normalized.txt";
		String test_file = "normalized.txt";
		// Tools.createTrainAndTestSets("normalized.txt");
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
		// m.printDetails();
		// m.printAll();
		ArrayList<String> dataResult = new ArrayList<>();
		for (int epochs = 0; epochs < ITERATIONS; epochs++) {

			System.out.println("Epoch: " + epochs);
			double time = System.currentTimeMillis() * 1000;
			System.out.println(time);
			/*******************************************************/

			for (int inLine = 0; inLine < TRAIN_LINES; inLine++) {
				m.enterNewInput(train_inputs.get(inLine));
				Node winner = m.findWinner();
				// winner.printNode();
				m.updateWeights(winner);

			}
			m.updateSigma(epochs);
			m.updateRate(epochs);

			/*******************************************************/
			/*******************************************************/
			/*
			 * for (int inLine = 0; inLine < TEST_LINES; inLine++) {
			 * m.enterNewInput(test_inputs.get(inLine)); Node winner = m.findWinner(); }
			 */
			/*******************************************************/

		}
		for (int inLine = 0; inLine < TEST_LINES; inLine++) {
			m.enterNewInput(test_inputs.get(inLine));
			Node winner = m.findWinner();
			dataResult.add(new String(test_outputs.get(inLine) + " " + winner.getX() + " " + winner.getY()));
		}

		Tools.writeFile("data.txt", dataResult);
		Tools.runPython("map.py", "data.txt");

	}
}