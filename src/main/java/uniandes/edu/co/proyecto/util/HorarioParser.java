package uniandes.edu.co.proyecto.util;
import java.time.LocalTime;

import java.util.Optional;

public final class HorarioParser {
    private HorarioParser(){}

    /** "08:00-18:30" -> TimeRange */
    public static Optional<TimeRange> parse(String horario) {
        if (horario == null) return Optional.empty();
        String[] p = horario.split("-");
        if (p.length != 2) return Optional.empty();
        try {
            LocalTime a = LocalTime.parse(p[0].trim());
            LocalTime b = LocalTime.parse(p[1].trim());
            return Optional.of(new TimeRange(a,b));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /** Valida formato simple HH:mm-HH:mm */
    public static boolean isValid(String horario) {
        return parse(horario).isPresent();
    }
}
