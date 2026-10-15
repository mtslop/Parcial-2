package co.uniquindio.edu.poo.model;

import co.uniquindio.edu.poo.model.ElementoTecnologico;
import co.uniquindio.edu.poo.model.ServicioBase;


public abstract class ServicioDecorator implements ElementoTecnologico {
    protected ElementoTecnologico elemento;
    protected ServicioBase servicioDecorar;

    /**
     * @param elemento elemento a decorar
     * @throws IllegalArgumentException si es nulo o ya tiene este mismo complemento
     */
    protected ServicioDecorator(ElementoTecnologico elemento) {
        if (elemento == null) {
            throw new IllegalArgumentException("El elemento a decorar no puede ser nulo");
        }
        if (elemento instanceof ServicioDecorator
                && ((ServicioDecorator) elemento).tieneComplemento(getClass())) {
            throw new IllegalArgumentException("El servicio ya incluye esta caracteristica complementaria");
        }
        this.elemento = elemento;

        if (elemento instanceof ServicioBase) {
            this.servicioDecorar = (ServicioBase) elemento;
        } else if (elemento instanceof ServicioDecorator) {
            this.servicioDecorar = ((ServicioDecorator) elemento).getServicioDecorar();
        }
    }

    /**
     * @return el incremento de costo del complemento
     */
    public abstract double getIncremento();

    /**
     * @return el nombre del complemento
     */
    public abstract String getNombreComplemento();

    /**
     * @param tipo clase del complemento buscado
     * @return true si la cadena de decoradores ya incluye ese complemento
     */
    public boolean tieneComplemento(Class<?> tipo) {
        if (tipo.isInstance(this)) {
            return true;
        }
        if (elemento instanceof ServicioDecorator) {
            return ((ServicioDecorator) elemento).tieneComplemento(tipo);
        }
        return false;
    }

    @Override
    public double calcularCosto() {
        return elemento.calcularCosto();
    }

    @Override
    public boolean requiereTecnico() {
        return elemento.requiereTecnico();
    }

    @Override
    public String getCodigo() {
        return elemento.getCodigo();
    }

    @Override
    public String getNombre() {
        return elemento.getNombre();
    }

    @Override
    public String getDescripcion() {
        return elemento.getDescripcion() + " + " + getNombreComplemento();
    }

    @Override
    public double getCosto() {
        return elemento.getCosto();
    }

    @Override
    public String getEspecialidad() {
        return elemento.getEspecialidad();
    }

    @Override
    public boolean contieneElemento(ElementoTecnologico objetivo) {
        return this == objetivo || elemento.contieneElemento(objetivo);
    }

    public ElementoTecnologico getElemento() {
        return elemento;
    }

    public ServicioBase getServicioDecorar() {
        return servicioDecorar;
    }
}