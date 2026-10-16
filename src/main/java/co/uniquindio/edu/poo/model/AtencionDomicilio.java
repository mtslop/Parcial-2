package co.uniquindio.edu.poo.model;

import co.uniquindio.edu.poo.model.ElementoTecnologico;


public class AtencionDomicilio extends ServicioDecorator {

    public static final double INCREMENTO = 25000;

    /**
     * @param elemento elemento a decorar
     */
    public AtencionDomicilio(ElementoTecnologico elemento) {
        super(elemento);
    }

    @Override
    public double calcularCosto() {
        return super.calcularCosto() + INCREMENTO;
    }

    @Override
    public boolean requiereTecnico() {
        return true;
    }

    @Override
    public double getIncremento() {
        return INCREMENTO;
    }

    @Override
    public String getNombreComplemento() {
        return "Atencion a domicilio";
    }
}