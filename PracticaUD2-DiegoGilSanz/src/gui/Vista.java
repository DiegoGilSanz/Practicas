package gui;

import com.github.lgooddatepicker.components.DatePicker;
import gui.enumerados.EstilosPeleador;
import gui.enumerados.TipoLiga;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Vista extends JFrame {
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JPanel JPanelPeleador;
    private JPanel JPanelLiga;
    private JPanel JPanelEntrenador;
    private final static String TITULO="APLICACION LIGAS";
    //peleador
     JLabel Estilo;
     JButton btnAñadirPeleador;
     JButton btnModificarPeleador;
     JButton btnEliminarPeleador;
     JTable tablaPeleador;
     JTextField txtNombre;
     JComboBox comboEstilo;
     JComboBox comboLiga;
     JComboBox comboEntrenador;
     JTextField txtGenero;
     JTextField txtPeso;
     DatePicker nacimiento;
    //liga
     JTextField txtNombreLiga;
     JTextField txtDescripcionLiga;
     JTextField txtParticipantes;
     JComboBox comboTipoLiga;
     JTextField txtWeb;
     JButton btnañadirLiga;
     JButton btnModificaLiga;
     JButton btnEliminarLiga;
     JTable tablaLiga;
    //entrenador
     JTextField txtEntrenadorNombre;
     JTextField txtEntrenadorApellidos;
     JTextField txtNacionalidad;
     DatePicker entrenadorFechaInicio;
     JButton btnAñadirEntrenador;
     JButton btnModificarEntrenador;
     JButton btnEliminarEntrenador;
     JTable tablaEntrenador;
    //estado
     JLabel estado;
    //Default Table Model
     DefaultTableModel dtmPeleador;
     DefaultTableModel dtmLiga;
     DefaultTableModel dtmEntrenador;
    //barra menu
    JMenuItem itemSalir;
    JMenuItem itemOpciones;
    JMenuItem itemDesconectar;
    //cuandro de dialogo
    OptionDialog optionDialog;
    JDialog adminPassworDialog;
    JButton btnValidate;
    JPasswordField adminPassword;
    public Vista(){
        super(TITULO);
        inicializar();

    }
    public void inicializar(){
        this.setContentPane(panel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setSize(new Dimension(this.getWidth()+100,this.getHeight()+100));
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        optionDialog = new OptionDialog(this);
        setMenu();
        setAdminDialog();
        setEnumCombo();
        setTableModels();



    }
    private void setMenu(){
     JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Archivo");
        itemSalir = new JMenuItem("Salir");
        itemSalir.setActionCommand("Salir");
        itemDesconectar = new JMenuItem("Desconectar");
        itemDesconectar.setActionCommand("Desconectar");
        itemOpciones = new JMenuItem("Opciones");
        itemOpciones.setActionCommand("Opciones");
        menu.add(itemSalir);
        menu.add(itemDesconectar);
        menu.add(itemOpciones);
        menuBar.add(menu);
        menuBar.add(Box.createHorizontalGlue());
        this.setJMenuBar(menuBar);
    }
    public void setAdminDialog(){
        btnValidate=new JButton("Validar");
        btnValidate.setActionCommand("Validar");
        adminPassword=new JPasswordField();
        adminPassword.setPreferredSize(new Dimension(100,26));
        Object[] elementos=new Object[]{adminPassword,btnValidate};
        JOptionPane jop=new JOptionPane("Introduce la contraseña",JOptionPane.WARNING_MESSAGE,
               JOptionPane.YES_NO_OPTION,null,elementos );
        adminPassworDialog=new JDialog(this,"Opciones",true);
        adminPassworDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        adminPassworDialog.setContentPane(jop);
        adminPassworDialog.pack();
        adminPassworDialog.setLocationRelativeTo(this);

    }
    private void setEnumCombo(){
        for (TipoLiga tipo: TipoLiga.values()){
            comboTipoLiga.addItem(tipo.getValor());
        }
        comboTipoLiga.setSelectedIndex(-1);
        for (EstilosPeleador estilo:EstilosPeleador.values()){
            comboEstilo.addItem(estilo.getValor());
        }
        comboEstilo.setSelectedIndex(-1);

    }
    private void setTableModels(){
       this.dtmPeleador=new DefaultTableModel();
       this.tablaPeleador.setModel(dtmPeleador);

         this.dtmLiga=new DefaultTableModel();
         this.tablaLiga.setModel(dtmLiga);

            this.dtmEntrenador=new DefaultTableModel();
            this.tablaEntrenador.setModel(dtmEntrenador);
    }


}
