package co.uniquindio.edu.poo.model;

import co.uniquindio.edu.poo.model.DisponibilidadTecnicos;
import co.uniquindio.edu.poo.model.Tecnico;


public class GestorSolicitudes {

    private DisponibilidadTecnicos disponibilidadTecnicos;

    /**
     * @param disponibilidadTecnicos proveedor de disponibilidad de tecnicos
     */
    public GestorSolicitudes(DisponibilidadTecnicos disponibilidadTecnicos) {
        this.disponibilidadTecnicos = disponibilidadTecnicos;
    }

    /**
     * @param especialidad nombre de la especialidad
     * @return el tecnico consultado con su disponibilidad y desplazamiento
     */
    public Tecnico consultarTecnico(String especialidad) {
        return disponibilidadTecnicos.consultar(especialidad);
    }

    /**
     * Verifica la disponibilidad de tecnico y, si existe, lo vincula a la solicitud.
     *
     * @param solicitud solicitud a validar
     * @return true si no requiere tecnico o si hay uno disponible
     */
    public boolean validarDisponibilidad(SolicitudServicio solicitud) {
        if (!solicitud.requiereTecnico()) {
            return true;
        }
        Tecnico tecnico = disponibilidadTecnicos.consultar(solicitud.getEspecialidadRequerida());
        if (!tecnico.isDisponible()) {
            return false;
        }
        solicitud.vincularTecnicoSolicitud(tecnico);
        return true;
    }

    /**
     * @param solicitud solicitud a calcular
     * @return el costo total de la solicitud
     */
    public double calcularTotal(SolicitudServicio solicitud) {
        return solicitud.calcularCostoTotal();
    }

    /**
     * Confirma la solicitud si hay tecnico disponible cuando se requiere.
     *
     * @param solicitud solicitud a registrar
     * @throws IllegalStateException si requiere tecnico y no hay ninguno disponible
     */
    public void registrar(SolicitudServicio solicitud) {
        if (!validarDisponibilidad(solicitud)) {
            throw new IllegalStateException("No hay tecnicos disponibles para la especialidad "
                    + solicitud.getEspecialidadRequerida() + ". La solicitud no puede confirmarse");
        }
        solicitud.setConfirmada(true);
    }

    public DisponibilidadTecnicos getDisponibilidadTecnicos() {
        return disponibilidadTecnicos;
    }

    public void setDisponibilidadTecnicos(DisponibilidadTecnicos disponibilidadTecnicos) {
        this.disponibilidadTecnicos = disponibilidadTecnicos;
    }
}