import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;

public class UVA10264 {
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

private static long readLong() {
	int number = readByte();

	while (eolchar(number))
		number = readByte();

	int sign = 1;
	long val = 0;

	if (number == '-') {
		sign = -1;
		number = readByte();
	}

	do {
		if ((number < '0') || (number > '9')) {
			//return sign*val;
			return 0;
		}
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
	if (args.length > 0 && "fileip".equals(args[0])) {
		stream = new FileInputStream(new File("testip.txt"));
	} else {
		stream = System.in;
	}

	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
	int[] twoPow = new int[16];
	twoPow[0] = 1;
	for(int i=1; i<16; ++i){
		twoPow[i] = 2*twoPow[i-1];
	}
	
	int testcases;

	while ((testcases=readInt())!=0) {
		int maxLen = twoPow[testcases];
		int[] weights = new int[maxLen];
		int[] potencies = new int[maxLen];
		for(int i=0; i<maxLen; ++i){
			weights[i] = readInt();
		}
		for(int i=0; i<maxLen; ++i){
			int currentPotency = 0;
			for(int j=0; j<testcases; ++j){
				currentPotency += weights[i^twoPow[j]];
			}
			potencies[i] = currentPotency;
		}
		int maxPotency = -1;
		for(int i=0; i<maxLen; ++i){
			int currentPotency = potencies[i];
			int maxNeighborPotency = -1;
			for(int j=0; j<testcases; ++j){
				maxNeighborPotency = max(maxNeighborPotency, potencies[i^twoPow[j]]);
			}
			maxPotency = max(maxPotency, currentPotency + maxNeighborPotency);
		}
		pw.println(maxPotency);
	}

	pw.flush();
	pw.close();
}
private static int max(int a, int b){
	return a>b?a:b;
}
}
