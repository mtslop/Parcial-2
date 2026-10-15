package co.uniquindio.edu.poo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import co.uniquindio.edu.poo.model.InstalacionRedFactory;
import co.uniquindio.edu.poo.model.MantenimientoCorrectivoFactory;
import co.uniquindio.edu.poo.model.MantenimientoPreventivoFactory;
import co.uniquindio.edu.poo.model.RecuperacionInformacionFactory;
import co.uniquindio.edu.poo.model.ServicioBaseFactory;
import co.uniquindio.edu.poo.model.PaqueteTecnologico;
import co.uniquindio.edu.poo.model.ServicioBase;
import co.uniquindio.edu.poo.model.LegacySupport;
import co.uniquindio.edu.poo.model.LegacySupportConversor;


public class TechSolutions {

    private static TechSolutions instancia;

    private String nombre;
    private List<SolicitudServicio> listaSolicitudes;
    private List<Cliente> listaClientes;
    private List<ServicioBase> catalogo;
    private List<PaqueteTecnologico> listaPaquetes;
    private GestorSolicitudes gestor;
    private int contadorSolicitudes;

    private TechSolutions(String nombre) {
        this.nombre = nombre;
        this.listaSolicitudes = new ArrayList<>();
        this.listaClientes = new ArrayList<>();
        this.catalogo = new ArrayList<>();
        this.listaPaquetes = new ArrayList<>();
        this.gestor = new GestorSolicitudes(new LegacySupportConversor(new LegacySupport()));
        this.contadorSolicitudes = 0;
        cargarDatosIniciales();
    }

    /**
     * @return la unica instancia de TechSolutions
     */
    public static synchronized TechSolutions obtenerInstancia() {
        if (instancia == null) {
            instancia = new TechSolutions("TechSolutions");
        }
        return instancia;
    }

    private void cargarDatosIniciales() {
        listaClientes.add(new Cliente("1001", "Laura Martinez", "laura@correo.com", "3001112233"));
        listaClientes.add(new Cliente("1002", "Carlos Gomez", "carlos@correo.com", "3004445566"));
        listaClientes.add(new Cliente("1003", "Empresa Andina SAS", "contacto@andina.com", "3007778899"));

        ServicioBaseFactory preventivo = new MantenimientoPreventivoFactory();
        ServicioBaseFactory correctivo = new MantenimientoCorrectivoFactory();
        ServicioBaseFactory redes = new InstalacionRedFactory();
        ServicioBaseFactory recuperacion = new RecuperacionInformacionFactory();

        catalogo.add(preventivo.crearServicio("SRV-001", "Revision y limpieza de equipos"));
        catalogo.add(correctivo.crearServicio("SRV-002", "Reparacion de fallas en equipos"));
        catalogo.add(redes.crearServicio("SRV-003", "Instalacion de redes de datos"));
        catalogo.add(recuperacion.crearServicio("SRV-004", "Recuperacion de datos perdidos"));
    }

    /**
     * @return un codigo unico para una nueva solicitud (por ejemplo SOL-0001)
     */
    public String generarCodigoSolicitud() {
        contadorSolicitudes++;
        return String.format("SOL-%04d", contadorSolicitudes);
    }

    /**
     * Confirma y almacena una solicitud.
     *
     * @param solicitud solicitud a registrar
     * @throws IllegalStateException si el codigo existe o no hay tecnico disponible
     */
    public void registrarSolicitud(SolicitudServicio solicitud) {
        if (consultarSolicitud(solicitud.getCodigo()) != null) {
            throw new IllegalStateException("Ya existe una solicitud con el codigo " + solicitud.getCodigo());
        }
        gestor.registrar(solicitud);
        listaSolicitudes.add(solicitud);
    }

    /**
     * @param codigo codigo de la solicitud
     * @return la solicitud encontrada o null
     */
    public SolicitudServicio consultarSolicitud(String codigo) {
        for (SolicitudServicio solicitud : listaSolicitudes) {
            if (solicitud.getCodigo().equals(codigo)) {
                return solicitud;
            }
        }
        return null;
    }

    /**
     * @param documento documento del cliente
     * @return el cliente encontrado o null
     */
    public Cliente buscarCliente(String documento) {
        for (Cliente cliente : listaClientes) {
            if (cliente.getDocumento().equals(documento)) {
                return cliente;
            }
        }
        return null;
    }

    /**
     * @param servicio servicio a agregar al catalogo
     * @throws IllegalArgumentException si el codigo ya existe
     */
    public void agregarServicio(ServicioBase servicio) {
        for (ServicioBase existente : catalogo) {
            if (existente.getCodigo().equals(servicio.getCodigo())) {
                throw new IllegalArgumentException("Ya existe un servicio con el codigo " + servicio.getCodigo());
            }
        }
        catalogo.add(servicio);
    }

    /**
     * @param paquete paquete a agregar
     * @throws IllegalArgumentException si el codigo ya existe
     */
    public void agregarPaquete(PaqueteTecnologico paquete) {
        for (PaqueteTecnologico existente : listaPaquetes) {
            if (existente.getCodigo().equals(paquete.getCodigo())) {
                throw new IllegalArgumentException("Ya existe un paquete con el codigo " + paquete.getCodigo());
            }
        }
        listaPaquetes.add(paquete);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<SolicitudServicio> getListaSolicitudes() {
        return Collections.unmodifiableList(listaSolicitudes);
    }

    public List<Cliente> getListaClientes() {
        return Collections.unmodifiableList(listaClientes);
    }

    public List<ServicioBase> getCatalogo() {
        return Collections.unmodifiableList(catalogo);
    }

    public List<PaqueteTecnologico> getListaPaquetes() {
        return Collections.unmodifiableList(listaPaquetes);
    }

    public GestorSolicitudes getGestor() {
        return gestor;
    }
}