package com.agustinmaidana.ochoadb;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapAttributeConfig;
import com.hazelcast.config.MapIndexConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.hazelcast.HazelcastSessionRepository;
import org.springframework.session.hazelcast.PrincipalNameExtractor;
import org.springframework.session.hazelcast.config.annotation.web.http.EnableHazelcastHttpSession;

/*
    Esto es para el manejo de sesion a nivel de navegador.
    De nuevo, hay varias formas de implementar y de almacenar esta info, a mi como me da lo mismo, simplemente la almaceno
    temporalmente en memoria con Hazelcast, que es basicamente eso.

 */
@Configuration
@EnableHazelcastHttpSession
public class SpringSessionController {

    @Bean
    public HazelcastInstance hazelcastInstance() {
        MapAttributeConfig attributeConfig = new MapAttributeConfig().setName(HazelcastSessionRepository.PRINCIPAL_NAME_ATTRIBUTE).setExtractor(PrincipalNameExtractor.class.getName());
        Config config = new Config();
        config.getMapConfig("spring:session:sessions").addMapAttributeConfig(attributeConfig).addMapIndexConfig(new MapIndexConfig(HazelcastSessionRepository.PRINCIPAL_NAME_ATTRIBUTE, false));
        return Hazelcast.newHazelcastInstance(config);
    }
}
