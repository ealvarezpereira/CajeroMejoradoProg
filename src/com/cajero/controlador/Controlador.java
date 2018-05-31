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
    
    public void registrarUsuario(String usuario, String ctra, String nombre, String apellido){
        objModelo.registrarUsuario(usuario, ctra, nombre, apellido);
    }
}
