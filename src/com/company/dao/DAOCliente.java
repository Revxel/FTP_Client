package com.company.dao;

import com.company.entidades.server;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;



public class DAOCliente <cliente> implements IDAO <FTPClient> {
    @Override
    public FTPClient conectarse(server servidor) throws DAOException {
        FTPClient clienteFTP = new FTPClient();


        String address = servidor.getAddress();
        int port = servidor.getPort();
        String username = servidor.getUsername();
        String password = servidor.getPassword();


        try {
            clienteFTP.connect(address, port);
            int respuesta = clienteFTP.getReplyCode();
            if (!FTPReply.isPositiveCompletion(respuesta)) {
                System.out.println("Error código: " + respuesta);
            }
            boolean loginSatisfactorio = clienteFTP.login(username, password);
            if (loginSatisfactorio) {
                System.out.println("Inicio de sesión correcto");
                servidor.setEstado(1);
            } else {
                System.out.println("Credenciales invalidas (Username o password)");
                servidor.setEstado(2);
                clienteFTP.disconnect();
            }
        } catch (IOException e) {
            throw new DAOException(e.getMessage());
        }
        return clienteFTP;
    }

    @Override
    public void desconectarse(FTPClient ftpClient) throws DAOException { //Desconectarse del Servidor FTP
        boolean status = ftpClient.isConnected();
        try {
            ftpClient.disconnect();
            if (status) {
                System.out.println("Desconectado exitosamente");
            }
        } catch (IOException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public void subir(String localPath, String remotePath, FTPClient ftpClient) throws DAOException {
        FileInputStream fileInputStream = null; //Se crea un 'dato' donde se encapsula el archivo para ser transmitido

        try {
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            fileInputStream = new FileInputStream(localPath);
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            System.out.println(localPath);
            System.out.println(remotePath);
            boolean estado = ftpClient.storeFile(remotePath, fileInputStream); //Se envia el archivo y se almacena su estado
            if (estado) { //Si el archivo se subió correctamente
                System.out.println("Se subió correctamente el archivo");
            } else {
                System.out.println("No se subió correctamente el archivo");
            }
        } catch (IOException e) {
            throw new DAOException(e.getMessage());
        } finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                throw new DAOException((e.getMessage()));
            }
        }
    }

    @Override
    public void bajar(String localPath, String remotePath, FTPClient ftpClient) throws DAOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        OutputStream outputStream = null;
        System.out.println(localPath);
        System.out.println(remotePath);
        try {
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            boolean estado = ftpClient.retrieveFile(remotePath, byteArrayOutputStream);
            if(estado){
                System.out.println("Se descargó el archivo correctamente");
                outputStream = new FileOutputStream(localPath);
                byteArrayOutputStream.writeTo(outputStream);
            }
            else{
                System.out.println("No se descargó el archivo correctamente");
            }
        } catch (IOException e) {
            throw new DAOException(e.getMessage());
        } finally {
            try {
                outputStream.close();
                byteArrayOutputStream.close();
            } catch (IOException e) {
                throw new DAOException((e.getMessage()));
            }
        }
    }

    @Override
    public void renombrarLocal(String oldPath, String newPath) throws DAOException{
        File oldName = new File(oldPath);
        File newName = new File(newPath);
        if(!newName.exists()){
            boolean estado = oldName.renameTo(newName);
            if(estado){
                System.out.println("Se cambio el nombre exitosamente");
            }else{
                System.out.println("No se cambio el nombre");
            }
        }else{
            System.out.println("El archivo con ese nombre ya existe");
        }
    }

    @Override
    public void renombrarRemoto(String oldPath, String newName, FTPClient ftpClient) throws DAOException {
        if (ftpClient.isConnected()) {
//            try { //No anda
//                System.out.println(oldPath);
//                //System.out.println(str+"/"+newName);
//                boolean estado = ftpClient.rename(oldPath, str+"/"+newName);
//                if (estado) {
//                    System.out.println("Se cambio de nombre al archivo correctamente ");
//                } else {
//                    System.out.println("No Se cambio de nombre al archivo correctamente ");
//                }
//            } catch (IOException e) {
//                throw new DAOException(e.getMessage());
//            }
//        }else{
//            System.out.println("No estoy conectado");
//        }
        }
    }

    @Override
    public void borrarLocal(String localPath) throws DAOException {
                File archivo = new File(localPath);
        if(archivo.delete()){
            System.out.println("Se borró el archivo correctamente");
        }else{
            System.out.println("No se borró el archivo correctamente");
        }

    }

    @Override
    public void borrarRemoto(String remotePath, FTPClient ftpClient) throws DAOException {
        try {
            boolean deleted = ftpClient.deleteFile(remotePath);
            if (deleted) {
                System.out.println("Se borró el archivo");
            } else {
                System.out.println("No se pudo borrar el archivo");
            }
        } catch (IOException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public String mostrarRemoto(String path, FTPClient ftpClient) throws DAOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String files;
        boolean conectado = ftpClient.isConnected();
        if (conectado) {
            try {
                FTPFile[] ftpFile = ftpClient.listFiles(path);
                int i;
                for (i = 0; i < (ftpFile.length); i++) {
                    files = ftpFile[i].getName();
                    if(ftpFile[i].isFile()){
                        if(ftpFile[i].isFile() && ftpFile[i+1].isFile()){
                            outputStream.write("├─".getBytes());
                        }else{
                            outputStream.write("└─".getBytes());
                        }
                    }
                    outputStream.write((files + System.lineSeparator()).getBytes());
                }
            } catch (IOException e) {
                throw new DAOException(e.getMessage());
            }
        }
            return (outputStream.toString());
    }

    @Override
    public String mostrarLocal(String path) throws DAOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String files;
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        try {
            int i;
            for (i = 0; i < (listOfFiles.length); i++) {
                files = listOfFiles[i].getName();
                if(listOfFiles[i].isFile() && !listOfFiles[i].isHidden()){
                    if(listOfFiles[i].isFile() && listOfFiles[i+1].isFile()){
                        outputStream.write("├─".getBytes());
                    }else{
                        outputStream.write("└─".getBytes());
                    }
                }
                outputStream.write((files + System.lineSeparator()).getBytes());
            }
        } catch (IOException e) {
            throw new DAOException(e.getMessage());
        }
        return (outputStream.toString());
    }


}



