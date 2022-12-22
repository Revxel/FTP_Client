package com.company.gui;

import com.company.entidades.server;
import com.company.service.ServiceCliente;
import com.company.service.ServiceException;
import org.apache.commons.net.ftp.FTPClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

public class Archivos extends JPanel {
    PanelManager panelArchivo;
    ServiceCliente serviceCliente = new ServiceCliente();

    JPanel botonesArchivos;

    JButton jButtonSubir;
    JButton jButtonBajar;
    JButton jButtonRenombrarLocal;
    JButton jButtonRenombrarRemoto;
    JButton jButtonBorrarLocal;
    JButton jButtonBorrarRemoto;
    JButton jButtonInput;
    JTextField jTextField;

    public Archivos(){
        armarPanelArchivo();
    }

    public void armarPanelArchivo() {
        botonesArchivos = new JPanel();
        botonesArchivos.setLayout(new GridBagLayout());

        GridBagConstraints cst = new GridBagConstraints();

        jButtonSubir = new JButton("Subir");
        jButtonBajar = new JButton("Bajar");
        jButtonRenombrarLocal = new JButton("Renombrar Local");
        jButtonRenombrarRemoto = new JButton("Renombrar Remoto");
        jButtonBorrarLocal = new JButton("Borrar Local");
        jButtonBorrarRemoto = new JButton("Borrar Remoto");
        jButtonInput = new JButton("Enter");
        jTextField = new JTextField("Terminal");


        botonesArchivos.add(jButtonSubir);
        botonesArchivos.add(jButtonBajar);
        botonesArchivos.add(jButtonRenombrarLocal);
        botonesArchivos.add(jButtonRenombrarRemoto);
        botonesArchivos.add(jButtonBorrarLocal);
        botonesArchivos.add(jButtonBorrarRemoto);


        cst.fill = GridBagConstraints.HORIZONTAL;
        cst.gridwidth = 5;
        cst.gridx = 0;
        cst.gridy = 1;
        botonesArchivos.add(jTextField,cst);

        cst.fill = GridBagConstraints.HORIZONTAL;
        cst.gridwidth = 1;
        cst.gridx = 5;
        cst.gridy = 1;
        botonesArchivos.add(jButtonInput,cst);


        add(botonesArchivos,BorderLayout.NORTH);


        FTPClient ftpClient= ServiceCliente.getFtpClient();
        if(ftpClient!=null){
            boolean estadoConexion = ftpClient.isConnected();
            if(estadoConexion){
                System.out.println(estadoConexion);
            }
        }

        jButtonSubir.addActionListener(new ActionListener() { //Subir archivo
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(actionEvent.getSource()==jButtonSubir){
                        subirArchivo();
                }
            }
        });

        jButtonBajar.addActionListener(new ActionListener() { //Bajar archivo
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                bajarArchivo();
            }
        });

        jButtonRenombrarLocal.addActionListener(new ActionListener() { //Renombrar un archivo local
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                renombrarArchivoLocal();
            }
        });

        jButtonRenombrarRemoto.addActionListener(new ActionListener() { //Renombrar un archivo remoto
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                renombrarArchivoRemoto();
            }
        });

        jButtonBorrarLocal.addActionListener(new ActionListener() { //Borrar un archivo
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(actionEvent.getSource()==jButtonBorrarLocal){
                    borrarLocal();
                }
            }
        });

        jButtonBorrarRemoto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(actionEvent.getSource()==jButtonBorrarRemoto){
                    borrarRemoto();
                }
            }
        });
        jButtonInput.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(!jTextField.getText().isEmpty()){
                    var input = jTextField.getText();
                    ShellCommand(input);
                    jTextField.setText("");
                }

            }
        });



    }

    public void subirArchivo(){
        if((ServiceCliente.getFtpClient())!=null) {
            JFileChooser file_origin = new JFileChooser();

            file_origin.setCurrentDirectory(file_origin.getFileSystemView().getParentDirectory(new File("C:\\")));

            int res = file_origin.showOpenDialog(null);

            if (res == JFileChooser.APPROVE_OPTION) {
                String localPath = String.valueOf(new File(file_origin.getSelectedFile().getAbsoluteFile().toURI()));
                String remotePath;
                remotePath = MostrarArchivos.getPathRemoto();
                try {
                    serviceCliente.subirArchivo(localPath, remotePath);

                } catch (ServiceException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public void bajarArchivo(){
        if((ServiceCliente.getFtpClient())!=null) {
            JFileChooser file_origin = new JFileChooser();

            file_origin.setCurrentDirectory(file_origin.getFileSystemView().getParentDirectory(new File("C:\\")));

            int res = file_origin.showOpenDialog(null);

            if (res == JFileChooser.APPROVE_OPTION) {
                String localPath = String.valueOf(new File(file_origin.getSelectedFile().getAbsoluteFile().toURI()));
                try {
                    serviceCliente.bajarArchivo(localPath, MostrarArchivos.getPathRemoto());

                } catch (ServiceException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public void renombrarArchivoLocal(){
        JFileChooser file_origin = new JFileChooser();
        JFileChooser file_destination = new JFileChooser();
        file_origin.setCurrentDirectory(file_origin.getFileSystemView().getParentDirectory(new File("C:\\")));
        int res=file_origin.showOpenDialog(null);
        if(res == JFileChooser.APPROVE_OPTION){
            String localPath = String.valueOf(new File(file_origin.getSelectedFile().getAbsoluteFile().toURI()));
            file_destination.setCurrentDirectory(new File(localPath));
            int res2=file_destination.showSaveDialog(null);
            if(res2 == JFileChooser.APPROVE_OPTION){
                String newPath = String.valueOf(new File(file_destination.getSelectedFile().getAbsoluteFile().toURI()));
                try{
                    serviceCliente.renombrarArchivoLocal(localPath,newPath);

                } catch (ServiceException e){
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public void renombrarArchivoRemoto(){
        if((ServiceCliente.getFtpClient())!=null) {
            String newName = JOptionPane.showInputDialog("Ingrese el nuevo nombre");
            try {
                serviceCliente.renombrarArchivoRemoto(MostrarArchivos.getPathRemoto(), newName);
            } catch (ServiceException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void borrarLocal(){
        JFileChooser file_origin = new JFileChooser();
        file_origin.setCurrentDirectory(file_origin.getFileSystemView().getParentDirectory(new File("C:\\")));

        int res=file_origin.showOpenDialog(null);

        if(res == JFileChooser.APPROVE_OPTION){
            String localPath = String.valueOf(new File(file_origin.getSelectedFile().getAbsoluteFile().toURI()));
            try{
                serviceCliente.borrarArchivosLocal(localPath);

            } catch (ServiceException e){
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void borrarRemoto(){
        if((ServiceCliente.getFtpClient())!=null) {
            try {
                serviceCliente.borrarArchivoRemoto(MostrarArchivos.getPathRemoto());

            } catch (ServiceException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void ShellCommand(String args){
        try {
            Process proceso = Runtime.getRuntime().exec(args);
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(proceso.getInputStream()));

            String line = "";
            while((line = reader.readLine()) != null) {
                System.out.print(line + "\n");
            }
            proceso.waitFor();
        } catch (IOException | InterruptedException ex) {
            System.out.println("'"+args+"' No es un comando reconocido");
            throw new RuntimeException(ex);
        }finally {
            return;
        }

    }
}
