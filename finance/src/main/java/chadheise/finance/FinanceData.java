package chadheise.finance;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class FinanceData {

    private final TreeSet<LocalDate> dates = new TreeSet<>();
    private final Map<LocalDate, Double> beginningBalances = new HashMap<>();
    private final Map<LocalDate, Double> additions = new HashMap<>();
    private final Map<LocalDate, Double> changeInValue = new HashMap<>();

    public TreeSet<LocalDate> getDates() {
        return dates;
    }

    public double getBeginningBalance(final LocalDate date) {
        return beginningBalances.get(date);
    }

    public double getAdditions(final LocalDate date) {
        return additions.get(date);
    }

    public double getChangeInValue(final LocalDate date) {
        return changeInValue.get(date);
    }

    public double getEndingBalance(final LocalDate date) {
        return beginningBalances.get(date) + additions.get(date) + changeInValue.get(date);
    }

    public void addEntry(final LocalDate date, final Double begBal, final Double add,
            final Double valChange) {
        dates.add(date);
        beginningBalances.put(date, begBal);
        additions.put(date, add);
        changeInValue.put(date, valChange);
    }
}
