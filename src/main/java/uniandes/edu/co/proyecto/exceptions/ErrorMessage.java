package uniandes.edu.co.proyecto.exceptions;

public final class ErrorMessage {
    public static final String LUGAR_INTERES_NOT_FOUND = 
        "The point of interest with the given id was not found";
    public static final String RESERVA_NOT_FOUND =
        "The reservation with the given id was not found";
    public static final String VIVIENDA_NOT_FOUND = null;
    
    public static final String ESTUDIANTE_NOT_FOUND =
        "The student with the given id was not found";

    private ErrorMessage() {
        throw new IllegalStateException("Utility class");
    }
}
