package chadheise.finance;

import java.util.Arrays;
import java.util.List;

public class Utils {

    // TODO: Refactor to reuse code
    public static double[] getBeginningBalances(final FinanceData financeData) {
        List<Integer> years = financeData.getYears();

        // Num years * 12 months/year
        int size = (years.get(years.size() - 1) - years.get(0) + 1) * 12;
        double[] values = new double[size];
        int index = 0;

        for (int year = years.get(0); year < years.get(years.size() - 1); year++) {
            for (int month = 1; month <= 12; month++) {
                if (financeData.hasData(year, month)) {
                    values[index] = financeData.getBeginningBalance(year, month);
                    index++;
                }
                // TODO: Account for missing data
            }
        }

        // return values;
        return Arrays.copyOfRange(values, 0, index);
    }

    // TODO: Refactor to reuse code
    public static double[] getAdditions(final FinanceData financeData) {
        List<Integer> years = financeData.getYears();

        // Num years * 12 months/year
        int size = (years.get(years.size() - 1) - years.get(0) + 1) * 12;
        double[] values = new double[size];
        int index = 0;

        for (int year = years.get(0); year < years.get(years.size() - 1); year++) {
            for (int month = 1; month <= 12; month++) {
                if (financeData.hasData(year, month)) {
                    values[index] = financeData.getAdditions(year, month);
                    index++;
                }
                // TODO: Account for missing data
            }
        }

        // return values;
        return Arrays.copyOfRange(values, 0, index);

    }

    // TODO: Refactor to reuse code
    public static double[] getChangeInValues(final FinanceData financeData) {
        List<Integer> years = financeData.getYears();

        // Num years * 12 months/year
        int size = (years.get(years.size() - 1) - years.get(0) + 1) * 12;
        double[] values = new double[size];
        int index = 0;

        for (int year = years.get(0); year < years.get(years.size() - 1); year++) {
            for (int month = 1; month <= 12; month++) {
                if (financeData.hasData(year, month)) {
                    values[index] = financeData.getChangeInValue(year, month);
                    index++;
                }
                // TODO: Account for missing data
            }
        }

        // return values;
        return Arrays.copyOfRange(values, 0, index);
    }

    // TODO: Refactor to reuse code
    public static double[] getEndingBalances(final FinanceData financeData) {
        List<Integer> years = financeData.getYears();

        // Num years * 12 months/year
        int size = (years.get(years.size() - 1) - years.get(0) + 1) * 12;
        double[] values = new double[size];
        int index = 0;

        for (int year = years.get(0); year < years.get(years.size() - 1); year++) {
            for (int month = 1; month <= 12; month++) {
                if (financeData.hasData(year, month)) {
                    double beginningBalance = financeData.getBeginningBalance(year, month);
                    double additions = financeData.getAdditions(year, month);
                    double changeInValue = financeData.getChangeInValue(year, month);
                    values[index] = beginningBalance + additions + changeInValue;
                    index++;
                }
                // TODO: Account for missing data
            }
        }

        // return values;
        return Arrays.copyOfRange(values, 0, index);
    }

    /**
     * The expected balances is the sum of the expected monthly return rate
     * times a value times how long that value has been invested.
     * 
     * 
     * @param financeData
     * @param yearlyReturn
     *            the expected yearly return as a percentage (e.g. 8% is 0.08)
     * @return
     */
    public static double[] getExpectedBalances(final FinanceData financeData,
            double yearlyReturn) {

        double monthlyReturn = Math.pow(1 + yearlyReturn, (1.0 / 12.0)) - 1;

        double[] additions = getAdditions(financeData);
        double[] expectedValues = new double[additions.length];

        int numMonths = additions.length;
        double runningTotal;

        for (int i = 0; i < additions.length; i++) {

            double prevVal = 0;
            if (i != 0) {
                prevVal = expectedValues[i - 1];
            }

            expectedValues[i] = prevVal * (1 + monthlyReturn) + additions[i];

            System.out.println("Prev: " + prevVal + " additions: " + additions[i] + " expected: " + expectedValues[i]);
        }

        return expectedValues;
    }

    public static double[] getTotalAdditions(final FinanceData financeData) {
        double[] additions = getAdditions(financeData);

        double[] runningTotals = new double[additions.length];
        for (int i = 0; i < additions.length; i++) {
            if (i == 0) {
                runningTotals[i] = additions[i];
            } else {
                runningTotals[i] = runningTotals[i - 1] + additions[i];
            }
        }

        return runningTotals;
    }

}
