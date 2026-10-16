package co.uniquindio.edu.poo.model;

import co.uniquindio.edu.poo.model.MantenimientoCorrectivo;
import co.uniquindio.edu.poo.model.ServicioBase;

/**
 * Fabrica de mantenimientos correctivos.
 */
public class MantenimientoCorrectivoFactory implements ServicioBaseFactory {

    @Override
    public ServicioBase crearServicio(String codigo, String descripcion) {
        return new MantenimientoCorrectivo(codigo, descripcion);
    }
}