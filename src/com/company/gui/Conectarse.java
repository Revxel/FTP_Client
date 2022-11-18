package com.company.gui;

import com.company.entidades.*;

import com.company.service.ServiceCliente;
import com.company.service.ServiceException;
import org.apache.commons.net.ftp.FTPClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Conectarse extends JPanel {
    FTPClient ftpClient;
    MostrarArchivos mostrarArchivos = new MostrarArchivos();
    ServiceCliente serviceCliente = new ServiceCliente();
    JPanel conectarse;
    JPanel botones;
    JPanel estado;

    JLabel jLabelHost;
    JLabel jLabelPort;
    JLabel jLabelUsername;
    JLabel jLabelPassword;
    JLabel jLabelEstado;

    JTextField jTextFieldHost;
    JTextField jTextFieldPort;
    JTextField jTextFieldUsername;
    JTextField jTextFieldPassword;
    JTextField jTextFieldEstado;

    JButton jButtonConectar;
    JButton jButtonDesconectar;
    JButton jButtonGuardar; // Este todavia no hace nada

    PanelManager panel;

    public Conectarse(PanelManager panelPrincipal) {
        panel = panelPrincipal;
        armarPanel();
    }

    public void conectarServer(){
        server servidor = new server();
        servidor.setAddress(jTextFieldHost.getText());
        if(!jTextFieldPort.getText().isEmpty()){
            servidor.setPort(Integer.parseInt(jTextFieldPort.getText()));
        }else{
            servidor.setPort(21);
        }
        servidor.setUsername(jTextFieldUsername.getText());
        servidor.setPassword(jTextFieldPassword.getText());
//        servidor.setAddress("192.168.0.3");
//        servidor.setAddress("localhost");
//        servidor.setPort(21);
//        servidor.setUsername("Axel");
//        servidor.setPassword("password");

        try {
            ftpClient = serviceCliente.conectarseServidor(servidor);
            if(ftpClient.isConnected()){
                estadoPanel("Estado: Conectado", Color.GREEN);
                //mostrarArchivos.refreshButton();
            }
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void desconectarseServer(){
        if((ServiceCliente.getFtpClient())!=null) {
            try {
                serviceCliente.desconectarServidor();
                if (!ftpClient.isConnected()) {
                    estadoPanel("Estado: Desconectado", Color.RED);
                }
            } catch (ServiceException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void armarPanel() {
        conectarse = new JPanel();
        conectarse.setLayout(new GridLayout(1, 8));

        jButtonConectar = new JButton("Conectar");
        jButtonDesconectar = new JButton("Desconectar");

        jLabelEstado = new JLabel("Estado: Desconectado");
        jLabelHost = new JLabel("Host");
        jLabelPort = new JLabel("Port");
        jLabelUsername = new JLabel("Username");
        jLabelPassword = new JLabel("Password");
        jTextFieldHost = new JTextField(20);
        jTextFieldPort = new JTextField(20);
        jTextFieldUsername = new JTextField(20);
        jTextFieldPassword = new JTextField(20);

        jLabelHost.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelHost.setPreferredSize(new Dimension(1, 1));
        jLabelPort.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelPort.setPreferredSize(new Dimension(1, 1));
        jLabelUsername.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelUsername.setPreferredSize(new Dimension(1, 1));
        jLabelPassword.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelPassword.setPreferredSize(new Dimension(1, 1));

        conectarse.add(jLabelHost);
        conectarse.add(jTextFieldHost);
        conectarse.add(jLabelUsername);
        conectarse.add(jTextFieldUsername);
        conectarse.add(jLabelPassword);
        conectarse.add(jTextFieldPassword);
        conectarse.add(jLabelPort);
        conectarse.add(jTextFieldPort);

        setLayout(new BorderLayout());
        conectarse.setPreferredSize(new Dimension(1000,25));
        add(conectarse, BorderLayout.SOUTH);

        botones = new JPanel();
        jButtonConectar = new JButton("Conectar");
        jButtonDesconectar = new JButton("Desconectar");

        botones.add(jButtonConectar);
        botones.add(jButtonDesconectar);

        add(botones, BorderLayout.PAGE_START);

        estado = new JPanel();
        estado.add(jLabelEstado);
        jLabelEstado.setForeground(Color.RED);
        add(estado, BorderLayout.CENTER);

        jButtonConectar.addActionListener(new ActionListener() { //ACCION DE BOTON CONECTAR
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                conectarServer();
            }
        });


        jButtonDesconectar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                desconectarseServer();
            }
        });
    }

    public void estadoPanel(String estadoActual, Color colorEstado){
        jLabelEstado.setText(estadoActual);
        jLabelEstado.setForeground(colorEstado);
    }
}


















