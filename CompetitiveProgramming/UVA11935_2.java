import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
/**
 * Through the Desert - Non-Binary Search Implementation
 * @author arun
 *
 */
public class UVA11935_2 {
	
	private static double max(double a, double b) {
		return a > b ? a : b;
	}

	private static final String FUELSTR = "Fuel", GOALSTR = "Goal", LEAKSTR = "Leak", GASSTR = "Gas",
			MECHANICSTR = "Mechanic";
	private static final int FUEL = 0, GOAL = 1, LEAK = 2, GAS = 3, MECHANIC = 4;
	private static final HashMap<String, Integer> intMap;
	static {
		intMap = new HashMap<String, Integer>();
		intMap.put(FUELSTR, FUEL);
		intMap.put(GOALSTR, GOAL);
		intMap.put(LEAKSTR, LEAK);
		intMap.put(GASSTR, GAS);
		intMap.put(MECHANICSTR, MECHANIC);
	}

	public static void main(String[] args) throws Exception {
		 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		String row;
		while ((row = br.readLine()) != null) {
			String[] info = row.split(" ");
			int n = Integer.parseInt(info[3]);
			if (n == 0) {
				break;
			}
			double requiredTankCapacity = 0, maxRequiredTankCapacity = 0, fuelConsumption = n / 100d;
			int leaks = 0, previousDistance = 0;
			info = br.readLine().split(" ");
			while (!GOALSTR.equals(info[1])) {
				int currentDistance = Integer.parseInt(info[0]);
				requiredTankCapacity += ((currentDistance - previousDistance) * (fuelConsumption + leaks));
				maxRequiredTankCapacity = max(maxRequiredTankCapacity, requiredTankCapacity);
				switch (intMap.get(info[1])) {
				case FUEL:
					fuelConsumption = Integer.parseInt(info[3]) / 100d;
					break;
				case LEAK:
					leaks++;
					break;
				case GAS:
					requiredTankCapacity = 0;
					break;
				case MECHANIC:
					leaks = 0;
				}
				info = br.readLine().split(" ");
				previousDistance = currentDistance;
			}
			int finalDistance = Integer.parseInt(info[0]);
			requiredTankCapacity += ((finalDistance - previousDistance) * (fuelConsumption + leaks));
			maxRequiredTankCapacity = max(maxRequiredTankCapacity, requiredTankCapacity);
			pw.printf("%.03f\n", maxRequiredTankCapacity);
		}

		br.close();
		pw.flush();
		pw.close();
	}
}
