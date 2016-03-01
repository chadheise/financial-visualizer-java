package chadheise.finance;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App {

    private static final String TITLE = "Financial Performance Over Time";
    private static final int WIDTH = 1440;
    private static final int HEIGHT = 720;
    private static final double YEARLY_RETURN = 0.08;
    private static final int START_YEAR = 2013;

    public static void main(String[] args) throws NumberFormatException, IOException {
        String filePath = args[0];
        String outputPath = args[1];

        CsvReader reader = new CsvReader();
        FinanceData financeData = reader.readFinanceData(filePath);

        ChartGenerator cg = new ChartGenerator(outputPath, TITLE, WIDTH,
                HEIGHT, YEARLY_RETURN, START_YEAR);
        cg.output(financeData);

    }
}
