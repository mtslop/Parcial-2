package co.uniquindio.edu.poo.model;
public class InstalacionRed extends ServicioBase {

    public static final double COSTO_BASE = 180000;

    /**
     * @param codigo codigo del servicio
     * @param descripcion descripcion del servicio
     */
    public InstalacionRed(String codigo, String descripcion) {
        super(codigo, "Instalacion de redes", descripcion, COSTO_BASE);
    }

    @Override
    public boolean requiereTecnico() {
        return true;
    }

    @Override
    public String getEspecialidad() {
        return "REDES";
    }
}