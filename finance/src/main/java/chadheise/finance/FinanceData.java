package chadheise.finance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

public class FinanceData {

    // Year -> Month -> value
    private final Map<Integer, Map<Integer, DateTime>> dates = new HashMap<Integer, Map<Integer, DateTime>>();
    private final Map<Integer, Map<Integer, Double>> beginningBalance = new HashMap<Integer, Map<Integer, Double>>();
    private final Map<Integer, Map<Integer, Double>> additions = new HashMap<Integer, Map<Integer, Double>>();
    private final Map<Integer, Map<Integer, Double>> changeInValue = new HashMap<Integer, Map<Integer, Double>>();

    public DateTime getDate(final int year, final int month) {
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

    public double getCurrentValue(final int year, final int month) {
        return getBeginningBalance(year, month) + getAdditions(year, month) + getChangeInValue(year, month);
    }

    public void addEntry(final DateTime date, final Double begBal, final Double add,
            final Double valChange) {
        addDate(date);
        addBeginningBalance(date, begBal);
        addAdditions(date, add);
        addChangeInValue(date, valChange);
    }

    private void addDate(DateTime date) {
        int year = date.getYear();
        int month = date.getMonthOfYear();

        if (!dates.containsKey(year)) {
            dates.put(year, new HashMap<Integer, DateTime>());
        }
        dates.get(year).put(month, date);
    }

    private void addBeginningBalance(DateTime date, double begBal) {
        int year = date.getYear();
        int month = date.getMonthOfYear();

        if (!beginningBalance.containsKey(year)) {
            beginningBalance.put(year, new HashMap<Integer, Double>());
        }
        beginningBalance.get(year).put(month, begBal);
    }

    private void addAdditions(DateTime date, double add) {
        int year = date.getYear();
        int month = date.getMonthOfYear();

        if (!additions.containsKey(year)) {
            additions.put(year, new HashMap<Integer, Double>());
        }
        additions.get(year).put(month, add);
    }

    private void addChangeInValue(DateTime date, double valChange) {
        int year = date.getYear();
        int month = date.getMonthOfYear();

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

    public boolean hasData(final int year, final int month) {
        return dates.containsKey(year) && dates.get(year).containsKey(month);
    }

}
