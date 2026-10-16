import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import co.uniquindio.edu.poo.model.PaqueteTecnologico;
import co.uniquindio.edu.poo.model.MantenimientoPreventivo;

class PruebasSimplesTest {

    @Test
    void verificarDatosServicioPreventivo() {
        MantenimientoPreventivo servicio = new MantenimientoPreventivo("SRV-001", "Limpieza general");

        assertNotNull(servicio);
        assertEquals("SRV-001", servicio.getCodigo());
        assertEquals("Limpieza general", servicio.getDescripcion());
    }

    @Test
    void paqueteIniciaConCostoCero() {
        PaqueteTecnologico paquete = new PaqueteTecnologico("PAQ-001", "Paquete Básico", "Soporte inicial");
        assertEquals(0.0, paquete.calcularCosto());
    }
}