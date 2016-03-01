package chadheise.finance;

import java.util.HashMap;
import java.util.Map;

public class Utils {

    public static Map<Integer, Map<Integer, Double>> getTotalAdditions(final FinanceData financeData) {
        Map<Integer, Map<Integer, Double>> additions = new HashMap<Integer, Map<Integer, Double>>();

        double runningTotal = 0;
        for (int year : financeData.getYears()) {
            additions.put(year, new HashMap<Integer, Double>());
            for (int month : financeData.getMonths(year)) {
                runningTotal += financeData.getAdditions(year, month);
                additions.get(year).put(month, runningTotal);
            }
        }

        return additions;
    }

    public static Map<Integer, Map<Integer, Double>> getExpectedBalances(final FinanceData financeData,
            double yearlyReturn) {

        double monthlyReturn = Math.pow(1 + yearlyReturn, (1.0 / 12.0)) - 1;

        Map<Integer, Map<Integer, Double>> expectedBalances = new HashMap<Integer, Map<Integer, Double>>();

        double previousBalance = 0;
        for (int year : financeData.getYears()) {
            expectedBalances.put(year, new HashMap<Integer, Double>());
            // TODO: This will not be accurate if there are months with missing
            // data
            for (int month : financeData.getMonths(year)) {
                double newBalance = previousBalance * (1 + monthlyReturn) + financeData.getAdditions(year, month);
                expectedBalances.get(year).put(month, newBalance);
                previousBalance = newBalance;
            }
        }

        return expectedBalances;
    }

}
