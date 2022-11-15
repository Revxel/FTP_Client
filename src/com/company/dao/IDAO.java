package com.company.dao;


import com.company.entidades.server;
import org.apache.commons.net.ftp.FTPClient;

public interface IDAO <T> {
    public FTPClient conectarse(server servidor) throws DAOException;                           //Conectarse al servidor
    public void desconectarse(T ftpClient) throws DAOException;                                 //Desconectarse del servidor
    public void subir(String localPath, String remotePath, T ftpClient) throws DAOException;    //Cargar un archivo al servidor

    public void bajar(String localPath, String remotePath, T ftpClient) throws DAOException;    //Descargar un archivo del servidor
    public void renombrarLocal(String oldPath, String newPath) throws DAOException;             //Cambiarle el nombre a un archivo local
    public void renombrarRemoto(String oldPath, String newPath, T ftpClient) throws DAOException;     //Cambiarle el nombre a un archivo
    public void borrarLocal(String localPath) throws DAOException;                              //Borrar un archivo de la maquina que estamos usando
    public void borrarRemoto(String remotePath, T ftpClient) throws DAOException;               //Borrar un archivo del servidor
    public String mostrarLocal(String path) throws DAOException;                                //Mostrar archivos locales
    public String mostrarRemoto(String path, T ftpClient) throws DAOException;                  //Mostrar archivos remotos
}
