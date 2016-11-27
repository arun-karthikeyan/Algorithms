import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class UVA1061 {
	private static final HashMap<String, String[]> parentABOVsChildABO;
	private static final HashMap<String, String[]> parentRhVsChildRh;
	private static final HashMap<String, String> aBOMap;
	private static final HashMap<String, String> reverseABOMap;
	private static final HashMap<String, String[]> parChildABOVsParABO;
	private static final HashMap<String, String[]> parChildRHVsParRh;
	static {

		parentABOVsChildABO = new HashMap<String, String[]>();
		parentABOVsChildABO.put("AA", new String[] { "O", "A" });
		parentABOVsChildABO.put("CA", new String[] { "C", "B", "A" });
		parentABOVsChildABO.put("AC", new String[] { "C", "B", "A" });
		parentABOVsChildABO.put("BA", new String[] { "O", "C", "B", "A" });
		parentABOVsChildABO.put("AB", new String[] { "O", "C", "B", "A" });
		parentABOVsChildABO.put("OA", new String[] { "O", "A" });
		parentABOVsChildABO.put("AO", new String[] { "O", "A" });
		parentABOVsChildABO.put("BC", new String[] { "C", "B", "A" });
		parentABOVsChildABO.put("CB", new String[] { "C", "B", "A" });
		parentABOVsChildABO.put("OC", new String[] { "B", "A" });
		parentABOVsChildABO.put("CO", new String[] { "B", "A" });
		parentABOVsChildABO.put("BB", new String[] { "O", "B" });
		parentABOVsChildABO.put("OB", new String[] { "O", "B" });
		parentABOVsChildABO.put("BO", new String[] { "O", "B" });
		parentABOVsChildABO.put("CC", new String[] { "C", "B", "A" });

		parentABOVsChildABO.put("OO", new String[] { "O" });

		parentRhVsChildRh = new HashMap<String, String[]>();
		parentRhVsChildRh.put("-+", new String[] { "-", "+" });
		parentRhVsChildRh.put("+-", new String[] { "-", "+" });
		parentRhVsChildRh.put("--", new String[] { "-" });
		parentRhVsChildRh.put("++", new String[] { "-", "+" });

		aBOMap = new HashMap<String, String>();
		aBOMap.put("A", "A");
		aBOMap.put("B", "B");
		aBOMap.put("O", "O");
		aBOMap.put("AB", "C");

		reverseABOMap = new HashMap<String, String>();
		reverseABOMap.put("A", "A");
		reverseABOMap.put("B", "B");
		reverseABOMap.put("C", "AB");
		reverseABOMap.put("O", "O");

		parChildABOVsParABO = new HashMap<String, String[]>();
		parChildABOVsParABO.put("AA", new String[] { "O", "C", "B", "A" });
		parChildABOVsParABO.put("AC", new String[] { "C", "B" });
		parChildABOVsParABO.put("AB", new String[] { "C", "B" });
		parChildABOVsParABO.put("AO", new String[] { "O", "B", "A" });
		parChildABOVsParABO.put("CA", new String[] { "O", "C", "B", "A" });
		parChildABOVsParABO.put("CC", new String[] { "C", "B", "A" });
		parChildABOVsParABO.put("CB", new String[] { "O", "C", "B", "A" });
		parChildABOVsParABO.put("CO", null);
		parChildABOVsParABO.put("BA", new String[] { "C", "A" });
		parChildABOVsParABO.put("BB", new String[] { "O", "C", "B", "A" });
		parChildABOVsParABO.put("BO", new String[] { "O", "B", "A" });
		parChildABOVsParABO.put("BC", new String[] { "C", "A" });
		parChildABOVsParABO.put("OA", new String[] { "C", "A" });
		parChildABOVsParABO.put("OC", null);
		parChildABOVsParABO.put("OB", new String[] { "C", "B" });
		parChildABOVsParABO.put("OO", new String[] { "O", "B", "A" });

		parChildRHVsParRh = new HashMap<String, String[]>();
		parChildRHVsParRh.put("+-", new String[] { "-", "+" });
		parChildRHVsParRh.put("-+", new String[] { "+" });
		parChildRHVsParRh.put("--", new String[] { "-", "+" });
		parChildRHVsParRh.put("++", new String[] { "-", "+" });
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		StringTokenizer st = new StringTokenizer(br.readLine());
		String p1 = st.nextToken(), p2 = st.nextToken(), c = st.nextToken();
		int testCase = 1;
		while (!p1.equals("E")) {
			String result = "";

			if (c.equals("?")) {
				String p1Rh = p1.substring(p1.length() - 1, p1.length());
				String p1ABO = aBOMap.get(p1.substring(0, p1.length() - 1));

				String p2Rh = p2.substring(p2.length() - 1, p2.length());
				String p2ABO = aBOMap.get(p2.substring(0, p2.length() - 1));

				String[] possibleChildren = parentABOVsChildABO.get(p1ABO + p2ABO);
				String[] possibleRh = parentRhVsChildRh.get(p1Rh + p2Rh);

				ArrayList<String> allChildren = new ArrayList<String>();

				for (int i = 0, iLen = possibleChildren.length; i < iLen; ++i) {
					String currentChild = reverseABOMap.get(possibleChildren[i]);
					for (int j = 0, jLen = possibleRh.length; j < jLen; ++j) {
						allChildren.add(currentChild + possibleRh[j]);
					}
				}
				if (allChildren.size() > 1) {
					StringBuilder resultChildren = new StringBuilder();
					resultChildren.append(allChildren.get(0));

					for (int i = 1, iLen = allChildren.size(); i < iLen; ++i) {
						resultChildren.append(", ").append(allChildren.get(i));
					}

					result = p1 + " " + p2 + " {" + resultChildren.toString() + "}";
				} else {
					result = p1 + " " + p2 + " " + allChildren.get(0);
				}

			} else {
				// missing parent case
				if (p1.equals("?")) {
					String p2Rh = p2.substring(p2.length() - 1, p2.length());
					String p2ABO = aBOMap.get(p2.substring(0, p2.length() - 1));
					String cRh = c.substring(c.length() - 1, c.length());
					String cABO = aBOMap.get(c.substring(0, c.length() - 1));

					String[] possibleParents = parChildABOVsParABO.get(p2ABO + cABO);
					String[] possibleRh = parChildRHVsParRh.get(p2Rh + cRh);

					ArrayList<String> allParents = new ArrayList<String>();

					if (possibleParents != null) {
						for (int i = 0, iLen = possibleParents.length; i < iLen; ++i) {
							String currentParent = reverseABOMap.get(possibleParents[i]);
							for (int j = 0, jLen = possibleRh.length; j < jLen; ++j) {
								allParents.add(currentParent + possibleRh[j]);
							}
						}
						if (allParents.size() > 1) {
							StringBuilder resultParents = new StringBuilder();
							resultParents.append(allParents.get(0));

							for (int i = 1, iLen = allParents.size(); i < iLen; ++i) {
								resultParents.append(", ").append(allParents.get(i));
							}

							result = "{" + resultParents.toString() + "} " + p2 + " " + c;
						} else {
							result = allParents.get(0) + " " + p2 + " " + c;
						}

					} else {
						result = "IMPOSSIBLE " + p2 + " " + c;
					}

				} else {

					String p1Rh = p1.substring(p1.length() - 1, p1.length());
					String p1ABO = aBOMap.get(p1.substring(0, p1.length() - 1));
					String cRh = c.substring(c.length() - 1, c.length());
					String cABO = aBOMap.get(c.substring(0, c.length() - 1));

					String[] possibleParents = parChildABOVsParABO.get(p1ABO + cABO);
					String[] possibleRh = parChildRHVsParRh.get(p1Rh + cRh);

					ArrayList<String> allParents = new ArrayList<String>();

					if (possibleParents != null) {
						for (int i = 0, iLen = possibleParents.length; i < iLen; ++i) {
							String currentParent = reverseABOMap.get(possibleParents[i]);
							for (int j = 0, jLen = possibleRh.length; j < jLen; ++j) {
								allParents.add(currentParent + possibleRh[j]);
							}
						}

						if (allParents.size() > 1) {
							StringBuilder resultParents = new StringBuilder();
							resultParents.append(allParents.get(0));

							for (int i = 1, iLen = allParents.size(); i < iLen; ++i) {
								resultParents.append(", ").append(allParents.get(i));
							}

							result = p1 + " {" + resultParents.toString() + "} " + c;
						} else {
							result = p1 + " " + allParents.get(0) + " " + c;
						}

					} else {
						result = p1 + " IMPOSSIBLE " + c;
					}

				}
			}

			pw.println("Case " + testCase + ": " + result);
			st = new StringTokenizer(br.readLine());
			p1 = st.nextToken();
			p2 = st.nextToken();
			c = st.nextToken();
			testCase++;
		}

		br.close();
		pw.flush();
		pw.close();
	}
}
