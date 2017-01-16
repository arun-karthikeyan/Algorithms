import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class UVA10700 {
	private static long performOperation(char op, long n1, long n2) {
		switch (op) {
		case '+':
			return n1 + n2;
		case '*':
			return n1 * n2;
		}
		return 0;
	}

	private static long evaluate(String[] operands, String[] operators, char highOp) {
		long result = 0;
		long p_no = Integer.parseInt(operands[0]);
		char p_op = '+';
		for (int i = 0, iLen = operators.length; i < iLen; ++i) {
			int c_no = Integer.parseInt(operands[i + 1]);
			char c_op = operators[i].charAt(0);
			if (c_op == highOp) {
				p_no = performOperation(c_op, p_no, c_no);
			} else {
				result = performOperation(p_op, result, p_no);
				p_no = c_no;
				p_op = c_op;
			}
		}
		return performOperation(p_op, result, p_no);

	}

	public static void main(String[] args) throws Exception {
		 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		int testcases = Integer.parseInt(br.readLine());
		while (testcases-- > 0) {
			String currentExpression = br.readLine();
			String[] operands = currentExpression.split("[+*]");
			if (operands.length == 1) {
				pw.println("The maximum and minimum are " + currentExpression + " and " + currentExpression + ".");
			} else {
				String[] operators = currentExpression.split("\\d+");
				operators = Arrays.copyOfRange(operators, 1, operands.length);
				pw.println("The maximum and minimum are " + evaluate(operands, operators, '+') + " and "
						+ evaluate(operands, operators, '*') + ".");
			}
		}

		br.close();
		pw.flush();
		pw.close();
	}
}
