package chadheise.finance;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CsvReader {

    public FinanceData readFinanceData(final String filePath) throws NumberFormatException, IOException {
        FinanceData data = new FinanceData();

        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line;
        bufferedReader.readLine(); // Skip first line of labels

        while ((line = bufferedReader.readLine()) != null) {
            String[] fields = line.split(",");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate date = LocalDate.parse(fields[0], formatter);

            double beginningBalance = Double.valueOf(fields[1]);
            double additions = Double.valueOf(fields[2]);
            double changeInValue = Double.valueOf(fields[3]);

            data.addEntry(date, beginningBalance, additions, changeInValue);
        }

        bufferedReader.close();

        return data;
    }

}
