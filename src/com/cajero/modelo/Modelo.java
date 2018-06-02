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

    /**
     * @param url Ruta sqlite de la base de datos.
     */
    public void conexionBD() {
        String url = "jdbc:sqlite:" + "cajero.db";
        ConexionesBD.conexionBase(url);
    }

    /**
     * @param usuario Nombre de usuario
     * @param ctra Contraseña de usuario
     * @param nombre Nombre de la persona
     * @param apellido Apellido de la persona
     */
    static boolean existe = false;

    public void registrarUsuario(String usuario, String ctra, String nombre, String apellido) {

        //Si existe = false, quiere decir que el usuario no existe en la base de datos.
        //Si existe = true, quiere deicr que el usuario existe en la base de datos.
        try {
            String id = "ES34" + String.valueOf((int) (Math.random() * (1000) + 1));
            String consultaUsuario = "select usuario from usuario";
            ConexionesBD.resultSet(consultaUsuario);

            //Mientras que haya datos en la base de datos sigue ejecutandose.
            while (ConexionesBD.rs.next()) {
                if (!ConexionesBD.rs.getString(1).equals(usuario)) {
                    existe = false;
                } else {
                    existe = true;
                    while (ConexionesBD.rs.getString(1).equals(usuario)) {
                        usuario = JOptionPane.showInputDialog("Usuario existente.");
                    }
                    existe = false;
                }
            }

            if (existe == true) {
                usuario = JOptionPane.showInputDialog("Usuario existente.");
            } else {
                String sentencia = "insert into usuario values (" + "'" + id + "', " + "'" + usuario + "', " + "'" + ctra
                        + "', " + "'" + nombre + "', " + "'" + apellido + "');";
                ConexionesBD.preparedStatement(sentencia);

                String saldo = "insert into saldo values (" + "'" + id + "', " + "'0');";
                ConexionesBD.preparedStatement(saldo);
            }

            ConexionesBD.rs.close();
            existe = false;

        } catch (SQLException ex) {
            System.out.println("Error al sacar los usuarios de la tabla. Metodo registrarUsuario de la clase Modelo" + ex);
        }

    }

    static String id;
    public static boolean iniciado = false;

    public void iniciarSesion(String usu, String contraseña) {

        String sentencia = "select usuario,ctra from usuario;";
        ConexionesBD.resultSet(sentencia);
        try {

            if (!usu.equals("")) {
                while (ConexionesBD.rs.next()) {
                    if (usu.equals(ConexionesBD.rs.getString(1)) && contraseña.equals(ConexionesBD.rs.getString(2))) {
                        iniciado = true;
                        break;
                    } else {
                        iniciado = false;
                    }
                }
            }

            ConexionesBD.rs.close();

            if (iniciado == true) {
                JOptionPane.showMessageDialog(null, "Sesión iniciada correctamente!", "Sesión inciada", JOptionPane.INFORMATION_MESSAGE, null);
                sacarID(usu);
            } else {
                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos.", "ERROR", JOptionPane.INFORMATION_MESSAGE, null);
            }

        } catch (SQLException ex) {
            System.out.println("ERROR SQL" + ex);
        }
    }

    public void sacarID(String usu) {

        try {
            String sentenciaID = "select id from usuario where usuario='" + usu + "'";
            ConexionesBD.resultSet(sentenciaID);
            id = ConexionesBD.rs.getString(1);
            System.out.println(id);
            ConexionesBD.rs.close();
        } catch (SQLException ex) {
            System.out.println("Error al sacar el id. " + ex);
        }
    }

    public void insertarDinero(String dinero) {

        try {
            String sentenciaDineroActual = "select saldo from saldo where id='" + id + "';";
            ConexionesBD.resultSet(sentenciaDineroActual);
            System.out.println(ConexionesBD.rs.getString(1));
            int saldoCuenta = Integer.parseInt(ConexionesBD.rs.getString(1));
            ConexionesBD.rs.close();
            int dineroAIngresar = Integer.parseInt(dinero);
            String sentenciaDineroIntroducido = "update saldo set saldo='" + (saldoCuenta + dineroAIngresar) + "' where id='" + id + "';";
            ConexionesBD.preparedStatement(sentenciaDineroIntroducido);

        } catch (SQLException ex) {
            System.out.println("Error al insertar dinero. " + ex);
        }

    }

}
