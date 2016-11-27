import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.GregorianCalendar;

public class UVA11947 {
	private static boolean between(int mm,int dd, int mm1, int dd1, int mm2, int dd2){
		return (mm==mm1 && dd>=dd1) || (mm==mm2 && dd<=dd2);
	}
	private static String getZodiac(int month, int date){
		if(between(month,date,1,21,2,19)){
			return "aquarius";
		}
		if(between(month,date,2,20,3,20)){
			return "pisces";
		}
		if(between(month,date,3,21,4,20)){
			return "aries";
		}
		if(between(month,date,4,21,5,21)){
			return "taurus";
		}
		if(between(month,date,5,22,6,21)){
			return "gemini";
		}
		if(between(month,date,6,22,7,22)){
			return "cancer";
		}
		if(between(month,date,7,23,8,21)){
			return "leo";
		}
		if(between(month,date,8,22,9,23)){
			return "virgo";
		}
		if(between(month,date,9,24,10,23)){
			return "libra";
		}
		if(between(month,date,10,24,11,22)){
			return "scorpio";
		}
		if(between(month,date,11,23,12,22)){
			return "sagittarius";
		}
		
		return "capricorn";
	}
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		//		BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		int testcases = Integer.parseInt(br.readLine()), i = 0;
		while (i++<testcases) {
			String date = br.readLine();
			int mm = Integer.parseInt(date.substring(0, 2));
			int dd = Integer.parseInt(date.substring(2, 4));
			int yy = Integer.parseInt(date.substring(4));
			GregorianCalendar gc = new GregorianCalendar(yy, mm-1, dd);
			gc.add(GregorianCalendar.DATE, 280);
			mm = gc.get(GregorianCalendar.MONTH)+1;
			dd = gc.get(GregorianCalendar.DAY_OF_MONTH);
			yy = gc.get(GregorianCalendar.YEAR);
			pw.printf("%d %02d/%02d/%04d %s\n", i,mm,dd,yy,getZodiac(mm,dd));

		}

		br.close();
		pw.flush();
		pw.close();
	}
}
