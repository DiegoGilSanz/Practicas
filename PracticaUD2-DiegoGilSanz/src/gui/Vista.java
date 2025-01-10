package gui;

import com.github.lgooddatepicker.components.DatePicker;
import gui.enumerados.EstilosPeleador;
import gui.enumerados.TipoLiga;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
/**
 * Clase Vista que define la interfaz gráfica de usuario para la aplicación de ligas.
 * Esta clase utiliza Swing para construir la interfaz con varias pestañas,
 * tablas, botones, campos de texto y otros componentes necesarios para gestionar
 * peleadores, ligas y entrenadores.
 * Extiende de JFrame y organiza los elementos en pestañas usando un JTabbedPane.
 */
public class Vista extends JFrame {

    // Panel principal y pestañas
    /** Panel principal de la aplicación. */
    private JPanel panel1;

    /** Contenedor de pestañas para dividir la interfaz en secciones. */
    private JTabbedPane tabbedPane1;

    /** Panel para gestionar peleadores. */
    private JPanel JPanelPeleador;

    /** Panel para gestionar ligas. */
    private JPanel JPanelLiga;

    /** Panel para gestionar entrenadores. */
    private JPanel JPanelEntrenador;

    /** Título de la ventana principal. */
    private final static String TITULO = "APLICACION LIGAS";

    // Componentes para la sección de peleadores
    /** Etiqueta para el estilo del peleador. */
    JLabel Estilo;

    /** Botón para añadir un nuevo peleador. */
    JButton btnAñadirPeleador;

    /** Botón para modificar un peleador existente. */
    JButton btnModificarPeleador;

    /** Botón para eliminar un peleador. */
    JButton btnEliminarPeleador;

    /** Tabla para visualizar los peleadores. */
    JTable tablaPeleador;

    /** Campo de texto para el nombre del peleador. */
    JTextField txtNombre;

    /** ComboBox para seleccionar el estilo del peleador. */
    JComboBox comboEstilo;

    /** ComboBox para seleccionar la liga del peleador. */
    JComboBox comboLiga;

    /** ComboBox para seleccionar el entrenador del peleador. */
    JComboBox comboEntrenador;

    /** Campo de texto para el género del peleador. */
    JTextField txtGenero;

    /** Campo de texto para el peso del peleador. */
    JTextField txtPeso;

    /** Componente para seleccionar la fecha de nacimiento del peleador. */
    DatePicker nacimiento;

    // Componentes para la sección de ligas
    /** Campo de texto para el nombre de la liga. */
    JTextField txtNombreLiga;

    /** Campo de texto para la descripción de la liga. */
    JTextField txtDescripcionLiga;

    /** Campo de texto para los participantes de la liga. */
    JTextField txtParticipantes;

    /** ComboBox para seleccionar el tipo de liga. */
    JComboBox comboTipoLiga;

    /** Campo de texto para la web de la liga. */
    JTextField txtWeb;

    /** Botón para añadir una nueva liga. */
    JButton btnañadirLiga;

    /** Botón para modificar una liga existente. */
    JButton btnModificaLiga;

    /** Botón para eliminar una liga. */
    JButton btnEliminarLiga;

    /** Tabla para visualizar las ligas. */
    JTable tablaLiga;

    // Componentes para la sección de entrenadores
    /** Campo de texto para el nombre del entrenador. */
    JTextField txtEntrenadorNombre;

    /** Campo de texto para los apellidos del entrenador. */
    JTextField txtEntrenadorApellidos;

    /** Campo de texto para la nacionalidad del entrenador. */
    JTextField txtNacionalidad;

    /** Componente para seleccionar la fecha de inicio del entrenador. */
    DatePicker entrenadorFechaInicio;

    /** Botón para añadir un nuevo entrenador. */
    JButton btnAñadirEntrenador;

    /** Botón para modificar un entrenador existente. */
    JButton btnModificarEntrenador;

    /** Botón para eliminar un entrenador. */
    JButton btnEliminarEntrenador;

    /** Tabla para visualizar los entrenadores. */
    JTable tablaEntrenador;

    // Estado y visualización
    /** Etiqueta para mostrar el estado actual de la aplicación. */
    JLabel estado;

    /** Panel para visualizar los datos en tablas separadas. */
    private JPanel JPanelVisualizar;

    /** Tabla para visualizar los peleadores. */
    JTable tablaVisualizarPeleador;

    /** Tabla para visualizar las ligas. */
    JTable tablaVisualizarLiga;

    /** Tabla para visualizar los entrenadores. */
    JTable tablaVisualizarEntrenadores;

    // Modelos de tabla por defecto
    DefaultTableModel dtmPeleador;
    DefaultTableModel dtmLiga;
    DefaultTableModel dtmEntrenador;
    DefaultTableModel dtmVisualizarPeleador;
    DefaultTableModel dtmVisualizarLiga;
    DefaultTableModel dtmVisualizarEntrenador;

    // Barra de menú
    JMenuItem itemSalir;
    JMenuItem itemOpciones;
    JMenuItem itemDesconectar;

    // Cuadro de diálogo
    OptionDialog optionDialog;
    JDialog adminPassworDialog;
    JButton btnValidate;
    JPasswordField adminPassword;

    /**
     * Constructor de la clase Vista.
     * Configura el título de la ventana y llama al método de inicialización.
     */
    public Vista() {
        super(TITULO);
        inicializar();
    }

    /**
     * Método para inicializar los componentes de la interfaz gráfica.
     */
    public void inicializar() {
        this.setContentPane(panel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setSize(new Dimension(this.getWidth() + 100, this.getHeight() + 100));
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        optionDialog = new OptionDialog(this);
        setMenu();
        setAdminDialog();
        setEnumCombo();
        setTableModels();
    }

    /**
     * Método para configurar la barra de menú.
     */
    private void setMenu() {
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

    /**
     * Método para configurar el cuadro de diálogo para validar la contraseña de administrador.
     */
    public void setAdminDialog() {
        btnValidate = new JButton("Validar");
        btnValidate.setActionCommand("Validar");
        adminPassword = new JPasswordField();
        adminPassword.setPreferredSize(new Dimension(100, 26));
        Object[] elementos = new Object[]{adminPassword, btnValidate};
        JOptionPane jop = new JOptionPane("Introduce la contraseña", JOptionPane.WARNING_MESSAGE,
                JOptionPane.YES_NO_OPTION, null, elementos);
        adminPassworDialog = new JDialog(this, "Opciones", true);
        adminPassworDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        adminPassworDialog.setContentPane(jop);
        adminPassworDialog.pack();
        adminPassworDialog.setLocationRelativeTo(this);
    }

    /**
     * Método para configurar los ComboBox con los valores de los enumerados.
     */
    private void setEnumCombo() {
        for (TipoLiga tipo : TipoLiga.values()) {
            comboTipoLiga.addItem(tipo.getValor());
        }
        comboTipoLiga.setSelectedIndex(-1);
        for (EstilosPeleador estilo : EstilosPeleador.values()) {
            comboEstilo.addItem(estilo.getValor());
        }
        comboEstilo.setSelectedIndex(-1);
    }

    /**
     * Método para inicializar los modelos de tabla por defecto y asociarlos a las tablas.
     */
    private void setTableModels() {
        this.dtmPeleador = new DefaultTableModel();
        this.tablaPeleador.setModel(dtmPeleador);

        this.dtmLiga = new DefaultTableModel();
        this.tablaLiga.setModel(dtmLiga);

        this.dtmEntrenador = new DefaultTableModel();
        this.tablaEntrenador.setModel(dtmEntrenador);

        this.dtmVisualizarPeleador = new DefaultTableModel();
        this.tablaVisualizarPeleador.setModel(dtmVisualizarPeleador);

        this.dtmVisualizarLiga = new DefaultTableModel();
        this.tablaVisualizarLiga.setModel(dtmVisualizarLiga);

        this.dtmVisualizarEntrenador = new DefaultTableModel();
        this.tablaVisualizarEntrenadores.setModel(dtmVisualizarEntrenador);

    }
}
