/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cajero.modelo;

import com.cajero.libreria.ConexionesBD;
import java.sql.SQLException;
import javax.swing.JOptionPane;

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

    /**
     * @param usuario Nombre de usuario
     * @param ctra Contraseña de usuario
     * @param nombre Nombre de la persona
     * @param apellido Apellido de la persona
     */
    public void registrarUsuario(String usuario, String ctra, String nombre, String apellido) {

        //Si existe = false, quiere decir que el usuario no existe en la base de datos.
        //Si existe = true, quiere deicr que el usuario existe en la base de datos.
        boolean existe = false;
        try {
            String id = "ES34" + String.valueOf((int) (Math.random() * (1000) + 1));
            String consultaUsuario = "select usuario from usuario";
            db.resultSet(consultaUsuario);

            //Mientras que haya datos en la base de datos sigue ejecutandose.
            while (db.rs.next()) {
                if (!db.rs.getString(1).equals(usuario)) {
                    existe = false;
                } else {
                    existe = true;
                    while (db.rs.getString(1).equals(usuario)) {
                    }
                }
            }
            
            if (existe == true) {
                usuario = JOptionPane.showInputDialog("Usuario existente.");
            } else {
                String sentencia = "insert into usuario values (" + "'" + id + "', " + "'" + usuario + "', " + "'" + ctra
                        + "', " + "'" + nombre + "', " + "'" + apellido + "');";
                db.preparedStatement(sentencia);
            }
            
        } catch (SQLException ex) {
            System.out.println("Error al sacar los usuarios de la tabla. Metodo registrarUsuario de la clase Modelo" + ex);
        }
        
    }
}
