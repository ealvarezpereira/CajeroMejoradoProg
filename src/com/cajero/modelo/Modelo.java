/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cajero.modelo;

import com.cajero.libreria.ConexionesBD;

/**
 *
 * @author quique
 */
public class Modelo {

    ConexionesBD db = new ConexionesBD();

    /**
     * @param url Ruta sqlite de la base de datos.
     */
    public void conexionBD() {
        String url = "jdbc:sqlite:" + "cajero.db";
        db.conexionBase(url);
    }
    
    public void registrarUsuario(String usuario,String ctra,String nombre, String apellido){
    
        String id = "ES34";
        
        String sentencia = "insert into usuario values ("+"'"+id+"', "+"'"+usuario+"', "+"'"+ctra
                +"', "+"'"+nombre+"', "+"'"+apellido+"');";
        
        db.preparedStatement(sentencia);
        
        
    }  
}
