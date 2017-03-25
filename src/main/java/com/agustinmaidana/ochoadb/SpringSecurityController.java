package com.agustinmaidana.ochoadb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/*

    Esta clase, o componente(en la aplicacion), extiende otra provista por Spring, resolviendo asi unas cuentas cosas.
    
    El primer metodo que redefino, lo que hace es solo permitir el ingreso a la aplicacion, de los usuarios autenticados.
    Es decir, cuando un usuario entra, lo unico que puede hacer es loguearse, despues se lo lleva para la pagina de inicio,
    que en este caso es el / (porque en ControllerMVC asocie la direccion / a inicio.html)
    Tambien desactiva unas pequeñas cositas... de las cuales nadie debe enterarse...

    El segundo metodo proporciona un metodo de autenticacion de usuarios para el formulario de logueo.
    en este caso, utiliza una autenticacion en memoria, con esas credenciales, pero podria ir a una bd, o leer de un directorio LDAP, o lo que sea.
    te dan la posibilidad de utilizar Roles, por si tuvieses distintos privilegios entre usuarios. en este caso esta al pedo.

 */
@Configuration
@EnableWebSecurity
public class SpringSecurityController extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/login", "/*").authenticated().anyRequest().permitAll().and().formLogin().loginPage("/login").permitAll().and().logout().permitAll().and().cors().and().csrf().disable();
    }

    @Autowired
    public void configuracionGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("ochoa").password("ochoa").roles("USER");
    }

}
