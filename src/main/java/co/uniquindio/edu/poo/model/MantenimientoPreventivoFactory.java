package co.uniquindio.edu.poo.model;

import co.uniquindio.edu.poo.model.MantenimientoPreventivo;
import co.uniquindio.edu.poo.model.ServicioBase;

/**
 * Fabrica de mantenimientos preventivos.
 */
public class MantenimientoPreventivoFactory implements ServicioBaseFactory {

    @Override
    public ServicioBase crearServicio(String codigo, String descripcion) {
        return new MantenimientoPreventivo(codigo, descripcion);
    }
}