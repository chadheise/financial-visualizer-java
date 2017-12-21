package chadheise.finance;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FinanceData {

    // Year -> Month -> value
    private final Map<Integer, Map<Integer, LocalDate>> dates = new HashMap<Integer, Map<Integer, LocalDate>>();
    private final Map<Integer, Map<Integer, Double>> beginningBalance = new HashMap<Integer, Map<Integer, Double>>();
    private final Map<Integer, Map<Integer, Double>> additions = new HashMap<Integer, Map<Integer, Double>>();
    private final Map<Integer, Map<Integer, Double>> changeInValue = new HashMap<Integer, Map<Integer, Double>>();

    public LocalDate getDate(final int year, final int month) {
        return dates.get(year).get(month);
    }

    public double getBeginningBalance(final int year, final int month) {
        return beginningBalance.get(year).get(month);
    }

    public double getAdditions(final int year, final int month) {
        return additions.get(year).get(month);
    }

    public double getChangeInValue(final int year, final int month) {
        return changeInValue.get(year).get(month);
    }

    public double getEndingBalance(final int year, final int month) {
        return getBeginningBalance(year, month) + getAdditions(year, month) + getChangeInValue(year, month);
    }

    public void addEntry(final LocalDate date, final Double begBal, final Double add,
            final Double valChange) {
        addDate(date);
        addBeginningBalance(date, begBal);
        addAdditions(date, add);
        addChangeInValue(date, valChange);
    }

    private void addDate(LocalDate date) {
        int year = date.getYear();
        int month = date.getMonthValue();

        if (!dates.containsKey(year)) {
            dates.put(year, new HashMap<Integer, LocalDate>());
        }
        dates.get(year).put(month, date);
    }

    private void addBeginningBalance(LocalDate date, double begBal) {
        int year = date.getYear();
        int month = date.getMonthValue();

        if (!beginningBalance.containsKey(year)) {
            beginningBalance.put(year, new HashMap<Integer, Double>());
        }
        beginningBalance.get(year).put(month, begBal);
    }

    private void addAdditions(LocalDate date, double add) {
        int year = date.getYear();
        int month = date.getMonthValue();

        if (!additions.containsKey(year)) {
            additions.put(year, new HashMap<Integer, Double>());
        }
        additions.get(year).put(month, add);
    }

    private void addChangeInValue(LocalDate date, double valChange) {
        int year = date.getYear();
        int month = date.getMonthValue();

        if (!changeInValue.containsKey(year)) {
            changeInValue.put(year, new HashMap<Integer, Double>());
        }
        changeInValue.get(year).put(month, valChange);
    }

    /**
     * Returns a sorted list of the years for which data is available
     * 
     * @return
     */
    public List<Integer> getYears() {
        List<Integer> years = new ArrayList<Integer>(dates.keySet());
        Collections.sort(years);
        return years;
    }

    /**
     * Get the sorted months available for a given year.
     * 
     * @param year
     * @return
     */
    public List<Integer> getMonths(final int year) {
        List<Integer> months = new ArrayList<Integer>(dates.get(year).keySet());
        Collections.sort(months);
        return months;
    }
}
