import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * One Handed Typist, Nothing to think about this problem - just mapping
 * 
 * @author arun
 *
 */
public class UVA11278 {
	private static final HashMap<Character, Character> Q2D;
	static {
		Q2D = new HashMap<Character, Character>();
		Q2D.put('4', 'q');
		Q2D.put('5', 'j');
		Q2D.put('6', 'l');
		Q2D.put('7', 'm');
		Q2D.put('8', 'f');
		Q2D.put('9', 'p');
		Q2D.put('0', '/');
		Q2D.put('-', '[');
		Q2D.put('=', ']');

		Q2D.put('q', '4');
		Q2D.put('w', '5');
		Q2D.put('e', '6');
		Q2D.put('r', '.');
		Q2D.put('t', 'o');
		Q2D.put('y', 'r');
		Q2D.put('u', 's');
		Q2D.put('i', 'u');
		Q2D.put('o', 'y');
		Q2D.put('p', 'b');
		Q2D.put('[', ';');
		Q2D.put(']', '=');

		Q2D.put('a', '7');
		Q2D.put('s', '8');
		Q2D.put('d', '9');
		Q2D.put('f', 'a');
		Q2D.put('g', 'e');
		Q2D.put('h', 'h');
		Q2D.put('j', 't');
		Q2D.put('k', 'd');
		Q2D.put('l', 'c');
		Q2D.put(';', 'k');
		Q2D.put('\'', '-');

		Q2D.put('z', '0');
		Q2D.put('x', 'z');
		Q2D.put('c', 'x');
		Q2D.put('v', ',');
		Q2D.put('b', 'i');
		Q2D.put('n', 'n');
		Q2D.put('m', 'w');
		Q2D.put(',', 'v');
		Q2D.put('.', 'g');
		Q2D.put('/', '\'');
		// shift up
		Q2D.put('$', 'Q');
		Q2D.put('%', 'J');
		Q2D.put('^', 'L');
		Q2D.put('&', 'M');
		Q2D.put('*', 'F');
		Q2D.put('(', 'P');
		Q2D.put(')', '?');
		Q2D.put('_', '{');
		Q2D.put('+', '}');

		Q2D.put('Q', '$');
		Q2D.put('W', '%');
		Q2D.put('E', '^');
		Q2D.put('R', '>');
		Q2D.put('T', 'O');
		Q2D.put('Y', 'R');
		Q2D.put('U', 'S');
		Q2D.put('I', 'U');
		Q2D.put('O', 'Y');
		Q2D.put('P', 'B');
		Q2D.put('{', ':');
		Q2D.put('}', '+');
		Q2D.put('|', '|');

		Q2D.put('A', '&');
		Q2D.put('S', '*');
		Q2D.put('D', '(');
		Q2D.put('F', 'A');
		Q2D.put('G', 'E');
		Q2D.put('H', 'H');
		Q2D.put('J', 'T');
		Q2D.put('K', 'D');
		Q2D.put('L', 'C');
		Q2D.put(':', 'K');
		Q2D.put('"', '_');

		Q2D.put('Z', ')');
		Q2D.put('X', 'Z');
		Q2D.put('C', 'X');
		Q2D.put('V', '<');
		Q2D.put('B', 'I');
		Q2D.put('N', 'N');
		Q2D.put('M', 'W');
		Q2D.put('<', 'V');
		Q2D.put('>', 'G');
		Q2D.put('?', '"');

	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		String line;
		while ((line = br.readLine()) != null) {
			for (int i = 0, iLen = line.length(); i < iLen; ++i) {
				char mappedChar = line.charAt(i);
				if (Q2D.containsKey(mappedChar)) {
					mappedChar = Q2D.get(mappedChar);
				}
				pw.print(mappedChar);
			}
			pw.println();
		}

		br.close();
		pw.flush();
		pw.close();
	}
}
