package co.uniquindio.edu.poo.model;

import co.uniquindio.edu.poo.model.InstalacionRed;
import co.uniquindio.edu.poo.model.ServicioBase;

/**
 * Fabrica de instalaciones de red.
 */
public class InstalacionRedFactory implements ServicioBaseFactory {

    @Override
    public ServicioBase crearServicio(String codigo, String descripcion) {
        return new InstalacionRed(codigo, descripcion);
    }
}