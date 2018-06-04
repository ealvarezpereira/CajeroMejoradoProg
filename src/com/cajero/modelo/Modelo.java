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

    public boolean iniciarSesion(String usu, String contraseña) {

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
        return iniciado;
    }

    public void sacarID(String usu) {

        try {
            String sentenciaID = "select id from usuario where usuario='" + usu + "'";
            ConexionesBD.resultSet(sentenciaID);
            id = ConexionesBD.rs.getString(1);
            ConexionesBD.rs.close();
        } catch (SQLException ex) {
            System.out.println("Error al sacar el id. " + ex);
        }
    }

    public void insertarDinero(String dinero) {

        try {
            String sentenciaDineroActual = "select saldo from saldo where id='" + id + "';";
            ConexionesBD.resultSet(sentenciaDineroActual);
            int saldoCuenta = Integer.parseInt(ConexionesBD.rs.getString(1));
            ConexionesBD.rs.close();
            int dineroAIngresar = Integer.parseInt(dinero);
            String sentenciaDineroIntroducido = "update saldo set saldo='" + (saldoCuenta + dineroAIngresar) + "' where id='" + id + "';";
            ConexionesBD.preparedStatement(sentenciaDineroIntroducido);

        } catch (SQLException ex) {
            System.out.println("Error al insertar dinero. " + ex);
        }
    }

    public String mostrarDinero() {
        String dinero = null;
        try {

            String sentenciaDineroActual = "select saldo from saldo where id='" + id + "';";
            ConexionesBD.resultSet(sentenciaDineroActual);
            dinero = ConexionesBD.rs.getString(1);
            ConexionesBD.rs.close();

        } catch (SQLException ex) {
            System.out.println("Error al sacar dinero. " + ex);
        }
        return dinero;

    }

    public void retirarDinero(String dinero) {

        try {
            if (!dinero.equals("0")) {
                String sentenciaDineroActual = "select saldo from saldo where id='" + id + "';";
                ConexionesBD.resultSet(sentenciaDineroActual);
                int saldoCuenta = Integer.parseInt(ConexionesBD.rs.getString(1));
                if (saldoCuenta > Integer.parseInt(dinero)) {
                    ConexionesBD.rs.close();
                    int dineroARetirar = Integer.parseInt(dinero);
                    String sentenciaDineroIntroducido = "update saldo set saldo='" + (saldoCuenta - dineroARetirar) + "' where id='" + id + "';";
                    ConexionesBD.preparedStatement(sentenciaDineroIntroducido);
                } else {
                    JOptionPane.showMessageDialog(null, "No hay suficiente saldo en la cuenta.", "ERROR", JOptionPane.INFORMATION_MESSAGE, null);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Si juan quiere una manzana y cuando va a un supermercado elige 0 manzanas,\n"
                        + "¿Crees que saciará su hambre de manzanas?");
            }

        } catch (SQLException ex) {
            System.out.println("Error al insertar dinero. " + ex);
        }

    }

    public void transferencia(String dinero, String usuarioTrans) {

        try {

            String sentenciaDineroActual = "select saldo from saldo where id='" + id + "';";
            ConexionesBD.resultSet(sentenciaDineroActual);
            int saldoCuenta = Integer.parseInt(ConexionesBD.rs.getString(1));

            if (saldoCuenta > Integer.parseInt(dinero)) {

                ConexionesBD.rs.close();

                String usuarioPrueba = "select id,usuario from usuario where usuario='" + usuarioTrans + "';";
                String usuarioExistente = "";
                String idUsuarioExistente = "";
                ConexionesBD.resultSet(usuarioPrueba);
                while (ConexionesBD.rs.next()) {
                    if (ConexionesBD.rs.getString(2).equals(usuarioTrans)) {
                        usuarioExistente = ConexionesBD.rs.getString(2);
                        idUsuarioExistente = ConexionesBD.rs.getString(1);
                    }
                }

                if (usuarioTrans.equals(usuarioExistente)) {

                    int dineroATransferir = 0;

                    dineroATransferir = Integer.parseInt(dinero);
                    String sentenciaDineroIntroducido = "update saldo set saldo='" + (saldoCuenta - dineroATransferir) + "' where id='" + id + "';";
                    ConexionesBD.preparedStatement(sentenciaDineroIntroducido);

                    ConexionesBD.rs.close();

                    String sentenciaDineroUsuarioTrans = "select saldo from saldo where id='" + idUsuarioExistente + "';";
                    ConexionesBD.resultSet(sentenciaDineroUsuarioTrans);
                    int dineroASumar = Integer.parseInt(ConexionesBD.rs.getString(1));
                    ConexionesBD.rs.close();

                    String sentenciaDineroAñadido = "update saldo set saldo='" + (dineroASumar + dineroATransferir) + "' where id='" + idUsuarioExistente + "';";
                    ConexionesBD.preparedStatement(sentenciaDineroAñadido);
                } else {
                    JOptionPane.showMessageDialog(null, "El usuario no existe.", "ERROR", JOptionPane.INFORMATION_MESSAGE, null);
                }

            } else {
                JOptionPane.showMessageDialog(null, "No hay suficiente saldo en la cuenta.", "ERROR", JOptionPane.INFORMATION_MESSAGE, null);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la transferencia.");
        }

    }

}
