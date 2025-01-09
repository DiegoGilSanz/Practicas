package gui;

import util.Util;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

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
        refrescar();
        setOptions();

    }
    private void refrescar(){
        refrescarEntrenador();
        refrescarLiga();
        refrescarPeleador();
        refrescarVisualizarPeleador();
        refrescarVisualizarLiga();
        refrescarVisualizarEntrenador();
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
        vista.btnValidate.addActionListener(listener);
        vista.btnValidate.setActionCommand("Validar");
        vista.optionDialog.btnGuardarOpciones.addActionListener(listener);
        vista.optionDialog.btnGuardarOpciones.setActionCommand("guardarOpciones");
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
                        if (e.getSource().equals(vista.tablaPeleador.getSelectionModel())){
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
                        if (e.getSource().equals(vista.tablaPeleador.getSelectionModel())){
                            borrarCamposPeleador();
                        }

                    }
                }
            }
        });
        vista.tablaPeleador.setCellSelectionEnabled(true);
        ListSelectionModel cellSelectionModel3 = vista.tablaPeleador.getSelectionModel();
        cellSelectionModel3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cellSelectionModel3.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() &&  !((ListSelectionModel) e.getSource()).isSelectionEmpty()){
                    if (e.getSource().equals(vista.tablaPeleador.getSelectionModel())){
                        int row= vista.tablaPeleador.getSelectedRow();
                        vista.txtNombre.setText(String.valueOf(vista.tablaPeleador.getValueAt(row,1)));
                        vista.comboEstilo.setSelectedItem(String.valueOf(vista.tablaPeleador.getValueAt(row,2)));
                        vista.comboLiga.setSelectedItem(String.valueOf(vista.tablaPeleador.getValueAt(row,3)));
                        vista.comboEntrenador.setSelectedItem(String.valueOf(vista.tablaPeleador.getValueAt(row,4)));
                        vista.txtGenero.setText(String.valueOf(vista.tablaPeleador.getValueAt(row,5)));
                        vista.txtPeso.setText(String.valueOf(vista.tablaPeleador.getValueAt(row,6)));
                        vista.nacimiento.setDate((Date.valueOf(String.valueOf(vista.tablaPeleador.getValueAt(row, 7))).toLocalDate()));

                    }else if(e.getValueIsAdjusting() && ((ListSelectionModel) e.getSource()).isSelectionEmpty() && !refrescar){
                        if (e.getSource().equals(vista.tablaLiga.getSelectionModel())){
                            borrarCamposLiga();
                        }
                        if (e.getSource().equals(vista.tablaEntrenador.getSelectionModel())){
                            borrarCamposEntrenador();
                        }
                        if (e.getSource().equals(vista.tablaPeleador.getSelectionModel())){
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
                vista.entrenadorFechaInicio.setDate(Date.valueOf(String.valueOf(vista.tablaEntrenador.getValueAt(row, 3))).toLocalDate());

            }else if(e.getSource().equals(vista.tablaPeleador.getSelectionModel())){
                int row = vista.tablaPeleador.getSelectedRow();
                vista.txtNombre.setText(String.valueOf(vista.tablaPeleador.getValueAt(row, 1)));
                vista.comboEstilo.setSelectedItem(String.valueOf(vista.tablaPeleador.getValueAt(row, 2)));
                vista.comboLiga.setSelectedItem(String.valueOf(vista.tablaPeleador.getValueAt(row, 3)));
                vista.comboEntrenador.setSelectedItem(String.valueOf(vista.tablaPeleador.getValueAt(row, 4)));
                vista.txtGenero.setText(String.valueOf(vista.tablaPeleador.getValueAt(row, 5)));
                vista.txtPeso.setText(String.valueOf(vista.tablaPeleador.getValueAt(row, 6)));
                vista.nacimiento.setDate(Date.valueOf(String.valueOf(vista.tablaPeleador.getValueAt(row, 7))).toLocalDate());            }else if(e.getValueIsAdjusting() && ((ListSelectionModel) e.getSource()).isSelectionEmpty() && !refrescar){
                if (e.getSource().equals(vista.tablaLiga.getSelectionModel())){
                    borrarCamposLiga();
                }
                if (e.getSource().equals(vista.tablaEntrenador.getSelectionModel())){
                    borrarCamposEntrenador();
                }
                if (e.getSource().equals(vista.tablaPeleador.getSelectionModel())){
                    borrarCamposPeleador();
                }

            }
        }
    }



    @Override
    public void actionPerformed(ActionEvent e) {
    String comando=e.getActionCommand();
       switch (comando){
           case "Salir":
               System.exit(0);
               break;
           case "Opciones":
               vista.adminPassworDialog.setVisible(true);
               break;
           case "Desconectar":
                modelo.desconectar();
                break;
           case "Validar":
                   vista.adminPassword.setText("");
                   vista.adminPassworDialog.dispose();
                   vista.optionDialog.setVisible(true);
                break;
           case "guardarOpciones":
               if (String.valueOf(vista.adminPassword.getPassword()).equals(modelo.getAdminPassword())){
                   vista.adminPassword.setText("");
                   vista.adminPassworDialog.dispose();
                   vista.optionDialog.setVisible(true);
               }else{
                   Util.showErrorAlert("Contraseña incorrecta");
               }
               break;
           case "Añadir Peleador":
               try {
                   if (comprobarPeleadorVacio()){
                       Util.showWarningAlert("Rellene todos los campos");
                       vista.tablaPeleador.clearSelection();

                   } else if (modelo.nombrePeleadorExiste(vista.txtNombre.getText())){
                       Util.showWarningAlert("El nombre del peleador ya existe");
                       vista.tablaPeleador.clearSelection();

                   }
                     else {
                           modelo.insertarPeleador(
                                   vista.txtNombre.getText(),
                                   String.valueOf(vista.comboEstilo.getSelectedItem()),
                                   String.valueOf(vista.comboLiga.getSelectedItem()),
                                   String.valueOf(vista.comboEntrenador.getSelectedItem()),
                                   vista.txtGenero.getText(),
                                   Float.parseFloat(vista.txtPeso.getText()),
                                   vista.nacimiento.getDate());
                                 refrescarPeleador();
                                 refrescarVisualizarPeleador();
                     }


               }catch (NumberFormatException ex){
                   Util.showErrorAlert("El peso debe ser un número");
                   vista.tablaPeleador.clearSelection();
               }
               borrarCamposPeleador();
               refrescarPeleador();
               refrescarVisualizarPeleador();

           break;
           case "Modificar Peleador":
               try {
                   if (comprobarPeleadorVacio()){
                       Util.showWarningAlert("Rellene todos los campos");
                       vista.tablaPeleador.clearSelection();

                   } else {
                       modelo.modificarPeleador(
                               vista.txtNombre.getText(),
                               String.valueOf(vista.comboEstilo.getSelectedItem()),
                               String.valueOf(vista.comboLiga.getSelectedItem()),
                               String.valueOf(vista.comboEntrenador.getSelectedItem()),
                               vista.txtGenero.getText(),
                               Float.parseFloat(vista.txtPeso.getText()),
                               vista.nacimiento.getDate(),
                               (Integer)vista.tablaPeleador.getValueAt(vista.tablaPeleador.getSelectedRow(),0));
                       refrescarPeleador();
                       refrescarVisualizarPeleador();
                   }
               }catch (NumberFormatException ex){
                   Util.showErrorAlert("El peso debe ser un número");
                   vista.tablaPeleador.clearSelection();
               }catch (ArrayIndexOutOfBoundsException ex){
                   Util.showErrorAlert("Seleccione un peleador");
               }
               borrarCamposPeleador();
               refrescarPeleador();
               refrescarVisualizarPeleador();
               break;
           case "Eliminar Peleador":
                modelo.eliminarPeleador((Integer)vista.tablaPeleador.getValueAt(vista.tablaPeleador.getSelectedRow(),0));
                refrescarPeleador();
                refrescarVisualizarPeleador();
                borrarCamposPeleador();
                break;
           case "Añadir Liga":
                try {
                     if (comprobarLigaVacio()){
                          Util.showWarningAlert("Rellene todos los campos");
                          vista.tablaLiga.clearSelection();

                     } else if (modelo.nombreLigaExiste(vista.txtNombreLiga.getText())){
                          Util.showWarningAlert("El nombre de la liga ya existe");
                          vista.tablaLiga.clearSelection();

                     }
                     else {
                          modelo.insertarLiga(
                                 vista.txtNombreLiga.getText(),
                                 vista.txtDescripcionLiga.getText(),
                                 Integer.parseInt(vista.txtParticipantes.getText()),
                                 String.valueOf(vista.comboTipoLiga.getSelectedItem()),
                                 vista.txtWeb.getText());
                          refrescarLiga();
                          refrescarVisualizarLiga();
                     }
                }catch (NumberFormatException ex){
                     Util.showErrorAlert("El número de participantes debe ser un número");
                     vista.tablaLiga.clearSelection();
                }
                borrarCamposLiga();
                refrescarLiga();
                refrescarVisualizarLiga();
                break;
              case "Modificar Liga":
                try {
                    if (comprobarLigaVacio()){
                        Util.showWarningAlert("Rellene todos los campos");
                        vista.tablaLiga.clearSelection();

                    } else {
                        modelo.modificarLiga(
                                vista.txtNombreLiga.getText(),
                                vista.txtDescripcionLiga.getText(),
                                Integer.parseInt(vista.txtParticipantes.getText()),
                                String.valueOf(vista.comboTipoLiga.getSelectedItem()),
                                vista.txtWeb.getText(),
                                (Integer)vista.tablaLiga.getValueAt(vista.tablaLiga.getSelectedRow(),0));
                        refrescarLiga();
                        refrescarVisualizarLiga();
                    }
                }catch (NumberFormatException ex){
                    Util.showErrorAlert("El número de participantes debe ser un número");
                    vista.tablaLiga.clearSelection();
                }catch (ArrayIndexOutOfBoundsException ex){
                    Util.showErrorAlert("Seleccione una liga");
                }
                borrarCamposLiga();
                refrescarLiga();
                refrescarVisualizarLiga();
                break;
              case "Eliminar Liga":
                modelo.eliminarLiga((Integer)vista.tablaLiga.getValueAt(vista.tablaLiga.getSelectedRow(),0));
                refrescarLiga();
                refrescarVisualizarLiga();
                borrarCamposLiga();
                break;
              case "Añadir Entrenador":
                try {
                    if (comprobarEntrenadorVacio()){
                        Util.showWarningAlert("Rellene todos los campos");
                        vista.tablaEntrenador.clearSelection();

                    } else if (modelo.nombreEntrenadorExiste(vista.txtEntrenadorNombre.getText())){
                        Util.showWarningAlert("El nombre del entrenador ya existe");
                        vista.tablaEntrenador.clearSelection();

                    }
                    else {
                        modelo.insertarEntrenador(
                                vista.txtEntrenadorNombre.getText(),
                                vista.txtEntrenadorApellidos.getText(),
                                vista.entrenadorFechaInicio.getDate(),
                                vista.txtNacionalidad.getText()
                               );
                        refrescarEntrenador();
                        refrescarVisualizarEntrenador();
                    }
                }catch (NumberFormatException ex){
                    Util.showErrorAlert("El número de participantes debe ser un número");
                    vista.tablaEntrenador.clearSelection();
                }
                borrarCamposEntrenador();
                refrescarEntrenador();
                refrescarVisualizarEntrenador();
                break;
              case "Modificar Entrenador":
                try {
                    if (comprobarEntrenadorVacio()){
                        Util.showWarningAlert("Rellene todos los campos");
                        vista.tablaEntrenador.clearSelection();

                    } else {
                        modelo.modificarEntrenador(
                                vista.txtEntrenadorNombre.getText(),
                                vista.txtEntrenadorApellidos.getText(),
                                vista.entrenadorFechaInicio.getDate(),
                                vista.txtNacionalidad.getText(),
                                (Integer)vista.tablaEntrenador.getValueAt(vista.tablaEntrenador.getSelectedRow(),0));
                        refrescarEntrenador();
                        refrescarVisualizarEntrenador();
                    }
                }catch (NumberFormatException ex){
                    Util.showErrorAlert("El número de participantes debe ser un número");
                    vista.tablaEntrenador.clearSelection();
                }catch (ArrayIndexOutOfBoundsException ex){
                    Util.showErrorAlert("Seleccione un entrenador");
                }
                borrarCamposEntrenador();
                refrescarEntrenador();
                refrescarVisualizarEntrenador();
                break;
                case "Eliminar Entrenador":
                modelo.eliminarEntrenador((Integer)vista.tablaEntrenador.getValueAt(vista.tablaEntrenador.getSelectedRow(),0));
                refrescarEntrenador();
                borrarCamposEntrenador();
                refrescarVisualizarEntrenador();
                break;







       }
    }
    private DefaultTableModel tableModelLiga(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData=rs.getMetaData();
        Vector<String> columnNames=new Vector<>();
        int columnCount=metaData.getColumnCount();
        for (int column=1;column<=columnCount;column++){
            columnNames.add(metaData.getColumnName(column));
        }
        Vector<Vector<Object>> data=new Vector<>();
        setDataVector(rs,columnCount,data);
        vista.dtmLiga.setDataVector(data,columnNames);

        return vista.dtmLiga;
    }
    private void refrescarLiga() {
            try{
                vista.tablaLiga.setModel(tableModelLiga(modelo.consultarLiga()));
                vista.comboLiga.removeAllItems();
                for (int i=0;i<vista.dtmLiga.getRowCount();i++){
                    vista.comboLiga.addItem(vista.dtmLiga.getValueAt(i,0)+ " - "+vista.dtmLiga.getValueAt(i,1));
                }

            }catch (SQLException e){
            e.printStackTrace();
            }
        }

        private DefaultTableModel tableModelEntrenador(ResultSet rs) throws SQLException {
            ResultSetMetaData metaData = rs.getMetaData();
            Vector<String> columnNames = new Vector<>();
            int columnCount = metaData.getColumnCount();
            for (int column = 1; column <= columnCount; column++) {
                columnNames.add(metaData.getColumnName(column));
            }
            Vector<Vector<Object>> data = new Vector<>();
            setDataVector(rs, columnCount, data);
            vista.dtmEntrenador.setDataVector(data, columnNames);

            return vista.dtmEntrenador;
        }

        private void refrescarEntrenador() {
            try {
                vista.tablaEntrenador.setModel(tableModelEntrenador(modelo.consultarEntrenador()));
                vista.comboEntrenador.removeAllItems();
                for (int i = 0; i < vista.dtmEntrenador.getRowCount(); i++) {
                    vista.comboEntrenador.addItem(vista.dtmEntrenador.getValueAt(i, 0) + " - " + vista.dtmEntrenador.getValueAt(i, 1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        private DefaultTableModel tableModelPeleador(ResultSet rs) throws SQLException {
            ResultSetMetaData metaData = rs.getMetaData();
            Vector<String> columnNames = new Vector<>();
            int columnCount = metaData.getColumnCount();
            for (int column = 1; column <= columnCount; column++) {
                columnNames.add(metaData.getColumnName(column));
            }
            Vector<Vector<Object>> data = new Vector<>();
            setDataVector(rs, columnCount, data);
            vista.dtmPeleador.setDataVector(data, columnNames);

            return vista.dtmPeleador;
        }

        private void refrescarPeleador() {
            try {
             vista.tablaPeleador.setModel(tableModelPeleador(modelo.consultarPeleador()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        private DefaultTableModel tableModelVisualizarPeleador(ResultSet rs) throws SQLException {
            ResultSetMetaData metaData = rs.getMetaData();
            Vector<String> columnNames = new Vector<>();
            int columnCount = metaData.getColumnCount();
            for (int column = 1; column <= columnCount; column++) {
                columnNames.add(metaData.getColumnName(column));
            }
            Vector<Vector<Object>> data = new Vector<>();
            setDataVector(rs, columnCount, data);
            vista.dtmVisualizarPeleador.setDataVector(data, columnNames);

            return vista.dtmVisualizarPeleador;
        }
        void refrescarVisualizarPeleador() {
            try {
                vista.tablaVisualizarPeleador.setModel(tableModelVisualizarPeleador(modelo.consultarPeleador()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        private DefaultTableModel tableModelVisualizarLiga(ResultSet rs) throws SQLException {
            ResultSetMetaData metaData = rs.getMetaData();
            Vector<String> columnNames = new Vector<>();
            int columnCount = metaData.getColumnCount();
            for (int column = 1; column <= columnCount; column++) {
                columnNames.add(metaData.getColumnName(column));
            }
            Vector<Vector<Object>> data = new Vector<>();
            setDataVector(rs, columnCount, data);
            vista.dtmVisualizarLiga.setDataVector(data, columnNames);

            return vista.dtmVisualizarLiga;
        }
        void refrescarVisualizarLiga() {
            try {
                vista.tablaVisualizarLiga.setModel(tableModelVisualizarLiga(modelo.consultarLiga()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        private DefaultTableModel tableModelVisualizarEntrenador(ResultSet rs) throws SQLException {
            ResultSetMetaData metaData = rs.getMetaData();
            Vector<String> columnNames = new Vector<>();
            int columnCount = metaData.getColumnCount();
            for (int column = 1; column <= columnCount; column++) {
                columnNames.add(metaData.getColumnName(column));
            }
            Vector<Vector<Object>> data = new Vector<>();
            setDataVector(rs, columnCount, data);
            vista.dtmVisualizarEntrenador.setDataVector(data, columnNames);

            return vista.dtmVisualizarEntrenador;
        }
        void refrescarVisualizarEntrenador() {
            try {
                vista.tablaVisualizarEntrenadores.setModel(tableModelVisualizarEntrenador(modelo.consultarEntrenador()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        private void borrarCamposLiga() {
            vista.txtNombreLiga.setText("");
            vista.txtDescripcionLiga.setText("");
            vista.txtParticipantes.setText("");
            vista.comboTipoLiga.setSelectedItem(-1);
            vista.txtWeb.setText("");
        }

        private void borrarCamposEntrenador() {
            vista.txtEntrenadorNombre.setText("");
            vista.txtEntrenadorApellidos.setText("");
            vista.txtNacionalidad.setText("");
            vista.entrenadorFechaInicio.setText("");
        }

        private void borrarCamposPeleador() {
            vista.txtNombre.setText("");
            vista.comboEstilo.setSelectedItem(-1);
            vista.comboLiga.setSelectedItem(-1);
            vista.comboEntrenador.setSelectedItem(-1);
            vista.txtGenero.setText("");
            vista.txtPeso.setText("");
            vista.nacimiento.setText("");
        }
        private boolean comprobarLigaVacio(){
            return vista.txtNombreLiga.getText().isEmpty() || vista.txtDescripcionLiga.getText().isEmpty() || vista.txtParticipantes.getText().isEmpty() || vista.comboTipoLiga.getSelectedItem()==null || vista.txtWeb.getText().isEmpty();
        }
        private boolean comprobarEntrenadorVacio(){
            return vista.txtEntrenadorNombre.getText().isEmpty() || vista.txtEntrenadorApellidos.getText().isEmpty() || vista.txtNacionalidad.getText().isEmpty() || vista.entrenadorFechaInicio.getDate()==null;
        }
        private boolean comprobarPeleadorVacio(){
            return vista.txtNombre.getText().isEmpty() || vista.comboEstilo.getSelectedItem()==null || vista.comboLiga.getSelectedItem()==null || vista.comboEntrenador.getSelectedItem()==null || vista.txtGenero.getText().isEmpty() || vista.txtPeso.getText().isEmpty() || vista.nacimiento.getDate()==null;
        }
    private void setDataVector(ResultSet rs, int columnCount, Vector<Vector<Object>> data) throws SQLException {
        while (rs.next()) {
            Vector<Object> vector = new Vector<>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }
    }
    private void setOptions() {
        vista.optionDialog.txtIp.setText(modelo.getIp());
        vista.optionDialog.txtUser.setText(modelo.getUser());
        vista.optionDialog.txtPass.setText(modelo.getPassword());
        vista.optionDialog.txtAdminPass.setText(modelo.getAdminPassword());
    }




    @Override
    public void itemStateChanged(ItemEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0);
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
