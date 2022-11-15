package com.company.dao;

import com.company.entidades.server;

public class DAOServer {
    public server dameLosDatosGil(){
        server servidor = new server();
        servidor.setAddress("192.168.0.3");
        servidor.setPort(21);
        servidor.setUsername("Axel");
        servidor.setPassword("password");
        return servidor;
    }
}
