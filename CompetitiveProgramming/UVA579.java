import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA579 {
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//			BufferedReader br = new BufferedReader(new FileReader("	testip.txt"));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
	String[] time = br.readLine().split(":");
	int hours = Integer.parseInt(time[0]);
	int minutes = Integer.parseInt(time[1]);
	while(hours!=0 || minutes!=0){
		hours = (hours*60)+minutes;
		minutes*=12;
		int minuteDifference = Math.abs(hours-minutes);
		int result = Math.min(minuteDifference, 720-minuteDifference);
		pw.printf("%.3f\n", (result/2d));
		time = br.readLine().split(":");
		hours = Integer.parseInt(time[0]);
		minutes = Integer.parseInt(time[1]);
	}
	
	
	br.close();
	pw.flush();
	pw.close();
}
}
