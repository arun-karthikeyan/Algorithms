import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA594 {
private static final int BYTEEXTRACTOR = ((int)Math.pow(2, 8))-1;
private static final String CONVERTSTO = " converts to ";
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//	BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
	String currentNo;
	while ((currentNo=br.readLine())!=null) {
		int no = Integer.parseInt(currentNo);
		pw.println(no+CONVERTSTO+(((no&BYTEEXTRACTOR)<<24)|(((no>>8)&BYTEEXTRACTOR)<<16)|(((no>>16)&BYTEEXTRACTOR)<<8)|((no>>24)&BYTEEXTRACTOR)));
	}

	br.close();
	pw.flush();
	pw.close();
}
}
