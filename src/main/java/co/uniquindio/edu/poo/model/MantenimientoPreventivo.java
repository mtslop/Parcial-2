package co.uniquindio.edu.poo.model;

public class MantenimientoPreventivo extends ServicioBase {

    public static final double COSTO_BASE = 80000;

    /**
     * @param codigo codigo del servicio
     * @param descripcion descripcion del servicio
     */
    public MantenimientoPreventivo(String codigo, String descripcion) {
        super(codigo, "Mantenimiento preventivo", descripcion, COSTO_BASE);
    }

    @Override
    public String getEspecialidad() {
        return "MANTENIMIENTO";
    }
}