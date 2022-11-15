package com.company.gui;

import com.company.dao.DAOCliente;
import com.company.entidades.server;
import com.company.service.ServiceCliente;
import com.company.service.ServiceException;
import org.apache.commons.net.ftp.FTPClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MostrarArchivos extends JPanel {
    static String pathRemoto=null;
    PanelManager panelMostrarArchivos;
    public JTextField jTextFieldDir2;
    String path2;

    public static String getPathRemoto() {
        return pathRemoto;
    }

    public MostrarArchivos(PanelManager panelPrincipal){
        panelMostrarArchivos = panelPrincipal;
        armarMostrarArchivos(null);
    }


    public void armarMostrarArchivos(PanelManager panelPrincipal){
        armarPanelArchivo("C:/",BorderLayout.WEST);
        armarPanelArchivoRemoto(BorderLayout.EAST);

    }

    void armarPanelArchivoRemoto(String ubicacion){
        JTextField jTextFieldDir;
        JTextArea jTextArea;
        JButton jButtonRefresh;
        JScrollPane jScrollPane;

        JPanel frame = new JPanel();
        frame.setLayout(new BorderLayout());


        jTextFieldDir = new JTextField(1);
        jTextArea = new JTextArea(20,30);
        jTextFieldDir2 = new JTextField(1);
        jButtonRefresh= new JButton("Refresh");
        jScrollPane = new JScrollPane(jTextArea);

        jTextFieldDir.setEditable(false);
        jTextArea.setEditable(false);

        frame.add(jTextFieldDir, BorderLayout.BEFORE_FIRST_LINE);
        frame.add(jScrollPane, BorderLayout.SOUTH);
        frame.add(jTextFieldDir2, BorderLayout.CENTER);
        frame.add(jButtonRefresh,BorderLayout.EAST);

        add(frame,ubicacion);




        jButtonRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if(pathRemoto == null){
                        pathRemoto = ServiceCliente.getFtpClient().printWorkingDirectory();
                        jTextFieldDir2.setText(pathRemoto);
                    }
                    pathRemoto=jTextFieldDir2.getText();
                    jTextFieldDir.setText(pathRemoto);
                    jTextArea.setText(ServiceCliente.mostrarArchivosPantallaRemoto(pathRemoto));
                } catch (ServiceException | IOException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    }
    void armarPanelArchivo(String path, String ubicacion){
        JTextField jTextFieldDir;
        JTextArea jTextArea;
        JButton jButton;
        JScrollPane jScrollPane;
        path2 = path;

        JPanel frame = new JPanel();
        frame.setLayout(new BorderLayout());


        jTextFieldDir = new JTextField(1);
        jTextArea = new JTextArea(20,30);
        jButton = new JButton("Carpeta");
        jScrollPane = new JScrollPane(jTextArea);

        jTextFieldDir.setEditable(false);
        jTextArea.setEditable(false);

        frame.add(jTextFieldDir, BorderLayout.BEFORE_FIRST_LINE);
        frame.add(jScrollPane, BorderLayout.SOUTH);
        frame.add(jButton, BorderLayout.CENTER);

        add(frame,ubicacion);

        jTextFieldDir.setText(path);

        try {
            jTextArea.append(ServiceCliente.mostrarArchivosPantallaLocal(path));
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser file_origin = new JFileChooser();
                file_origin.setCurrentDirectory(file_origin.getFileSystemView().getParentDirectory(new File("C:\\")));
                file_origin.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                int res=file_origin.showOpenDialog(null);

                if(res == JFileChooser.APPROVE_OPTION){
                    path2 = String.valueOf(new File(file_origin.getSelectedFile().getAbsoluteFile().toURI()));
                }
                try {
                    jTextArea.setText(ServiceCliente.mostrarArchivosPantallaLocal(path2));
                    jTextFieldDir.setText(path2);
                } catch (ServiceException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    }
}
