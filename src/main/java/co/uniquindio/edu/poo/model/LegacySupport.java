package co.uniquindio.edu.poo.model;

public class LegacySupport {

    /**
     * Consulta un tecnico por codigo numerico de especialidad.
     *
     * @param codigoEspecialidad codigo de la especialidad
     * @return cadena con formato "ID|ESTADO|DESPLAZAMIENTO"
     */
    public String consultarTecnico(int codigoEspecialidad) {
        switch (codigoEspecialidad) {
            case 1:
                return "TEC-101|DISPONIBLE|25000";
            case 2:
                return "TEC-105|DISPONIBLE|30000";
            default:
                return "SIN_TECNICO|NO_DISPONIBLE|0";
        }
    }
}