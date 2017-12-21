package chadheise.finance;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Utils {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public static Map<LocalDate, Double> getTotalAdditions(final FinanceData financeData) {
        Map<LocalDate, Double> additions = new HashMap<>();

        double runningTotal = 0;
        for (LocalDate date : financeData.getDates()) {
            runningTotal += financeData.getAdditions(date);
            additions.put(date, runningTotal);
            System.out.format("%s %.2f%n", date.format(FORMATTER), runningTotal);
        }

        return additions;
    }

    public static Map<LocalDate, Double> getExpectedBalances(final FinanceData financeData, double yearlyReturn) {
        final Map<LocalDate, Double> expectedBalances = new HashMap<>();

        LocalDate previousDate = financeData.getDates().first();
        expectedBalances.put(previousDate, 0d);
        for (LocalDate date : financeData.getDates()) {
            double totalPeriods = (double) daysInYear(date);
            double returnPerPeriod = Math.pow(1 + yearlyReturn, (1.0 / totalPeriods)) - 1;

            double numPeriods = daysBetween(previousDate, date);

            double newBalance = expectedBalances.get(previousDate) * Math.pow(1 + returnPerPeriod, numPeriods)
                    + financeData.getAdditions(date);
            expectedBalances.put(date, newBalance);
            System.out.format("%s %.2f%n", date.format(FORMATTER), newBalance);

            previousDate = date;
        }

        return expectedBalances;
    }

    private static int daysInYear(final LocalDate date) {
        if (date.isLeapYear()) {
            return 366;
        }
        return 365;
    }

    private static int daysBetween(final LocalDate startDate, final LocalDate endDate) {
        int daysBetween = endDate.getDayOfYear() - startDate.getDayOfYear();
        if (startDate.getYear() != endDate.getYear()) {
            int daysInPreviousYear = daysInYear(startDate) - startDate.getDayOfYear();
            daysBetween = endDate.getDayOfYear() + daysInPreviousYear;
        }
        return daysBetween;
    }

}
