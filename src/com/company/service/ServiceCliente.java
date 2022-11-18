package com.company.service;

import com.company.dao.DAOCliente;
import com.company.dao.DAOException;
import com.company.entidades.server;
import org.apache.commons.net.ftp.FTPClient;


public class ServiceCliente {
    private static DAOCliente daoCliente;
    public ServiceCliente(){
        daoCliente=new DAOCliente();
    }
    private static FTPClient ftpClient;


    public FTPClient conectarseServidor(server servidor) throws ServiceException{
        try {
            this.ftpClient = daoCliente.conectarse(servidor);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
        return ftpClient;
    }

    public static FTPClient getFtpClient() {
        return ftpClient;
    }


    public void desconectarServidor() throws ServiceException{
        try {
            daoCliente.desconectarse(getFtpClient());
        } catch (DAOException e) {
            throw new ServiceException((e.getMessage()));
        }
    }

    public void subirArchivo(String localPath, String remotePath) throws ServiceException{
        try{
            daoCliente.subir(localPath,remotePath,getFtpClient());

        }catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    public void bajarArchivo(String localPath,String remotePath) throws ServiceException{
        try{
            daoCliente.bajar(localPath,remotePath,getFtpClient());
        }catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    public void renombrarArchivoLocal(String oldPath, String newPath) throws ServiceException{
        try{
            daoCliente.renombrarLocal(oldPath,newPath);
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    public void renombrarArchivoRemoto(String oldPath, String newPath) throws ServiceException{
        try{
            daoCliente.renombrarRemoto(oldPath,newPath,getFtpClient());
        }catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    public static String mostrarArchivosPantallaRemoto(String path) throws ServiceException{
        String returnMostrar = null;
        try{
            returnMostrar = daoCliente.mostrarRemoto(path, getFtpClient());
        }catch (DAOException e) {
            throw new ServiceException((e.getMessage()));
        }finally {
            return(returnMostrar);
        }
    }

    public static String mostrarArchivosPantallaLocal(String path) throws ServiceException{
        String returnMostrar = null;
        try{
            returnMostrar = daoCliente.mostrarLocal(path);
        }catch (DAOException e) {
            throw new ServiceException((e.getMessage()));
        }finally {
            return(returnMostrar);
        }
    }

    public void borrarArchivosLocal(String localPath) throws ServiceException{
        try{
            daoCliente.borrarLocal(localPath);
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    public void borrarArchivoRemoto(String remotePath) throws ServiceException{
        try{
            daoCliente.borrarRemoto(remotePath, getFtpClient());
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

}
