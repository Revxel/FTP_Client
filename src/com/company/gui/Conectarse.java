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

    JButton jButtonConectar;
    JButton jButtonDesconectar;

    server servidor = new server();

    PanelManager panel;


    public Conectarse(PanelManager panelPrincipal) {
        panel = panelPrincipal;
        armarPanel();
    }



    public void conectarServer(){
        servidor.setAddress(jTextFieldHost.getText());
        if(!jTextFieldPort.getText().isEmpty()){
            servidor.setPort(Integer.parseInt(jTextFieldPort.getText()));
        }else{
            servidor.setPort(21);
        }
        servidor.setUsername(jTextFieldUsername.getText());
        servidor.setPassword(jTextFieldPassword.getText());

        try {
            ftpClient = serviceCliente.conectarseServidor(servidor);
            if(ftpClient.isConnected()){
                estadoPanel("Estado: Conectado", Color.GREEN);
            }else if(servidor.getEstado()==2){
                estadoPanel("Estado: Credenciales Inválidas", Color.BLUE);
            }
        } catch (ServiceException e) {
            estadoPanel("Estado: Host no existe", Color.RED);
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void desconectarseServer(){
        if((ServiceCliente.getFtpClient())!=null) {
            try {
                serviceCliente.desconectarServidor();
                if (!ftpClient.isConnected()) {
                    estadoPanel("Estado: Desconectado", Color.RED);
                    servidor.setEstado(0);

                }
            } catch (ServiceException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void armarPanel() {
        conectarse = new JPanel();
        conectarse.setLayout(new GridBagLayout());
        GridBagConstraints cst = new GridBagConstraints();

        jButtonConectar = new JButton("Conectar");
        jButtonConectar.setPreferredSize(new Dimension(150,28));
        jButtonDesconectar = new JButton("Desconectar");
        jButtonDesconectar.setPreferredSize(new Dimension(150,28));

        jLabelEstado = new JLabel("Estado: Desconectado");
        jLabelHost = new JLabel("Host:");
        jLabelPort = new JLabel("       Port:");
        jLabelUsername = new JLabel("       Username:");
        jLabelPassword = new JLabel("       Password:");
        jTextFieldHost = new JTextField(15);
        jTextFieldHost.setSize(20,1);
        jTextFieldPort = new JTextField(5);
        jTextFieldPort.setSize(10,1);
        jTextFieldUsername = new JTextField(15);
        jTextFieldUsername.setSize(20,1);
        jTextFieldPassword = new JTextField(15);
        jTextFieldPassword.setSize(20,1);

        //jLabelHost.setHorizontalAlignment(SwingConstants.RIGHT);
        //jLabelHost.setPreferredSize(new Dimension(1, 1));
        //jLabelPort.setHorizontalAlignment(SwingConstants.RIGHT);
        //jLabelPort.setPreferredSize(new Dimension(1, 1));
        //jLabelUsername.setHorizontalAlignment(SwingConstants.RIGHT);
        //jLabelUsername.setPreferredSize(new Dimension(1, 1));
        //jLabelPassword.setHorizontalAlignment(SwingConstants.RIGHT);
        //jLabelPassword.setPreferredSize(new Dimension(1, 1));


        cst.fill = GridBagConstraints.HORIZONTAL;
        cst.gridwidth = 1;
        cst.gridx = 0;
//        cst.gridy = 0;
        conectarse.add(jLabelHost,cst);
        cst.fill = GridBagConstraints.HORIZONTAL;
        cst.gridwidth = 1;
        cst.gridx = 1;
//        cst.gridy = 1;
        conectarse.add(jTextFieldHost,cst);

        cst.fill = GridBagConstraints.HORIZONTAL;
        cst.gridwidth = 1;
        cst.gridx = 2;
//        cst.gridy = 0;
        conectarse.add(jLabelUsername,cst);
        cst.fill = GridBagConstraints.HORIZONTAL;
        cst.gridwidth = 1;
        cst.gridx = 3;
//        cst.gridy = 1;
        conectarse.add(jTextFieldUsername,cst);

        cst.fill = GridBagConstraints.HORIZONTAL;
        cst.gridwidth = 1;
        cst.gridx = 4;
//        cst.gridy = 0;
        conectarse.add(jLabelPassword,cst);
        cst.fill = GridBagConstraints.HORIZONTAL;
        cst.gridwidth = 1;
        cst.gridx = 5;
//        cst.gridy = 1;
        conectarse.add(jTextFieldPassword,cst);

        cst.fill = GridBagConstraints.HORIZONTAL;
        cst.gridwidth = 1;
        cst.gridx = 6;
//        cst.gridy = 0;
        conectarse.add(jLabelPort,cst);
        cst.fill = GridBagConstraints.HORIZONTAL;
        cst.gridwidth = 1;
        cst.gridx = 7;
//        cst.gridy = 1;
        conectarse.add(jTextFieldPort,cst);

        setLayout(new BorderLayout());
        conectarse.setPreferredSize(new Dimension(1000,25));
        add(conectarse, BorderLayout.SOUTH);

        botones = new JPanel();
        botones.setLayout(new GridBagLayout());

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


















