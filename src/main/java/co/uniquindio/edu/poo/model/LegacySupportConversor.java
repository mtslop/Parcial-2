package co.uniquindio.edu.poo.model;

import java.util.HashMap;
import java.util.Map;

public class LegacySupportConversor implements DisponibilidadTecnicos {

    private LegacySupport legacySupport;
    private Map<String, Integer> codigosEspecialidad;

    /**
     * Crea el conversor.
     *
     * @param legacySupport plataforma externa a adaptar
     */
    public LegacySupportConversor(LegacySupport legacySupport) {
        this.legacySupport = legacySupport;
        this.codigosEspecialidad = new HashMap<>();
        this.codigosEspecialidad.put("MANTENIMIENTO", 1);
        this.codigosEspecialidad.put("REDES", 2);
        this.codigosEspecialidad.put("RECUPERACION", 3);
    }

    /**
     * Consulta la disponibilidad de un tecnico para una especialidad.
     *
     * @param especialidad nombre de la especialidad
     * @return el tecnico con su disponibilidad y desplazamiento
     */
    @Override
    public Tecnico consultar(String especialidad) {
        return consultarEstadoTecnico(especialidad);
    }

    /**
     * Traduce la especialidad a codigo, consulta LegacySupport e interpreta la cadena.
     *
     * @param especialidad nombre de la especialidad
     * @return el tecnico con su disponibilidad y desplazamiento
     */
    public Tecnico consultarEstadoTecnico(String especialidad) {
        int codigo = codigosEspecialidad.getOrDefault(especialidad, 0);
        String[] partes = legacySupport.consultarTecnico(codigo).split("\\|");

        boolean disponible = partes[1].equals("DISPONIBLE");
        double desplazamiento = Double.parseDouble(partes[2]);

        if (!disponible) {
            return new Tecnico(partes[0], "Sin tecnico", especialidad, "N/A", false, 0);
        }
        return new Tecnico(partes[0], "Tecnico " + partes[0], especialidad, "No registrado", true, desplazamiento);
    }

    public LegacySupport getLegacySupport() {
        return legacySupport;
    }

    public void setLegacySupport(LegacySupport legacySupport) {
        this.legacySupport = legacySupport;
    }
}