package chadheise.finance;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App {

    private static final String OUTPUT_PATH = "/Users/chadheise/Documents/Maple Grove/Ameriprise/out.jpg";
    private static final String TITLE = "Financial Performance Over Time";
    private static final int WIDTH = 720;
    private static final int HEIGHT = 720;

    public static void main(String[] args) throws NumberFormatException, IOException {
        String filePath = args[0];

        CsvReader reader = new CsvReader();
        FinanceData financeData = reader.readFinanceData(filePath);

        // System.out.println(data.getDate(2016, 1));
        // System.out.println(data.getBeginningBalance(2016, 1));
        // System.out.println(data.getAdditions(2016, 1));
        // System.out.println(data.getChangeInValue(2016, 1));
        // double[] eb = Utils.getEndingBalances(financeData);
        // for (int i = 0; i < eb.length; i++) {
        // System.out.println(eb[i]);
        // }

        ChartGenerator cg = new ChartGenerator(OUTPUT_PATH, TITLE, WIDTH,
                HEIGHT);
        cg.output(financeData);

        double[] totalAdditions = Utils.getTotalAdditions(financeData);
        for (int i = 0; i < totalAdditions.length; i++) {
            System.out.println(totalAdditions[i]);
        }

    }
}
