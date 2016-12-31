import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA386 {
	private static int cubeRoot(int val) {
		int cubeRoot = (int) Math.cbrt(val);
		if (cubeRoot * cubeRoot * cubeRoot == val) {
			return cubeRoot;
		}
		return -1;
	}

	public static void main(String[] args) throws Exception {
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		for (int a = 6; a <= 200; ++a) {
			int aCube = a * a * a;
			for (int b = 2; b <= 200; ++b) {
				int bCube = b * b * b;
				if (aCube <= 2 * bCube)
					break;
				for (int c = b; c <= 200; ++c) {
					int cCube = c * c * c;
					if (aCube <= (bCube + cCube))
						break;
					int d = cubeRoot(aCube - bCube - cCube);
					if (d != -1 && d >= c) {
						pw.println("Cube = " + a + ", Triple = (" + b + "," + c + "," + d + ")");
					}
				}
			}
		}

		pw.flush();
		pw.close();
	}
}
