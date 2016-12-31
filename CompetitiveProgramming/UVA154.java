import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class UVA154 {
	private static final String END = "#";

	private static int differences(String c1, String c2) {
		String[] bins1 = c1.split(",");
		String[] bins2 = c2.split(",");
		int[] waste = new int[5];
		for (int i = 0; i < 5; ++i) {
			int material = bins1[i].charAt(2);
			switch (bins1[i].charAt(0)) {
			case 'r':
				waste[0] = material;
				break;
			case 'o':
				waste[1] = material;
				break;
			case 'y':
				waste[2] = material;
				break;
			case 'g':
				waste[3] = material;
				break;
			case 'b':
				waste[4] = material;
			}
		}
		int count = 0;
		for (int i = 0; i < 5; ++i) {
			int material = bins2[i].charAt(2);
			switch (bins2[i].charAt(0)) {
			case 'r':
				count = waste[0] == material ? count : count + 1;
				break;
			case 'o':
				count = waste[1] == material ? count : count + 1;
				break;
			case 'y':
				count = waste[2] == material ? count : count + 1;
				break;
			case 'g':
				count = waste[3] == material ? count : count + 1;
				break;
			case 'b':
				count = waste[4] == material ? count : count + 1;
			}
		}
		return count;
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		 BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		String line;
		while ((line=br.readLine())!=null && !END.equals(line)) {
			ArrayList<String> cities = new ArrayList<String>();
			while (line.charAt(0) != 'e') {
				cities.add(line);
				line = br.readLine();
			}
			int minCount = Integer.MAX_VALUE, minCountCity = -1;
			for (int i = 0, iLen = cities.size(); i < iLen; ++i) {
				int count = 0;
				String city1 = cities.get(i);
				for (int j = 0; j < iLen; ++j) {
					count += differences(city1, cities.get(j));
				}
				if (count < minCount) {
					minCount = count;
					minCountCity = i + 1;
				}
			}
			pw.println(minCountCity);
		}

		br.close();
		pw.flush();
		pw.close();
	}
}
