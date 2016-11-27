import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA12085 {
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
private static String getRangeNumber(int startNumber, int endNumber){
	String n1 = startNumber+"";
	String n2 = endNumber+"";
	int idx=-1;
	for(int i=0, iLen = n1.length(); i<iLen; ++i){
		if(n1.charAt(i)!=n2.charAt(i)){
			idx=i;
			break;
		}
	}
	return "0"+n1+"-"+n2.substring(idx);
}
public static void main(String[] args) throws Exception {
	stream = System.in;
//	stream = new FileInputStream(new File("testip.txt"));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

	int n = readInt(),testCase=0;

	while (n!=0) {
		int startNumber = readInt(), previousNumber=startNumber;
		pw.println("Case "+(++testCase)+":");
		for(int i=1;i<n;++i){
			int currentNumber=readInt();
			if(currentNumber!=(previousNumber+1)){
				if(startNumber==previousNumber){
					pw.println("0"+startNumber);
				}else{
					pw.println(getRangeNumber(startNumber, previousNumber));
				}
				startNumber=currentNumber;
			}
			previousNumber = currentNumber;
		}
		if(startNumber==previousNumber){
			pw.println("0"+startNumber);
		}else{
			pw.println(getRangeNumber(startNumber, previousNumber));
		}
		pw.println();
		n=readInt();
	}

	pw.flush();
	pw.close();
}
}
