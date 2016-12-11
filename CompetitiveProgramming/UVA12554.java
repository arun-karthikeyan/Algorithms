import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA12554 {
	private static final String[] SONG = { "Happy", "birthday", "to", "you",
			"Happy", "birthday", "to", "you", "Happy", "birthday", "to",
			"Rujia", "Happy", "birthday", "to", "you" };
	private static final int SONGLENGTH = 16;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		int people = Integer.parseInt(br.readLine());
		String[] attendees = new String[people];
		for (int i = 0; i < people; ++i) {
			attendees[i] = br.readLine();
		}
		int newLength = (int) Math.ceil((double) people / SONGLENGTH)
				* SONGLENGTH;
		for (int i = 0, j = 0, k = 0; k < newLength; ++k, i = (k % SONGLENGTH), j = (k % people)) {
			pw.println(attendees[j] + ": " + SONG[i]);
		}

		pw.flush();
		pw.close();
		br.close();
	}
}
