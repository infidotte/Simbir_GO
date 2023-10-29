package simbir.go.simbir_go.Utilit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

public class DateCalculator {
    private final LocalDateTime start;
    private final LocalDateTime end;

    public DateCalculator(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    public Long calculateMinutes() {
        return Duration.between(start, end).toMinutes();
    }

    public Long calculateDays() {
        return Duration.between(start, end).toDays();
    }

}
