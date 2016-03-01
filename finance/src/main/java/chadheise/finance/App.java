package chadheise.finance;

import java.io.IOException;

public class App {

    private static final String TITLE = "Financial Performance Over Time";
    private static final int WIDTH = 1440;
    private static final int HEIGHT = 720;
    private static final double YEARLY_RETURN = 0.08;

    public static void main(String[] args) throws NumberFormatException, IOException {
        String filePath = args[0];
        String outputPath = args[1];
        // Typically 12 for monthly statements or 4 for quarterly statements
        int numPeriods = Integer.parseInt(args[2]);
        int startYear = 0; // Default to 0 so no years are skipped
        if (args.length > 3) {
            startYear = Integer.parseInt(args[3]);
        }

        CsvReader reader = new CsvReader();
        FinanceData financeData = reader.readFinanceData(filePath);

        ChartGenerator cg = new ChartGenerator(outputPath, TITLE, WIDTH,
                HEIGHT, YEARLY_RETURN, numPeriods, startYear);
        cg.output(financeData);

    }
}
