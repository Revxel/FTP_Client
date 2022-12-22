package com.company.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;
import org.apache.commons.lang.*;

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

        botoneraArchivos = new Archivos();
        mostrarArchivo(botoneraArchivos,BorderLayout.SOUTH);

        ventana.setVisible(true);

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

        //Menu Server Conectar Desconectar etc
        JMenu server = new JMenu("Server");
        menuBar.add(server);
        JMenuItem conectarse = new JMenuItem("Conectarse",conectarseIcon);
        server.add(conectarse);
        JMenuItem desconectarse = new JMenuItem("Desconectarse", desconectarseIcon);
        server.add(desconectarse);

        //Submenu de conectar

        JMenu jFavoritos = new JMenu("Favoritos");

        JMenuItem guardarFavo = new JMenuItem("Guardar Favorito", guardarFavIcon);
        jFavoritos.add(guardarFavo);

        JMenu cargarFavo = new JMenu("Cargar Favorito");
        mostrarFavoritos(cargarFavo);

        jFavoritos.add(cargarFavo);

        JMenuItem eliminarFavo = new JMenuItem("Eliminar Favoritos");
        jFavoritos.add(eliminarFavo);

        menuBar.add(jFavoritos);

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
                mostrarArchivos.refreshButton();
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

        guardarFavo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                guardarFavorito();
                cargarFavo.removeAll();
                mostrarFavoritos(cargarFavo);

            }
        });

        eliminarFavo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                eliminarFavoritos();
                cargarFavo.removeAll();
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
    void guardarFavorito(){
        Preferences prefer = Preferences.userNodeForPackage(PanelManager.class);
        int i = 0;
        while(prefer.get("Username"+i,"Error")!="Error"){
            i++;
            System.out.println(i);
        }
        if(conectarseServidor.servidor.getUsername()!=null){
            prefer.put("Username"+i, conectarseServidor.servidor.getUsername()+"@"+conectarseServidor.servidor.getAddress()+":"+conectarseServidor.servidor.getPort()+"/"+conectarseServidor.servidor.getPassword());
        }
    }
    void mostrarFavoritos(JMenu cargarFavo){
        int i=0,l=0;
        String item = cargarFavorito(i);
        while(item!="-1"){
            JMenuItem jMenuItem = new JMenuItem(item);
            cargarFavo.add(jMenuItem);
            int finalI = i;
            jMenuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Preferences prefer = Preferences.userNodeForPackage(PanelManager.class);
                    conectarseServidor.jTextFieldHost.setText(StringUtils.substringBetween(prefer.get("Username"+finalI,"Error"),"@",":"));
                    conectarseServidor.jTextFieldUsername.setText(StringUtils.substringBefore(prefer.get("Username"+finalI,"Error"),"@"));
                    conectarseServidor.jTextFieldPort.setText(StringUtils.substringBetween(prefer.get("Username"+finalI,"Error"),":","/"));
                    conectarseServidor.jTextFieldPassword.setText(StringUtils.substringAfter(prefer.get("Username"+finalI,"Error"),"/"));
                    conectarseServidor.conectarServer();
                    mostrarArchivos.refreshButton();
                }
            });
            i++;
            item = cargarFavorito(i);
        }
    }

    String cargarFavorito(int i){
        Preferences prefer = Preferences.userNodeForPackage(PanelManager.class);

        if(prefer.get("Username"+i,"Error")!="Error"){
            String start = StringUtils.substringBefore(prefer.get("Username"+i,"Error"),"/");
            return(start);
        }else{
            return ("-1");
        }

    }

    void eliminarFavoritos(){
        Preferences prefer = Preferences.userNodeForPackage(PanelManager.class);
        int i=0;
        while(prefer.get("Username"+i,"Error")!="Error") {
            prefer.remove("Username"+ i);
            i++;
        }
    }

}
