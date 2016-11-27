import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA12439 {
	private static final int MM = 0,DD = 1, YY=2;
	private static long[] getMMDDYY(String date) throws Exception{
		long[] d = new long[3];
		String[] temp = date.split(",");
		d[YY] = Integer.parseInt(temp[1].trim());
		temp = temp[0].trim().split(" ");
		d[MM] = getMonth(temp[0].trim());
		d[DD] = Integer.parseInt(temp[1].trim());
		return d;
	}
	private static int getMonth(String month) throws Exception{
		switch(month){
		case "January":
			return 1;
		case "February":
			return 2;
		case "March":
			return 3;
		case "April":
			return 4;
		case "May":
			return 5;
		case "June":
			return 6;
		case "July":
			return 7;
		case "August":
			return 8;
		case "September":
			return 9;
		case "October":
			return 10;
		case "November":
			return 11;
		case "December":
			return 12;
		default:
			throw new Exception();
		}
	}
	private static boolean isLeapYear(long year){
		return (((year%4)==0) && (((year%100)==0 && (year%400)==0) || ((year%100)!=0)));
	}
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//			BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
	int testcases = Integer.parseInt(br.readLine()), i=0;
	while (i++<testcases) {
		long[] d1 = getMMDDYY(br.readLine());
		long[] d2 = getMMDDYY(br.readLine());
		long startYear = d1[YY]+1;
		long endYear = d2[YY]-1;
		long leapDays = 0;
		long nextLeapYearToStartYear = ((startYear+3)/4)*4;
		long noOfFours = (endYear-nextLeapYearToStartYear);
		
		if(noOfFours>=0){
			leapDays = (noOfFours/4)+1;
			nextLeapYearToStartYear = ((startYear+99)/100)*100;
			long noOfHundreds = (endYear - nextLeapYearToStartYear);
			if(noOfHundreds>=0){
				leapDays = leapDays - (noOfHundreds/100) - 1;
				nextLeapYearToStartYear = ((startYear+399)/400)*400;
				long noOfFourHundreds = (endYear - nextLeapYearToStartYear);
				if(noOfFourHundreds>=0){
					leapDays = leapDays + (noOfFourHundreds/400) + 1;
				}
			}
		}
		
		
		if(isLeapYear(d1[YY]) && d1[MM]<3 && (d1[YY]!=d2[YY] || (d2[MM]>2) || (d2[MM]==2 && d2[DD]==29))){
			leapDays++;
		}
		if((d1[YY]!=d2[YY]) && (isLeapYear(d2[YY])) && ((d2[MM]>2) || (d2[MM]==2 && d2[DD]==29))){
			leapDays++;
		}
		pw.println("Case "+i+": "+leapDays);
		
	}

	br.close();
	pw.flush();
	pw.close();
}
}
