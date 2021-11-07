public class Node {
	private int x, y;
	public double[] weights;
	double[] counters = new double[26];
	private char letter = ' ';

	public Node(int x, int y, int numberOfInputs) {
		this.x = x;
		this.y = y;
		weights = new double[numberOfInputs];
		for (int i = 0; i < numberOfInputs; i++)
			weights[i] = Tools.generateRandomWeights();
		for (int i = 0; i < 26; i++)
			counters[i] = 0;

	}

	/**
	 * @return the letter
	 */
	public char getLetter() {
		double a = 0;
		char l = ' ';
		for (int i = 0; i < 26; i++)
			if (counters[i] > a) {
				a = counters[i];
				l = (char) ('A' + i);
			}
		return l;
	}

	/**
	 * @param letter the letter to set
	 */
	public void setLetter(char letter) {
		this.letter = letter;
		counters[letter - 'A']++;

	}

	private char[] doubler(double copy[]) {
		char[] letters = new char[26];
		for (int i = 0; i < 26; i++) {
			copy[i] = counters[i];
			letters[i] = (char) ('A' + i);
		}
		return letters;
	}

	public static void insertionSort(double array[], char[] parallel) {
		int size = array.length;
		for (int step = 1; step < size; step++) {
			double key = array[step];
			char k = parallel[step];
			int j = step - 1;
			while (j >= 0 && key > array[j]) {
				// For ascending order, change key> arr[j] to key< arr[j].
				array[j + 1] = array[j];
				parallel[j + 1] = parallel[j];
				--j;
			}
			array[j + 1] = key;
			parallel[j + 1] = k;
		}
	}

	public double errorCalcu(char letter) {
		double[] real = Tools.createExpectedOutputArray(letter);
		if (getLetter() == ' ')
			return 1;
		double[] exp = Tools.createExpectedOutputArray(getLetter());
		// return 0.5 * Math.pow(getLetter() - letter, 2);
		return Tools.error(exp, real);

	}

	public static void main(String[] args) {
		Node n = new Node(1, 1, 1);
		n.setLetter('A');
		System.out.println(n.errorCalcu('B'));
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public double[] getWeights() {
		return weights;
	}

	public void setWeights(double[] weights) {
		this.weights = weights;
	}

	public void printNode() {
		System.out.println("============================================================");
		System.out.println("Place: ( " + x + " , " + y + " )");
		System.out.println("Letter: " + letter);
		System.out.println("\n============================================================");
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		return x == other.x && y == other.y;
	}

}
