public class Kohonen {
	public static void main(String[] args) {
		Map m = new Map(300, 2, 300, 0.5);
		m.printDetails();
		// m.printAll();
		m.updateSigma(100);
		m.updateWeights(new Node(1, 1, 0));

	}
}