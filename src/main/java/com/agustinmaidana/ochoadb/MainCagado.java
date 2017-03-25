package com.agustinmaidana.ochoadb;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.server.OServer;
import com.orientechnologies.orient.server.OServerMain;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainCagado {

    public static void main(String[] args) throws Exception {

        SpringApplication.run(MainCagado.class, args);

        /*
            SpringBootApplication declara que es una aplicacion hecha con spring. 
            Habilita unas cuantas cosas, entre ellas, el chequeo en todas las otras clases de componentes que tambien sean de spring.

            SpringApplication.run ejecuta la aplicacion, y la deja corriendo en caso de estarse brindando algun servicio.
            En este caso, se brindara un Web Service RESTful, por lo que la aplicacion queda ejecutandose indefinidamente, a no ser
            que explote algo, o se detenga a huevo.
            
            A todo el chorizo siguiente no le den bola, lo que hace es crear una instancia del servidor de bd y luego inicia el servicio.
            Utiliza OrientDB, es noSQL. hay varios "tipos" de gestores de bd noSQL en el mercado, las key/value, las basadas en 
            documentos, en grafos
            Esta es las ultimas dos.
            
            La servidor, la bd, esta hecha completamente en Java, por eso es embebible.
            Lo que yo hago, es levantar el servidor que me creo aca abajo, y empezar a guardar documentos.
        
            Los documentos son clases basicamente o "plantillas", tipo, seria el equivalente a las tablas en los sistemas SQL,
            Nada mas que en vez de definiar columnas, aca no precisas definir nada.
            Simplemente decis, voy a guardar un documento, del tipo "Usaurio" con estos campos(nombre, apellido, etc) y se guarda
            
            Pero no hay ningun esquema que se tenga que definir necesaria o estrictamente antes.
        
            En realidad, la nocion de noSQL viene de ahi, de que, no es SQL, no es estructurado, pero tenes la posibilidad de hacerlo si queres
            O sea, previamente, uno podria crear un archivo de texto, definiar una estructura de como seran los documentos a guardar y funka
            Pero no es necesario.
            Por eso tambien se dice que noSQL es tambien Not Only SQL.
        
            De esta manera, se puede guardar un documento con ciertos campos, y despues, guardar otro, con algunos distintos, o en diferente orden.
        
            Los SGBD noSQL NO SON ESTANDAR, es decir, no hay una sintaxis estandar, como lo es SQL. aca depende de cada proveedor.
            OrientDB por ejemplo, utiliza un dialecto identico al SQL clasico(SELECT FROM bla bla bla) o sea, lo mismo.
            Dependiendo de la bd tambien, son las apis que te proporcionan.
        
         */
        OServer server = OServerMain.create();
        server.startup(
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<orient-server>"
                + "<network>"
                + "<protocols>"
                + "<protocol name=\"binary\" implementation=\"com.orientechnologies.orient.server.network.protocol.binary.ONetworkProtocolBinary\"/>"
                + "<protocol name=\"http\" implementation=\"com.orientechnologies.orient.server.network.protocol.http.ONetworkProtocolHttpDb\"/>"
                + "</protocols>"
                + "<listeners>"
                + "<listener ip-address=\"0.0.0.0\" port-range=\"2424-2430\" protocol=\"binary\"/>"
                + "<listener ip-address=\"0.0.0.0\" port-range=\"2480-2490\" protocol=\"http\"/>"
                + "</listeners>"
                + "</network>"
                + "<users>"
                + "<user name=\"root\" password=\"root\" resources=\"*\"/>"
                + "</users>"
                + "<properties>"
                + "<entry name=\"orientdb.www.path\" value=\"C:/work/dev/orientechnologies/orientdb/releases/1.0rc1-SNAPSHOT/www/\"/>"
                + "<entry name=\"orientdb.config.file\" value=\"C:/work/dev/orientechnologies/orientdb/releases/1.0rc1-SNAPSHOT/config/orientdb-server-config.xml\"/>"
                + "<entry name=\"server.cache.staticResources\" value=\"false\"/>"
                + "<entry name=\"log.console.level\" value=\"info\"/>"
                + "<entry name=\"log.file.level\" value=\"fine\"/>"
                //The following is required to eliminate an error or warning "Error on resolving property: ORIENTDB_HOME"
                + "<entry name=\"plugin.dynamic\" value=\"false\"/>"
                + "</properties>" + "</orient-server>");
        server.activate();
        ODatabaseDocumentTx db = new ODatabaseDocumentTx("plocal:/databases/ochoaDB");
        if(!db.exists()) db.create();
        if(db.isClosed()) db.open("admin", "admin");
        if(!db.getMetadata().getSchema().existsClass("Usaurio")) db.getMetadata().getSchema().createClass("Usaurio");
            
        /*
            Busco la bd en tal lado.
            Si existe puede seguir utilizandola, y sino la creo y punto.
        */
    }
}
