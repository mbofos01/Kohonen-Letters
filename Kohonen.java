import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.tools.Tool;

public class Kohonen {
	public static void main(String[] args) {
		int DIMENSION = 100;
		int ITERATIONS = 5;
		int INPUTS = 16;
		double RATE = 0.3;
		String file_dump = "data.txt";
		String train_file = "train.txt";
		String test_file = "test.txt";
		Tools.createTrainAndTestSets("normalized.txt");
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
		ArrayList<String> dataResult = new ArrayList<>();
		for (int epochs = 0; epochs < ITERATIONS; epochs++) {

			System.out.println("Epoch: " + epochs);
			/*******************************************************/

			for (int inLine = 0; inLine < TRAIN_LINES; inLine++) {
				m.enterNewInput(train_inputs.get(inLine));
				Node winner = m.findWinner();
				// winner.printNode();
				m.updateWeights(winner);

			}
			m.updateSigma(epochs);
			m.updateRate(epochs);

		}
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
		for (int inLine = 0; inLine < TEST_LINES; inLine++) {
			m.enterNewInput(test_inputs.get(inLine));
			Node winner = m.findWinner();
			dataResult.add(new String(test_outputs.get(inLine) + " " + winner.getX() + " " + winner.getY()));
			Tools.appendToFile(new String(test_outputs.get(inLine) + ""),
					new String(winner.getX() + " " + winner.getY()));
		}

		Tools.writeFile(file_dump, dataResult);
		Tools.runPython("map.py", file_dump);

	}
}
