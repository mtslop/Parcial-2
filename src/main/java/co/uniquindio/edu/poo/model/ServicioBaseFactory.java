package co.uniquindio.edu.poo.model;

import co.uniquindio.edu.poo.model.ServicioBase;


public interface ServicioBaseFactory {

    /**
     * @param codigo codigo del servicio
     * @param descripcion descripcion del servicio
     * @return el servicio creado
     */
    ServicioBase crearServicio(String codigo, String descripcion);
}