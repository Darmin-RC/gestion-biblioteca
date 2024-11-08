package vista;

import modelo.Autor;
import modelo.GestorBiblioteca;
import modelo.Libro;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class App extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable tablaLibros;
    private JTextField campoBusqueda;
    private JComboBox<String> filtroBusqueda;
    private GestorBiblioteca gestor;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    App frame = new App();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public App() {
        gestor = GestorBiblioteca.getInstance();
        setTitle("Gestión de Biblioteca");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 600);
        
        contentPane = new JPanel();
        contentPane.setBackground(new Color(0, 0, 51));
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        
        inicializarComponentes();
    }

    private void inicializarComponentes() {

        JMenuBar menuBar = new JMenuBar();
        menuBar.setFont(new Font("Roboto", Font.PLAIN, 12));
        JMenu archivoMenu = new JMenu("Archivo");
        JMenuItem salirItem = new JMenuItem("Salir");
        salirItem.setFont(new Font("Roboto", Font.PLAIN, 12));
        salirItem.setForeground(new Color(255, 255, 255));
        salirItem.setBackground(new Color(204, 0, 0));
        salirItem.addActionListener(e -> System.exit(0));
        archivoMenu.add(salirItem);
        menuBar.add(archivoMenu);

        JMenu ayudaMenu = new JMenu("Ayuda");
        JMenuItem acercaDeItem = new JMenuItem("Acerca de");
        acercaDeItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, 
                "Ésta es una aplicación de Gestión de Biblioteca hecha por Darmin Reyes (2024-0170)", 
                "Acerca de la Aplicación", 
                JOptionPane.INFORMATION_MESSAGE);
        });

        acercaDeItem.setForeground(new Color(255, 255, 255));
        acercaDeItem.setFont(new Font("Roboto", Font.PLAIN, 12));
        acercaDeItem.setBackground(new Color(102, 204, 255));
        ayudaMenu.add(acercaDeItem);
        menuBar.add(ayudaMenu);

        setJMenuBar(menuBar);

        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelSuperior.setBackground(new Color(0, 0, 0));
        campoBusqueda = new JTextField(20);
        campoBusqueda.setFont(new Font("Roboto", Font.PLAIN, 15));
        filtroBusqueda = new JComboBox<>(new String[] { "Título", "Autor", "Género" });
        filtroBusqueda.setForeground(new Color(0, 0, 0));
        filtroBusqueda.setBackground(new Color(255, 255, 255));
        filtroBusqueda.setFont(new Font("Roboto", Font.PLAIN, 15));
        JButton botonBuscar = new JButton("Buscar");
        botonBuscar.setBackground(new Color(255, 204, 0));
        botonBuscar.setFont(new Font("Roboto Medium", Font.PLAIN, 15));
        botonBuscar.addActionListener(e -> buscarLibros());

        JButton botonAgregar = new JButton("Agregar");
        botonAgregar.setBackground(new Color(102, 255, 153));
        botonAgregar.setFont(new Font("Roboto Medium", botonAgregar.getFont().getStyle(), 15));
        botonAgregar.setForeground(new Color(0, 0, 0));
        botonAgregar.addActionListener(e -> agregarLibro());

        JButton botonEditar = new JButton("Editar");
        botonEditar.setBackground(new Color(0, 204, 255));
        botonEditar.setFont(new Font("Roboto Medium", botonAgregar.getFont().getStyle(), 15));
        botonEditar.setForeground(new Color(255, 255, 255));
        botonEditar.addActionListener(e -> editarLibro());

        JButton botonEliminar = new JButton("Eliminar");
        botonEliminar.setBackground(new Color(204, 0, 0));
        botonEliminar.setFont(new Font("Roboto Medium", botonAgregar.getFont().getStyle(), 15));
        botonEliminar.setForeground(new Color(255, 255, 255));
        botonEliminar.addActionListener(e -> eliminarLibro());

        JLabel label = new JLabel("Buscar por:");
        label.setForeground(new Color(255, 255, 255));
        label.setFont(new Font("Roboto", Font.PLAIN, 13));
        panelSuperior.add(label);
        panelSuperior.add(filtroBusqueda);
        panelSuperior.add(campoBusqueda);
        panelSuperior.add(botonBuscar);
        panelSuperior.add(botonAgregar);
        panelSuperior.add(botonEditar);
        panelSuperior.add(botonEliminar);

        contentPane.add(panelSuperior, BorderLayout.NORTH);

        tablaLibros = new JTable();
        tablaLibros.setRowHeight(25);
        tablaLibros.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tablaLibros.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        actualizarTabla();
        contentPane.add(new JScrollPane(tablaLibros), BorderLayout.CENTER);
    }

    private void buscarLibros() {
        String criterio = campoBusqueda.getText();
        String filtro = (String) filtroBusqueda.getSelectedItem();
        var librosFiltrados = gestor.buscarLibrosConFiltro(criterio, filtro);
        actualizarTabla(librosFiltrados);
    }

    private void agregarLibro() {
        String titulo = JOptionPane.showInputDialog("Ingrese el título del libro:");
        String autorNombre = JOptionPane.showInputDialog("Ingrese el nombre del autor:");
        String genero = JOptionPane.showInputDialog("Ingrese el género del libro:");
        String descripcion = JOptionPane.showInputDialog("Ingrese una descripción del libro:");

        Autor autor = new Autor(autorNombre, "");
        Libro libro = new Libro(titulo, autor, genero, descripcion);
        gestor.agregarLibro(libro);
        actualizarTabla();
    }

    private void editarLibro() {
        int filaSeleccionada = tablaLibros.getSelectedRow();
        if (filaSeleccionada != -1) {
            String titulo = JOptionPane.showInputDialog("Ingrese el nuevo título del libro:");
            String autorNombre = JOptionPane.showInputDialog("Ingrese el nuevo nombre del autor:");
            String genero = JOptionPane.showInputDialog("Ingrese el nuevo género del libro:");
            String descripcion = JOptionPane.showInputDialog("Ingrese la nueva descricpión del libro:");

            Autor autor = new Autor(autorNombre, "");
            Libro libro = new Libro(titulo, autor, genero, descripcion);
            gestor.getLibros().set(filaSeleccionada, libro);
            actualizarTabla();
        }
    }

    private void eliminarLibro() {
        int filaSeleccionada = tablaLibros.getSelectedRow();
        if (filaSeleccionada != -1) {
            gestor.getLibros().remove(filaSeleccionada);
            actualizarTabla();
        }
    }

    private void actualizarTabla() {
        actualizarTabla(gestor.getLibros());
    }

    private void actualizarTabla(java.util.List<Libro> libros) {
        String[] columnas = {"Título", "Autor", "Género", "Descripción"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0);

        for (Libro libro : libros) {
            String[] fila = {
                libro.getTitulo(),
                libro.getAutor().getNombre(),
                libro.getGenero(),
                libro.getDescripcion()
            };
            modeloTabla.addRow(fila);
        }

        tablaLibros.setModel(modeloTabla);
    }
}
