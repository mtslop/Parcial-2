package co.uniquindio.edu.poo.viewcontroller;

import co.uniquindio.edu.poo.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PaquetesViewController {

    @FXML private TextField txtCodigo;
    @FXML private TextField txtNombre;
    @FXML private TextField txtDescripcion;
    @FXML private Label lblMensajePaquete;
    @FXML private ListView<PaqueteTecnologico> lstPaquetes;
    @FXML private Label lblPaqueteSeleccionado;
    @FXML private ComboBox<ElementoTecnologico> cmbComponente;
    @FXML private Label lblMensajeComponente;
    @FXML private TreeView<ElementoTecnologico> treeComponentes;
    @FXML private Label lblCostoPaquete;
    private ObservableList<PaqueteTecnologico> listaPaquetes;
    private ObservableList<ElementoTecnologico> listaElementosDisponibles;
    private PaqueteTecnologico paqueteSeleccionado;

    @FXML
    public void initialize() {
        listaPaquetes = FXCollections.observableArrayList();
        listaElementosDisponibles = FXCollections.observableArrayList();

        lstPaquetes.setItems(listaPaquetes);
        cmbComponente.setItems(listaElementosDisponibles);

        lstPaquetes.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                paqueteSeleccionado = newVal;
                actualizarPanelPaquete();
            }
        });

        cargarDatosPrueba();
    }

    private void cargarDatosPrueba() {
        try {
            PaqueteTecnologico paqueteBase = new PaqueteTecnologico("PAQ-001", "Kit Mantenimiento Oficina", "Preventivo y correctivo básico");
            paqueteBase.agregarElemento(new MantenimientoPreventivo("SRV-101", "Limpieza de equipos"));
            listaPaquetes.add(paqueteBase);

            listaElementosDisponibles.add(paqueteBase);
        } catch (Exception e) {
            System.err.println("Error al cargar datos iniciales: " + e.getMessage());
        }
    }

    @FXML
    private void onCrearPaquete() {
        try {
            String codigo = txtCodigo.getText();
            String nombre = txtNombre.getText();
            String descripcion = txtDescripcion.getText();

            PaqueteTecnologico nuevoPaquete = new PaqueteTecnologico(codigo, nombre, descripcion);
            listaPaquetes.add(nuevoPaquete);
            listaElementosDisponibles.add(nuevoPaquete);

            lblMensajePaquete.setText("¡Paquete creado con éxito!");
            limpiarFormularioCreacion();

        } catch (Exception e) {
            lblMensajePaquete.setText("Error: " + e.getMessage());
        }
    }

    private void limpiarFormularioCreacion() {
        txtCodigo.clear();
        txtNombre.clear();
        txtDescripcion.clear();
    }

    private void actualizarPanelPaquete() {
        if (paqueteSeleccionado != null) {
            lblPaqueteSeleccionado.setText(paqueteSeleccionado.getNombre() + " (" + paqueteSeleccionado.getCodigo() + ")");
            lblCostoPaquete.setText("$" + paqueteSeleccionado.calcularCosto());
            if (lblMensajeComponente != null) {
                lblMensajeComponente.setText("");
            }
            construirArbolComponentes();
        }
    }

    private void construirArbolComponentes() {
        if (paqueteSeleccionado == null) return;

        TreeItem<ElementoTecnologico> raizArbol = new TreeItem<>(paqueteSeleccionado);
        raizArbol.setExpanded(true);

        for (ElementoTecnologico elemento : paqueteSeleccionado.getListaElementos()) {
            agregarHijosAlArbol(elemento, raizArbol);
        }

        treeComponentes.setRoot(raizArbol);
    }

    private void agregarHijosAlArbol(ElementoTecnologico elemento, TreeItem<ElementoTecnologico> padreItem) {
        TreeItem<ElementoTecnologico> itemActual = new TreeItem<>(elemento);
        padreItem.getChildren().add(itemActual);

        if (elemento instanceof PaqueteTecnologico paqueteHijo) {
            itemActual.setExpanded(true);
            for (ElementoTecnologico subElemento : paqueteHijo.getListaElementos()) {
                agregarHijosAlArbol(subElemento, itemActual);
            }
        }
    }

    @FXML
    private void onAgregarComponente() {
        try {
            if (paqueteSeleccionado == null) {
                lblMensajeComponente.setText("Seleccione un paquete primero.");
                return;
            }

            ElementoTecnologico elementoSeleccionado = cmbComponente.getValue();
            if (elementoSeleccionado == null) {
                lblMensajeComponente.setText("Seleccione un componente válido.");
                return;
            }

            paqueteSeleccionado.agregarElemento(elementoSeleccionado);

            lblMensajeComponente.setText("¡Componente agregado correctamente!");
            actualizarPanelPaquete();

        } catch (Exception e) {
            lblMensajeComponente.setText("Error: " + e.getMessage());
        }
    }
}