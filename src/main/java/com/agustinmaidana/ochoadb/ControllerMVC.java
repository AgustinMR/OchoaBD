package com.agustinmaidana.ochoadb;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/*
    @EnableWebMvn habilita o activa la aplicacion como una aplicacion web.
    Eso pone a correr el servidor web embebido que trae spring, el cual por defecto es tomcat, pero puede cambiarse a otros

    Por defecto, todos los recursos web(html, css, js) estaticos son leidos de una carpeta llamada sample o static en /src/main/resources
    Para utilizar algun lenguaje de scripting, como puede ser jsp, ThymeLeaf, Groovy, tiene que confgiurarse aca.
    igual no es el caso. con unos html cagados sobra.
 */
@Configuration
public class ControllerMVC extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("inicio.html");
        registry.addViewController("/login").setViewName("login.html");
    }
}
