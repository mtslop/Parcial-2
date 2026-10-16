package co.uniquindio.edu.poo.model;

import co.uniquindio.edu.poo.model.RecuperacionInformacion;
import co.uniquindio.edu.poo.model.ServicioBase;

/**
 * Fabrica de servicios de recuperacion de informacion.
 */
public class RecuperacionInformacionFactory implements ServicioBaseFactory {

    @Override
    public ServicioBase crearServicio(String codigo, String descripcion) {
        return new RecuperacionInformacion(codigo, descripcion);
    }
}