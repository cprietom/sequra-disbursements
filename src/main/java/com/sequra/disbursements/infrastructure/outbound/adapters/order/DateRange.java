package com.sequra.disbursements.infrastructure.outbound.adapters.order;

import com.sequra.disbursements.domain.model.Period;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.time.temporal.IsoFields;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
class DateRange {
    private Date initDate;
    private Date endDate;

    DateRange(Period period) {
        LocalDate localDate = LocalDate.of(period.getYear(), 1, 1)
                .with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, period.getWeek())
                .with(ChronoField.DAY_OF_WEEK, DayOfWeek.MONDAY.getValue());
        this.initDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        this.endDate = Date.from(localDate.plusDays(7).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
