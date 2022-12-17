package com.company.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelManager {
    private Conectarse conectarseServidor;
    private Archivos botoneraArchivos;

    private MostrarArchivos mostrarArchivos;
    private Terminal mostrarTerminal;
    JFrame ventana;

    public PanelManager(){
        ventana = new JFrame("Cliente FTP");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        armarMenu(ventana);

        conectarseServidor = new Conectarse(this);
        mostrarConeccion(conectarseServidor,BorderLayout.NORTH);

        mostrarArchivos = new MostrarArchivos();
        mostrarPanelArchivo(mostrarArchivos,BorderLayout.CENTER);

        mostrarTerminal = new Terminal();
        //mostrarTerminal(mostrarTerminal,BorderLayout.);

        botoneraArchivos = new Archivos();
        mostrarArchivo(botoneraArchivos,BorderLayout.SOUTH);

        ventana.setVisible(true);
        //ventana.pack();

    }

    void armarMenu(JFrame panelMenu){
        JMenuBar menuBar = new JMenuBar();
        panelMenu.setJMenuBar(menuBar);

        JMenu file = new JMenu("Menu");
        menuBar.add(file);

        ImageIcon exitIcon = new ImageIcon("src/com/company/gui/resources/Exit_Icon16.png");
        ImageIcon conectarseIcon = new ImageIcon("src/com/company/gui/resources/Connect_Icon.png");
        ImageIcon desconectarseIcon = new ImageIcon("src/com/company/gui/resources/Disconnect_Icon.png");
        ImageIcon guardarFavIcon = new ImageIcon("src/com/company/gui/resources/Favorite_Icon.png");
        JMenuItem exit = new JMenuItem("Exit",exitIcon);
        file.add(exit);

        JMenu server = new JMenu("Server");
        menuBar.add(server);
        JMenuItem conectarse = new JMenuItem("Conectarse",conectarseIcon);
        server.add(conectarse);
        JMenuItem desconectarse = new JMenuItem("Desconectarse", desconectarseIcon);
        server.add(desconectarse);
        JMenuItem guardarFav = new JMenuItem("Guardar favorito",guardarFavIcon);
        server.add(guardarFav);
        JMenuItem cargarFav = new JMenuItem("Favoritos");
        server.add(cargarFav);

        ImageIcon subirIcon = new ImageIcon("src/com/company/gui/resources/Upload_Icon.png");
        ImageIcon bajarIcon = new ImageIcon("src/com/company/gui/resources/Download_Icon.png");
        ImageIcon renombrarIcon = new ImageIcon("src/com/company/gui/resources/Rename_Icon.png");
        ImageIcon borrarIcon = new ImageIcon("src/com/company/gui/resources/Delete_Icon.png");

        JMenu archivos = new JMenu("Archivos");
        menuBar.add(archivos);
        JMenuItem subir = new JMenuItem("Subir Archivo", subirIcon);
        archivos.add(subir);
        JMenuItem bajar = new JMenuItem("Bajar Archivo", bajarIcon);
        archivos.add(bajar);
        JMenuItem renombrarLocal = new JMenuItem("Renombrar Local", renombrarIcon);
        archivos.add(renombrarLocal);
        JMenuItem renombrarRemoto = new JMenuItem("Renombrar Remoto");
        archivos.add(renombrarRemoto);
        JMenuItem borrarLocal = new JMenuItem("Borrar Local", borrarIcon);
        archivos.add(borrarLocal);
        JMenuItem borrarRemoto = new JMenuItem("Borrar Remoto");
        archivos.add(borrarRemoto);

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });

        conectarse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                conectarseServidor.conectarServer();
            }
        });

        desconectarse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                conectarseServidor.desconectarseServer();
            }
        });

        subir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                botoneraArchivos.subirArchivo();
            }
        });

        bajar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                botoneraArchivos.bajarArchivo();
            }
        });

        renombrarLocal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                botoneraArchivos.renombrarArchivoLocal();
            }
        });

        renombrarRemoto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                botoneraArchivos.renombrarArchivoRemoto();
            }
        });

        borrarLocal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                botoneraArchivos.borrarLocal();
            }
        });

        borrarRemoto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                botoneraArchivos.borrarRemoto();
            }
        });
    }

    void mostrarConeccion(JPanel panel, String ubicacion){
        ventana.getContentPane().removeAll();
        ventana.getContentPane().add(ubicacion,panel);
        ventana.getContentPane().validate();
        ventana.getContentPane().repaint();
        ventana.pack();
    }
    void mostrarArchivo(JPanel panel, String ubicacion){
        ventana.getContentPane().add(ubicacion,panel);
        ventana.getContentPane().validate();
        ventana.getContentPane().repaint();
        ventana.pack();
    }

    void mostrarPanelArchivo(JPanel panel, String ubicacion){
        ventana.getContentPane().add(ubicacion,panel);
        ventana.getContentPane().validate();
        ventana.getContentPane().repaint();
        ventana.pack();
    }

    void mostrarTerminal(JPanel panel, String ubicacion){
        ventana.getContentPane().add(ubicacion,panel);
        ventana.getContentPane().validate();;
        ventana.getContentPane().repaint();;
        ventana.pack();
    }

}
