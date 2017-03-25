package com.agustinmaidana.ochoadb;

import java.io.Serializable;
import org.springframework.boot.jackson.JsonComponent;

/* 
    La anotacion JsonComponent esta de pinta nomas ahi.
    Lo vi en algun ejemplo, y queda re pro. pero en realidad el proceso de convertirse de un objeto java(POJO por Plain Old Java Object) a JSON(String con cierta notacion) lo realiza Spring automaticamente.
 */

 /*
    Para ser serializable(requisito de varias cosas, como por ejemplo, Web Services SOAP, lo que utilizamos en Prog de Ap) se tiene que:
        implementar la clase Serializable
        tener un constructor por defecto(vacio)
        y tener todos los getters y setters.

    Por ejemplo, uno de los requisitos de jpa, es que las entidades manejadas, las que son guardadas en la bd, sean serializables, si mal no me acuerdo lol.

    Desde netbeans, haciendo secundario en cualquier lugar, dentro de la clase, y despues, en insert code, podes generar automaticamente constructores, getters, setters, y otras porquerias al pedo.
 */
@JsonComponent
public class Usaurio implements Serializable {

    private String nombre, apellido, email, password;

    public Usaurio() {
    }

    public Usaurio(String nombre, String apellido, String email, String password) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
