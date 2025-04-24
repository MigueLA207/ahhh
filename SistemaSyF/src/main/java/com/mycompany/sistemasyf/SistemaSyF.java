package com.mycompany.sistemasyf;
public class SistemaSyF {

    public static void main(String[] args) {
        ConfiguracionBD.ConexionDB objetoConexion = new ConfiguracionBD.ConexionDB();
        objetoConexion.establecerConexion();
        
        Formularios.MenuPrincipal objetoMenu = new Formularios.MenuPrincipal();
        objetoMenu.setVisible(true);
    }
}
