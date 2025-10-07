package uniandes.edu.co.proyecto.util;

public final class Geo {
    private Geo(){}

    /** "lat,long" -> double[]{lat, lon} */
    public static double[] parseLatLon(String coord) {
        if (coord == null) return null;
        String[] parts = coord.trim().split("\\s*,\\s*");
        if (parts.length != 2) return null;
        try {
            return new double[]{ Double.parseDouble(parts[0]), Double.parseDouble(parts[1]) };
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
