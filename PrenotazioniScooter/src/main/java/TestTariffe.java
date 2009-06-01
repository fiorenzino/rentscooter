

import java.util.HashMap;
import java.util.Map;

public class TestTariffe {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Map<Integer, Float> tariffeA = new HashMap<Integer, Float>();
		tariffeA.put(1, Float.parseFloat("22.5"));
		tariffeA.put(2, Float.parseFloat("50"));
		tariffeA.put(3, Float.parseFloat("60"));
		tariffeA.put(4, Float.parseFloat("82.5"));
		tariffeA.put(5, Float.parseFloat("90"));
		tariffeA.put(7, Float.parseFloat("105"));
		tariffeA.put(30, Float.parseFloat("335"));
		tariffeA.put(0, Float.parseFloat("10"));

		Map<Integer, Float> tariffeB = new HashMap<Integer, Float>();
		tariffeB.put(1, Float.parseFloat("22.5"));
		tariffeB.put(2, Float.parseFloat("50"));
		tariffeB.put(3, Float.parseFloat("60"));
		tariffeB.put(4, Float.parseFloat("82.5"));
		tariffeB.put(5, Float.parseFloat("90"));
		tariffeB.put(7, Float.parseFloat("105"));
		tariffeB.put(30, Float.parseFloat("335"));
		tariffeB.put(0, Float.parseFloat("10"));

		Map<String, Map<Integer, Float>> tariffeList = new HashMap<String, Map<Integer, Float>>();

		tariffeList.put("50A", tariffeA);
		tariffeList.put("50B", tariffeB);

		// System.out.println("3 gg: " + calcola(3, tariffeA));
		//
		// System.out.println("5 gg: " + calcola(5, tariffeA));
		//
		// System.out.println("7 gg: " + calcola(7, tariffeA));

		System.out.println("9 gg: " + calcola(9, tariffeA));

		System.out.println("14 gg: " + calcola(14, tariffeA));

		System.out.println("29 gg: " + calcola(29, tariffeA));

		System.out.println("30 gg: " + calcola(30, tariffeA));

	}

	public static Float calcola(Integer numGiorni, Map<Integer, Float> tariffe) {
		Float result = null;
		Integer[] valori = { 1, 2, 3, 4, 5, 7, 30 };

		for (int i = valori.length - 1; i >= 0; i--) {
			if (numGiorni >= valori[i]) {
				Integer resto = numGiorni - valori[i];
				Float costoMax = tariffe.get(valori[i]);
				Float costoEx = tariffe.get(0);
				result = costoMax + (costoEx * resto);
				return result;
			}
		}
		return result;
	}
}
