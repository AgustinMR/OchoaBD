package com.agustinmaidana.ochoadb;

public class ManejadorConexion {

    private static ManejadorConexion instancia = null;

    ManejadorConexion() {

    }

    public static ManejadorConexion getinstance() {
        if (instancia == null) {
            instancia = new ManejadorConexion();
        }
        return instancia;
    }

}
