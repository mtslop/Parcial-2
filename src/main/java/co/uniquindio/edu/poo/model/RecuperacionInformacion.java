package co.uniquindio.edu.poo.model;

public class RecuperacionInformacion extends ServicioBase {

    public static final double COSTO_BASE = 150000;

    /**
     * @param codigo codigo del servicio
     * @param descripcion descripcion del servicio
     */
    public RecuperacionInformacion(String codigo, String descripcion) {
        super(codigo, "Recuperacion de informacion", descripcion, COSTO_BASE);
    }

    @Override
    public String getEspecialidad() {
        return "RECUPERACION";
    }
}