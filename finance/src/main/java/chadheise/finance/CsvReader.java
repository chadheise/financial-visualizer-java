package chadheise.finance;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.joda.time.DateTime;

public class CsvReader {

    public FinanceData readFinanceData(final String filePath) throws NumberFormatException, IOException {
        FinanceData data = new FinanceData();

        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line;
        bufferedReader.readLine(); // Skip first line of labels

        while ((line = bufferedReader.readLine()) != null) {
            String[] fields = line.split(",");
            DateTime date = convertDate(fields[0]);

            double beginningBalance = Double.valueOf(fields[1]);
            double additions = Double.valueOf(fields[2]);
            double changeInValue = Double.valueOf(fields[3]);

            data.addEntry(date, beginningBalance, additions, changeInValue);
        }

        return data;
    }

    /**
     * Converts String date of form "dd/mm/yyy" to a JFreeChart Day.
     * 
     * @param date
     * @return
     */
    private DateTime convertDate(final String dateString) {
        String[] date = dateString.split("/");
        int day = Integer.valueOf(date[1]);
        int month = Integer.valueOf(date[0]);
        int year = Integer.valueOf(date[2]);
        return new DateTime(year, month, day, 0, 0); // 0 hour & 0 minute
    }

}
