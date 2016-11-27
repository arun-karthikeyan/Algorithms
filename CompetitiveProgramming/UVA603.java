import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class UVA603 {
	private static final int OP = 0,CP = 1, PP = 2, NOTPARKED = -1;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		 BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		int testcases = Integer.parseInt(br.readLine());
		br.readLine();
		while (testcases-- > 0) {
			ArrayList<int[]> waitingCars = new ArrayList<int[]>();
			int val;
			while ((val = Integer.parseInt(br.readLine())) != 99) {
				waitingCars.add(new int[] { val - 1, val-1, NOTPARKED });
			}
			String exitCar = "";
			int totalParked = 0;
			while (((exitCar = br.readLine())!=null) && (exitCar.length() > 0)) {
				if (totalParked < waitingCars.size()) {
					int currentExitCar = Integer.parseInt(exitCar)-1;
					int nearestDistance = Integer.MAX_VALUE, nearestCar = -1;
					for (int i = 0, iLen = waitingCars.size(); i < iLen; ++i) {
						int[] currentWaitingCar = waitingCars.get(i);
						if (currentWaitingCar[PP] == NOTPARKED) {
							int distanceToFreeSpot = (currentExitCar - currentWaitingCar[CP]);
							if (distanceToFreeSpot < 0) {
								distanceToFreeSpot += 20;
							}
							if (distanceToFreeSpot < nearestDistance) {
								nearestDistance = distanceToFreeSpot;
								nearestCar = i;
							}
						}
					}
					waitingCars.get(nearestCar)[PP] = currentExitCar+1;
					for(int i=0, iLen = waitingCars.size(); i<iLen; ++i){
						waitingCars.get(i)[CP]=(waitingCars.get(i)[CP]+nearestDistance)%20;
					}
					totalParked++;
				}
			}
			for (int i = 0, iLen = waitingCars.size(); i < iLen; ++i) {
				int[] currentWaitingCar = waitingCars.get(i);
				pw.print("Original position " + (currentWaitingCar[OP]+1));
				if (currentWaitingCar[PP] == NOTPARKED) {
					pw.println(" did not park");
				} else {
					pw.println(" parked in " + currentWaitingCar[PP]);
				}
			}
			if(testcases!=0){
			pw.println();
			}
		}

		br.close();
		pw.flush();
		pw.close();
	}
}
