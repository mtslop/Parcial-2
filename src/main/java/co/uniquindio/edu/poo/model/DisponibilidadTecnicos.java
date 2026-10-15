package co.uniquindio.edu.poo.model;

public interface DisponibilidadTecnicos {

    /**
     * Consulta el tecnico disponible para una especialidad.
     *
     * @param especialidad nombre de la especialidad
     * @return el tecnico consultado; si no hay disponibilidad, isDisponible() es false
     */
    Tecnico consultar(String especialidad);
}