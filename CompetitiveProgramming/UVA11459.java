import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

public class UVA11459 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		//		BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		int testcases = Integer.parseInt(br.readLine());
		while (testcases-- > 0) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int players = Integer.parseInt(st.nextToken());
			int snakesLadders = Integer.parseInt(st.nextToken());
			int diceRolls = Integer.parseInt(st.nextToken());
			int[] playersPosition = new int[players];
			boolean won = false;
			Arrays.fill(playersPosition, 1);
			HashMap<Integer, Integer> jumpPositions = new HashMap<Integer, Integer>();
			for(int i=0; i<snakesLadders; ++i){
				st = new StringTokenizer(br.readLine());
				jumpPositions.put(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
			}
			for(int i=0; i<diceRolls; ++i){
				int diceRoll = Integer.parseInt(br.readLine());
				if(won){
					continue;
				}
				int playerNo = i%players;
				playersPosition[playerNo]=playersPosition[playerNo]+diceRoll;
				if(jumpPositions.containsKey(playersPosition[playerNo])){
					playersPosition[playerNo] = jumpPositions.get(playersPosition[playerNo]);
				}
				if(playersPosition[playerNo]>=100){
					playersPosition[playerNo] = 100;
					won = true;
				}
			}
			for(int i=0; i<players; ++i){
				pw.println("Position of player "+(i+1)+" is "+playersPosition[i]+".");
			}

		}

		br.close();
		pw.flush();
		pw.close();
	}
}
