import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Through the Desert - A great example question for Binary Search The Answer
 * Technique
 * 
 * @author arun
 *
 */
public class UVA11935 {
	private static final String FUELSTR = "Fuel", GOALSTR = "Goal", LEAKSTR = "Leak", GASSTR = "Gas",
			MECHANICSTR = "Mechanic";
	private static final int FUEL = 0, GOAL = 1, LEAK = 2, GAS = 3, MECHANIC = 4;
	private static final int NOVALUE = -1;
	private static final double ERROR = 1e-9;
	private static final HashMap<String, Integer> intMap;

	static {
		intMap = new HashMap<String, Integer>();
		intMap.put(FUELSTR, FUEL);
		intMap.put(GOALSTR, GOAL);
		intMap.put(LEAKSTR, LEAK);
		intMap.put(GASSTR, GAS);
		intMap.put(MECHANICSTR, MECHANIC);
	}

	static class Events {
		int eventType;
		int value;
		int distance;

		public Events(int journeyType, int distance, int value) {
			this.eventType = journeyType;
			this.distance = distance;
			this.value = value;
		}
	}

	private static ArrayList<Events> journey;

	private static boolean canMakeJourney(double tankCapacity) {
		int leaks = 0, i = 0, len = journey.size();
		double fuelConsumption = 0d;
		int previousDistance = 0;
		double remainingFuel = tankCapacity;
		for (i = 0; i < len; ++i) {
			Events currentEvent = journey.get(i);
			int currentDistance = currentEvent.distance;
			int distanceTravelled = currentDistance - previousDistance;
			remainingFuel = remainingFuel - (distanceTravelled * (fuelConsumption + leaks));
			if (remainingFuel < 0d) {
				break;
			}
			switch (currentEvent.eventType) {
			case FUEL:
				fuelConsumption = currentEvent.value / 100d;
				break;
			case GOAL:
				return true;
			case MECHANIC:
				leaks = 0;
				break;
			case LEAK:
				leaks++;
				break;
			case GAS:
				remainingFuel = tankCapacity;

			}
			previousDistance = currentDistance;
		}
		return false;
	}

	private static double binarySearchTheAnswer() {
		double high = 1e4, low = 0d, ans = 0d;
		while (Math.abs(high - low) > ERROR) {
			double mid = ((high + low) / 2d);
			if (canMakeJourney(mid)) {
				high = ans = mid;
			} else {
				low = mid;
			}
		}
		return ans;
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		String row;
		while ((row = br.readLine()) != null) {
			String[] info = row.split(" ");
			int n = Integer.parseInt(info[3]);
			if (n == 0) {
				break;
			}
			journey = new ArrayList<Events>();
			while (!GOALSTR.equals(info[1])) {
				int distance = Integer.parseInt(info[0]);
				switch (intMap.get(info[1])) {
				case FUEL:
					journey.add(new Events(FUEL, distance, Integer.parseInt(info[3])));
					break;
				case LEAK:
					journey.add(new Events(LEAK, distance, NOVALUE));
					break;
				case GAS:
					journey.add(new Events(GAS, distance, NOVALUE));
					break;
				case MECHANIC:
					journey.add(new Events(MECHANIC, distance, NOVALUE));
				}
				info = br.readLine().split(" ");
			}
			journey.add(new Events(GOAL, Integer.parseInt(info[0]), NOVALUE));
			pw.printf("%.03f\n", binarySearchTheAnswer());
		}

		br.close();
		pw.flush();
		pw.close();
	}
}
