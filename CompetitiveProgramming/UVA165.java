import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class UVA165 {
	private static int totalchars = 0, offset = 0;
	private static InputStream stream;
	private static byte[] buffer = new byte[1024];

	private static int readByte() {
		if (totalchars < 0)
			return 0;
		if (offset >= totalchars) {
			offset = 0;
			try {
				totalchars = stream.read(buffer);
			} catch (IOException e) {
				return 0;
			}
			if (totalchars <= 0)
				return -1;
		}
		return buffer[offset++];
	}

	private static int readInt() {
		int number = readByte();

		while (eolchar(number))
			number = readByte();

		int sign = 1;
		int val = 0;

		if (number == '-') {
			sign = -1;
			number = readByte();
		}

		do {
			if ((number < '0') || (number > '9'))
				return 0;
			val *= 10;
			val += (number - '0');
			number = readByte();
		} while (!eolchar(number));

		return sign * val;
	}

	private static boolean eolchar(int c) {
		return c == ' ' || c == '\n' || c == -1 || c == '\r' || c == '\t';
	}

	public static void main(String[] args) throws Exception {
		stream = System.in;
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		int fz = -1;
		while ((fz = readInt()) != 0) {
			ArrayList<Integer> values = new ArrayList<Integer>();
			values.add(fz);
			values.add(readInt());
			int tz = readInt();
			if (tz != 0) {
				values.add(tz);
				while ((tz = readInt()) != 0) {
					values.add(tz);
				}
			}
			int beginOffset = 2*arrayMin(values);
			boolean found = false;
			int simultaneousGreen = -1;
			for (int start = beginOffset, end = (5 * 60 * 60); start <= end; ++start) {
				boolean currentSecondGreen = true;
				for (int i = 0, iLen = values.size(); i < iLen; ++i) {
					int val = values.get(i);
					if (((start / val) % 2 == 1) || (start % val) >= (val - 5)) {
						currentSecondGreen = false;
						break;
					}
				}
				if (currentSecondGreen) {
					found = true;
					simultaneousGreen = start;
					break;
				}
			}
			if(found){
				int hours = simultaneousGreen/3600;
				int minutes = ((simultaneousGreen-(hours*3600))%3600)/60;
				int seconds = (simultaneousGreen - hours*3600 - minutes*60);
				pw.printf("%02d:%02d:%02d\n",hours, minutes, seconds);
			}else{
				pw.println("Signals fail to synchronise in 5 hours");
			}
		}

		pw.flush();
		pw.close();
	}
	private static int arrayMin(ArrayList<Integer> values){
		int min = values.get(0);
		for(int i=1, iLen = values.size(); i<iLen; ++i){
			min = min(min, values.get(i));
		}
		return min;
	}
	private static int min(int a, int b){
		return a<b?a:b;
	}
}
