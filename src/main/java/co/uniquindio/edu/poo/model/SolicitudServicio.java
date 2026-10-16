package co.uniquindio.edu.poo.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import co.uniquindio.edu.poo.model.ElementoTecnologico;
import co.uniquindio.edu.poo.model.Tecnico;


public class SolicitudServicio {

    private String codigo;
    private LocalDateTime fechaCreacion;
    private Cliente cliente;
    private List<ElementoTecnologico> elementos;
    private LocalDate fechaProgramada;
    private String observacionesTecnicas;
    private String prioridad;
    private List<Tecnico> listaTecnicos;
    private double costoDesplazamiento;
    private boolean confirmada;

    /**
     * Crea una solicitud validando la informacion esencial.
     *
     * @param codigo codigo unico de la solicitud
     * @param cliente cliente asociado
     * @param elementos servicios o paquetes contratados (al menos uno)
     * @throws IllegalArgumentException si falta informacion obligatoria
     */
    public SolicitudServicio(String codigo, Cliente cliente, List<ElementoTecnologico> elementos) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("El codigo de la solicitud es obligatorio");
        }
        if (cliente == null || !cliente.validarDatos()) {
            throw new IllegalArgumentException("La solicitud requiere un cliente con datos validos");
        }
        if (elementos == null || elementos.isEmpty()) {
            throw new IllegalArgumentException("La solicitud debe incluir al menos un servicio o paquete");
        }
        this.codigo = codigo;
        this.cliente = cliente;
        this.elementos = new ArrayList<>(elementos);
        this.fechaCreacion = LocalDateTime.now();
        this.prioridad = "NORMAL";
        this.listaTecnicos = new ArrayList<>();
        this.costoDesplazamiento = 0;
        this.confirmada = false;
    }

    /**
     * Calcula el costo total: elementos con sus complementos mas el
     * desplazamiento, que se cobra una sola vez.
     *
     * @return el costo total de la solicitud
     */
    public double calcularCostoTotal() {
        double total = 0;
        for (ElementoTecnologico elemento : elementos) {
            total += elemento.calcularCosto();
        }
        return total + costoDesplazamiento;
    }

    /**
     * @return true si algun elemento requiere atencion presencial
     */
    public boolean requiereTecnico() {
        for (ElementoTecnologico elemento : elementos) {
            if (elemento.requiereTecnico()) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return la especialidad del primer elemento que requiere tecnico, o GENERAL
     */
    public String getEspecialidadRequerida() {
        for (ElementoTecnologico elemento : elementos) {
            if (elemento.requiereTecnico()) {
                return elemento.getEspecialidad();
            }
        }
        return "GENERAL";
    }

    /**
     * Vincula un tecnico y toma su costo de desplazamiento. El costo se
     * reemplaza (no se acumula) para no duplicar cargos.
     *
     * @param tecnico tecnico asignado
     */
    public void vincularTecnicoSolicitud(Tecnico tecnico) {
        if (tecnico == null) {
            return;
        }
        if (!listaTecnicos.contains(tecnico)) {
            listaTecnicos.add(tecnico);
        }
        this.costoDesplazamiento = tecnico.getCostoDesplazamiento();
    }

    public String getCodigo() {
        return codigo;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<ElementoTecnologico> getElementos() {
        return Collections.unmodifiableList(elementos);
    }

    public LocalDate getFechaProgramada() {
        return fechaProgramada;
    }

    public void setFechaProgramada(LocalDate fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
    }

    public String getObservacionesTecnicas() {
        return observacionesTecnicas;
    }

    public void setObservacionesTecnicas(String observacionesTecnicas) {
        this.observacionesTecnicas = observacionesTecnicas;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        if (prioridad != null && !prioridad.isBlank()) {
            this.prioridad = prioridad;
        }
    }

    public List<Tecnico> getListaTecnicos() {
        return Collections.unmodifiableList(listaTecnicos);
    }

    public double getCostoDesplazamiento() {
        return costoDesplazamiento;
    }

    public boolean isConfirmada() {
        return confirmada;
    }

    public void setConfirmada(boolean confirmada) {
        this.confirmada = confirmada;
    }

    @Override
    public String toString() {
        return codigo + " - " + cliente.getNombre() + " - $" + calcularCostoTotal();
    }
}