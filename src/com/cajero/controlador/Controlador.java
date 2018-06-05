/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cajero.controlador;

import com.cajero.modelo.FuncionamientoCajero;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author quique
 */
public class Controlador {

    FuncionamientoCajero objModelo = new FuncionamientoCajero();

    public void conexionBD() {
        objModelo.conexionBD();
    }

    public void registrarUsuario(String usuario, String ctra, String nombre, String apellido) {
        objModelo.registrarUsuario(usuario, ctra, nombre, apellido);
    }

    public boolean iniciarSesion(String usuario, String contr) {
        return objModelo.iniciarSesion(usuario, contr);
    }

    public void insertarDinero(String dinero) {
        objModelo.insertarDinero(dinero);
    }

    public boolean mostrarComboBox() {
        return FuncionamientoCajero.iniciado;
    }

    public String mostrarDinero() {
        return objModelo.mostrarDinero();
    }

    public void retirarDinero(String dinero) {
        objModelo.retirarDinero(dinero);
    }

    public void transferir(String dinero, String usuarioTrans) {
        objModelo.transferencia(dinero, usuarioTrans);
    }

    public void darseDeBaja() {
        objModelo.borrarCuenta();
    }

    ArrayList<String> usrs = new ArrayList<String>();

    public ArrayList<String> usuarios() {
        usrs = objModelo.usuarios();
        return usrs;
    }
    
        public DefaultTableModel botonMostrarOperaciones() {
        DefaultTableModel modelo = new DefaultTableModel();
        return modelo = objModelo.rellenarTablaOperaciones();
    }
        
        
            ArrayList<String> campos = new ArrayList<String>();

    public ArrayList<String> modificarUsuarioCampos() {
        campos = objModelo.devolverCamposAModifUsuario();
        return campos;
    }
    
    public void modificarUsuario(String usu, String ctra, String nombre, String apellido){
        objModelo.modificarUsuario(usu, ctra, nombre, apellido);
    }
}
