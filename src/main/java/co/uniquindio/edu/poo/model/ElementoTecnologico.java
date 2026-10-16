package co.uniquindio.edu.poo.model;

public interface ElementoTecnologico {

    String getCodigo();

    String getNombre();

    String getDescripcion();

    /**
     * @return el costo base, sin caracteristicas complementarias
     */
    double getCosto();

    /**
     * @return el costo final, con caracteristicas complementarias
     */
    double calcularCosto();

    /**
     * @return true si requiere atencion presencial de un tecnico
     */
    boolean requiereTecnico();

    /**
     * @return la especialidad del tecnico requerido
     */
    default String getEspecialidad() {
        return "GENERAL";
    }

    /**
     * Indica si el elemento es o contiene (en cualquier nivel) al objetivo.
     *
     * @param objetivo elemento buscado
     * @return true si lo es o lo contiene
     */
    default boolean contieneElemento(ElementoTecnologico objetivo) {
        return this == objetivo;
    }
}