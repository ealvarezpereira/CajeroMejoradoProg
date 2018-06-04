/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cajero.controlador;

import com.cajero.modelo.Modelo;

/**
 *
 * @author quique
 */
public class Controlador {
    
    Modelo objModelo = new Modelo();
    
    public void conexionBD(){
        objModelo.conexionBD();
    }
    
    public void registrarUsuario(String usuario, String ctra, String nombre, String apellido){
        objModelo.registrarUsuario(usuario, ctra, nombre, apellido);
    }
    
    public boolean iniciarSesion(String usuario,String contr){
        return objModelo.iniciarSesion(usuario, contr);
    }
    
    public void insertarDinero(String dinero){
        objModelo.insertarDinero(dinero);
    }
    
    public boolean mostrarComboBox(){
       return Modelo.iniciado;
    }
    
    public String mostrarDinero(){
        return objModelo.mostrarDinero();
    }
}
