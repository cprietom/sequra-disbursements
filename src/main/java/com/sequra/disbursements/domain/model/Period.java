package com.sequra.disbursements.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Period implements Comparable {
    private Integer year;
    private Integer week;

    @Override
    public int compareTo(Object o) {
        Period p = (Period) o;
        if (year < p.getYear()) {
            return -1;
        } else if (year > p.getYear()) {
            return 1;
        } else {
            return week.compareTo(p.getWeek());
        }
    }
}
