package stablematching;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class StableMatchingAlgorithm {
    private static int indexOf(int[] arr, int val) {
        int index = -1;
        for (int i = 0, j = arr.length; i < j; ++i) {
            if (arr[i] == val) {
                index = i;
                break;
            }
        }
        return index;
    }

    public static void main(String[] args) throws Exception {

        PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out),
                true);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int noOfPairs;

        pw.println("Enter the total Number of Pairs");
        noOfPairs = Integer.parseInt(br.readLine());

        int[][] preferenceListMen = new int[noOfPairs][noOfPairs];
        int[][] preferenceListWomen = new int[noOfPairs][noOfPairs];

        for (int i = 0; i < noOfPairs; ++i) {
            pw.println("Enter preference order of m" + (i + 1));
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < noOfPairs; ++j) {
                preferenceListMen[i][j] = Integer.parseInt(st.nextToken()) - 1;
            }
        }

        for (int i = 0; i < noOfPairs; ++i) {
            pw.println("Enter preference order of w" + (i + 1));
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < noOfPairs; ++j) {
                preferenceListWomen[i][j] = Integer.parseInt(st.nextToken()) - 1;
            }
        }

        int[] womenProposed = new int[noOfPairs];
        boolean[] womenFree = new boolean[noOfPairs];
        boolean[] manFree = new boolean[noOfPairs];
        Arrays.fill(womenFree, true);
        Arrays.fill(manFree, true);
        LinkedList<Integer> bfs = new LinkedList<Integer>();

        for (int i = 0; i < noOfPairs; ++i) {
            bfs.add(i);
        }

        int[] stablePairingMen = new int[noOfPairs];
        int[] stablePairingWomen = new int[noOfPairs];
        int matchedPairs = 0;

        // Start Music
        while (!bfs.isEmpty()) {

            int currentMan = bfs.remove();
            if (womenProposed[currentMan] < noOfPairs) {

                int currentPreferredWoman = preferenceListMen[currentMan][womenProposed[currentMan]++];
                if (womenFree[currentPreferredWoman]) {
                    stablePairingMen[currentMan] = currentPreferredWoman;
                    womenFree[currentPreferredWoman] = false;
                    manFree[currentMan] = false;
                    stablePairingWomen[currentPreferredWoman] = currentMan;
                    matchedPairs++;
                } else if (indexOf(preferenceListWomen[currentPreferredWoman],
                        currentMan) < indexOf(
                        preferenceListWomen[currentPreferredWoman],
                        stablePairingWomen[currentPreferredWoman])) {
                    manFree[stablePairingWomen[currentPreferredWoman]] = true;
                    bfs.add(stablePairingWomen[currentPreferredWoman]);
                    manFree[currentMan] = false;
                    stablePairingMen[currentMan] = currentPreferredWoman;
                    stablePairingWomen[currentPreferredWoman] = currentMan;
                } else {
                    bfs.add(currentMan);
                }

            }

        }
        // End Music

        if (matchedPairs == noOfPairs) {
            pw.println("Perfect Match found !");
            for (int i = 0; i < noOfPairs; ++i) {
                pw.println("m" + (i + 1) + " with w"
                        + (stablePairingMen[i] + 1));
            }
        } else {
            pw.println("Perfect Match Not Found !");
        }
        br.close();
        pw.flush();
        pw.close();

    }
}
