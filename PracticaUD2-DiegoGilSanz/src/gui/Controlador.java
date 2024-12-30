package gui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.sql.Date;

public class Controlador implements ActionListener, ItemListener, ListSelectionListener, WindowListener {
    private Modelo modelo;
    private Vista vista;
    private boolean refrescar;
    public Controlador(Modelo modelo, Vista vista) {
        this.modelo = modelo;
        this.vista = vista;
        addActionListener(this);
        addWindowListener(this);
        iniciar();
        modelo.conectar();
    }
    private void refrescar(){
        refrescarEntrenador();
        refrescarLiga();
        refrescarPeleador();
        refrescar = false;
    }
    private void addActionListener(ActionListener listener){
        vista.btnAñadirPeleador.addActionListener(listener);
        vista.btnAñadirPeleador.setActionCommand("Añadir Peleador");
        vista.btnModificarPeleador.addActionListener(listener);
        vista.btnModificarPeleador.setActionCommand("Modificar Peleador");
        vista.btnEliminarPeleador.addActionListener(listener);
        vista.btnEliminarPeleador.setActionCommand("Eliminar Peleador");
        vista.btnañadirLiga.addActionListener(listener);
        vista.btnañadirLiga.setActionCommand("Añadir Liga");
        vista.btnModificaLiga.addActionListener(listener);
        vista.btnModificaLiga.setActionCommand("Modificar Liga");
        vista.btnEliminarLiga.addActionListener(listener);
        vista.btnEliminarLiga.setActionCommand("Eliminar Liga");
        vista.btnAñadirEntrenador.addActionListener(listener);
        vista.btnAñadirEntrenador.setActionCommand("Añadir Entrenador");
        vista.btnModificarEntrenador.addActionListener(listener);
        vista.btnModificarEntrenador.setActionCommand("Modificar Entrenador");
        vista.btnEliminarEntrenador.addActionListener(listener);
        vista.btnEliminarEntrenador.setActionCommand("Eliminar Entrenador");
        vista.itemSalir.addActionListener(listener);
        vista.itemSalir.setActionCommand("Salir");
        vista.itemOpciones.addActionListener(listener);
        vista.itemOpciones.setActionCommand("Opciones");
        vista.itemDesconectar.addActionListener(listener);
        vista.itemDesconectar.setActionCommand("Desconectar");
    }
    private void addWindowListener(WindowListener listener){
        vista.addWindowListener(listener);
    }
    void iniciar(){
        vista.tablaLiga.setCellSelectionEnabled(true);
        ListSelectionModel cellSelectionModel = vista.tablaLiga.getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() &&  !((ListSelectionModel) e.getSource()).isSelectionEmpty()){
                    if (e.getSource().equals(vista.tablaLiga.getSelectionModel())){
                        int row= vista.tablaLiga.getSelectedRow();
                        vista.txtNombreLiga.setText(String.valueOf(vista.tablaLiga.getValueAt(row,1)));
                        vista.txtDescripcionLiga.setText(String.valueOf(vista.tablaLiga.getValueAt(row,2)));
                        vista.txtParticipantes.setText(String.valueOf(vista.tablaLiga.getValueAt(row,3)));
                        vista.comboTipoLiga.setSelectedItem(String.valueOf(vista.tablaLiga.getValueAt(row,4)));
                        vista.txtWeb.setText(String.valueOf(vista.tablaLiga.getValueAt(row,5)));


                    }else if(e.getValueIsAdjusting() && ((ListSelectionModel) e.getSource()).isSelectionEmpty() && !refrescar){
                        if (e.getSource().equals(vista.tablaLiga.getSelectionModel())){
                           borrarCamposLiga();
                        }
                        if (e.getSource().equals(vista.tablaEntrenador.getSelectionModel())){
                            borrarCamposEntrenador();
                        }
                        if (e.getSource().equals(vista.TablaPeleador.getSelectionModel())){
                            borrarCamposPeleador();
                        }

                    }

                }
            }
        });
        vista.tablaEntrenador.setCellSelectionEnabled(true);
        ListSelectionModel cellSelectionModel2 = vista.tablaEntrenador.getSelectionModel();
        cellSelectionModel2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cellSelectionModel2.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() &&  !((ListSelectionModel) e.getSource()).isSelectionEmpty()){
                    if (e.getSource().equals(vista.tablaEntrenador.getSelectionModel())){
                        int row= vista.tablaEntrenador.getSelectedRow();
                        vista.txtEntrenadorNombre.setText(String.valueOf(vista.tablaEntrenador.getValueAt(row,1)));
                        vista.txtEntrenadorApellidos.setText(String.valueOf(vista.tablaEntrenador.getValueAt(row,2)));
                        vista.txtNacionalidad.setText(String.valueOf(vista.tablaEntrenador.getValueAt(row,3)));
                        vista.entrenadorFechaInicio.setDate((Date.valueOf(String.valueOf(vista.tablaEntrenador.getValueAt(row, 3)))).toLocalDate());

                    }else if(e.getValueIsAdjusting() && ((ListSelectionModel) e.getSource()).isSelectionEmpty() && !refrescar){
                        if (e.getSource().equals(vista.tablaLiga.getSelectionModel())){
                            borrarCamposLiga();
                        }
                        if (e.getSource().equals(vista.tablaEntrenador.getSelectionModel())){
                            borrarCamposEntrenador();
                        }
                        if (e.getSource().equals(vista.TablaPeleador.getSelectionModel())){
                            borrarCamposPeleador();
                        }

                    }
                }
            }
        });
        vista.TablaPeleador.setCellSelectionEnabled(true);
        ListSelectionModel cellSelectionModel3 = vista.TablaPeleador.getSelectionModel();
        cellSelectionModel3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cellSelectionModel3.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() &&  !((ListSelectionModel) e.getSource()).isSelectionEmpty()){
                    if (e.getSource().equals(vista.TablaPeleador.getSelectionModel())){
                        int row= vista.TablaPeleador.getSelectedRow();
                        vista.txtNombre.setText(String.valueOf(vista.TablaPeleador.getValueAt(row,1)));
                        vista.comboEstilo.setSelectedItem(String.valueOf(vista.TablaPeleador.getValueAt(row,2)));
                        vista.comboLiga.setSelectedItem(String.valueOf(vista.TablaPeleador.getValueAt(row,3)));
                        vista.comboEntrenador.setSelectedItem(String.valueOf(vista.TablaPeleador.getValueAt(row,4)));
                        vista.txtGenero.setText(String.valueOf(vista.TablaPeleador.getValueAt(row,5)));
                        vista.txtPeso.setText(String.valueOf(vista.TablaPeleador.getValueAt(row,6)));
                        vista.nacimiento.setDate((Date.valueOf(String.valueOf(vista.TablaPeleador.getValueAt(row, 7))).toLocalDate()));

                    }else if(e.getValueIsAdjusting() && ((ListSelectionModel) e.getSource()).isSelectionEmpty() && !refrescar){
                        if (e.getSource().equals(vista.tablaLiga.getSelectionModel())){
                            borrarCamposLiga();
                        }
                        if (e.getSource().equals(vista.tablaEntrenador.getSelectionModel())){
                            borrarCamposEntrenador();
                        }
                        if (e.getSource().equals(vista.TablaPeleador.getSelectionModel())){
                            borrarCamposPeleador();
                        }

                    }
                }
            }
        });
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting() && !((ListSelectionModel) e.getSource()).isSelectionEmpty()) {
            if (e.getSource().equals(vista.tablaLiga.getSelectionModel())) {
                int row = vista.tablaLiga.getSelectedRow();
                vista.txtNombreLiga.setText(String.valueOf(vista.tablaLiga.getValueAt(row, 1)));
                vista.txtDescripcionLiga.setText(String.valueOf(vista.tablaLiga.getValueAt(row, 2)));
                vista.txtParticipantes.setText(String.valueOf(vista.tablaLiga.getValueAt(row, 3)));
                vista.comboTipoLiga.setSelectedItem(String.valueOf(vista.tablaLiga.getValueAt(row, 4)));
                vista.txtWeb.setText(String.valueOf(vista.tablaLiga.getValueAt(row, 5)));
            }else if (e.getSource().equals(vista.tablaEntrenador.getSelectionModel())) {
                int row = vista.tablaEntrenador.getSelectedRow();
                vista.txtEntrenadorNombre.setText(String.valueOf(vista.tablaEntrenador.getValueAt(row, 1)));
                vista.txtEntrenadorApellidos.setText(String.valueOf(vista.tablaEntrenador.getValueAt(row, 2)));
                vista.txtNacionalidad.setText(String.valueOf(vista.tablaEntrenador.getValueAt(row, 3)));
                vista.entrenadorFechaInicio.setDate((Date.valueOf(String.valueOf(vista.tablaEntrenador.getValueAt(row, 3))).toLocalDate());



            }else if(e.getSource().equals(vista.TablaPeleador.getSelectionModel())){
                int row = vista.TablaPeleador.getSelectedRow();
                vista.txtNombre.setText(String.valueOf(vista.TablaPeleador.getValueAt(row, 1)));
                vista.comboEstilo.setSelectedItem(String.valueOf(vista.TablaPeleador.getValueAt(row, 2)));
                vista.comboLiga.setSelectedItem(String.valueOf(vista.TablaPeleador.getValueAt(row, 3)));
                vista.comboEntrenador.setSelectedItem(String.valueOf(vista.TablaPeleador.getValueAt(row, 4)));
                vista.txtGenero.setText(String.valueOf(vista.TablaPeleador.getValueAt(row, 5)));
                vista.txtPeso.setText(String.valueOf(vista.TablaPeleador.getValueAt(row, 6)));
                vista.nacimiento.setDate((Date.valueOf(String.valueOf(vista.TablaPeleador.getValueAt(row, 7))).toLocalDate());
            }else if(e.getValueIsAdjusting() && ((ListSelectionModel) e.getSource()).isSelectionEmpty() && !refrescar){
                if (e.getSource().equals(vista.tablaLiga.getSelectionModel())){
                    borrarCamposLiga();
                }
                if (e.getSource().equals(vista.tablaEntrenador.getSelectionModel())){
                    borrarCamposEntrenador();
                }
                if (e.getSource().equals(vista.TablaPeleador.getSelectionModel())){
                    borrarCamposPeleador();
                }

            }
        }
    }



    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }


}
