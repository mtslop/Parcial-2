import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import co.uniquindio.edu.poo.model.PaqueteTecnologico;
import co.uniquindio.edu.poo.model.MantenimientoPreventivo;

class PruebasSimplesTest {
    /**
     * Prueba unitaria que sirve para comprobar la correcta creacion de un objeto de tipo mantenimiento preventivo
     * la correcta obtencion del codigo y la correcta obtencion de la descripcion y que sean iguales a lo esperado
     * esperado:NoNull obtenido:se crea correctamente y no es vacio
     * esperado:SRV-001 actual:SRV-001
     * esperado:Limpieza general   actual:Limpieza general
     */
    @Test
    void verificarDatosServicioPreventivo() {
        MantenimientoPreventivo servicio = new MantenimientoPreventivo("SRV-001", "Limpieza general");

        assertNotNull(servicio);
        assertEquals("SRV-001", servicio.getCodigo());
        assertEquals("Limpieza general", servicio.getDescripcion());
    }

    /**
     * pruba unitaria que sirve para comprobar que el precio incial o costo inicial de un paquete tecnologico sea 0.0
     * esperado:0.0   actual:0.0
     */
    @Test
    void paqueteIniciaConCostoCero() {
        PaqueteTecnologico paquete = new PaqueteTecnologico("PAQ-001", "Paquete Básico", "Soporte inicial");
        assertEquals(0.0, paquete.calcularCosto());
    }
}