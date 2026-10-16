package co.uniquindio.edu.poo.model;

public class MantenimientoCorrectivo extends ServicioBase {

    public static final double COSTO_BASE = 120000;

    /**
     * @param codigo codigo del servicio
     * @param descripcion descripcion del servicio
     */
    public MantenimientoCorrectivo(String codigo, String descripcion) {
        super(codigo, "Mantenimiento correctivo", descripcion, COSTO_BASE);
    }

    @Override
    public String getEspecialidad() {
        return "MANTENIMIENTO";
    }
}