package uniandes.edu.co.proyecto.util;
import java.time.LocalTime;

public final class TimeRange {
    public final LocalTime start, end;
    public TimeRange(LocalTime start, LocalTime end) {
        if (start.isAfter(end)) throw new IllegalArgumentException("start > end");
        this.start = start; this.end = end;
    }
    public boolean overlaps(TimeRange other) {
        // [start, end) vs [other.start, other.end)
        return !this.end.isBefore(other.start) && !other.end.isBefore(this.start);
    }
}
