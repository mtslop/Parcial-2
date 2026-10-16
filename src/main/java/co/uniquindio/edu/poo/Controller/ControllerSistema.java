package co.uniquindio.edu.poo.Controller;

import co.uniquindio.edu.poo.model.*;
import java.util.List;

public class ControllerSistema {

    private static ControllerSistema instancia;
    private TechSolutions sistema;

    private ControllerSistema() {
        this.sistema = TechSolutions.obtenerInstancia();
    }

    public static synchronized ControllerSistema obtenerInstancia() {
        if (instancia == null) {
            instancia = new ControllerSistema();
        }
        return instancia;
    }

    public List<Cliente> obtenerClientes() {
        return sistema.getListaClientes();
    }

    public Cliente buscarCliente(String documento) {
        return sistema.buscarCliente(documento);
    }

    public List<ServicioBase> obtenerCatalogo() {
        return sistema.getCatalogo();
    }

    public void agregarServicio(ServicioBase servicio) {
        sistema.agregarServicio(servicio);
    }

    public List<PaqueteTecnologico> obtenerPaquetes() {
        return sistema.getListaPaquetes();
    }

    public void agregarPaquete(PaqueteTecnologico paquete) {
        sistema.agregarPaquete(paquete);
    }

    public List<SolicitudServicio> obtenerSolicitudes() {
        return sistema.getListaSolicitudes();
    }

    public void registrarSolicitud(SolicitudServicio solicitud) {
        sistema.registrarSolicitud(solicitud);
    }

    public SolicitudServicio consultarSolicitud(String codigo) {
        return sistema.consultarSolicitud(codigo);
    }

    public String generarCodigoSolicitud() {
        return sistema.generarCodigoSolicitud();
    }

    public List<Tecnico> obtenerTecnicos() {
        return sistema.getListaTecnicos();
    }

    public void agregarTecnico(Tecnico tecnico) {
        sistema.agregarTecnico(tecnico);
    }
}